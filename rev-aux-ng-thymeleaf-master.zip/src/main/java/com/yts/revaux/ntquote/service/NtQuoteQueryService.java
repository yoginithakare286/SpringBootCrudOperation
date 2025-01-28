package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.repository.NtQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteMapper;
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
 * Service for executing complex queries for {@link NtQuote} entities in the database.
 * The main input is a {@link NtQuoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteQueryService extends QueryService<NtQuote> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteQueryService.class);

    private final NtQuoteRepository ntQuoteRepository;

    private final NtQuoteMapper ntQuoteMapper;

    private final NtQuoteSearchRepository ntQuoteSearchRepository;

    public NtQuoteQueryService(
        NtQuoteRepository ntQuoteRepository,
        NtQuoteMapper ntQuoteMapper,
        NtQuoteSearchRepository ntQuoteSearchRepository
    ) {
        this.ntQuoteRepository = ntQuoteRepository;
        this.ntQuoteMapper = ntQuoteMapper;
        this.ntQuoteSearchRepository = ntQuoteSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteDTO> findByCriteria(NtQuoteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuote> specification = createSpecification(criteria);
        return ntQuoteRepository.findAll(specification, page).map(ntQuoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuote> specification = createSpecification(criteria);
        return ntQuoteRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuote> createSpecification(NtQuoteCriteria criteria) {
        Specification<NtQuote> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuote_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuote_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuote_.uid));
            }
            if (criteria.getQuoteKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuoteKey(), NtQuote_.quoteKey));
            }
            if (criteria.getSalesPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalesPerson(), NtQuote_.salesPerson));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), NtQuote_.customerName));
            }
            if (criteria.getQuoteNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuoteNumber(), NtQuote_.quoteNumber));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), NtQuote_.status));
            }
            if (criteria.getMoldNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoldNumber(), NtQuote_.moldNumber));
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartNumber(), NtQuote_.partNumber));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), NtQuote_.dueDate));
            }
            if (criteria.getMoldManual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoldManual(), NtQuote_.moldManual));
            }
            if (criteria.getCustomerPo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerPo(), NtQuote_.customerPo));
            }
            if (criteria.getVendorQuote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorQuote(), NtQuote_.vendorQuote));
            }
            if (criteria.getVendorPo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorPo(), NtQuote_.vendorPo));
            }
            if (criteria.getCadFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCadFile(), NtQuote_.cadFile));
            }
            if (criteria.getQuotedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuotedPrice(), NtQuote_.quotedPrice));
            }
            if (criteria.getDeliveryTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryTime(), NtQuote_.deliveryTime));
            }
            if (criteria.getQuoteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteDate(), NtQuote_.quoteDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuote_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuote_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuote_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuote_.updatedDate));
            }
            if (criteria.getProjectConsiderationsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProjectConsiderationsId(), root ->
                        root.join(NtQuote_.projectConsiderations, JoinType.LEFT).get(NtQuoteProjectConsiderations_.id)
                    )
                );
            }
            if (criteria.getContractReviewInformationId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getContractReviewInformationId(), root ->
                        root.join(NtQuote_.contractReviewInformations, JoinType.LEFT).get(NtQuoteContractReviewInformation_.id)
                    )
                );
            }
            if (criteria.getCustomerInputOutputVersionId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCustomerInputOutputVersionId(), root ->
                        root.join(NtQuote_.customerInputOutputVersions, JoinType.LEFT).get(NtQuoteCustomerInputOutputVersion_.id)
                    )
                );
            }
            if (criteria.getPartInformationMasterId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPartInformationMasterId(), root ->
                        root.join(NtQuote_.partInformationMasters, JoinType.LEFT).get(NtQuotePartInformationMaster_.id)
                    )
                );
            }
            if (criteria.getCommentsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCommentsId(), root ->
                        root.join(NtQuote_.comments, JoinType.LEFT).get(NtQuoteComments_.id)
                    )
                );
            }
            if (criteria.getTermsConditionsId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTermsConditionsId(), root ->
                        root.join(NtQuote_.termsConditions, JoinType.LEFT).get(NtQuoteTermsConditions_.id)
                    )
                );
            }
            if (criteria.getProjectApprovalId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProjectApprovalId(), root ->
                        root.join(NtQuote_.projectApprovals, JoinType.LEFT).get(NtProjectApproval_.id)
                    )
                );
            }
            if (criteria.getPartInformationVersionId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPartInformationVersionId(), root ->
                        root.join(NtQuote_.partInformationVersions, JoinType.LEFT).get(NtQuotePartInformationVersion_.id)
                    )
                );
            }
            if (criteria.getCustomerPoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCustomerPoId(), root ->
                        root.join(NtQuote_.customerPos, JoinType.LEFT).get(NtQuoteCustomerPo_.id)
                    )
                );
            }
            if (criteria.getVendorQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVendorQuoteId(), root ->
                        root.join(NtQuote_.vendorQuotes, JoinType.LEFT).get(NtQuoteVendorQuote_.id)
                    )
                );
            }
            if (criteria.getVendorPoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVendorPoId(), root ->
                        root.join(NtQuote_.vendorPos, JoinType.LEFT).get(NtQuoteVendorPo_.id)
                    )
                );
            }
            if (criteria.getRfqDetailId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRfqDetailId(), root -> root.join(NtQuote_.rfqDetail, JoinType.LEFT).get(RfqDetail_.id))
                );
            }
            if (criteria.getNtQuoteProjectApprovalId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteProjectApprovalId(), root ->
                        root.join(NtQuote_.ntQuoteProjectApproval, JoinType.LEFT).get(NtQuoteProjectApproval_.id)
                    )
                );
            }
        }
        return specification;
    }
}
