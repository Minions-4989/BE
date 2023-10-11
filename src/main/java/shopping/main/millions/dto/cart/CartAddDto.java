package shopping.main.millions.dto.cart;

import lombok.*;
import shopping.main.millions.entity.cart.CartProductEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartAddDto {

    private Long userId;
    private Long productId;
    private List<OptionDto> option;

}
