package ru.ilezzov.group.flowgoods.inventory.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
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

    @GetMapping("/{uuid}")
    public ResponseEntity<MarketplaceResponseDto> getMarketplace(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        return ResponseEntity.ok(
                this.marketplaceService.getMarketplace(uuid, workspaceId)
        );
    }

    @PostMapping
    public ResponseEntity<MarketplaceResponseDto> createCategory(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final MarketplaceCreateDto dto) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        final MarketplaceResponseDto responseDto = this.marketplaceService.createMarketplace(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}
