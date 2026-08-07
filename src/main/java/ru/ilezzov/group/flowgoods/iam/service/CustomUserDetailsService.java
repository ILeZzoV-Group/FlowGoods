package ru.ilezzov.group.flowgoods.iam.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.user.UserNotFoundException;
import ru.ilezzov.group.flowgoods.iam.entity.SecurityUser;
import ru.ilezzov.group.flowgoods.iam.mapper.UserResolver;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserResolver userResolver;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UserNotFoundException {
        return new SecurityUser(
                this.userResolver.resolveUserByEmail(email)
        );
    }
}
