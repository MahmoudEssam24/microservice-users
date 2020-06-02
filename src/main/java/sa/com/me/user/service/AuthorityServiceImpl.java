package sa.com.me.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa.com.me.core.exception.NotAuthorizedException;
import sa.com.me.user.data.AuthorityRepository;
import sa.com.me.user.model.Authority;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public void validateAuhtority(String action, String resource, Long roleId) {
        Optional<Authority> authority = authorityRepository.findByActionAndResourceAndRoleId(action, resource, roleId);
        if (!authority.isPresent()) {
            throw new NotAuthorizedException("You are not authorized to perform this action", "401", "role");
        }
    }

    public Optional<Authority> findAuhtority(String action, String resource, Long roleId) {
        return authorityRepository.findByActionAndResourceAndRoleId(action, resource, roleId);
    }
}