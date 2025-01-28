package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.repository.BuyerRfqPricesDetailRepository;
import com.yts.revaux.ntquote.repository.search.BuyerRfqPricesDetailSearchRepository;
import com.yts.revaux.ntquote.service.criteria.BuyerRfqPricesDetailCriteria;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
import com.yts.revaux.ntquote.service.mapper.BuyerRfqPricesDetailMapper;
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
 * Service for executing complex queries for {@link BuyerRfqPricesDetail} entities in the database.
 * The main input is a {@link BuyerRfqPricesDetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BuyerRfqPricesDetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuyerRfqPricesDetailQueryService extends QueryService<BuyerRfqPricesDetail> {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerRfqPricesDetailQueryService.class);

    private final BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository;

    private final BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper;

    private final BuyerRfqPricesDetailSearchRepository buyerRfqPricesDetailSearchRepository;

    public BuyerRfqPricesDetailQueryService(
        BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository,
        BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper,
        BuyerRfqPricesDetailSearchRepository buyerRfqPricesDetailSearchRepository
    ) {
        this.buyerRfqPricesDetailRepository = buyerRfqPricesDetailRepository;
        this.buyerRfqPricesDetailMapper = buyerRfqPricesDetailMapper;
        this.buyerRfqPricesDetailSearchRepository = buyerRfqPricesDetailSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link BuyerRfqPricesDetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuyerRfqPricesDetailDTO> findByCriteria(BuyerRfqPricesDetailCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BuyerRfqPricesDetail> specification = createSpecification(criteria);
        return buyerRfqPricesDetailRepository.findAll(specification, page).map(buyerRfqPricesDetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BuyerRfqPricesDetailCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<BuyerRfqPricesDetail> specification = createSpecification(criteria);
        return buyerRfqPricesDetailRepository.count(specification);
    }

    /**
     * Function to convert {@link BuyerRfqPricesDetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BuyerRfqPricesDetail> createSpecification(BuyerRfqPricesDetailCriteria criteria) {
        Specification<BuyerRfqPricesDetail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BuyerRfqPricesDetail_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), BuyerRfqPricesDetail_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), BuyerRfqPricesDetail_.uid));
            }
            if (criteria.getLine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLine(), BuyerRfqPricesDetail_.line));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialId(), BuyerRfqPricesDetail_.materialId));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), BuyerRfqPricesDetail_.quantity));
            }
            if (criteria.getEstUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstUnitPrice(), BuyerRfqPricesDetail_.estUnitPrice));
            }
            if (criteria.getActUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActUnitPrice(), BuyerRfqPricesDetail_.actUnitPrice));
            }
            if (criteria.getAwardFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAwardFlag(), BuyerRfqPricesDetail_.awardFlag));
            }
            if (criteria.getQuoteId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuoteId(), BuyerRfqPricesDetail_.quoteId));
            }
            if (criteria.getReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedDate(), BuyerRfqPricesDetail_.receivedDate));
            }
            if (criteria.getLeadDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeadDays(), BuyerRfqPricesDetail_.leadDays));
            }
            if (criteria.getRank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRank(), BuyerRfqPricesDetail_.rank));
            }
            if (criteria.getSplitQuantityFlag() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getSplitQuantityFlag(), BuyerRfqPricesDetail_.splitQuantityFlag)
                );
            }
            if (criteria.getMaterialDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialDescription(), BuyerRfqPricesDetail_.materialDescription)
                );
            }
            if (criteria.getLastUpdated() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastUpdated(), BuyerRfqPricesDetail_.lastUpdated));
            }
            if (criteria.getInviteRaFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInviteRaFlag(), BuyerRfqPricesDetail_.inviteRaFlag));
            }
            if (criteria.getAwardAcceptancesDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAwardAcceptancesDate(), BuyerRfqPricesDetail_.awardAcceptancesDate)
                );
            }
            if (criteria.getOrderAcceptancesDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOrderAcceptancesDate(), BuyerRfqPricesDetail_.orderAcceptancesDate)
                );
            }
            if (criteria.getOrderAcceptancesFlag() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOrderAcceptancesFlag(), BuyerRfqPricesDetail_.orderAcceptancesFlag)
                );
            }
            if (criteria.getMaterialName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialName(), BuyerRfqPricesDetail_.materialName));
            }
            if (criteria.getMaterialImage() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialImage(), BuyerRfqPricesDetail_.materialImage)
                );
            }
            if (criteria.getTechnicalScrutinyFlag() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTechnicalScrutinyFlag(), BuyerRfqPricesDetail_.technicalScrutinyFlag)
                );
            }
            if (criteria.getVendorAttributes() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getVendorAttributes(), BuyerRfqPricesDetail_.vendorAttributes)
                );
            }
            if (criteria.getMarginFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMarginFactor(), BuyerRfqPricesDetail_.marginFactor));
            }
            if (criteria.getFob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFob(), BuyerRfqPricesDetail_.fob));
            }
            if (criteria.getShippingFactor() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getShippingFactor(), BuyerRfqPricesDetail_.shippingFactor)
                );
            }
            if (criteria.getFreight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreight(), BuyerRfqPricesDetail_.freight));
            }
            if (criteria.getFinalShipmentCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getFinalShipmentCost(), BuyerRfqPricesDetail_.finalShipmentCost)
                );
            }
            if (criteria.getTariff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTariff(), BuyerRfqPricesDetail_.tariff));
            }
            if (criteria.getCalculatedTariffsCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCalculatedTariffsCost(), BuyerRfqPricesDetail_.calculatedTariffsCost)
                );
            }
            if (criteria.getTotalCumberlandPrice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalCumberlandPrice(), BuyerRfqPricesDetail_.totalCumberlandPrice)
                );
            }
            if (criteria.getLandedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandedPrice(), BuyerRfqPricesDetail_.landedPrice));
            }
            if (criteria.getApprovalToGain() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getApprovalToGain(), BuyerRfqPricesDetail_.approvalToGain)
                );
            }
            if (criteria.getMoldSizeMoldWeight() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMoldSizeMoldWeight(), BuyerRfqPricesDetail_.moldSizeMoldWeight)
                );
            }
            if (criteria.getMoldLifeExpectancy() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMoldLifeExpectancy(), BuyerRfqPricesDetail_.moldLifeExpectancy)
                );
            }
            if (criteria.getTotalCostComparison() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalCostComparison(), BuyerRfqPricesDetail_.totalCostComparison)
                );
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLength(), BuyerRfqPricesDetail_.length));
            }
            if (criteria.getWidth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWidth(), BuyerRfqPricesDetail_.width));
            }
            if (criteria.getGuage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuage(), BuyerRfqPricesDetail_.guage));
            }
            if (criteria.getTolerance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTolerance(), BuyerRfqPricesDetail_.tolerance));
            }
            if (criteria.getRfqDetailId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRfqDetailId(), root ->
                        root.join(BuyerRfqPricesDetail_.rfqDetail, JoinType.LEFT).get(RfqDetail_.id)
                    )
                );
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVendorId(), root ->
                        root.join(BuyerRfqPricesDetail_.vendor, JoinType.LEFT).get(VendorProfile_.id)
                    )
                );
            }
        }
        return specification;
    }
}
