package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.exception.password.PasswordDoNotMatchException;
import ru.ilezzov.group.flowgoods.common.exception.user.UserNotFoundException;
import ru.ilezzov.group.flowgoods.iam.dto.AuthResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserLoginDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.entity.SecurityUser;
import ru.ilezzov.group.flowgoods.iam.jwt.JwtProperties;
import ru.ilezzov.group.flowgoods.iam.jwt.JwtService;
import ru.ilezzov.group.flowgoods.iam.mapper.UserMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Transactional(readOnly = true)
    public AuthResponseDto authenticate(final UserLoginDto dto) {
        final String email = dto.email().toLowerCase().trim();

        try {
            final Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            dto.password()
                    )
            );

            final SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

            if (securityUser == null) {
                throw new UserNotFoundException(email);
            }

            final UserResponseDto userResponseDto = this.userMapper.toDto(
                    securityUser.user()
            );
            final String jwtToken = this.jwtService.generate(userResponseDto.uuid());

            return new AuthResponseDto(
                    jwtToken,
                    jwtProperties.type(),
                    jwtProperties.expiration(),
                    userResponseDto
            );
        } catch (final BadCredentialsException e) {
            throw new PasswordDoNotMatchException();
        }
    }
}
