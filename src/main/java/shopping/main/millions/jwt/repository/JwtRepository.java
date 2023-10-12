package shopping.main.millions.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.jwt.entity.RefreshToken;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberEntity_UserId(Long id);

}
