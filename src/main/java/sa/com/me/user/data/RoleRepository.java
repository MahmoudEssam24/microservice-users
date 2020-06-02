package sa.com.me.user.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sa.com.me.user.model.Role;
import sa.com.me.user.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(RoleName roleName);
}
