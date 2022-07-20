package by.burov.user.service;

import by.burov.user.core.dto.ReadUserDto;
import by.burov.user.core.enums.UserRole;
import by.burov.user.repository.entity.Authority;
import by.burov.user.repository.entity.User;
import by.burov.user.service.api.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserHolder {

    private UserMapper userMapper;


    @Autowired
    public UserHolder(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ReadUserDto getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReadUserDto readUserDto = userMapper.toReadDto(user);
        Set<UserRole> roles = user.getRoles().stream().map(Authority::getRoleName).collect(Collectors.toSet());
        readUserDto.setRoles(roles);
        return readUserDto;
    }

}
