package ru.ilezzov.group.flowgoods.iam.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilezzov.group.flowgoods.iam.dto.AuthResponseDto;
import ru.ilezzov.group.flowgoods.iam.dto.UserLoginDto;
import ru.ilezzov.group.flowgoods.iam.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid final UserLoginDto dto) {
        return ResponseEntity.ok(
                this.authService.authenticate(dto)
        );
    }
}
