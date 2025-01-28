package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteComments;
import com.yts.revaux.ntquote.repository.NtQuoteCommentsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCommentsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCommentsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCommentsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteComments}.
 */
@Service
@Transactional
public class NtQuoteCommentsService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCommentsService.class);

    private final NtQuoteCommentsRepository ntQuoteCommentsRepository;

    private final NtQuoteCommentsMapper ntQuoteCommentsMapper;

    private final NtQuoteCommentsSearchRepository ntQuoteCommentsSearchRepository;

    public NtQuoteCommentsService(
        NtQuoteCommentsRepository ntQuoteCommentsRepository,
        NtQuoteCommentsMapper ntQuoteCommentsMapper,
        NtQuoteCommentsSearchRepository ntQuoteCommentsSearchRepository
    ) {
        this.ntQuoteCommentsRepository = ntQuoteCommentsRepository;
        this.ntQuoteCommentsMapper = ntQuoteCommentsMapper;
        this.ntQuoteCommentsSearchRepository = ntQuoteCommentsSearchRepository;
    }

    /**
     * Save a ntQuoteComments.
     *
     * @param ntQuoteCommentsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCommentsDTO save(NtQuoteCommentsDTO ntQuoteCommentsDTO) {
        LOG.debug("Request to save NtQuoteComments : {}", ntQuoteCommentsDTO);
        NtQuoteComments ntQuoteComments = ntQuoteCommentsMapper.toEntity(ntQuoteCommentsDTO);
        ntQuoteComments = ntQuoteCommentsRepository.save(ntQuoteComments);
        ntQuoteCommentsSearchRepository.index(ntQuoteComments);
        return ntQuoteCommentsMapper.toDto(ntQuoteComments);
    }

    /**
     * Update a ntQuoteComments.
     *
     * @param ntQuoteCommentsDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCommentsDTO update(NtQuoteCommentsDTO ntQuoteCommentsDTO) {
        LOG.debug("Request to update NtQuoteComments : {}", ntQuoteCommentsDTO);
        NtQuoteComments ntQuoteComments = ntQuoteCommentsMapper.toEntity(ntQuoteCommentsDTO);
        ntQuoteComments = ntQuoteCommentsRepository.save(ntQuoteComments);
        ntQuoteCommentsSearchRepository.index(ntQuoteComments);
        return ntQuoteCommentsMapper.toDto(ntQuoteComments);
    }

    /**
     * Partially update a ntQuoteComments.
     *
     * @param ntQuoteCommentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteCommentsDTO> partialUpdate(NtQuoteCommentsDTO ntQuoteCommentsDTO) {
        LOG.debug("Request to partially update NtQuoteComments : {}", ntQuoteCommentsDTO);

        return ntQuoteCommentsRepository
            .findById(ntQuoteCommentsDTO.getId())
            .map(existingNtQuoteComments -> {
                ntQuoteCommentsMapper.partialUpdate(existingNtQuoteComments, ntQuoteCommentsDTO);

                return existingNtQuoteComments;
            })
            .map(ntQuoteCommentsRepository::save)
            .map(savedNtQuoteComments -> {
                ntQuoteCommentsSearchRepository.index(savedNtQuoteComments);
                return savedNtQuoteComments;
            })
            .map(ntQuoteCommentsMapper::toDto);
    }

    /**
     * Get all the ntQuoteComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCommentsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all NtQuoteComments");
        return ntQuoteCommentsRepository.findAll(pageable).map(ntQuoteCommentsMapper::toDto);
    }

    /**
     * Get one ntQuoteComments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteCommentsDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteComments : {}", id);
        return ntQuoteCommentsRepository.findById(id).map(ntQuoteCommentsMapper::toDto);
    }

    /**
     * Delete the ntQuoteComments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteComments : {}", id);
        ntQuoteCommentsRepository.deleteById(id);
        ntQuoteCommentsSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteComments corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCommentsDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteComments for query {}", query);
        return ntQuoteCommentsSearchRepository.search(query, pageable).map(ntQuoteCommentsMapper::toDto);
    }
}
