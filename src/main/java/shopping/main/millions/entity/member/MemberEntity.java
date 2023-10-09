package shopping.main.millions.entity.member;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import shopping.main.millions.entity.cart.CartProductEntity;
import shopping.main.millions.entity.product.ProductEntity;
import shopping.main.millions.service.cart.CartService;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_email" , nullable = false , unique = true , length = 50)
    private String userEmail;
    @Column(name = "user_password", nullable = false, length = 21)
    private String userPassword;
    @Column(name = "address_zipcode", nullable = false, length = 20)
    private String addressZipcode;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(name = "user_name", length = 30)
    private String userName;
    @Column(name = "address_detail", nullable = false, length = 100)
    private String addressDetail;
    @Column(name = "tel_number", nullable = false, unique = true, length = 13)
    private String telNumber;
    @Column(nullable = false, length = 10)
    private String gender;
    @Column(name = "profile_image")
    private String profileImage;
    //사용자 활성화 상태
    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "memberEntity" , cascade = CascadeType.REMOVE , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<CartProductEntity> cartProductEntityList;

    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ProductEntity> productEntityList;

}