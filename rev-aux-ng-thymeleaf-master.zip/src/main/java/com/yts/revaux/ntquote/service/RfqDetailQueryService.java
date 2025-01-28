package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.repository.RfqDetailRepository;
import com.yts.revaux.ntquote.repository.search.RfqDetailSearchRepository;
import com.yts.revaux.ntquote.service.criteria.RfqDetailCriteria;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import com.yts.revaux.ntquote.service.mapper.RfqDetailMapper;
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
 * Service for executing complex queries for {@link RfqDetail} entities in the database.
 * The main input is a {@link RfqDetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link RfqDetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RfqDetailQueryService extends QueryService<RfqDetail> {

    private static final Logger LOG = LoggerFactory.getLogger(RfqDetailQueryService.class);

    private final RfqDetailRepository rfqDetailRepository;

    private final RfqDetailMapper rfqDetailMapper;

    private final RfqDetailSearchRepository rfqDetailSearchRepository;

    public RfqDetailQueryService(
        RfqDetailRepository rfqDetailRepository,
        RfqDetailMapper rfqDetailMapper,
        RfqDetailSearchRepository rfqDetailSearchRepository
    ) {
        this.rfqDetailRepository = rfqDetailRepository;
        this.rfqDetailMapper = rfqDetailMapper;
        this.rfqDetailSearchRepository = rfqDetailSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link RfqDetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RfqDetailDTO> findByCriteria(RfqDetailCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RfqDetail> specification = createSpecification(criteria);
        return rfqDetailRepository.findAll(specification, page).map(rfqDetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RfqDetailCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<RfqDetail> specification = createSpecification(criteria);
        return rfqDetailRepository.count(specification);
    }

    /**
     * Function to convert {@link RfqDetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RfqDetail> createSpecification(RfqDetailCriteria criteria) {
        Specification<RfqDetail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RfqDetail_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), RfqDetail_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), RfqDetail_.uid));
            }
            if (criteria.getRfqId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfqId(), RfqDetail_.rfqId));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), RfqDetail_.orderDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), RfqDetail_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), RfqDetail_.endDate));
            }
            if (criteria.getItemDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemDescription(), RfqDetail_.itemDescription));
            }
            if (criteria.getRfqStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfqStatus(), RfqDetail_.rfqStatus));
            }
            if (criteria.getRfqType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfqType(), RfqDetail_.rfqType));
            }
            if (criteria.getCustomer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomer(), RfqDetail_.customer));
            }
            if (criteria.getRfqReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRfqReceivedDate(), RfqDetail_.rfqReceivedDate));
            }
            if (criteria.getQuoteDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteDueDate(), RfqDetail_.quoteDueDate));
            }
            if (criteria.getPart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPart(), RfqDetail_.part));
            }
            if (criteria.getBuyer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuyer(), RfqDetail_.buyer));
            }
            if (criteria.getExpectedLaunch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpectedLaunch(), RfqDetail_.expectedLaunch));
            }
            if (criteria.getRequestor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestor(), RfqDetail_.requestor));
            }
            if (criteria.getRaStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRaStatus(), RfqDetail_.raStatus));
            }
            if (criteria.getIsDelete() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsDelete(), RfqDetail_.isDelete));
            }
            if (criteria.getCustomerFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerFeedback(), RfqDetail_.customerFeedback));
            }
            if (criteria.getBuyerRfqPricesDetailId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getBuyerRfqPricesDetailId(), root ->
                        root.join(RfqDetail_.buyerRfqPricesDetail, JoinType.LEFT).get(BuyerRfqPricesDetail_.id)
                    )
                );
            }
        }
        return specification;
    }
}
