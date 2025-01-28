package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.repository.RevAuxUserRepository;
import com.yts.revaux.ntquote.repository.search.RevAuxUserSearchRepository;
import com.yts.revaux.ntquote.service.criteria.RevAuxUserCriteria;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import com.yts.revaux.ntquote.service.mapper.RevAuxUserMapper;
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
 * Service for executing complex queries for {@link RevAuxUser} entities in the database.
 * The main input is a {@link RevAuxUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link RevAuxUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RevAuxUserQueryService extends QueryService<RevAuxUser> {

    private static final Logger LOG = LoggerFactory.getLogger(RevAuxUserQueryService.class);

    private final RevAuxUserRepository revAuxUserRepository;

    private final RevAuxUserMapper revAuxUserMapper;

    private final RevAuxUserSearchRepository revAuxUserSearchRepository;

    public RevAuxUserQueryService(
        RevAuxUserRepository revAuxUserRepository,
        RevAuxUserMapper revAuxUserMapper,
        RevAuxUserSearchRepository revAuxUserSearchRepository
    ) {
        this.revAuxUserRepository = revAuxUserRepository;
        this.revAuxUserMapper = revAuxUserMapper;
        this.revAuxUserSearchRepository = revAuxUserSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link RevAuxUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RevAuxUserDTO> findByCriteria(RevAuxUserCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RevAuxUser> specification = createSpecification(criteria);
        return revAuxUserRepository.findAll(specification, page).map(revAuxUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RevAuxUserCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<RevAuxUser> specification = createSpecification(criteria);
        return revAuxUserRepository.count(specification);
    }

    /**
     * Function to convert {@link RevAuxUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RevAuxUser> createSpecification(RevAuxUserCriteria criteria) {
        Specification<RevAuxUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RevAuxUser_.id));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), RevAuxUser_.phoneNumber));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPincode(), RevAuxUser_.pincode));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), RevAuxUser_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), RevAuxUser_.state));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), RevAuxUser_.country));
            }
            if (criteria.getPreferredLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferredLanguage(), RevAuxUser_.preferredLanguage));
            }
            if (criteria.getInternalUserId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getInternalUserId(), root ->
                        root.join(RevAuxUser_.internalUser, JoinType.LEFT).get(User_.id)
                    )
                );
            }
            if (criteria.getPermissionsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPermissionsId(), root ->
                        root.join(RevAuxUser_.permissions, JoinType.LEFT).get(PermissionMaster_.id)
                    )
                );
            }
        }
        return specification;
    }
}
