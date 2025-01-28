package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.PermissionMaster;
import com.yts.revaux.ntquote.repository.PermissionMasterRepository;
import com.yts.revaux.ntquote.repository.search.PermissionMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.PermissionMasterDTO;
import com.yts.revaux.ntquote.service.mapper.PermissionMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.PermissionMaster}.
 */
@Service
@Transactional
public class PermissionMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionMasterService.class);

    private final PermissionMasterRepository permissionMasterRepository;

    private final PermissionMasterMapper permissionMasterMapper;

    private final PermissionMasterSearchRepository permissionMasterSearchRepository;

    public PermissionMasterService(
        PermissionMasterRepository permissionMasterRepository,
        PermissionMasterMapper permissionMasterMapper,
        PermissionMasterSearchRepository permissionMasterSearchRepository
    ) {
        this.permissionMasterRepository = permissionMasterRepository;
        this.permissionMasterMapper = permissionMasterMapper;
        this.permissionMasterSearchRepository = permissionMasterSearchRepository;
    }

    /**
     * Save a permissionMaster.
     *
     * @param permissionMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public PermissionMasterDTO save(PermissionMasterDTO permissionMasterDTO) {
        LOG.debug("Request to save PermissionMaster : {}", permissionMasterDTO);
        PermissionMaster permissionMaster = permissionMasterMapper.toEntity(permissionMasterDTO);
        permissionMaster = permissionMasterRepository.save(permissionMaster);
        permissionMasterSearchRepository.index(permissionMaster);
        return permissionMasterMapper.toDto(permissionMaster);
    }

    /**
     * Update a permissionMaster.
     *
     * @param permissionMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public PermissionMasterDTO update(PermissionMasterDTO permissionMasterDTO) {
        LOG.debug("Request to update PermissionMaster : {}", permissionMasterDTO);
        PermissionMaster permissionMaster = permissionMasterMapper.toEntity(permissionMasterDTO);
        permissionMaster = permissionMasterRepository.save(permissionMaster);
        permissionMasterSearchRepository.index(permissionMaster);
        return permissionMasterMapper.toDto(permissionMaster);
    }

    /**
     * Partially update a permissionMaster.
     *
     * @param permissionMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PermissionMasterDTO> partialUpdate(PermissionMasterDTO permissionMasterDTO) {
        LOG.debug("Request to partially update PermissionMaster : {}", permissionMasterDTO);

        return permissionMasterRepository
            .findById(permissionMasterDTO.getId())
            .map(existingPermissionMaster -> {
                permissionMasterMapper.partialUpdate(existingPermissionMaster, permissionMasterDTO);

                return existingPermissionMaster;
            })
            .map(permissionMasterRepository::save)
            .map(savedPermissionMaster -> {
                permissionMasterSearchRepository.index(savedPermissionMaster);
                return savedPermissionMaster;
            })
            .map(permissionMasterMapper::toDto);
    }

    /**
     * Get one permissionMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PermissionMasterDTO> findOne(Long id) {
        LOG.debug("Request to get PermissionMaster : {}", id);
        return permissionMasterRepository.findById(id).map(permissionMasterMapper::toDto);
    }

    /**
     * Delete the permissionMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PermissionMaster : {}", id);
        permissionMasterRepository.deleteById(id);
        permissionMasterSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the permissionMaster corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PermissionMasterDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of PermissionMasters for query {}", query);
        return permissionMasterSearchRepository.search(query, pageable).map(permissionMasterMapper::toDto);
    }
}
