package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import com.yts.revaux.ntquote.repository.NtQuoteComponentDetailRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteComponentDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteComponentDetailDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteComponentDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteComponentDetail}.
 */
@Service
@Transactional
public class NtQuoteComponentDetailService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteComponentDetailService.class);

    private final NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository;

    private final NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper;

    private final NtQuoteComponentDetailSearchRepository ntQuoteComponentDetailSearchRepository;

    public NtQuoteComponentDetailService(
        NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository,
        NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper,
        NtQuoteComponentDetailSearchRepository ntQuoteComponentDetailSearchRepository
    ) {
        this.ntQuoteComponentDetailRepository = ntQuoteComponentDetailRepository;
        this.ntQuoteComponentDetailMapper = ntQuoteComponentDetailMapper;
        this.ntQuoteComponentDetailSearchRepository = ntQuoteComponentDetailSearchRepository;
    }

    /**
     * Save a ntQuoteComponentDetail.
     *
     * @param ntQuoteComponentDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteComponentDetailDTO save(NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO) {
        LOG.debug("Request to save NtQuoteComponentDetail : {}", ntQuoteComponentDetailDTO);
        NtQuoteComponentDetail ntQuoteComponentDetail = ntQuoteComponentDetailMapper.toEntity(ntQuoteComponentDetailDTO);
        ntQuoteComponentDetail = ntQuoteComponentDetailRepository.save(ntQuoteComponentDetail);
        ntQuoteComponentDetailSearchRepository.index(ntQuoteComponentDetail);
        return ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);
    }

    /**
     * Update a ntQuoteComponentDetail.
     *
     * @param ntQuoteComponentDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteComponentDetailDTO update(NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO) {
        LOG.debug("Request to update NtQuoteComponentDetail : {}", ntQuoteComponentDetailDTO);
        NtQuoteComponentDetail ntQuoteComponentDetail = ntQuoteComponentDetailMapper.toEntity(ntQuoteComponentDetailDTO);
        ntQuoteComponentDetail = ntQuoteComponentDetailRepository.save(ntQuoteComponentDetail);
        ntQuoteComponentDetailSearchRepository.index(ntQuoteComponentDetail);
        return ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);
    }

    /**
     * Partially update a ntQuoteComponentDetail.
     *
     * @param ntQuoteComponentDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteComponentDetailDTO> partialUpdate(NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO) {
        LOG.debug("Request to partially update NtQuoteComponentDetail : {}", ntQuoteComponentDetailDTO);

        return ntQuoteComponentDetailRepository
            .findById(ntQuoteComponentDetailDTO.getId())
            .map(existingNtQuoteComponentDetail -> {
                ntQuoteComponentDetailMapper.partialUpdate(existingNtQuoteComponentDetail, ntQuoteComponentDetailDTO);

                return existingNtQuoteComponentDetail;
            })
            .map(ntQuoteComponentDetailRepository::save)
            .map(savedNtQuoteComponentDetail -> {
                ntQuoteComponentDetailSearchRepository.index(savedNtQuoteComponentDetail);
                return savedNtQuoteComponentDetail;
            })
            .map(ntQuoteComponentDetailMapper::toDto);
    }

    /**
     * Get one ntQuoteComponentDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteComponentDetailDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteComponentDetail : {}", id);
        return ntQuoteComponentDetailRepository.findById(id).map(ntQuoteComponentDetailMapper::toDto);
    }

    /**
     * Delete the ntQuoteComponentDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteComponentDetail : {}", id);
        ntQuoteComponentDetailRepository.deleteById(id);
        ntQuoteComponentDetailSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteComponentDetail corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteComponentDetailDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteComponentDetails for query {}", query);
        return ntQuoteComponentDetailSearchRepository.search(query, pageable).map(ntQuoteComponentDetailMapper::toDto);
    }
}
