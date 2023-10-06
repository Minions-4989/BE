package shopping.main.millions.service.sales;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.dto.sales.GoodsModifyDto;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
import shopping.main.millions.entity.product.GoodsImageEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.sales.GoodsEditRepository;
import shopping.main.millions.repository.sales.GoodsImageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Service
public class GoodsSaveService {

    private final GoodsEditRepository goodsEditRepository;
    private final AmazonS3Client amazonS3Client;
    private final GoodsImageRepository goodsImageRepository;

    @Value("${cloud.aws.s3.goods-bucket}")
    private String bucketName;

// 왜 키보드가 멈추셨죵?
// 보고있는데 봐도 모르것어요
    //ㅠㅠ 누구 한놈좀 도와주고 보죵
    // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
    private String getFileExtension(String filename){
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
    public ResponseEntity<Map<String, String>> editItem(GoodsSaveDto goodsSaveDto, List<MultipartFile> imageFile) {

        List<String> goodsImgFile = new ArrayList<>();
        for (MultipartFile multipartFile : goodsSaveDto.getImageFile()) {

            String fileName = createFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            goodsImgFile.add(fileName);
        }

        List<GoodsStockEntity> goodsStockEntityList = new ArrayList<>();
        for (StockSaveDto stockSaveDto : goodsSaveDto.getStockOption()) {
            GoodsStockEntity goodsStockEntity =  GoodsStockEntity.builder()
                    .stockColor(stockSaveDto.getStockColor())
                    .stockQuantity(stockSaveDto.getStockQuantity())
                    .stockSize(stockSaveDto.getStockSize())
                    .build();
            goodsStockEntityList.add(goodsStockEntity);
        }

        //Client에 값을 받은 data를 entity로 변환
        ProductEntity goodsSaveEntity = ProductEntity.builder()
                .productName(goodsSaveDto.getProductName())
                .productPrice(goodsSaveDto.getProductPrice())
                .productDate(goodsSaveDto.getProductDate())
                .goodsStockEntityList(goodsStockEntityList)
                .build();

        Long productId = goodsEditRepository.save(goodsSaveEntity).getProductId();

        // 변환 후 data를 db에 저장
        Map<String, String> result = new HashMap<>();

        if(productId > 0){
            result.put("message","상품등록 등록 완료");
            return ResponseEntity.status(200).body(result);
        }else {
            result.put("message","상품등록 실패");
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
    public ResponseEntity<Map<String, String>> modifyItem(GoodsModifyDto modifyDto, Long productId, Long
            UserId, StockSaveDto stockSaveDto, GoodsImageDto goodsImageDto) {

        Map<String, String> map = new HashMap<>();

        Optional<ProductEntity> updateId = goodsEditRepository.findById(productId);

        if (updateId.isPresent()) {
            ProductEntity productEntity = updateId.get();
            //product modify
            if (productEntity.getProductId().equals(UserId)) {
                productEntity.setCategoryEntity(modifyDto.getCategoryName());
                productEntity.setProductName(modifyDto.getProductName());
                productEntity.setProductPrice(modifyDto.getProductPrice());
                //stock option modify
                List<GoodsStockEntity> modifyStock = productEntity.getGoodsStockEntityList();
                for (GoodsStockEntity goodsStockEntity : modifyStock) {
                    goodsStockEntity.setStockColor(stockSaveDto.getStockColor());
                    goodsStockEntity.setStockQuantity(stockSaveDto.getStockQuantity());
                    goodsStockEntity.setStockSize(stockSaveDto.getStockSize());
                }
                List<GoodsImageEntity> imageEntities = productEntity.getGoodsImageEntityList();
                for (GoodsImageEntity imageEntity : imageEntities) {
                    //이미지 오리지널 네임 저장
                    imageEntity.setProductImageOriginName(goodsImageDto.getProductImageOriginName());
                    //s3 주소 저장
                    imageEntity.setProductImage(goodsImageDto.getProductImage());
                    //이미지 s3에 저장될 네임 저장
                    imageEntity.setProductImageSave(goodsImageDto.getProductImageSave());
                }

                goodsEditRepository.save(productEntity);

                map.put("message", "상품 수정 완료");
                return ResponseEntity.status(200).body(map);
            } else {
                map.put("message", "상품 수정 권한이 없습니다.");
                return ResponseEntity.status(403).body(map);
            }
        } else {
            map.put("message", "상품을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(map);
        }
    }
}

