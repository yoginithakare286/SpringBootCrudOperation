package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.repository.NtQuoteProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectApprovalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteProjectApproval}.
 */
@Service
@Transactional
public class NtQuoteProjectApprovalService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectApprovalService.class);

    private final NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository;

    private final NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper;

    private final NtQuoteProjectApprovalSearchRepository ntQuoteProjectApprovalSearchRepository;

    public NtQuoteProjectApprovalService(
        NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository,
        NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper,
        NtQuoteProjectApprovalSearchRepository ntQuoteProjectApprovalSearchRepository
    ) {
        this.ntQuoteProjectApprovalRepository = ntQuoteProjectApprovalRepository;
        this.ntQuoteProjectApprovalMapper = ntQuoteProjectApprovalMapper;
        this.ntQuoteProjectApprovalSearchRepository = ntQuoteProjectApprovalSearchRepository;
    }

    /**
     * Save a ntQuoteProjectApproval.
     *
     * @param ntQuoteProjectApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteProjectApprovalDTO save(NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO) {
        LOG.debug("Request to save NtQuoteProjectApproval : {}", ntQuoteProjectApprovalDTO);
        NtQuoteProjectApproval ntQuoteProjectApproval = ntQuoteProjectApprovalMapper.toEntity(ntQuoteProjectApprovalDTO);
        ntQuoteProjectApproval = ntQuoteProjectApprovalRepository.save(ntQuoteProjectApproval);
        ntQuoteProjectApprovalSearchRepository.index(ntQuoteProjectApproval);
        return ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);
    }

    /**
     * Update a ntQuoteProjectApproval.
     *
     * @param ntQuoteProjectApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteProjectApprovalDTO update(NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO) {
        LOG.debug("Request to update NtQuoteProjectApproval : {}", ntQuoteProjectApprovalDTO);
        NtQuoteProjectApproval ntQuoteProjectApproval = ntQuoteProjectApprovalMapper.toEntity(ntQuoteProjectApprovalDTO);
        ntQuoteProjectApproval = ntQuoteProjectApprovalRepository.save(ntQuoteProjectApproval);
        ntQuoteProjectApprovalSearchRepository.index(ntQuoteProjectApproval);
        return ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);
    }

    /**
     * Partially update a ntQuoteProjectApproval.
     *
     * @param ntQuoteProjectApprovalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteProjectApprovalDTO> partialUpdate(NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO) {
        LOG.debug("Request to partially update NtQuoteProjectApproval : {}", ntQuoteProjectApprovalDTO);

        return ntQuoteProjectApprovalRepository
            .findById(ntQuoteProjectApprovalDTO.getId())
            .map(existingNtQuoteProjectApproval -> {
                ntQuoteProjectApprovalMapper.partialUpdate(existingNtQuoteProjectApproval, ntQuoteProjectApprovalDTO);

                return existingNtQuoteProjectApproval;
            })
            .map(ntQuoteProjectApprovalRepository::save)
            .map(savedNtQuoteProjectApproval -> {
                ntQuoteProjectApprovalSearchRepository.index(savedNtQuoteProjectApproval);
                return savedNtQuoteProjectApproval;
            })
            .map(ntQuoteProjectApprovalMapper::toDto);
    }

    /**
     * Get one ntQuoteProjectApproval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteProjectApprovalDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteProjectApproval : {}", id);
        return ntQuoteProjectApprovalRepository.findById(id).map(ntQuoteProjectApprovalMapper::toDto);
    }

    /**
     * Delete the ntQuoteProjectApproval by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteProjectApproval : {}", id);
        ntQuoteProjectApprovalRepository.deleteById(id);
        ntQuoteProjectApprovalSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteProjectApproval corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteProjectApprovalDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteProjectApprovals for query {}", query);
        return ntQuoteProjectApprovalSearchRepository.search(query, pageable).map(ntQuoteProjectApprovalMapper::toDto);
    }
}
