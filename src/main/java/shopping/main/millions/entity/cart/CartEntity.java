package shopping.main.millions.entity.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.main.millions.entity.member.MemberEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntityList;




}
