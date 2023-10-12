package shopping.main.millions.dto.sales;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsModifyDto {

    private Long productId; // 품목 Id

    private String productName; // 품목 이름

    private String categoryName; // 품목 종류

    private Integer productPrice; // 품목 가격

    private List<StockSaveDto> stockOption; //품목 옵션

}
