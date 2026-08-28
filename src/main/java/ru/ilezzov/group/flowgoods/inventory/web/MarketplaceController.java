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
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.service.MarketplaceService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/marketplaces")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class MarketplaceController {
    private final MarketplaceService marketplaceService;

    private final WorkspaceResolver workspaceResolver;
    private final UserResolver userResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<MarketplaceResponseDto> getMarketplace(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.marketplaceService.getMarketplace(uuid, workspaceId)
        );
    }

    @GetMapping
    public ResponseEntity<CursorResponseDto<MarketplaceResponseDto>> getMarketplaces(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestHeader("X-Workspace-ID") final UUID workspaceUuid,
            @Valid final MarketplaceFilterDto filter,
            @RequestParam(required = false) final String cursor,
            @RequestParam(defaultValue = "20") final int limit) {

        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.marketplaceService.getMarketplaces(workspaceId, filter, cursor, limit)
        );
    }

    @PostMapping
    public ResponseEntity<MarketplaceResponseDto> createCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final MarketplaceCreateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final MarketplaceResponseDto responseDto = this.marketplaceService.createMarketplace(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<MarketplaceResponseDto> updateMarketplace(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid, @RequestBody @Valid final MarketplaceUpdateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final MarketplaceResponseDto responseDto = this.marketplaceService.updateMarketplace(uuid, dto, workspaceId);
        return ResponseEntity.ok(responseDto);
    }
}
