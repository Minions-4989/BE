package shopping.main.millions.dto.cart;

import lombok.*;
import shopping.main.millions.entity.cart.CartProductEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {

    private Long userId;
    private Long productId;
    private List<OptionDto> option;



    public CartProductDto convertToDto(CartProductEntity cartProductEntity) {
        return CartProductDto.builder()
                .productId(cartProductEntity.getCartProductId())
                .build();
    }

}
