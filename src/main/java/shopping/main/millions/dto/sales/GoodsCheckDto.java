package shopping.main.millions.dto.sales;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsCheckDto {

    private Long productId;

    private String productName; // 품목 이름

    private CategoryEntity categoryName; // 품목 종류

    private Integer productPrice; // 품목 가격

    private StockSaveDto stockSaveDto; // 재고

    private List<MultipartFile> imageFile;//품목 이미지



}
