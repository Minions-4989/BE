package shopping.main.millions.entity.order;

import lombok.*;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "user_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId; //구매상품 번호

    @Column(name = "product_name")
    private String productName; //상품 이름

    @Column(name = "cart_product_count")
    private Long cartProductCount; //상품 수량

    @Column(name = "cart_product_size")
    private String cartProductSize; // 상품 사이즈

    @Column(name = "cart_product_color")
    private String cartProductColor; // 상품 색상

    @Column(name = "product_price")
    private Long productPrice; // 상품 가격

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}
