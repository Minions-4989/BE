package shopping.main.millions.dto.cart;

import com.sun.istack.NotNull;
import lombok.*;
import shopping.main.millions.dto.sales.GoodsImageDto;
import shopping.main.millions.entity.product.GoodsImageEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {

    private Long userId;
    private Long productId;
    private Long cartProductId;
    private Long cartProductCount;
    private String cartProductSize;
    private String cartProductColor;
    private Integer productPrice;
    private String productName;
    private List<GoodsImageEntity> productImage;

}