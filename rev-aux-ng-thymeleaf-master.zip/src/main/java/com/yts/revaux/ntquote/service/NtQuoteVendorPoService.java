package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import com.yts.revaux.ntquote.repository.NtQuoteVendorPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorPoSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorPoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteVendorPo}.
 */
@Service
@Transactional
public class NtQuoteVendorPoService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorPoService.class);

    private final NtQuoteVendorPoRepository ntQuoteVendorPoRepository;

    private final NtQuoteVendorPoMapper ntQuoteVendorPoMapper;

    private final NtQuoteVendorPoSearchRepository ntQuoteVendorPoSearchRepository;

    public NtQuoteVendorPoService(
        NtQuoteVendorPoRepository ntQuoteVendorPoRepository,
        NtQuoteVendorPoMapper ntQuoteVendorPoMapper,
        NtQuoteVendorPoSearchRepository ntQuoteVendorPoSearchRepository
    ) {
        this.ntQuoteVendorPoRepository = ntQuoteVendorPoRepository;
        this.ntQuoteVendorPoMapper = ntQuoteVendorPoMapper;
        this.ntQuoteVendorPoSearchRepository = ntQuoteVendorPoSearchRepository;
    }

    /**
     * Save a ntQuoteVendorPo.
     *
     * @param ntQuoteVendorPoDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteVendorPoDTO save(NtQuoteVendorPoDTO ntQuoteVendorPoDTO) {
        LOG.debug("Request to save NtQuoteVendorPo : {}", ntQuoteVendorPoDTO);
        NtQuoteVendorPo ntQuoteVendorPo = ntQuoteVendorPoMapper.toEntity(ntQuoteVendorPoDTO);
        ntQuoteVendorPo = ntQuoteVendorPoRepository.save(ntQuoteVendorPo);
        ntQuoteVendorPoSearchRepository.index(ntQuoteVendorPo);
        return ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);
    }

    /**
     * Update a ntQuoteVendorPo.
     *
     * @param ntQuoteVendorPoDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteVendorPoDTO update(NtQuoteVendorPoDTO ntQuoteVendorPoDTO) {
        LOG.debug("Request to update NtQuoteVendorPo : {}", ntQuoteVendorPoDTO);
        NtQuoteVendorPo ntQuoteVendorPo = ntQuoteVendorPoMapper.toEntity(ntQuoteVendorPoDTO);
        ntQuoteVendorPo = ntQuoteVendorPoRepository.save(ntQuoteVendorPo);
        ntQuoteVendorPoSearchRepository.index(ntQuoteVendorPo);
        return ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);
    }

    /**
     * Partially update a ntQuoteVendorPo.
     *
     * @param ntQuoteVendorPoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteVendorPoDTO> partialUpdate(NtQuoteVendorPoDTO ntQuoteVendorPoDTO) {
        LOG.debug("Request to partially update NtQuoteVendorPo : {}", ntQuoteVendorPoDTO);

        return ntQuoteVendorPoRepository
            .findById(ntQuoteVendorPoDTO.getId())
            .map(existingNtQuoteVendorPo -> {
                ntQuoteVendorPoMapper.partialUpdate(existingNtQuoteVendorPo, ntQuoteVendorPoDTO);

                return existingNtQuoteVendorPo;
            })
            .map(ntQuoteVendorPoRepository::save)
            .map(savedNtQuoteVendorPo -> {
                ntQuoteVendorPoSearchRepository.index(savedNtQuoteVendorPo);
                return savedNtQuoteVendorPo;
            })
            .map(ntQuoteVendorPoMapper::toDto);
    }

    /**
     * Get one ntQuoteVendorPo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteVendorPoDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteVendorPo : {}", id);
        return ntQuoteVendorPoRepository.findById(id).map(ntQuoteVendorPoMapper::toDto);
    }

    /**
     * Delete the ntQuoteVendorPo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteVendorPo : {}", id);
        ntQuoteVendorPoRepository.deleteById(id);
        ntQuoteVendorPoSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteVendorPo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteVendorPoDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteVendorPos for query {}", query);
        return ntQuoteVendorPoSearchRepository.search(query, pageable).map(ntQuoteVendorPoMapper::toDto);
    }
}
