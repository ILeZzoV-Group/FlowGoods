package ru.ilezzov.group.flowgoods.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryCreateDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryFilterDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryUpdateDto;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;
import ru.ilezzov.group.flowgoods.finance.exception.TransactionCategoryAlreadyExistsException;
import ru.ilezzov.group.flowgoods.finance.mapper.TransactionCategoryMapper;
import ru.ilezzov.group.flowgoods.finance.repository.TransactionCategoryRepository;
import ru.ilezzov.group.flowgoods.finance.resolver.TransactionCategoryResolver;
import ru.ilezzov.group.flowgoods.finance.specification.TransactionCategorySpecification;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionCategoryService {
    private final TransactionCategoryRepository categoryRepository;
    private final TransactionCategoryResolver categoryResolver;
    private final TransactionCategoryMapper categoryMapper;

    private final AesCursorEncoder cursorEncoder;

    @Transactional(readOnly = true)
    public TransactionCategoryResponseDto getTransactionCategory(final UUID uuid, final Long workspaceId) {
        return this.categoryMapper.toDto(
                this.categoryResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId)
        );
    }

    public TransactionCategoryResponseDto createTransactionCategory(final TransactionCategoryCreateDto dto, final Long workspaceId) {
        if (this.categoryRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
            throw new TransactionCategoryAlreadyExistsException(dto.name());
        }

        final TransactionCategory category = this.categoryMapper.toEntity(dto, workspaceId);
        return this.categoryMapper.toDto(
                this.categoryRepository.save(category)
        );
    }

    public TransactionCategoryResponseDto updateTransactionCategory(final UUID uuid, final TransactionCategoryUpdateDto dto, final Long workspaceId) {
        final TransactionCategory category = this.categoryResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId);

        if (!category.getName().equalsIgnoreCase(dto.name())) {
            if (this.categoryRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
                throw new TransactionCategoryAlreadyExistsException(dto.name());
            }
        }

        this.categoryMapper.updateEntity(dto, category);
        return this.categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public CursorResponseDto<TransactionCategoryResponseDto> getTransactionCategories(final Long workspaceId, final TransactionCategoryFilterDto dto) {
        final Long lastId = this.cursorEncoder.decode(dto.cursor());
        final int limit = dto.limit();

        final Specification<TransactionCategory> specification = Specification
                .where(TransactionCategorySpecification.workspaceIdEquals(workspaceId))
                .and(TransactionCategorySpecification.categoryTypeEquals(dto.type()))
                .and(TransactionCategorySpecification.categoryLikeName(dto.name()))
                .and(TransactionCategorySpecification.idGreaterThan(lastId));

        final List<TransactionCategory> content = categoryRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.ASC, "id"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<TransactionCategoryResponseDto> categoryResponseDtoList = content.stream()
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
