package shopping.main.millions.entity.member;

import lombok.*;

import javax.persistence.*;

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
    @Column(name = "user_email" , nullable = false , unique = true)
    private String userEmail;
    @Column(name = "user_password" , nullable = false)
    private String userPassword;
    @Column(name = "address_zipcode", nullable = false)
    private String addressZipcode;
    @Column(nullable = false)
    private String address;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "address_detail" , nullable = false)
    private String addressDetail;
    @Column(name = "tel_number" , nullable = false, unique = true)
    private String telNumber;
    @Column(nullable = false)
    private String gender;
    @Column(name = "profile_image")
    private String profileImage;

}