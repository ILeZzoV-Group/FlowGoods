package ru.ilezzov.group.flowgoods.finance.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryCreateDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryFilterDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryUpdateDto;
import ru.ilezzov.group.flowgoods.finance.service.TransactionCategoryService;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction-categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionCategoryController {
    private final TransactionCategoryService transactionCategoryService;

    private final WorkspaceResolver workspaceResolver;
    private final UserResolver userResolver;

    @GetMapping("/{uuid}")
    public ResponseEntity<TransactionCategoryResponseDto> getCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.transactionCategoryService.getTransactionCategory(uuid, workspaceId)
        );
    }

    @GetMapping
    public ResponseEntity<CursorResponseDto<TransactionCategoryResponseDto>> getCategories(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestHeader("X-Workspace-ID") final UUID workspaceUuid,
            @Valid final TransactionCategoryFilterDto filter
    ) {

        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.transactionCategoryService.getTransactionCategories(workspaceId, filter)
        );
    }

    @PostMapping
    public ResponseEntity<TransactionCategoryResponseDto> createCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @RequestBody @Valid final TransactionCategoryCreateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final TransactionCategoryResponseDto responseDto = this.transactionCategoryService.createTransactionCategory(dto, workspaceId);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(responseDto.uuid())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<TransactionCategoryResponseDto> updateCategory(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID uuid, @RequestBody @Valid final TransactionCategoryUpdateDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        final TransactionCategoryResponseDto responseDto = this.transactionCategoryService.updateTransactionCategory(uuid, dto, workspaceId);
        return ResponseEntity.ok(responseDto);
    }
}
