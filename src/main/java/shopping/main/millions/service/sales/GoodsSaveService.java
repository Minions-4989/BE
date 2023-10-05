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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
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
        }catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"잘못된 형식의 파일("+filename+")입니다.");
        }
    }
    //진짜 삭제 맨
    public void deleteFile(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName,fileName));
    }
    /**
     * 2023-10-04
     * 상품 등록
     * 작성자 : 김대한
     */
    public ResponseEntity<Map<String,String>> editItem(GoodsSaveDto goodsSaveDto) {
        //여기서 한번 일단 해보시죵?
        // 해보시고 봐드릴게영^_^
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
                .productId(goodsSaveDto.getProductId())
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
}
