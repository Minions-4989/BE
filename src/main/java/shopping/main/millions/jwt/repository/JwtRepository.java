package shopping.main.millions.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.jwt.entity.RefreshToken;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {

}
