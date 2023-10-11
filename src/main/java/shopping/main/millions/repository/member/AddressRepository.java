package shopping.main.millions.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.main.millions.entity.member.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
