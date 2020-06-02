package sa.com.me.user.service;

import java.util.Optional;

import sa.com.me.user.model.Role;
import sa.com.me.user.model.User;

public interface UserService {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByEmail(String email);

    User addUser(User user);

    Role findRoleByName(String role);

}