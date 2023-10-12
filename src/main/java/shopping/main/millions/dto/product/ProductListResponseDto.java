package shopping.main.millions.dto.product;

import lombok.*;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.dto.sales.StockSaveDto;

import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponseDto {

    private String productName; //물건 이름

    private String categoryName;//카테고리 이름

    private Integer productPrice;//물건 가격

    private List<StockSaveDto> stockOption;//물건 옵션 (재고 수량,사이즈,칼라)

    private List<GoodsImageDto> imageFileList;//물건 사진(s3주소에 저장 , 이미지 오리지널 네임 저장, 이미지 s3에 저장될 네임)

}
