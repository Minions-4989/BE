package shopping.main.millions.entity.member;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address_zipcode", nullable = false)
    private String addressZipcode;

    @Column(nullable = false)
    private String address;

    @Column(name = "address_detail" , nullable = false)
    private String addressDetail;

    @OneToOne(mappedBy = "addressEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }
}
