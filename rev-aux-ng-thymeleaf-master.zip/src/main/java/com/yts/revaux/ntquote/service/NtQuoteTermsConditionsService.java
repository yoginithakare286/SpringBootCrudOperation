package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteTermsConditions;
import com.yts.revaux.ntquote.repository.NtQuoteTermsConditionsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteTermsConditionsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteTermsConditionsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteTermsConditionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteTermsConditions}.
 */
@Service
@Transactional
public class NtQuoteTermsConditionsService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteTermsConditionsService.class);

    private final NtQuoteTermsConditionsRepository ntQuoteTermsConditionsRepository;

    private final NtQuoteTermsConditionsMapper ntQuoteTermsConditionsMapper;

    private final NtQuoteTermsConditionsSearchRepository ntQuoteTermsConditionsSearchRepository;

    public NtQuoteTermsConditionsService(
        NtQuoteTermsConditionsRepository ntQuoteTermsConditionsRepository,
        NtQuoteTermsConditionsMapper ntQuoteTermsConditionsMapper,
        NtQuoteTermsConditionsSearchRepository ntQuoteTermsConditionsSearchRepository
    ) {
        this.ntQuoteTermsConditionsRepository = ntQuoteTermsConditionsRepository;
        this.ntQuoteTermsConditionsMapper = ntQuoteTermsConditionsMapper;
        this.ntQuoteTermsConditionsSearchRepository = ntQuoteTermsConditionsSearchRepository;
    }

    /**
     * Save a ntQuoteTermsConditions.
     *
     * @param ntQuoteTermsConditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteTermsConditionsDTO save(NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO) {
        LOG.debug("Request to save NtQuoteTermsConditions : {}", ntQuoteTermsConditionsDTO);
        NtQuoteTermsConditions ntQuoteTermsConditions = ntQuoteTermsConditionsMapper.toEntity(ntQuoteTermsConditionsDTO);
        ntQuoteTermsConditions = ntQuoteTermsConditionsRepository.save(ntQuoteTermsConditions);
        ntQuoteTermsConditionsSearchRepository.index(ntQuoteTermsConditions);
        return ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);
    }

    /**
     * Update a ntQuoteTermsConditions.
     *
     * @param ntQuoteTermsConditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteTermsConditionsDTO update(NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO) {
        LOG.debug("Request to update NtQuoteTermsConditions : {}", ntQuoteTermsConditionsDTO);
        NtQuoteTermsConditions ntQuoteTermsConditions = ntQuoteTermsConditionsMapper.toEntity(ntQuoteTermsConditionsDTO);
        ntQuoteTermsConditions = ntQuoteTermsConditionsRepository.save(ntQuoteTermsConditions);
        ntQuoteTermsConditionsSearchRepository.index(ntQuoteTermsConditions);
        return ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);
    }

    /**
     * Partially update a ntQuoteTermsConditions.
     *
     * @param ntQuoteTermsConditionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteTermsConditionsDTO> partialUpdate(NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO) {
        LOG.debug("Request to partially update NtQuoteTermsConditions : {}", ntQuoteTermsConditionsDTO);

        return ntQuoteTermsConditionsRepository
            .findById(ntQuoteTermsConditionsDTO.getId())
            .map(existingNtQuoteTermsConditions -> {
                ntQuoteTermsConditionsMapper.partialUpdate(existingNtQuoteTermsConditions, ntQuoteTermsConditionsDTO);

                return existingNtQuoteTermsConditions;
            })
            .map(ntQuoteTermsConditionsRepository::save)
            .map(savedNtQuoteTermsConditions -> {
                ntQuoteTermsConditionsSearchRepository.index(savedNtQuoteTermsConditions);
                return savedNtQuoteTermsConditions;
            })
            .map(ntQuoteTermsConditionsMapper::toDto);
    }

    /**
     * Get all the ntQuoteTermsConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteTermsConditionsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all NtQuoteTermsConditions");
        return ntQuoteTermsConditionsRepository.findAll(pageable).map(ntQuoteTermsConditionsMapper::toDto);
    }

    /**
     * Get one ntQuoteTermsConditions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteTermsConditionsDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteTermsConditions : {}", id);
        return ntQuoteTermsConditionsRepository.findById(id).map(ntQuoteTermsConditionsMapper::toDto);
    }

    /**
     * Delete the ntQuoteTermsConditions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteTermsConditions : {}", id);
        ntQuoteTermsConditionsRepository.deleteById(id);
        ntQuoteTermsConditionsSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteTermsConditions corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteTermsConditionsDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteTermsConditions for query {}", query);
        return ntQuoteTermsConditionsSearchRepository.search(query, pageable).map(ntQuoteTermsConditionsMapper::toDto);
    }
}
