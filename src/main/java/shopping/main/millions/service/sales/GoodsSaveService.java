package shopping.main.millions.service.sales;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.main.millions.dto.product.ProductListResponseDto;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.dto.sales.GoodsModifyDto;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.GoodsImageEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.member.MemberRepository;
import shopping.main.millions.repository.product.CategoryRepository;
import shopping.main.millions.repository.product.ProductRepository;
import shopping.main.millions.repository.sales.GoodsEditRepository;
import shopping.main.millions.repository.sales.GoodsImageRepository;
import shopping.main.millions.repository.sales.GoodsStockRepository;

import java.io.IOException;
import java.util.*;


@RequiredArgsConstructor
@Service
public class GoodsSaveService {

    private final GoodsEditRepository goodsEditRepository;
    private final AmazonS3Client amazonS3Client;
    private final GoodsImageRepository goodsImageRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsStockRepository goodsStockRepository;
    @Value("${cloud.aws.s3.goods-bucket}")
    private String bucketName;


    // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
    private String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + filename + ")입니다.");
        }
    }

    //진짜 삭제 맨
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));

    }

    /**
     * 2023-10-04
     * 상품 등록
     * 작성자 : 김대한
     */

    @Transactional
    public ResponseEntity<Map<String, String>> editItem (GoodsSaveDto goodsSaveDto, List<MultipartFile> imageFile, String userId) {
        //ㄹㅇ.. 레파지토리로 id불러오는거 진짜 대단하시네요 존경's 전혀 생각 못했었는데 ;;; 무조건 entity 타고 넘어가서 불러올생각만했지
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(goodsSaveDto.getCategoryName()).get();
        //이건 디비에 저장되어 있는걸 가져온 entity예영 그럼 이 entity에는 id값도 들어있죵 저장되어있는걸
        MemberEntity memberEntity = (memberRepository.findById(Long.valueOf(userId)).get());

        ProductEntity goodsSaveEntity = ProductEntity.builder()
                .productName(goodsSaveDto.getProductName())
                .productPrice(goodsSaveDto.getProductPrice())
                .memberEntity(memberEntity)
                .categoryEntity(categoryEntity) // 그대로 넣어주는거예영 물품 등록을 하는데 어떻게 디비에 저장되있는 값을 불러오는지가 궁금해용
                .build();

        ProductEntity productEntity = goodsEditRepository.save(goodsSaveEntity);

        List<String> goodsImgFile = new ArrayList<>();

        for (MultipartFile multipartFile : imageFile) {

            String fileName = multipartFile.getOriginalFilename();

            try {
                //이미지 객체 생성
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(multipartFile.getContentType());
                objectMetadata.setContentLength(multipartFile.getInputStream().available());
                String storedName = createFileName(fileName);
                amazonS3Client.putObject(new PutObjectRequest(bucketName, storedName, multipartFile.getInputStream(), objectMetadata));

                String accessUrl = amazonS3Client.getUrl(bucketName, storedName).toString();
                System.out.println(accessUrl);
                //이미지 저장
                goodsImageRepository.save(GoodsImageEntity.builder()
                        .productImageSave(accessUrl)
                        .productImageOriginName(fileName)
                        .productImage(storedName)
                        .productEntity(productEntity)
                        .build());

                accessUrl = amazonS3Client.getUrl(bucketName, storedName).toString();
                System.out.println(accessUrl);

            } catch (IOException e) {
                throw new RuntimeException();
            }

            goodsImgFile.add(fileName);

        }

        for (StockSaveDto stockSaveDto : goodsSaveDto.getStockOption()) {
            GoodsStockEntity goodsStockEntity =  GoodsStockEntity.builder()
                    .stockColor(stockSaveDto.getStockColor())
                    .stockQuantity(stockSaveDto.getStockQuantity())
                    .stockSize(stockSaveDto.getStockSize())
                    .productEntity(productEntity)
                    .build();
            goodsStockRepository.save(goodsStockEntity);
        }

        // 변환 후 data를 db에 저장
        Map<String, String> result = new HashMap<>();

        if (productEntity.getProductId() > 0) {
            result.put("message", "상품등록 등록 완료");
            return ResponseEntity.status(200).body(result);
        } else {
            result.put("message", "상품등록 실패");
            return ResponseEntity.status(400).body(result);
        }

    }

    /**
     * 상품 수정
     * 작성자: 김대한
     */
    //상품 데이터를 읽어오는 트랙잭션을 읽기전용으로 설정
    //why? JPA가 더티체킹(변경감지)을 수행하지 않아서 성능을 향상 시킬 수 있음
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, String>> modifyItem(GoodsModifyDto modifyDto, Long productId, String userId,
                                                          StockSaveDto stockSaveDto) {
//        // 수정같은경우 이미지가 변경될수도있고 변경이 안될수도 있으니 이부분도 체크하는것이 필요할듯 싶습니당(작업하실내용)

        Map<String, String> result = new HashMap<>();
        // 상품정보를 가져옴
        Optional<ProductEntity> updateId = goodsEditRepository.findById(productId);

        if (updateId.isPresent()) {
            ProductEntity productEntity = updateId.get();

            //product modify user_id와 로그인한 user_id 가 같은지 확인(수정 권한 확인)
            if (productEntity.getMemberEntity().getUserId().equals(Long.valueOf(userId))) {
                //상품 정보 수정
                productEntity.setProductName(modifyDto.getProductName());
                productEntity.setProductPrice(modifyDto.getProductPrice());

                //stock 수정
                List<GoodsStockEntity> modifyStock = productEntity.getGoodsStockEntityList();
                int count = 0;
                for (GoodsStockEntity goodsStockEntity : modifyStock) {
                    //재고 수량 저장
                    goodsStockEntity.setStockQuantity(stockSaveDto.getStockQuantity());
                    //재고
                    goodsStockEntity.setStockSize(stockSaveDto.getStockSize());
                    //색상
                    goodsStockEntity.setStockColor(stockSaveDto.getStockColor());
                    modifyStock.set(count, goodsStockEntity);
                    count++;
                }

                // 수정할때 사진이 있나 없나 체크
                if (modifyDto.getImageFile() != null) {
                    List<MultipartFile> multipartFiles = modifyDto.getImageFile();
                    //새로 만듬
                    List<String> newImageOrigin = new ArrayList<>();
                    int imageCount = 0;
                    List<GoodsImageEntity> goodsImageEntityList = goodsImageRepository.findByProductEntityProductId(productId);
                    for (MultipartFile newImageFile : multipartFiles) {
                        String origin = newImageFile.getOriginalFilename();
                        //기존 이미지와 새 이미지를 비교하여 삭제
                        for (GoodsImageEntity goodsImage : goodsImageEntityList) {
                            String oldOrigin = goodsImage.getProductImageOriginName();
                            //기존 이미지의 오리지널 이름이 새 이미지 리스트에 없으면 삭제
                            if (!newImageOrigin.contains(oldOrigin)) {
                                String storageName = goodsImage.getProductImageSave();
                                amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, storageName));
                            }

                        }
//                            String origin1 = modifyDto.getImageFile().get(imageCount).getOriginalFilename();
//                            String oldOrigin = goodsImageEntity.getProductImageOriginName();
//                            if (origin.equals(oldOrigin)) {
//                                imageCount++;
//                            } else {
//                                String storageName = goodsImageEntity.getProductImageSave();
//                                amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, storageName));

                    }


                    //새 이미지 업로드 및 데이터베이스 업데이트
                    for (MultipartFile newImageFile : multipartFiles) {
                        String newFileName = newImageFile.getOriginalFilename();
                        String newStoredName = createFileName(newFileName);
                        //새 이미지업로드
                        try {
                            ObjectMetadata objectMetadata = new ObjectMetadata();
                            objectMetadata.setContentType(newImageFile.getContentType());
                            objectMetadata.setContentLength(newImageFile.getInputStream().available());
                            amazonS3Client.putObject(new PutObjectRequest(bucketName, newStoredName, newImageFile.getInputStream(), objectMetadata));

                            String newAccessUrl = amazonS3Client.getUrl(bucketName, newStoredName).toString();

                            System.out.println(newAccessUrl);

                            Optional<GoodsImageEntity> findId = goodsImageRepository.findById(modifyDto.getProductId());

                            //이미지 정보 업데이트
                            if (findId.isPresent()) {
                                goodsImageRepository.save(GoodsImageEntity.builder()
                                        .productImageSave(newAccessUrl)
                                        .productImageOriginName(newFileName)
                                        .productImage(newStoredName)
                                        .build());
                            }
                        } catch (IOException e) {
                            throw new RuntimeException("이미지 업로드 오류");
                        }
                    }

                }

                //이미지 수정
                List<GoodsImageEntity> imageEntities = productEntity.getGoodsImageEntityList();
                for (GoodsImageEntity imageEntity : imageEntities) {

                    imageEntity = GoodsImageEntity.builder()
                            //이미지 오리지널 네임 저장
                            .productImage(imageEntity.getProductImage())
                            //s3 주소 저장
                            .productImageOriginName(imageEntity.getProductImageOriginName())
                            //이미지 s3에 저장될 네임 저장
                            .productImageSave(imageEntity.getProductImageSave())
                            .build();
                }

                goodsEditRepository.save(productEntity);

//
            } else {
                result.put("message", "상품 수정 권한이 없습니다.");
                return ResponseEntity.status(403).body(result);
            }
        } else {
            result.put("message", "상품을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(result);
        }
        result.put("message", "상품 수정 완료");
        return ResponseEntity.status(200).body(result);

    }

    //판매자 상품 전체 조회
    public ResponseEntity<?> findGoods(String userId) {
       return ResponseEntity.status(200).body(getProductListResDto(userId));
    }

    public List<ProductListResponseDto> getProductListResDto(String userId){
        List<ProductEntity> productEntityList = productRepository.findByMemberEntity_UserId(Long.valueOf(userId));
        List<ProductListResponseDto> productListResponseDtoList = new ArrayList<>();
        for(ProductEntity productEntity : productEntityList){
            List<StockSaveDto> stockSaveDtoList = new ArrayList<StockSaveDto>();
            for(GoodsStockEntity goodsStockEntity : productEntity.getGoodsStockEntityList()){
                StockSaveDto stockSaveDto = StockSaveDto.builder()
                        .stockColor(goodsStockEntity.getStockColor())
                        .stockSize(goodsStockEntity.getStockSize())
                        .stockQuantity(goodsStockEntity.getStockQuantity())
                        .build();
                stockSaveDtoList.add(stockSaveDto);
            }
            List<GoodsImageDto> goodsImageDtoList = new ArrayList<GoodsImageDto>();
            for(GoodsImageEntity goodsImageEntity: productEntity.getGoodsImageEntityList()) {
                GoodsImageDto goodsImageDto = GoodsImageDto.builder()
                        .imageId(goodsImageEntity.getImageId())
                        .productId(goodsImageEntity.getProductEntity().getProductId())
                        .productImageSave(goodsImageEntity.getProductImageSave())
                        .productImageOriginName(goodsImageEntity.getProductImageOriginName())
                        .productImage(goodsImageEntity.getProductImage())
                        .build();
                goodsImageDtoList.add(goodsImageDto);
            }

            ProductListResponseDto productListResponseDto = ProductListResponseDto.builder()
                    .productName(productEntity.getProductName())
                    .categoryName(productEntity.getProductName())
                    .productPrice(productEntity.getProductPrice())
                    .stockOption(stockSaveDtoList)
                    .imageFileList(goodsImageDtoList)
                    .build();
            productListResponseDtoList.add(productListResponseDto);
        }
        return productListResponseDtoList;
    }

}












