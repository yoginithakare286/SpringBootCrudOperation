package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.repository.NtQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuote}.
 */
@Service
@Transactional
public class NtQuoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteService.class);

    private final NtQuoteRepository ntQuoteRepository;

    private final NtQuoteMapper ntQuoteMapper;

    private final NtQuoteSearchRepository ntQuoteSearchRepository;

    public NtQuoteService(
        NtQuoteRepository ntQuoteRepository,
        NtQuoteMapper ntQuoteMapper,
        NtQuoteSearchRepository ntQuoteSearchRepository
    ) {
        this.ntQuoteRepository = ntQuoteRepository;
        this.ntQuoteMapper = ntQuoteMapper;
        this.ntQuoteSearchRepository = ntQuoteSearchRepository;
    }

    /**
     * Save a ntQuote.
     *
     * @param ntQuoteDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteDTO save(NtQuoteDTO ntQuoteDTO) {
        LOG.debug("Request to save NtQuote : {}", ntQuoteDTO);
        NtQuote ntQuote = ntQuoteMapper.toEntity(ntQuoteDTO);
        ntQuote = ntQuoteRepository.save(ntQuote);
        ntQuoteSearchRepository.index(ntQuote);
        return ntQuoteMapper.toDto(ntQuote);
    }

    /**
     * Update a ntQuote.
     *
     * @param ntQuoteDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteDTO update(NtQuoteDTO ntQuoteDTO) {
        LOG.debug("Request to update NtQuote : {}", ntQuoteDTO);
        NtQuote ntQuote = ntQuoteMapper.toEntity(ntQuoteDTO);
        ntQuote = ntQuoteRepository.save(ntQuote);
        ntQuoteSearchRepository.index(ntQuote);
        return ntQuoteMapper.toDto(ntQuote);
    }

    /**
     * Partially update a ntQuote.
     *
     * @param ntQuoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteDTO> partialUpdate(NtQuoteDTO ntQuoteDTO) {
        LOG.debug("Request to partially update NtQuote : {}", ntQuoteDTO);

        return ntQuoteRepository
            .findById(ntQuoteDTO.getId())
            .map(existingNtQuote -> {
                ntQuoteMapper.partialUpdate(existingNtQuote, ntQuoteDTO);

                return existingNtQuote;
            })
            .map(ntQuoteRepository::save)
            .map(savedNtQuote -> {
                ntQuoteSearchRepository.index(savedNtQuote);
                return savedNtQuote;
            })
            .map(ntQuoteMapper::toDto);
    }

    /**
     * Get one ntQuote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuote : {}", id);
        return ntQuoteRepository.findById(id).map(ntQuoteMapper::toDto);
    }

    /**
     * Delete the ntQuote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuote : {}", id);
        ntQuoteRepository.deleteById(id);
        ntQuoteSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuote corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuotes for query {}", query);
        return ntQuoteSearchRepository.search(query, pageable).map(ntQuoteMapper::toDto);
    }
}
