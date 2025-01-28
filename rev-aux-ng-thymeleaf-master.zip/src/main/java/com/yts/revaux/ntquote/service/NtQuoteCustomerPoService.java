package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerPoSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerPoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerPo}.
 */
@Service
@Transactional
public class NtQuoteCustomerPoService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerPoService.class);

    private final NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository;

    private final NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper;

    private final NtQuoteCustomerPoSearchRepository ntQuoteCustomerPoSearchRepository;

    public NtQuoteCustomerPoService(
        NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository,
        NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper,
        NtQuoteCustomerPoSearchRepository ntQuoteCustomerPoSearchRepository
    ) {
        this.ntQuoteCustomerPoRepository = ntQuoteCustomerPoRepository;
        this.ntQuoteCustomerPoMapper = ntQuoteCustomerPoMapper;
        this.ntQuoteCustomerPoSearchRepository = ntQuoteCustomerPoSearchRepository;
    }

    /**
     * Save a ntQuoteCustomerPo.
     *
     * @param ntQuoteCustomerPoDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerPoDTO save(NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO) {
        LOG.debug("Request to save NtQuoteCustomerPo : {}", ntQuoteCustomerPoDTO);
        NtQuoteCustomerPo ntQuoteCustomerPo = ntQuoteCustomerPoMapper.toEntity(ntQuoteCustomerPoDTO);
        ntQuoteCustomerPo = ntQuoteCustomerPoRepository.save(ntQuoteCustomerPo);
        ntQuoteCustomerPoSearchRepository.index(ntQuoteCustomerPo);
        return ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);
    }

    /**
     * Update a ntQuoteCustomerPo.
     *
     * @param ntQuoteCustomerPoDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerPoDTO update(NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO) {
        LOG.debug("Request to update NtQuoteCustomerPo : {}", ntQuoteCustomerPoDTO);
        NtQuoteCustomerPo ntQuoteCustomerPo = ntQuoteCustomerPoMapper.toEntity(ntQuoteCustomerPoDTO);
        ntQuoteCustomerPo = ntQuoteCustomerPoRepository.save(ntQuoteCustomerPo);
        ntQuoteCustomerPoSearchRepository.index(ntQuoteCustomerPo);
        return ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);
    }

    /**
     * Partially update a ntQuoteCustomerPo.
     *
     * @param ntQuoteCustomerPoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteCustomerPoDTO> partialUpdate(NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO) {
        LOG.debug("Request to partially update NtQuoteCustomerPo : {}", ntQuoteCustomerPoDTO);

        return ntQuoteCustomerPoRepository
            .findById(ntQuoteCustomerPoDTO.getId())
            .map(existingNtQuoteCustomerPo -> {
                ntQuoteCustomerPoMapper.partialUpdate(existingNtQuoteCustomerPo, ntQuoteCustomerPoDTO);

                return existingNtQuoteCustomerPo;
            })
            .map(ntQuoteCustomerPoRepository::save)
            .map(savedNtQuoteCustomerPo -> {
                ntQuoteCustomerPoSearchRepository.index(savedNtQuoteCustomerPo);
                return savedNtQuoteCustomerPo;
            })
            .map(ntQuoteCustomerPoMapper::toDto);
    }

    /**
     * Get one ntQuoteCustomerPo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteCustomerPoDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteCustomerPo : {}", id);
        return ntQuoteCustomerPoRepository.findById(id).map(ntQuoteCustomerPoMapper::toDto);
    }

    /**
     * Delete the ntQuoteCustomerPo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteCustomerPo : {}", id);
        ntQuoteCustomerPoRepository.deleteById(id);
        ntQuoteCustomerPoSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteCustomerPo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerPoDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteCustomerPos for query {}", query);
        return ntQuoteCustomerPoSearchRepository.search(query, pageable).map(ntQuoteCustomerPoMapper::toDto);
    }
}
