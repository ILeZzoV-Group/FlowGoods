package ru.ilezzov.group.flowgoods.tenant.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceCreateDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.service.WorkspaceService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @GetMapping("/{uuid}")
    public ResponseEntity<WorkspaceResponseDto> getWorkspace(@PathVariable final UUID uuid) {
        return ResponseEntity.ok(
                this.workspaceService.getWorkspaceByUuid(uuid)
        );
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid WorkspaceCreateDto createDto) {
        final WorkspaceResponseDto responseDto = this.workspaceService.createWorkspace(createDto, principal.uuid());
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}
