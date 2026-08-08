package ru.ilezzov.group.flowgoods.inventory.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.service.SupplierService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    private final WorkspaceResolver workspaceResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierResponseDto> getSupplier(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        return ResponseEntity.ok(
                this.supplierService.getSupplier(uuid, workspaceId)
        );
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDto> createCategory(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final SupplierCreateDto dto) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        final SupplierResponseDto responseDto = this.supplierService.createSupplier(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}
