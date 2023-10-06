package shopping.main.millions.entity.cart;

import lombok.*;
import shopping.main.millions.dto.cart.CartProductDto;
import shopping.main.millions.dto.cart.CartProductInputDto;
import shopping.main.millions.dto.cart.OptionDto;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
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

    @Column(name = "cart_product_size")
    private String cartProductSize;

    @Column(name = "cart_product_color")
    private String cartProductColor;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;


    public CartProductEntity convertToEntity(CartProductInputDto cartProductInputDto) {

        return CartProductEntity.builder()
                .cartProductCount(cartProductInputDto.getProductCount())
                .cartProductSize(cartProductInputDto.getProductSize())
                .cartProductColor(cartProductInputDto.getProductColor())
                .memberEntity(cartProductInputDto.getMemberEntity())
                .productEntity(cartProductInputDto.getProductEntity())
                .build();
    }
}
