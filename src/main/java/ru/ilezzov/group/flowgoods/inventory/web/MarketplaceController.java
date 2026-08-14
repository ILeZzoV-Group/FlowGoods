package ru.ilezzov.group.flowgoods.inventory.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.service.MarketplaceService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/marketplaces")
@RequiredArgsConstructor
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
