package shopping.main.millions.entity.order;

import lombok.*;
import shopping.main.millions.entity.cart.CartEntity;
import shopping.main.millions.entity.cart.CartProductEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_order")
public class UserOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_order_id")
    private Long userOrderId;

    @Column(name = "tell_number")
    private String tellNumber;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "address_zipcode")
    private String addressZipcode;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "user_email")
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @OneToMany(mappedBy = "userOrderEntity" , cascade = CascadeType.REMOVE , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<OrderPaymentEntity> orderPaymentEntityList;
}
