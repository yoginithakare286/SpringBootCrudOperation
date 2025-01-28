package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerProjectRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerProjectSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerProjectCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerProjectDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerProjectMapper;
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
 * Service for executing complex queries for {@link NtQuoteCustomerProject} entities in the database.
 * The main input is a {@link NtQuoteCustomerProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteCustomerProjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteCustomerProjectQueryService extends QueryService<NtQuoteCustomerProject> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerProjectQueryService.class);

    private final NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository;

    private final NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper;

    private final NtQuoteCustomerProjectSearchRepository ntQuoteCustomerProjectSearchRepository;

    public NtQuoteCustomerProjectQueryService(
        NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository,
        NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper,
        NtQuoteCustomerProjectSearchRepository ntQuoteCustomerProjectSearchRepository
    ) {
        this.ntQuoteCustomerProjectRepository = ntQuoteCustomerProjectRepository;
        this.ntQuoteCustomerProjectMapper = ntQuoteCustomerProjectMapper;
        this.ntQuoteCustomerProjectSearchRepository = ntQuoteCustomerProjectSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteCustomerProjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerProjectDTO> findByCriteria(NtQuoteCustomerProjectCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteCustomerProject> specification = createSpecification(criteria);
        return ntQuoteCustomerProjectRepository.findAll(specification, page).map(ntQuoteCustomerProjectMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteCustomerProjectCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteCustomerProject> specification = createSpecification(criteria);
        return ntQuoteCustomerProjectRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteCustomerProjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteCustomerProject> createSpecification(NtQuoteCustomerProjectCriteria criteria) {
        Specification<NtQuoteCustomerProject> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteCustomerProject_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteCustomerProject_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteCustomerProject_.uid));
            }
            if (criteria.getQsf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQsf(), NtQuoteCustomerProject_.qsf));
            }
            if (criteria.getRev() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRev(), NtQuoteCustomerProject_.rev));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), NtQuoteCustomerProject_.date));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCustomerName(), NtQuoteCustomerProject_.customerName)
                );
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), NtQuoteCustomerProject_.contactName));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), NtQuoteCustomerProject_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), NtQuoteCustomerProject_.email));
            }
            if (criteria.getOverallProjectRiskEvaluation() != null) {
                specification = specification.and(
                    buildStringSpecification(
                        criteria.getOverallProjectRiskEvaluation(),
                        NtQuoteCustomerProject_.overallProjectRiskEvaluation
                    )
                );
            }
            if (criteria.getAssessmentDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAssessmentDate(), NtQuoteCustomerProject_.assessmentDate)
                );
            }
            if (criteria.getReAssessmentDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getReAssessmentDate(), NtQuoteCustomerProject_.reAssessmentDate)
                );
            }
            if (criteria.getProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectName(), NtQuoteCustomerProject_.projectName));
            }
            if (criteria.getProjectInformation() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getProjectInformation(), NtQuoteCustomerProject_.projectInformation)
                );
            }
            if (criteria.getProjectManager() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getProjectManager(), NtQuoteCustomerProject_.projectManager)
                );
            }
            if (criteria.getProjectRequirement() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getProjectRequirement(), NtQuoteCustomerProject_.projectRequirement)
                );
            }
            if (criteria.getLengthOfProject() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLengthOfProject(), NtQuoteCustomerProject_.lengthOfProject)
                );
            }
            if (criteria.getNewMold() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewMold(), NtQuoteCustomerProject_.newMold));
            }
            if (criteria.getTransferMold() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getTransferMold(), NtQuoteCustomerProject_.transferMold)
                );
            }
            if (criteria.getContactReviewDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getContactReviewDate(), NtQuoteCustomerProject_.contactReviewDate)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteCustomerProject_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteCustomerProject_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteCustomerProject_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteCustomerProject_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteCustomerProject_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
