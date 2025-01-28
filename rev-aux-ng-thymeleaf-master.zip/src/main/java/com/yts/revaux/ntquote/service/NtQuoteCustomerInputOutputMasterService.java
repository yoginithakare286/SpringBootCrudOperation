package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputMasterRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerInputOutputMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputMasterDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerInputOutputMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster}.
 */
@Service
@Transactional
public class NtQuoteCustomerInputOutputMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerInputOutputMasterService.class);

    private final NtQuoteCustomerInputOutputMasterRepository ntQuoteCustomerInputOutputMasterRepository;

    private final NtQuoteCustomerInputOutputMasterMapper ntQuoteCustomerInputOutputMasterMapper;

    private final NtQuoteCustomerInputOutputMasterSearchRepository ntQuoteCustomerInputOutputMasterSearchRepository;

    public NtQuoteCustomerInputOutputMasterService(
        NtQuoteCustomerInputOutputMasterRepository ntQuoteCustomerInputOutputMasterRepository,
        NtQuoteCustomerInputOutputMasterMapper ntQuoteCustomerInputOutputMasterMapper,
        NtQuoteCustomerInputOutputMasterSearchRepository ntQuoteCustomerInputOutputMasterSearchRepository
    ) {
        this.ntQuoteCustomerInputOutputMasterRepository = ntQuoteCustomerInputOutputMasterRepository;
        this.ntQuoteCustomerInputOutputMasterMapper = ntQuoteCustomerInputOutputMasterMapper;
        this.ntQuoteCustomerInputOutputMasterSearchRepository = ntQuoteCustomerInputOutputMasterSearchRepository;
    }

    /**
     * Save a ntQuoteCustomerInputOutputMaster.
     *
     * @param ntQuoteCustomerInputOutputMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerInputOutputMasterDTO save(NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO) {
        LOG.debug("Request to save NtQuoteCustomerInputOutputMaster : {}", ntQuoteCustomerInputOutputMasterDTO);
        NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterMapper.toEntity(
            ntQuoteCustomerInputOutputMasterDTO
        );
        ntQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.save(ntQuoteCustomerInputOutputMaster);
        ntQuoteCustomerInputOutputMasterSearchRepository.index(ntQuoteCustomerInputOutputMaster);
        return ntQuoteCustomerInputOutputMasterMapper.toDto(ntQuoteCustomerInputOutputMaster);
    }

    /**
     * Update a ntQuoteCustomerInputOutputMaster.
     *
     * @param ntQuoteCustomerInputOutputMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerInputOutputMasterDTO update(NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO) {
        LOG.debug("Request to update NtQuoteCustomerInputOutputMaster : {}", ntQuoteCustomerInputOutputMasterDTO);
        NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterMapper.toEntity(
            ntQuoteCustomerInputOutputMasterDTO
        );
        ntQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.save(ntQuoteCustomerInputOutputMaster);
        ntQuoteCustomerInputOutputMasterSearchRepository.index(ntQuoteCustomerInputOutputMaster);
        return ntQuoteCustomerInputOutputMasterMapper.toDto(ntQuoteCustomerInputOutputMaster);
    }

    /**
     * Partially update a ntQuoteCustomerInputOutputMaster.
     *
     * @param ntQuoteCustomerInputOutputMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteCustomerInputOutputMasterDTO> partialUpdate(
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO
    ) {
        LOG.debug("Request to partially update NtQuoteCustomerInputOutputMaster : {}", ntQuoteCustomerInputOutputMasterDTO);

        return ntQuoteCustomerInputOutputMasterRepository
            .findById(ntQuoteCustomerInputOutputMasterDTO.getId())
            .map(existingNtQuoteCustomerInputOutputMaster -> {
                ntQuoteCustomerInputOutputMasterMapper.partialUpdate(
                    existingNtQuoteCustomerInputOutputMaster,
                    ntQuoteCustomerInputOutputMasterDTO
                );

                return existingNtQuoteCustomerInputOutputMaster;
            })
            .map(ntQuoteCustomerInputOutputMasterRepository::save)
            .map(savedNtQuoteCustomerInputOutputMaster -> {
                ntQuoteCustomerInputOutputMasterSearchRepository.index(savedNtQuoteCustomerInputOutputMaster);
                return savedNtQuoteCustomerInputOutputMaster;
            })
            .map(ntQuoteCustomerInputOutputMasterMapper::toDto);
    }

    /**
     * Get all the ntQuoteCustomerInputOutputMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerInputOutputMasterDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all NtQuoteCustomerInputOutputMasters");
        return ntQuoteCustomerInputOutputMasterRepository.findAll(pageable).map(ntQuoteCustomerInputOutputMasterMapper::toDto);
    }

    /**
     * Get one ntQuoteCustomerInputOutputMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteCustomerInputOutputMasterDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteCustomerInputOutputMaster : {}", id);
        return ntQuoteCustomerInputOutputMasterRepository.findById(id).map(ntQuoteCustomerInputOutputMasterMapper::toDto);
    }

    /**
     * Delete the ntQuoteCustomerInputOutputMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteCustomerInputOutputMaster : {}", id);
        ntQuoteCustomerInputOutputMasterRepository.deleteById(id);
        ntQuoteCustomerInputOutputMasterSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteCustomerInputOutputMaster corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerInputOutputMasterDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteCustomerInputOutputMasters for query {}", query);
        return ntQuoteCustomerInputOutputMasterSearchRepository.search(query, pageable).map(ntQuoteCustomerInputOutputMasterMapper::toDto);
    }
}
