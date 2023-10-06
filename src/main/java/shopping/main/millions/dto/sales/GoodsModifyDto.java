package shopping.main.millions.dto.sales;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shopping.main.millions.entity.category.CategoryEntity;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsModifyDto {

    private Long productId; // 품목 Id

    private String productName; // 품목 이름

    private CategoryEntity categoryName; // 품목 종류

    private Integer productPrice; // 품목 가격

    private List<StockSaveDto> stockOption; //품목 옵션

    private List<MultipartFile> imageFile;//품목 이미지
}
