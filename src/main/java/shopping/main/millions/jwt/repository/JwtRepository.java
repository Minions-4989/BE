package shopping.main.millions.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import shopping.main.millions.jwt.entity.RefreshToken;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.memberEntity.userId = :userId")
    void deleteByMemberEntity_UserId(Long userId);
}
