package sa.com.me.user.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa.com.me.core.service.BaseService;
import sa.com.me.user.data.RoleRepository;
import sa.com.me.user.data.UserRepository;
import sa.com.me.user.model.Role;
import sa.com.me.user.model.RoleName;
import sa.com.me.user.model.User;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        if (user.getRoles() == null || user.getRoles().size() == 0) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_CONSUMER);
            user.setRoles(Collections.singleton(userRole));
        }
        return userRepository.save(user);
    }

    @Override
    public Role findRoleByName(String role) {
        return roleRepository.findByName(RoleName.valueOf(role));
    }

}