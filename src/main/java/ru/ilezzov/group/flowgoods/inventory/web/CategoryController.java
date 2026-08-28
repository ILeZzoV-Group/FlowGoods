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
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.service.CategoryService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CategoryController {
    private final CategoryService categoryService;

    private final WorkspaceResolver workspaceResolver;
    private final UserResolver userResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryResponseDto> getCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.categoryService.getCategory(uuid, workspaceId)
        );
    }

    @GetMapping
    public ResponseEntity<CursorResponseDto<CategoryResponseDto>> getCategories(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestHeader("X-Workspace-ID") final UUID workspaceUuid,
            @Valid final CommonCursorFilterDto filter
    ) {

        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.categoryService.getCategories(workspaceId, filter)
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final CategoryCreateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final CategoryResponseDto responseDto = this.categoryService.createCategory(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid, @RequestBody @Valid final CategoryUpdateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final CategoryResponseDto responseDto = this.categoryService.updateCategory(uuid, dto, workspaceId);
        return ResponseEntity.ok(responseDto);
    }
}
