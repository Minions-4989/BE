package shopping.main.millions.entity.cart;

import lombok.*;
import shopping.main.millions.entity.member.MemberEntity;
import shopping.main.millions.entity.order.UserOrderEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToMany(mappedBy = "cartEntity")
    private List<CartProductEntity> cartProductEntityList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "cartEntity")
    private List<UserOrderEntity> userOrderEntity;
}
