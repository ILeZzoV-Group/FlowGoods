package ru.ilezzov.group.flowgoods.inventory.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.inventory.dto.filter.CommonCursorFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.service.SupplierService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SupplierController {
    private final SupplierService supplierService;

    private final WorkspaceResolver workspaceResolver;
    private final UserResolver userResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierResponseDto> getSupplier(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.supplierService.getSupplier(uuid, workspaceId)
        );
    }

    @GetMapping
    public ResponseEntity<CursorResponseDto<SupplierResponseDto>> getSuppliers(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestHeader("X-Workspace-ID") final UUID workspaceUuid,
            @Valid final CommonCursorFilterDto filter
    ) {

        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.supplierService.getSuppliers(workspaceId, filter)
        );
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDto> createSupplier(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final SupplierCreateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final SupplierResponseDto responseDto = this.supplierService.createSupplier(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid, @RequestBody @Valid final SupplierUpdateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final SupplierResponseDto responseDto = this.supplierService.updateSupplier(uuid, dto, workspaceId);
        return ResponseEntity.ok(responseDto);
    }
}
