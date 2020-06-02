package sa.com.me.user.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sa.com.me.core.dto.AuthorityDto;
import sa.com.me.core.dto.UserDto;
import sa.com.me.core.exception.BadRequestException;
import sa.com.me.core.exception.NotAuthorizedException;
import sa.com.me.core.exception.NotFoundException;
import sa.com.me.core.util.Action;
import sa.com.me.core.util.Resource;
import sa.com.me.user.mapper.UserMapper;
import sa.com.me.user.model.Authority;
import sa.com.me.user.model.User;
import sa.com.me.user.service.AuthorityService;
import sa.com.me.user.service.UserService;
import sa.com.me.user.util.JwtTokenProvider;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@Api(value = "User Service", description = "Handle User specific requests")
class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping("/api/v1/private/users/{email}")
    @ApiOperation(value = "Retrieve User info by email", response = UserDto.class)
    public UserDto getUsers(@PathVariable String email) {

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return userMapper.userToDto(user.get());
        } else {
            throw new NotFoundException("User not found", "404", email);
        }
    }

    @GetMapping("/internal/api/v1/private/users/{email}")
    public User getInternalUsers(@PathVariable String email) {

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User not found", "404", email);
        }
    }

    @PostMapping(value = "/api/v1/private/users")
    @ApiOperation(value = "Add User", response = UserDto.class)
    public UserDto addUser(@RequestBody @Valid UserDto userDto, @RequestHeader("Authorization") String token) {
        String role = tokenProvider.getRolesFromToken(token.replace("Bearer ", "")).get(0);
        AuthorityDto authority = new AuthorityDto(role, Resource.ARTICLE.getValue(), Action.POST.getValue());

        authorityService.validateAuhtority(authority.getAction(), authority.getResource(),
                userService.findRoleByName(authority.getRoleName()).getId());
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new BadRequestException("Improper request", "400", "password");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userService.addUser(userMapper.dtoToUser(userDto));
        return userMapper.userToDto(user);
    }

    @PostMapping(value = "/internal/api/v1/private/authorities/validate")
    public String validateAuthorities(@RequestBody AuthorityDto authorityDto) {

        Optional<Authority> authorityRetrieved = authorityService.findAuhtority(authorityDto.getAction(),
                authorityDto.getResource(), userService.findRoleByName(authorityDto.getRoleName()).getId());
        if (authorityRetrieved.isPresent()) {
            return "true";
        }
        return null;
    }

}