package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.repository.RevAuxUserRepository;
import com.yts.revaux.ntquote.repository.UserRepository;
import com.yts.revaux.ntquote.repository.search.RevAuxUserSearchRepository;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import com.yts.revaux.ntquote.service.mapper.RevAuxUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.RevAuxUser}.
 */
@Service
@Transactional
public class RevAuxUserService {

    private static final Logger LOG = LoggerFactory.getLogger(RevAuxUserService.class);

    private final RevAuxUserRepository revAuxUserRepository;

    private final RevAuxUserMapper revAuxUserMapper;

    private final RevAuxUserSearchRepository revAuxUserSearchRepository;

    private final UserRepository userRepository;

    public RevAuxUserService(
        RevAuxUserRepository revAuxUserRepository,
        RevAuxUserMapper revAuxUserMapper,
        RevAuxUserSearchRepository revAuxUserSearchRepository,
        UserRepository userRepository
    ) {
        this.revAuxUserRepository = revAuxUserRepository;
        this.revAuxUserMapper = revAuxUserMapper;
        this.revAuxUserSearchRepository = revAuxUserSearchRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a revAuxUser.
     *
     * @param revAuxUserDTO the entity to save.
     * @return the persisted entity.
     */
    public RevAuxUserDTO save(RevAuxUserDTO revAuxUserDTO) {
        LOG.debug("Request to save RevAuxUser : {}", revAuxUserDTO);
        RevAuxUser revAuxUser = revAuxUserMapper.toEntity(revAuxUserDTO);
        Long userId = revAuxUser.getInternalUser().getId();
        userRepository.findById(userId).ifPresent(revAuxUser::internalUser);
        revAuxUser = revAuxUserRepository.save(revAuxUser);
        revAuxUserSearchRepository.index(revAuxUser);
        return revAuxUserMapper.toDto(revAuxUser);
    }

    /**
     * Update a revAuxUser.
     *
     * @param revAuxUserDTO the entity to save.
     * @return the persisted entity.
     */
    public RevAuxUserDTO update(RevAuxUserDTO revAuxUserDTO) {
        LOG.debug("Request to update RevAuxUser : {}", revAuxUserDTO);
        RevAuxUser revAuxUser = revAuxUserMapper.toEntity(revAuxUserDTO);
        revAuxUser = revAuxUserRepository.save(revAuxUser);
        revAuxUserSearchRepository.index(revAuxUser);
        return revAuxUserMapper.toDto(revAuxUser);
    }

    /**
     * Partially update a revAuxUser.
     *
     * @param revAuxUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RevAuxUserDTO> partialUpdate(RevAuxUserDTO revAuxUserDTO) {
        LOG.debug("Request to partially update RevAuxUser : {}", revAuxUserDTO);

        return revAuxUserRepository
            .findById(revAuxUserDTO.getId())
            .map(existingRevAuxUser -> {
                revAuxUserMapper.partialUpdate(existingRevAuxUser, revAuxUserDTO);

                return existingRevAuxUser;
            })
            .map(revAuxUserRepository::save)
            .map(savedRevAuxUser -> {
                revAuxUserSearchRepository.index(savedRevAuxUser);
                return savedRevAuxUser;
            })
            .map(revAuxUserMapper::toDto);
    }

    /**
     * Get all the revAuxUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RevAuxUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return revAuxUserRepository.findAllWithEagerRelationships(pageable).map(revAuxUserMapper::toDto);
    }

    /**
     * Get one revAuxUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RevAuxUserDTO> findOne(Long id) {
        LOG.debug("Request to get RevAuxUser : {}", id);
        return revAuxUserRepository.findOneWithEagerRelationships(id).map(revAuxUserMapper::toDto);
    }

    /**
     * Delete the revAuxUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RevAuxUser : {}", id);
        revAuxUserRepository.deleteById(id);
        revAuxUserSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the revAuxUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RevAuxUserDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of RevAuxUsers for query {}", query);
        return revAuxUserSearchRepository.search(query, pageable).map(revAuxUserMapper::toDto);
    }
}
