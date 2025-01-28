package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerInputOutputVersionSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerInputOutputVersionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion}.
 */
@Service
@Transactional
public class NtQuoteCustomerInputOutputVersionService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerInputOutputVersionService.class);

    private final NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository;

    private final NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper;

    private final NtQuoteCustomerInputOutputVersionSearchRepository ntQuoteCustomerInputOutputVersionSearchRepository;

    public NtQuoteCustomerInputOutputVersionService(
        NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository,
        NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper,
        NtQuoteCustomerInputOutputVersionSearchRepository ntQuoteCustomerInputOutputVersionSearchRepository
    ) {
        this.ntQuoteCustomerInputOutputVersionRepository = ntQuoteCustomerInputOutputVersionRepository;
        this.ntQuoteCustomerInputOutputVersionMapper = ntQuoteCustomerInputOutputVersionMapper;
        this.ntQuoteCustomerInputOutputVersionSearchRepository = ntQuoteCustomerInputOutputVersionSearchRepository;
    }

    /**
     * Save a ntQuoteCustomerInputOutputVersion.
     *
     * @param ntQuoteCustomerInputOutputVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerInputOutputVersionDTO save(NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO) {
        LOG.debug("Request to save NtQuoteCustomerInputOutputVersion : {}", ntQuoteCustomerInputOutputVersionDTO);
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionMapper.toEntity(
            ntQuoteCustomerInputOutputVersionDTO
        );
        ntQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.save(ntQuoteCustomerInputOutputVersion);
        ntQuoteCustomerInputOutputVersionSearchRepository.index(ntQuoteCustomerInputOutputVersion);
        return ntQuoteCustomerInputOutputVersionMapper.toDto(ntQuoteCustomerInputOutputVersion);
    }

    /**
     * Update a ntQuoteCustomerInputOutputVersion.
     *
     * @param ntQuoteCustomerInputOutputVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerInputOutputVersionDTO update(NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO) {
        LOG.debug("Request to update NtQuoteCustomerInputOutputVersion : {}", ntQuoteCustomerInputOutputVersionDTO);
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionMapper.toEntity(
            ntQuoteCustomerInputOutputVersionDTO
        );
        ntQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.save(ntQuoteCustomerInputOutputVersion);
        ntQuoteCustomerInputOutputVersionSearchRepository.index(ntQuoteCustomerInputOutputVersion);
        return ntQuoteCustomerInputOutputVersionMapper.toDto(ntQuoteCustomerInputOutputVersion);
    }

    /**
     * Partially update a ntQuoteCustomerInputOutputVersion.
     *
     * @param ntQuoteCustomerInputOutputVersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteCustomerInputOutputVersionDTO> partialUpdate(
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO
    ) {
        LOG.debug("Request to partially update NtQuoteCustomerInputOutputVersion : {}", ntQuoteCustomerInputOutputVersionDTO);

        return ntQuoteCustomerInputOutputVersionRepository
            .findById(ntQuoteCustomerInputOutputVersionDTO.getId())
            .map(existingNtQuoteCustomerInputOutputVersion -> {
                ntQuoteCustomerInputOutputVersionMapper.partialUpdate(
                    existingNtQuoteCustomerInputOutputVersion,
                    ntQuoteCustomerInputOutputVersionDTO
                );

                return existingNtQuoteCustomerInputOutputVersion;
            })
            .map(ntQuoteCustomerInputOutputVersionRepository::save)
            .map(savedNtQuoteCustomerInputOutputVersion -> {
                ntQuoteCustomerInputOutputVersionSearchRepository.index(savedNtQuoteCustomerInputOutputVersion);
                return savedNtQuoteCustomerInputOutputVersion;
            })
            .map(ntQuoteCustomerInputOutputVersionMapper::toDto);
    }

    /**
     * Get one ntQuoteCustomerInputOutputVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteCustomerInputOutputVersionDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteCustomerInputOutputVersion : {}", id);
        return ntQuoteCustomerInputOutputVersionRepository.findById(id).map(ntQuoteCustomerInputOutputVersionMapper::toDto);
    }

    /**
     * Delete the ntQuoteCustomerInputOutputVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteCustomerInputOutputVersion : {}", id);
        ntQuoteCustomerInputOutputVersionRepository.deleteById(id);
        ntQuoteCustomerInputOutputVersionSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteCustomerInputOutputVersion corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerInputOutputVersionDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteCustomerInputOutputVersions for query {}", query);
        return ntQuoteCustomerInputOutputVersionSearchRepository
            .search(query, pageable)
            .map(ntQuoteCustomerInputOutputVersionMapper::toDto);
    }
}
