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
import ru.ilezzov.group.flowgoods.inventory.dto.filter.ProductFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.service.ProductService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final ProductService productService;

    private final UserResolver userResolver;
    private final WorkspaceResolver workspaceResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> getProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.productService.getProduct(uuid, workspaceId)
        );
    }

    @GetMapping
    public ResponseEntity<CursorResponseDto<ProductResponseDto>> getProducts(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestHeader("X-Workspace-ID") final UUID workspaceUuid,
            @Valid ProductFilterDto filter
    ) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.productService.getProducts(workspaceId, filter)
        );
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final ProductCreateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final ProductResponseDto responseDto = this.productService.createProduct(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ProductResponseDto> updateProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid, @RequestBody @Valid final ProductUpdateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final ProductResponseDto responseDto = this.productService.updateProduct(uuid, dto, workspaceId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{uuid}/publish")
    public ResponseEntity<ProductResponseDto> publishProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final ProductResponseDto responseDto = this.productService.publishProduct(uuid, workspaceId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{uuid}/suspend")
    public ResponseEntity<ProductResponseDto> suspendProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final ProductResponseDto responseDto = this.productService.suspendProduct(uuid, workspaceId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{uuid}/archive")
    public ResponseEntity<ProductResponseDto> archiveProduct(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final ProductResponseDto responseDto = this.productService.archiveProduct(uuid, workspaceId);
        return ResponseEntity.ok(responseDto);
    }
}
