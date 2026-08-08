package ru.ilezzov.group.flowgoods.inventory.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.service.CategoryService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final WorkspaceResolver workspaceResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryResponseDto> getCategory(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        return ResponseEntity.ok(
                this.categoryService.getCategory(uuid, workspaceId)
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final CategoryCreateDto dto) {
        final Long workspaceId = this.workspaceResolver.resolverIdByUuid(workspaceUuid);
        final CategoryResponseDto responseDto = this.categoryService.createCategory(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}
