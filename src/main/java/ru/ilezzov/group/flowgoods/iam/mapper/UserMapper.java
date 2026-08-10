package ru.ilezzov.group.flowgoods.iam.mapper;

import org.mapstruct.*;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserUpdateDto;
import ru.ilezzov.group.flowgoods.iam.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {
    @Mapping(source = "profile", target = ".")
    UserResponseDto toDto(final User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "hash", source = "encodedPassword")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "profile.username", source = "username")
    User toEntity(final UserCreateDto dto, final String encodedPassword, final String email, final String username);

    @Mapping(target = "profile.username", source = "username")
    @Mapping(target = "profile.firstName", source = "dto.firstName")
    @Mapping(target = "profile.secondName", source = "dto.secondName")
    @Mapping(target = "profile.avatarUrl", source = "dto.avatarUrl")
    void updateEntity(final UserUpdateDto dto, @MappingTarget final User user, final String username);
}
