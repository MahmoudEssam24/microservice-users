package sa.com.me.user.service;

import java.util.Optional;
import sa.com.me.user.model.Authority;

public interface AuthorityService {
    public void validateAuhtority(String action, String resource, Long roleId);
    public Optional<Authority> findAuhtority(String action, String resource, Long roleId);
}