package shopping.main.millions.entity.cart;

import lombok.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
        return CartProductEntity.builder()
                .cartProductId(cartProductDto.getCartProductId())
                .cartProductCount(cartProductDto.getCartProductCount())
                .build();
    }
}
