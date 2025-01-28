package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationMasterRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationMasterDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster}.
 */
@Service
@Transactional
public class NtQuotePartInformationMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationMasterService.class);

    private final NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository;

    private final NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper;

    private final NtQuotePartInformationMasterSearchRepository ntQuotePartInformationMasterSearchRepository;

    public NtQuotePartInformationMasterService(
        NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository,
        NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper,
        NtQuotePartInformationMasterSearchRepository ntQuotePartInformationMasterSearchRepository
    ) {
        this.ntQuotePartInformationMasterRepository = ntQuotePartInformationMasterRepository;
        this.ntQuotePartInformationMasterMapper = ntQuotePartInformationMasterMapper;
        this.ntQuotePartInformationMasterSearchRepository = ntQuotePartInformationMasterSearchRepository;
    }

    /**
     * Save a ntQuotePartInformationMaster.
     *
     * @param ntQuotePartInformationMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuotePartInformationMasterDTO save(NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO) {
        LOG.debug("Request to save NtQuotePartInformationMaster : {}", ntQuotePartInformationMasterDTO);
        NtQuotePartInformationMaster ntQuotePartInformationMaster = ntQuotePartInformationMasterMapper.toEntity(
            ntQuotePartInformationMasterDTO
        );
        ntQuotePartInformationMaster = ntQuotePartInformationMasterRepository.save(ntQuotePartInformationMaster);
        ntQuotePartInformationMasterSearchRepository.index(ntQuotePartInformationMaster);
        return ntQuotePartInformationMasterMapper.toDto(ntQuotePartInformationMaster);
    }

    /**
     * Update a ntQuotePartInformationMaster.
     *
     * @param ntQuotePartInformationMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuotePartInformationMasterDTO update(NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO) {
        LOG.debug("Request to update NtQuotePartInformationMaster : {}", ntQuotePartInformationMasterDTO);
        NtQuotePartInformationMaster ntQuotePartInformationMaster = ntQuotePartInformationMasterMapper.toEntity(
            ntQuotePartInformationMasterDTO
        );
        ntQuotePartInformationMaster = ntQuotePartInformationMasterRepository.save(ntQuotePartInformationMaster);
        ntQuotePartInformationMasterSearchRepository.index(ntQuotePartInformationMaster);
        return ntQuotePartInformationMasterMapper.toDto(ntQuotePartInformationMaster);
    }

    /**
     * Partially update a ntQuotePartInformationMaster.
     *
     * @param ntQuotePartInformationMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuotePartInformationMasterDTO> partialUpdate(NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO) {
        LOG.debug("Request to partially update NtQuotePartInformationMaster : {}", ntQuotePartInformationMasterDTO);

        return ntQuotePartInformationMasterRepository
            .findById(ntQuotePartInformationMasterDTO.getId())
            .map(existingNtQuotePartInformationMaster -> {
                ntQuotePartInformationMasterMapper.partialUpdate(existingNtQuotePartInformationMaster, ntQuotePartInformationMasterDTO);

                return existingNtQuotePartInformationMaster;
            })
            .map(ntQuotePartInformationMasterRepository::save)
            .map(savedNtQuotePartInformationMaster -> {
                ntQuotePartInformationMasterSearchRepository.index(savedNtQuotePartInformationMaster);
                return savedNtQuotePartInformationMaster;
            })
            .map(ntQuotePartInformationMasterMapper::toDto);
    }

    /**
     * Get one ntQuotePartInformationMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuotePartInformationMasterDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuotePartInformationMaster : {}", id);
        return ntQuotePartInformationMasterRepository.findById(id).map(ntQuotePartInformationMasterMapper::toDto);
    }

    /**
     * Delete the ntQuotePartInformationMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuotePartInformationMaster : {}", id);
        ntQuotePartInformationMasterRepository.deleteById(id);
        ntQuotePartInformationMasterSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuotePartInformationMaster corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuotePartInformationMasterDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuotePartInformationMasters for query {}", query);
        return ntQuotePartInformationMasterSearchRepository.search(query, pageable).map(ntQuotePartInformationMasterMapper::toDto);
    }
}
