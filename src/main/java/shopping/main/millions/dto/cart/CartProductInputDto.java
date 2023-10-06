package shopping.main.millions.dto.cart;

import lombok.*;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.ProductEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductInputDto {
    private String productSize;
    private String productColor;
    private Long productCount;
    private MemberEntity memberEntity;
    private ProductEntity productEntity;
}
