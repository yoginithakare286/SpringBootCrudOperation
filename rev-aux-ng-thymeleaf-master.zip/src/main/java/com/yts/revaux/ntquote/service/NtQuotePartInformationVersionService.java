package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationVersionSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationVersionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion}.
 */
@Service
@Transactional
public class NtQuotePartInformationVersionService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationVersionService.class);

    private final NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository;

    private final NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper;

    private final NtQuotePartInformationVersionSearchRepository ntQuotePartInformationVersionSearchRepository;

    public NtQuotePartInformationVersionService(
        NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository,
        NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper,
        NtQuotePartInformationVersionSearchRepository ntQuotePartInformationVersionSearchRepository
    ) {
        this.ntQuotePartInformationVersionRepository = ntQuotePartInformationVersionRepository;
        this.ntQuotePartInformationVersionMapper = ntQuotePartInformationVersionMapper;
        this.ntQuotePartInformationVersionSearchRepository = ntQuotePartInformationVersionSearchRepository;
    }

    /**
     * Save a ntQuotePartInformationVersion.
     *
     * @param ntQuotePartInformationVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuotePartInformationVersionDTO save(NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO) {
        LOG.debug("Request to save NtQuotePartInformationVersion : {}", ntQuotePartInformationVersionDTO);
        NtQuotePartInformationVersion ntQuotePartInformationVersion = ntQuotePartInformationVersionMapper.toEntity(
            ntQuotePartInformationVersionDTO
        );
        ntQuotePartInformationVersion = ntQuotePartInformationVersionRepository.save(ntQuotePartInformationVersion);
        ntQuotePartInformationVersionSearchRepository.index(ntQuotePartInformationVersion);
        return ntQuotePartInformationVersionMapper.toDto(ntQuotePartInformationVersion);
    }

    /**
     * Update a ntQuotePartInformationVersion.
     *
     * @param ntQuotePartInformationVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuotePartInformationVersionDTO update(NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO) {
        LOG.debug("Request to update NtQuotePartInformationVersion : {}", ntQuotePartInformationVersionDTO);
        NtQuotePartInformationVersion ntQuotePartInformationVersion = ntQuotePartInformationVersionMapper.toEntity(
            ntQuotePartInformationVersionDTO
        );
        ntQuotePartInformationVersion = ntQuotePartInformationVersionRepository.save(ntQuotePartInformationVersion);
        ntQuotePartInformationVersionSearchRepository.index(ntQuotePartInformationVersion);
        return ntQuotePartInformationVersionMapper.toDto(ntQuotePartInformationVersion);
    }

    /**
     * Partially update a ntQuotePartInformationVersion.
     *
     * @param ntQuotePartInformationVersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuotePartInformationVersionDTO> partialUpdate(NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO) {
        LOG.debug("Request to partially update NtQuotePartInformationVersion : {}", ntQuotePartInformationVersionDTO);

        return ntQuotePartInformationVersionRepository
            .findById(ntQuotePartInformationVersionDTO.getId())
            .map(existingNtQuotePartInformationVersion -> {
                ntQuotePartInformationVersionMapper.partialUpdate(existingNtQuotePartInformationVersion, ntQuotePartInformationVersionDTO);

                return existingNtQuotePartInformationVersion;
            })
            .map(ntQuotePartInformationVersionRepository::save)
            .map(savedNtQuotePartInformationVersion -> {
                ntQuotePartInformationVersionSearchRepository.index(savedNtQuotePartInformationVersion);
                return savedNtQuotePartInformationVersion;
            })
            .map(ntQuotePartInformationVersionMapper::toDto);
    }

    /**
     * Get one ntQuotePartInformationVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuotePartInformationVersionDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuotePartInformationVersion : {}", id);
        return ntQuotePartInformationVersionRepository.findById(id).map(ntQuotePartInformationVersionMapper::toDto);
    }

    /**
     * Delete the ntQuotePartInformationVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuotePartInformationVersion : {}", id);
        ntQuotePartInformationVersionRepository.deleteById(id);
        ntQuotePartInformationVersionSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuotePartInformationVersion corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuotePartInformationVersionDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuotePartInformationVersions for query {}", query);
        return ntQuotePartInformationVersionSearchRepository.search(query, pageable).map(ntQuotePartInformationVersionMapper::toDto);
    }
}
