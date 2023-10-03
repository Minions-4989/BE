package shopping.main.millions.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    MemberEntity findByUserEmail(String email);

    MemberEntity findByTelNumber(String telNumber);
}
