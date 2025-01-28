package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import com.yts.revaux.ntquote.repository.NtQuoteVendorQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorQuoteSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorQuoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteVendorQuote}.
 */
@Service
@Transactional
public class NtQuoteVendorQuoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorQuoteService.class);

    private final NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository;

    private final NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper;

    private final NtQuoteVendorQuoteSearchRepository ntQuoteVendorQuoteSearchRepository;

    public NtQuoteVendorQuoteService(
        NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository,
        NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper,
        NtQuoteVendorQuoteSearchRepository ntQuoteVendorQuoteSearchRepository
    ) {
        this.ntQuoteVendorQuoteRepository = ntQuoteVendorQuoteRepository;
        this.ntQuoteVendorQuoteMapper = ntQuoteVendorQuoteMapper;
        this.ntQuoteVendorQuoteSearchRepository = ntQuoteVendorQuoteSearchRepository;
    }

    /**
     * Save a ntQuoteVendorQuote.
     *
     * @param ntQuoteVendorQuoteDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteVendorQuoteDTO save(NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO) {
        LOG.debug("Request to save NtQuoteVendorQuote : {}", ntQuoteVendorQuoteDTO);
        NtQuoteVendorQuote ntQuoteVendorQuote = ntQuoteVendorQuoteMapper.toEntity(ntQuoteVendorQuoteDTO);
        ntQuoteVendorQuote = ntQuoteVendorQuoteRepository.save(ntQuoteVendorQuote);
        ntQuoteVendorQuoteSearchRepository.index(ntQuoteVendorQuote);
        return ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);
    }

    /**
     * Update a ntQuoteVendorQuote.
     *
     * @param ntQuoteVendorQuoteDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteVendorQuoteDTO update(NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO) {
        LOG.debug("Request to update NtQuoteVendorQuote : {}", ntQuoteVendorQuoteDTO);
        NtQuoteVendorQuote ntQuoteVendorQuote = ntQuoteVendorQuoteMapper.toEntity(ntQuoteVendorQuoteDTO);
        ntQuoteVendorQuote = ntQuoteVendorQuoteRepository.save(ntQuoteVendorQuote);
        ntQuoteVendorQuoteSearchRepository.index(ntQuoteVendorQuote);
        return ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);
    }

    /**
     * Partially update a ntQuoteVendorQuote.
     *
     * @param ntQuoteVendorQuoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteVendorQuoteDTO> partialUpdate(NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO) {
        LOG.debug("Request to partially update NtQuoteVendorQuote : {}", ntQuoteVendorQuoteDTO);

        return ntQuoteVendorQuoteRepository
            .findById(ntQuoteVendorQuoteDTO.getId())
            .map(existingNtQuoteVendorQuote -> {
                ntQuoteVendorQuoteMapper.partialUpdate(existingNtQuoteVendorQuote, ntQuoteVendorQuoteDTO);

                return existingNtQuoteVendorQuote;
            })
            .map(ntQuoteVendorQuoteRepository::save)
            .map(savedNtQuoteVendorQuote -> {
                ntQuoteVendorQuoteSearchRepository.index(savedNtQuoteVendorQuote);
                return savedNtQuoteVendorQuote;
            })
            .map(ntQuoteVendorQuoteMapper::toDto);
    }

    /**
     * Get one ntQuoteVendorQuote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteVendorQuoteDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteVendorQuote : {}", id);
        return ntQuoteVendorQuoteRepository.findById(id).map(ntQuoteVendorQuoteMapper::toDto);
    }

    /**
     * Delete the ntQuoteVendorQuote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteVendorQuote : {}", id);
        ntQuoteVendorQuoteRepository.deleteById(id);
        ntQuoteVendorQuoteSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteVendorQuote corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteVendorQuoteDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteVendorQuotes for query {}", query);
        return ntQuoteVendorQuoteSearchRepository.search(query, pageable).map(ntQuoteVendorQuoteMapper::toDto);
    }
}
