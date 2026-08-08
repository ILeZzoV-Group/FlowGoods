package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;
import ru.ilezzov.group.flowgoods.inventory.exception.category.CategoryAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.mapper.CategoryMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.CategoryRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.CategoryResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResolver categoryResolver;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(final UUID uuid, final Long workspaceId) {
        return this.categoryMapper.toDto(
                this.categoryResolver.resolverByUuidAndWorkspace(
                        uuid, workspaceId
                )
        );
    }

    public CategoryResponseDto createCategory(final CategoryCreateDto dto, final Long workspaceId) {
        if (this.categoryRepository.existsByNameAndWorkspaceId(dto.name(), workspaceId)) {
            throw new CategoryAlreadyExistsException(dto.name());
        }

        final Category category = Category.builder()
                .name(dto.name())
                .description(dto.description())
                .workspaceId(workspaceId)
                .build();

        return this.categoryMapper.toDto(
                this.categoryRepository.save(category)
        );
    }
}
