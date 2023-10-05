package shopping.main.millions.dto.cart;

import com.sun.istack.NotNull;
import lombok.*;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.product.ProductEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {

    private Long userId;
    private Long productId;
    private List<Option> option;

    @Getter
    @Setter
    public static class Option{
        private String productSize;
        private String productColor;
        private Long productCount;
    }


    public CartProductDto convertToDto(CartProductEntity cartProductEntity) {
        return CartProductDto.builder()
                .productId(cartProductEntity.getCartProductId())
//                .productCount(cartProductEntity.getCartProductCount())
//                .productSize(cartProductEntity.getCartProductSize())
//                .productColor(cartProductEntity.getCartProductColor())
                .build();
    }

}
