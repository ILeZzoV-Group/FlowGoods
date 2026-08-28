package ru.ilezzov.group.flowgoods.tenant.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceCreateDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceUpdateDto;
import ru.ilezzov.group.flowgoods.tenant.service.WorkspaceService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserResolver userResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<WorkspaceResponseDto> getWorkspace(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());

        return ResponseEntity.ok(
                this.workspaceService.getWorkspaceByUuidAndOwnerId(uuid, ownerId)
        );
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid WorkspaceCreateDto createDto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());

        final WorkspaceResponseDto responseDto = this.workspaceService.createWorkspace(createDto, ownerId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<WorkspaceResponseDto> updateWorkspace(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable final UUID uuid, @RequestBody @Valid WorkspaceUpdateDto updateDto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());

        final WorkspaceResponseDto responseDto = this.workspaceService.updateWorkspace(updateDto, uuid, ownerId);
        return ResponseEntity.ok(responseDto);
    }
}
