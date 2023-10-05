package shopping.main.millions.entity.cart;

import lombok.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product")
@Builder
public class CartProductEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long cartProductId;

    @Column(name = "cart_product_count")
    private Long cartProductCount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    public CartProductEntity convertToEntity(CartProductDto cartProductDto) {
        //CartProductDto 내에 있는 List<Option>을 하나씩 정의해주는 과정
        List<CartProductDto.Option> options = cartProductDto.getOption();
        String productSize = null;
        Long productCount = null;
        String productColor = null;
        for (CartProductDto.Option option : options) {
            productSize = option.getProductSize();
            productCount = option.getProductCount();
            productColor = option.getProductColor();
        }

        return CartProductEntity.builder()
                .cartProductId(cartProductDto.getProductId())
                .cartProductCount(productCount)
                .cartProductSize(productSize)
                .cartProductColor(productColor)
                .build();
    }
}
