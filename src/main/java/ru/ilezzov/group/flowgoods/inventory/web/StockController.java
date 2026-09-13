package ru.ilezzov.group.flowgoods.inventory.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.iam.security.jwt.JwtPrincipal;
import ru.ilezzov.group.flowgoods.inventory.dto.stock.StockAddDeductDto;
import ru.ilezzov.group.flowgoods.inventory.dto.stock.StockResponseDto;
import ru.ilezzov.group.flowgoods.inventory.service.StockService;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class StockController {
    private final StockService stockService;

    private final UserResolver userResolver;
    private final WorkspaceResolver workspaceResolver;

    @PostMapping("/{productUuid}/add")
    public ResponseEntity<StockResponseDto> addStock(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID productUuid, @RequestBody @Valid final StockAddDeductDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.stockService.addStock(dto, productUuid, workspaceId)
        );
    }

    @PostMapping("/{productUuid}/deduct")
    public ResponseEntity<StockResponseDto> deductStock(@AuthenticationPrincipal JwtPrincipal principal, @RequestHeader("X-Workspace-ID") final UUID workspaceUuid, @PathVariable final UUID productUuid, @RequestBody @Valid final StockAddDeductDto dto) {
        final Long ownerId = this.userResolver.resolveUserIdByUuid(principal.uuid());
        final Long workspaceId = this.workspaceResolver.resolveIdByUuidAndOwnerId(workspaceUuid, ownerId);

        return ResponseEntity.ok(
                this.stockService.deductStock(dto, productUuid, workspaceId)
        );
    }
}
