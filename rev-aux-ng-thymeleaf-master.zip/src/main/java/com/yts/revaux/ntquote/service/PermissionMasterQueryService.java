package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.PermissionMaster;
import com.yts.revaux.ntquote.repository.PermissionMasterRepository;
import com.yts.revaux.ntquote.repository.search.PermissionMasterSearchRepository;
import com.yts.revaux.ntquote.service.criteria.PermissionMasterCriteria;
import com.yts.revaux.ntquote.service.dto.PermissionMasterDTO;
import com.yts.revaux.ntquote.service.mapper.PermissionMasterMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PermissionMaster} entities in the database.
 * The main input is a {@link PermissionMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PermissionMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PermissionMasterQueryService extends QueryService<PermissionMaster> {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionMasterQueryService.class);

    private final PermissionMasterRepository permissionMasterRepository;

    private final PermissionMasterMapper permissionMasterMapper;

    private final PermissionMasterSearchRepository permissionMasterSearchRepository;

    public PermissionMasterQueryService(
        PermissionMasterRepository permissionMasterRepository,
        PermissionMasterMapper permissionMasterMapper,
        PermissionMasterSearchRepository permissionMasterSearchRepository
    ) {
        this.permissionMasterRepository = permissionMasterRepository;
        this.permissionMasterMapper = permissionMasterMapper;
        this.permissionMasterSearchRepository = permissionMasterSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link PermissionMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PermissionMasterDTO> findByCriteria(PermissionMasterCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PermissionMaster> specification = createSpecification(criteria);
        return permissionMasterRepository.findAll(specification, page).map(permissionMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PermissionMasterCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PermissionMaster> specification = createSpecification(criteria);
        return permissionMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link PermissionMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PermissionMaster> createSpecification(PermissionMasterCriteria criteria) {
        Specification<PermissionMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PermissionMaster_.id));
            }
            if (criteria.getPermissionGroup() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPermissionGroup(), PermissionMaster_.permissionGroup)
                );
            }
            if (criteria.getPermission() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermission(), PermissionMaster_.permission));
            }
            if (criteria.getRevAuxUserId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRevAuxUserId(), root ->
                        root.join(PermissionMaster_.revAuxUser, JoinType.LEFT).get(RevAuxUser_.id)
                    )
                );
            }
        }
        return specification;
    }
}
