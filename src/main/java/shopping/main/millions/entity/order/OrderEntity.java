package shopping.main.millions.entity.order;

import lombok.*;
import shopping.main.millions.entity.product.ProductEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "order_product") // 아 근데 order 못써용 깜빡했넹ㅎㅎ why? mysql order라는게 예약어 일껄용?들어본거같아요
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
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
    private Integer productPrice; // 상품 가격

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}
