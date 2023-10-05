package shopping.main.millions.dto.cart;

import com.sun.istack.NotNull;
import lombok.*;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.product.ProductEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {

    private Long cartProductId;
    private Long cartProductCount;

    public CartProductDto convertToDto(CartProductEntity cartProductEntity) {
        return CartProductDto.builder()
                .cartProductId(cartProductEntity.getCartProductId())
                .cartProductCount(cartProductEntity.getCartProductCount())
                .build();
    }
}
