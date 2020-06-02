package sa.com.me.user.data;

import sa.com.me.user.model.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);

}