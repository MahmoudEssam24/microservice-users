package sa.com.me.user.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sa.com.me.user.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Optional<Authority> findByActionAndResourceAndRoleId(String action, String resource, Long roleId);
}
