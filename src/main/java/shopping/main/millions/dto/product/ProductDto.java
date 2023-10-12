package shopping.main.millions.dto.product;

import lombok.*;
import org.joda.time.DateTime;
import shopping.main.millions.dto.sales.GoodsImageDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long productId;

    private String productName;

    private Integer productPrice;

    private List<GoodsImageDto> goodsImageDtoList;

}
