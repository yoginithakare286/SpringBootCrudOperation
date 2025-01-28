package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerProjectRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerProjectSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerProjectDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerProjectMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerProject}.
 */
@Service
@Transactional
public class NtQuoteCustomerProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerProjectService.class);

    private final NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository;

    private final NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper;

    private final NtQuoteCustomerProjectSearchRepository ntQuoteCustomerProjectSearchRepository;

    public NtQuoteCustomerProjectService(
        NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository,
        NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper,
        NtQuoteCustomerProjectSearchRepository ntQuoteCustomerProjectSearchRepository
    ) {
        this.ntQuoteCustomerProjectRepository = ntQuoteCustomerProjectRepository;
        this.ntQuoteCustomerProjectMapper = ntQuoteCustomerProjectMapper;
        this.ntQuoteCustomerProjectSearchRepository = ntQuoteCustomerProjectSearchRepository;
    }

    /**
     * Save a ntQuoteCustomerProject.
     *
     * @param ntQuoteCustomerProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerProjectDTO save(NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO) {
        LOG.debug("Request to save NtQuoteCustomerProject : {}", ntQuoteCustomerProjectDTO);
        NtQuoteCustomerProject ntQuoteCustomerProject = ntQuoteCustomerProjectMapper.toEntity(ntQuoteCustomerProjectDTO);
        ntQuoteCustomerProject = ntQuoteCustomerProjectRepository.save(ntQuoteCustomerProject);
        ntQuoteCustomerProjectSearchRepository.index(ntQuoteCustomerProject);
        return ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);
    }

    /**
     * Update a ntQuoteCustomerProject.
     *
     * @param ntQuoteCustomerProjectDTO the entity to save.
     * @return the persisted entity.
     */
    public NtQuoteCustomerProjectDTO update(NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO) {
        LOG.debug("Request to update NtQuoteCustomerProject : {}", ntQuoteCustomerProjectDTO);
        NtQuoteCustomerProject ntQuoteCustomerProject = ntQuoteCustomerProjectMapper.toEntity(ntQuoteCustomerProjectDTO);
        ntQuoteCustomerProject = ntQuoteCustomerProjectRepository.save(ntQuoteCustomerProject);
        ntQuoteCustomerProjectSearchRepository.index(ntQuoteCustomerProject);
        return ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);
    }

    /**
     * Partially update a ntQuoteCustomerProject.
     *
     * @param ntQuoteCustomerProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NtQuoteCustomerProjectDTO> partialUpdate(NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO) {
        LOG.debug("Request to partially update NtQuoteCustomerProject : {}", ntQuoteCustomerProjectDTO);

        return ntQuoteCustomerProjectRepository
            .findById(ntQuoteCustomerProjectDTO.getId())
            .map(existingNtQuoteCustomerProject -> {
                ntQuoteCustomerProjectMapper.partialUpdate(existingNtQuoteCustomerProject, ntQuoteCustomerProjectDTO);

                return existingNtQuoteCustomerProject;
            })
            .map(ntQuoteCustomerProjectRepository::save)
            .map(savedNtQuoteCustomerProject -> {
                ntQuoteCustomerProjectSearchRepository.index(savedNtQuoteCustomerProject);
                return savedNtQuoteCustomerProject;
            })
            .map(ntQuoteCustomerProjectMapper::toDto);
    }

    /**
     * Get one ntQuoteCustomerProject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NtQuoteCustomerProjectDTO> findOne(Long id) {
        LOG.debug("Request to get NtQuoteCustomerProject : {}", id);
        return ntQuoteCustomerProjectRepository.findById(id).map(ntQuoteCustomerProjectMapper::toDto);
    }

    /**
     * Delete the ntQuoteCustomerProject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete NtQuoteCustomerProject : {}", id);
        ntQuoteCustomerProjectRepository.deleteById(id);
        ntQuoteCustomerProjectSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the ntQuoteCustomerProject corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerProjectDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of NtQuoteCustomerProjects for query {}", query);
        return ntQuoteCustomerProjectSearchRepository.search(query, pageable).map(ntQuoteCustomerProjectMapper::toDto);
    }
}
