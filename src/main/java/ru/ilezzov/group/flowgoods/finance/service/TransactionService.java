package ru.ilezzov.group.flowgoods.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionCreateDto;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionFilterDto;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionUpdateDto;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction;
import ru.ilezzov.group.flowgoods.finance.exception.TransactionAlreadyExistsException;
import ru.ilezzov.group.flowgoods.finance.mapper.TransactionMapper;
import ru.ilezzov.group.flowgoods.finance.repository.TransactionRepository;
import ru.ilezzov.group.flowgoods.finance.resolver.TransactionCategoryResolver;
import ru.ilezzov.group.flowgoods.finance.resolver.TransactionResolver;
import ru.ilezzov.group.flowgoods.finance.specification.TransactionSpecification;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionResolver transactionResolver;
    private final TransactionMapper transactionMapper;

    private final AesCursorEncoder cursorEncoder;
    private final TransactionCategoryResolver categoryResolver;

    @Transactional(readOnly = true)
    public TransactionResponseDto getTransaction(final UUID uuid, final Long workspaceId) {
        return this.transactionMapper.toDto(
                this.transactionResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId)
        );
    }

    public TransactionResponseDto createTransaction(final TransactionCreateDto dto, final Long workspaceId) {
        if (this.transactionRepository.existsByWorkspaceIdAndIdempotencyKey(workspaceId, dto.idempotencyKey())) {
            throw new TransactionAlreadyExistsException(dto.idempotencyKey());
        }


        final Transaction transaction = this.transactionMapper.toEntity(dto, workspaceId, workspaceId);
        return this.transactionMapper.toDto(
                this.transactionRepository.save(transaction)
        );
    }

    public TransactionResponseDto updateTransaction(final UUID uuid, final TransactionUpdateDto dto, final Long workspaceId) {
        final Transaction transaction = this.transactionResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId);

        this.transactionMapper.updateEntity(dto, transaction, workspaceId);
        return this.transactionMapper.toDto(transaction);
    }

    @Transactional(readOnly = true)
    public CursorResponseDto<TransactionResponseDto> getTransactions(final Long workspaceId, final TransactionFilterDto dto) {
        final Long lastId = this.cursorEncoder.decode(dto.cursor());
        final int limit = dto.limit();

        Long categoryId = null;
        if (dto.categoryId() != null) {
            categoryId = this.categoryResolver.resolveIdByUuidAndWorkspaceId(dto.categoryId(), workspaceId);
        }

        final Specification<Transaction> specification = Specification
                .where(TransactionSpecification.workspaceIdEquals(workspaceId))
                .and(TransactionSpecification.transactionTypeEquals(dto.type()))
                .and(TransactionSpecification.categoryEquals(categoryId))
                .and(TransactionSpecification.dateGreaterThanOrEqualTo(dto.from()))
                .and(TransactionSpecification.dateLessThanOrEqualTo(dto.to()))
                .and(TransactionSpecification.idLessThan(lastId));

        final List<Transaction> content = transactionRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.DESC, "id"))
                        .sortBy(Sort.by(Sort.Direction.DESC, "createdAt"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<TransactionResponseDto> transactionResponseDtoList = content.stream()
                .limit(limit)
                .map(this.transactionMapper::toDto)
                .toList();

        String nextCursor = null;

        if (hasNext) {
            content.removeLast();

            if (!transactionResponseDtoList.isEmpty()) {
                nextCursor = this.cursorEncoder.encode(content.getLast().getId());
            }
        }

        return new CursorResponseDto<>(transactionResponseDtoList, nextCursor);
    }
}
