package shopping.main.millions.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shopping.main.millions.entity.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    MemberEntity findByUserEmail(String email);

    MemberEntity findByTelNumber(String telNumber);

    @Modifying
    @Transactional
    @Query("UPDATE MemberEntity m SET m.status = :status WHERE m.userId = :userId")
    void updateMemberStatusByUserId(Long userId, boolean status);
}
