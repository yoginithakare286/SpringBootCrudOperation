package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import com.yts.revaux.ntquote.repository.NtQuoteProjectConsiderationsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectConsiderationsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectConsiderationsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectConsiderationsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations}.
 */
@Service
@Transactional
public class NtQuoteProjectConsiderationsService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectConsiderationsService.class);

    private final NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository;

    private final NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper;

    private final NtQuoteProjectConsiderationsSearchRepository ntQuoteProjectConsiderationsSearchRepository;

    public NtQuoteProjectConsiderationsService(
        NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository,
        NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper,
        NtQuoteProjectConsiderationsSearchRepository ntQuoteProjectConsiderationsSearchRepository
    ) {
        this.ntQuoteProjectConsiderationsRepository = ntQuoteProjectConsiderationsRepository;
        this.ntQuoteProjectConsiderationsMapper = ntQuoteProjectConsiderationsMapper;
        this.ntQuoteProjectConsiderationsSearchRepository = ntQuoteProjectConsiderationsSearchRepository;
    }

    /**
     * Save a ntQuoteProjectConsiderations.
     *
     * @param ntQuoteProjectConsiderationsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteProjectConsiderationsDTO save(NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO) {
        LOG.debug("Request to save NtQuoteProjectConsiderations : {}", ntQuoteProjectConsiderationsDTO);
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations = ntQuoteProjectConsiderationsMapper.toEntity(
            ntQuoteProjectConsiderationsDTO
        );
        ntQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.save(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderationsSearchRepository.index(ntQuoteProjectConsiderations);
        return ntQuoteProjectConsiderationsMapper.toDto(ntQuoteProjectConsiderations);
    }

    /**
     * Update a ntQuoteProjectConsiderations.
     *
     * @param ntQuoteProjectConsiderationsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteProjectConsiderationsDTO update(NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO) {
        LOG.debug("Request to update NtQuoteProjectConsiderations : {}", ntQuoteProjectConsiderationsDTO);
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations = ntQuoteProjectConsiderationsMapper.toEntity(
            ntQuoteProjectConsiderationsDTO
        );
        ntQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.save(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderationsSearchRepository.index(ntQuoteProjectConsiderations);
        return ntQuoteProjectConsiderationsMapper.toDto(ntQuoteProjectConsiderations);
    }

    /**
     * Partially update a ntQuoteProjectConsiderations.
     *
     * @param ntQuoteProjectConsiderationsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteProjectConsiderationsDTO> partialUpdate(NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO) {
        LOG.debug("Request to partially update NtQuoteProjectConsiderations : {}", ntQuoteProjectConsiderationsDTO);

        return ntQuoteProjectConsiderationsRepository
            .findById(ntQuoteProjectConsiderationsDTO.getId())
            .map(existingNtQuoteProjectConsiderations -> {
                ntQuoteProjectConsiderationsMapper.partialUpdate(existingNtQuoteProjectConsiderations, ntQuoteProjectConsiderationsDTO);

                return existingNtQuoteProjectConsiderations;
            })
            .map(ntQuoteProjectConsiderationsRepository::save)
            .map(savedNtQuoteProjectConsiderations -> {
                ntQuoteProjectConsiderationsSearchRepository.index(savedNtQuoteProjectConsiderations);
                return savedNtQuoteProjectConsiderations;
            })
            .map(ntQuoteProjectConsiderationsMapper::toDto);
    }

    /**
     * Get one ntQuoteProjectConsiderations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteProjectConsiderationsDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteProjectConsiderations : {}", id);
        return ntQuoteProjectConsiderationsRepository.findById(id).map(ntQuoteProjectConsiderationsMapper::toDto);
    }

    /**
     * Delete the ntQuoteProjectConsiderations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteProjectConsiderations : {}", id);
        ntQuoteProjectConsiderationsRepository.deleteById(id);
        ntQuoteProjectConsiderationsSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteProjectConsiderations corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteProjectConsiderationsDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteProjectConsiderations for query {}", query);
        return ntQuoteProjectConsiderationsSearchRepository.search(query, pageable).map(ntQuoteProjectConsiderationsMapper::toDto);
    }
}
