package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtProjectApproval;
import com.yts.revaux.ntquote.repository.NtProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtProjectApprovalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtProjectApproval}.
 */
@Service
@Transactional
public class NtProjectApprovalService {

    private static final Logger LOG = LoggerFactory.getLogger(NtProjectApprovalService.class);

    private final NtProjectApprovalRepository ntProjectApprovalRepository;

    private final NtProjectApprovalMapper ntProjectApprovalMapper;

    private final NtProjectApprovalSearchRepository ntProjectApprovalSearchRepository;

    public NtProjectApprovalService(
        NtProjectApprovalRepository ntProjectApprovalRepository,
        NtProjectApprovalMapper ntProjectApprovalMapper,
        NtProjectApprovalSearchRepository ntProjectApprovalSearchRepository
    ) {
        this.ntProjectApprovalRepository = ntProjectApprovalRepository;
        this.ntProjectApprovalMapper = ntProjectApprovalMapper;
        this.ntProjectApprovalSearchRepository = ntProjectApprovalSearchRepository;
    }

    /**
     * Save a ntProjectApproval.
     *
     * @param ntProjectApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public NtProjectApprovalDTO save(NtProjectApprovalDTO ntProjectApprovalDTO) {
        LOG.debug("Request to save NtProjectApproval : {}", ntProjectApprovalDTO);
        NtProjectApproval ntProjectApproval = ntProjectApprovalMapper.toEntity(ntProjectApprovalDTO);
        ntProjectApproval = ntProjectApprovalRepository.save(ntProjectApproval);
        ntProjectApprovalSearchRepository.index(ntProjectApproval);
        return ntProjectApprovalMapper.toDto(ntProjectApproval);
    }

    /**
     * Update a ntProjectApproval.
     *
     * @param ntProjectApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public NtProjectApprovalDTO update(NtProjectApprovalDTO ntProjectApprovalDTO) {
        LOG.debug("Request to update NtProjectApproval : {}", ntProjectApprovalDTO);
        NtProjectApproval ntProjectApproval = ntProjectApprovalMapper.toEntity(ntProjectApprovalDTO);
        ntProjectApproval = ntProjectApprovalRepository.save(ntProjectApproval);
        ntProjectApprovalSearchRepository.index(ntProjectApproval);
        return ntProjectApprovalMapper.toDto(ntProjectApproval);
    }

    /**
     * Partially update a ntProjectApproval.
     *
     * @param ntProjectApprovalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtProjectApprovalDTO> partialUpdate(NtProjectApprovalDTO ntProjectApprovalDTO) {
        LOG.debug("Request to partially update NtProjectApproval : {}", ntProjectApprovalDTO);

        return ntProjectApprovalRepository
            .findById(ntProjectApprovalDTO.getId())
            .map(existingNtProjectApproval -> {
                ntProjectApprovalMapper.partialUpdate(existingNtProjectApproval, ntProjectApprovalDTO);

                return existingNtProjectApproval;
            })
            .map(ntProjectApprovalRepository::save)
            .map(savedNtProjectApproval -> {
                ntProjectApprovalSearchRepository.index(savedNtProjectApproval);
                return savedNtProjectApproval;
            })
            .map(ntProjectApprovalMapper::toDto);
    }

    /**
     * Get one ntProjectApproval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtProjectApprovalDTO> findOne(Long id) {
        LOG.debug("Request to get NtProjectApproval : {}", id);
        return ntProjectApprovalRepository.findById(id).map(ntProjectApprovalMapper::toDto);
    }

    /**
     * Delete the ntProjectApproval by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtProjectApproval : {}", id);
        ntProjectApprovalRepository.deleteById(id);
        ntProjectApprovalSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntProjectApproval corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtProjectApprovalDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtProjectApprovals for query {}", query);
        return ntProjectApprovalSearchRepository.search(query, pageable).map(ntProjectApprovalMapper::toDto);
    }
}
