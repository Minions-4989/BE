package shopping.main.millions.service.sales;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.main.millions.dto.sales.GoodsSaveDto;
import shopping.main.millions.dto.sales.StockSaveDto;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.repository.sales.GoodsEditRepository;
import shopping.main.millions.repository.sales.GoodsImageRepository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service

public class GoodsSaveService {

    private final GoodsEditRepository goodsEditRepository;
    private final AmazonS3Client amazonS3Client;
    private final GoodsImageRepository goodsImageRepository;

    @Value("${cloud.aws.s3.goods-bucket}")
    private String bucketName;

    /**
     * 2023-10-04
     * 상품 등록
     * 작성자 : 김대한
     */
    public ResponseEntity<Map<String,String>> editItem(GoodsSaveDto goodsSaveDto) {
        //여기서 한번 일단 해보시죵?
        // 해보시고 봐드릴게영^_^
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
