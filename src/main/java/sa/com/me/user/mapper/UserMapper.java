package sa.com.me.user.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import sa.com.me.core.dto.UserDto;
import sa.com.me.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User dtoToUser(UserDto userDto);

	UserDto userToDto(User user);
	
	@IterableMapping(elementTargetType=UserDto.class)
    List<UserDto> usersToDto(List<User> users);
}