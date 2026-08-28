package ru.ilezzov.group.flowgoods.iam.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.iam.dto.AuthResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserUpdateDto;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.iam.service.UserService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDto> getUserByUuid(@PathVariable final UUID uuid) {
        return ResponseEntity.ok(
                this.userService.getUserByUuid(uuid)
        );
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody @Valid final UserCreateDto dto) {
        final AuthResponseDto authResponseDto = userService.registerUser(dto);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(authResponseDto.user().uuid())
                .toUri();

        return ResponseEntity.created(location).body(authResponseDto);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@AuthenticationPrincipal JwtPrincipal jwtPrincipal, @RequestBody@Valid final UserUpdateDto dto) {
        return ResponseEntity.ok(
                this.userService.updateUser(dto, jwtPrincipal.uuid())
        );
    }
}
