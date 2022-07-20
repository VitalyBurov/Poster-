package by.burov.user.service.api;

import by.burov.user.core.dto.CreateUserDto;
import by.burov.user.core.dto.ReadUserDto;
import by.burov.user.core.dto.RegistrationUserDto;
import by.burov.user.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    ReadUserDto toReadDto(User entity);

    @Mapping(target = "authorities", ignore = true)
    User toCreateEntity(CreateUserDto dto);

    @Mapping(target = "authorities", ignore = true)
    User toReadEntity(ReadUserDto dto);

    User toRegistrationEntity(RegistrationUserDto dto);



}
    //implement custom mapping from UserRole enum to Authority class
    // and apply it for all of the methods

