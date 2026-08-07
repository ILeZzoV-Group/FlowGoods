package ru.ilezzov.group.flowgoods.iam.dto;

public record AuthResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserResponseDto user
){
}
