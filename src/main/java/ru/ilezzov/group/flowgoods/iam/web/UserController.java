package ru.ilezzov.group.flowgoods.iam.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.iam.dto.UserCreateDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserResponseDto;
import ru.ilezzov.group.flowgoods.iam.service.UserService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponseDto> getUserByUuid(@PathVariable final UUID uuid) {
        final UserResponseDto userDto = this.userService.getUserByUuid(uuid);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid final UserCreateDto dto) {
        final UserResponseDto createdUser = userService.registerUser(dto);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(createdUser.uuid())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }
}
