package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;
import ru.ilezzov.group.flowgoods.inventory.exception.category.CategoryAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.mapper.CategoryMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.CategoryRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.CategoryResolver;
import ru.ilezzov.group.flowgoods.inventory.specification.CategorySpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResolver categoryResolver;
    private final CategoryMapper categoryMapper;

    private final AesCursorEncoder cursorEncoder;
    
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(final UUID uuid, final Long workspaceId) {
        return this.categoryMapper.toDto(
                this.categoryResolver.resolveByUuidAndWorkspaceId(
                        uuid, workspaceId
                )
        );
    }

    public CategoryResponseDto createCategory(final CategoryCreateDto dto, final Long workspaceId) {
        if (this.categoryRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
            throw new CategoryAlreadyExistsException(dto.name());
        }

        final Category category = this.categoryMapper.toEntity(dto, workspaceId);
        return this.categoryMapper.toDto(
                this.categoryRepository.save(category)
        );
    }

    public CategoryResponseDto updateCategory(final UUID uuid, final CategoryUpdateDto dto, final Long workspaceId) {
        final Category category = this.categoryResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId);

        if (!category.getName().equalsIgnoreCase(dto.name())) {
            if (this.categoryRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
                throw new CategoryAlreadyExistsException(dto.name());
            }
        }

        this.categoryMapper.updateEntity(dto, category);
        return this.categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public CursorResponseDto<CategoryResponseDto> getCategories(final Long workspaceId, final CategoryFilterDto dto, final String cursor, final int limit) {
        final Long lastId = this.cursorEncoder.decode(cursor);

        final Specification<Category> specification = Specification
                .where(CategorySpecification.workspaceIdEquals(workspaceId))
                .and(CategorySpecification.categoryLikeName(dto.name()))
                .and(CategorySpecification.idGreaterThan(lastId));

        final List<Category> content = categoryRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.ASC, "id"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<CategoryResponseDto> categoryResponseDtoList = content.stream()
                .limit(limit)
                .map(this.categoryMapper::toDto)
                .toList();

        String nextCursor = null;

        if (hasNext) {
            content.removeLast();

            if (!categoryResponseDtoList.isEmpty()) {
                nextCursor = this.cursorEncoder.encode(content.getLast().getId());
            }
        }

        return new CursorResponseDto<>(categoryResponseDtoList, nextCursor);
    }
}
