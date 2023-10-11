package shopping.main.millions.dto.sales;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;
import shopping.main.millions.entity.category.CategoryEntity;
import shopping.main.millions.entity.product.GoodsStockEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsSaveDto {
//근데 이건 물품등록인가용?

    private Long productId; // 품목 Id

    private String productName; // 품목 이름

    private CategoryEntity categoryName; // 품목 종류

    private Integer productPrice; // 품목 가격

    private DateTime productDate;// 품목 등록 날짜

    private List<StockSaveDto> stockOption;

    private List<MultipartFile> imageFile;





//어 카테고리도 필요하네 네 처음에 api명세서 안보고 이것만 필요하겠지~ 하다가 명세서보니 생각보다 많아서 ㅋㅋㅋㅋㅋ 다시 하는중입니다 오키 알겠습니다
//그춍    잠시만요 저쪽 카테고리 엔티티 만들었을건데 물어봐야겠네요


}
