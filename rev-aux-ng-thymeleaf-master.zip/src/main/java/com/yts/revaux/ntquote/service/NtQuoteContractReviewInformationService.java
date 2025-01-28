package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import com.yts.revaux.ntquote.repository.NtQuoteContractReviewInformationRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteContractReviewInformationSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteContractReviewInformationDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteContractReviewInformationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation}.
 */
@Service
@Transactional
public class NtQuoteContractReviewInformationService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteContractReviewInformationService.class);

    private final NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository;

    private final NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper;

    private final NtQuoteContractReviewInformationSearchRepository ntQuoteContractReviewInformationSearchRepository;

    public NtQuoteContractReviewInformationService(
        NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository,
        NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper,
        NtQuoteContractReviewInformationSearchRepository ntQuoteContractReviewInformationSearchRepository
    ) {
        this.ntQuoteContractReviewInformationRepository = ntQuoteContractReviewInformationRepository;
        this.ntQuoteContractReviewInformationMapper = ntQuoteContractReviewInformationMapper;
        this.ntQuoteContractReviewInformationSearchRepository = ntQuoteContractReviewInformationSearchRepository;
    }

    /**
     * Save a ntQuoteContractReviewInformation.
     *
     * @param ntQuoteContractReviewInformationDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteContractReviewInformationDTO save(NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO) {
        LOG.debug("Request to save NtQuoteContractReviewInformation : {}", ntQuoteContractReviewInformationDTO);
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation = ntQuoteContractReviewInformationMapper.toEntity(
            ntQuoteContractReviewInformationDTO
        );
        ntQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.save(ntQuoteContractReviewInformation);
        ntQuoteContractReviewInformationSearchRepository.index(ntQuoteContractReviewInformation);
        return ntQuoteContractReviewInformationMapper.toDto(ntQuoteContractReviewInformation);
    }

    /**
     * Update a ntQuoteContractReviewInformation.
     *
     * @param ntQuoteContractReviewInformationDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteContractReviewInformationDTO update(NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO) {
        LOG.debug("Request to update NtQuoteContractReviewInformation : {}", ntQuoteContractReviewInformationDTO);
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation = ntQuoteContractReviewInformationMapper.toEntity(
            ntQuoteContractReviewInformationDTO
        );
        ntQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.save(ntQuoteContractReviewInformation);
        ntQuoteContractReviewInformationSearchRepository.index(ntQuoteContractReviewInformation);
        return ntQuoteContractReviewInformationMapper.toDto(ntQuoteContractReviewInformation);
    }

    /**
     * Partially update a ntQuoteContractReviewInformation.
     *
     * @param ntQuoteContractReviewInformationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteContractReviewInformationDTO> partialUpdate(
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO
    ) {
        LOG.debug("Request to partially update NtQuoteContractReviewInformation : {}", ntQuoteContractReviewInformationDTO);

        return ntQuoteContractReviewInformationRepository
            .findById(ntQuoteContractReviewInformationDTO.getId())
            .map(existingNtQuoteContractReviewInformation -> {
                ntQuoteContractReviewInformationMapper.partialUpdate(
                    existingNtQuoteContractReviewInformation,
                    ntQuoteContractReviewInformationDTO
                );

                return existingNtQuoteContractReviewInformation;
            })
            .map(ntQuoteContractReviewInformationRepository::save)
            .map(savedNtQuoteContractReviewInformation -> {
                ntQuoteContractReviewInformationSearchRepository.index(savedNtQuoteContractReviewInformation);
                return savedNtQuoteContractReviewInformation;
            })
            .map(ntQuoteContractReviewInformationMapper::toDto);
    }

    /**
     * Get one ntQuoteContractReviewInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteContractReviewInformationDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteContractReviewInformation : {}", id);
        return ntQuoteContractReviewInformationRepository.findById(id).map(ntQuoteContractReviewInformationMapper::toDto);
    }

    /**
     * Delete the ntQuoteContractReviewInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteContractReviewInformation : {}", id);
        ntQuoteContractReviewInformationRepository.deleteById(id);
        ntQuoteContractReviewInformationSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteContractReviewInformation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteContractReviewInformationDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteContractReviewInformations for query {}", query);
        return ntQuoteContractReviewInformationSearchRepository.search(query, pageable).map(ntQuoteContractReviewInformationMapper::toDto);
    }
}
