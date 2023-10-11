package shopping.main.millions.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.main.millions.entity.member.MemberEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken  extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private String token;

    public RefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(MemberEntity memberEntity, String token){
        this.memberEntity = memberEntity;
        this.token = token;
    }

}

