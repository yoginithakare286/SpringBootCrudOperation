package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.repository.VendorProfileRepository;
import com.yts.revaux.ntquote.repository.search.VendorProfileSearchRepository;
import com.yts.revaux.ntquote.service.criteria.VendorProfileCriteria;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
import com.yts.revaux.ntquote.service.mapper.VendorProfileMapper;
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
 * Service for executing complex queries for {@link VendorProfile} entities in the database.
 * The main input is a {@link VendorProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link VendorProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorProfileQueryService extends QueryService<VendorProfile> {

    private static final Logger LOG = LoggerFactory.getLogger(VendorProfileQueryService.class);

    private final VendorProfileRepository vendorProfileRepository;

    private final VendorProfileMapper vendorProfileMapper;

    private final VendorProfileSearchRepository vendorProfileSearchRepository;

    public VendorProfileQueryService(
        VendorProfileRepository vendorProfileRepository,
        VendorProfileMapper vendorProfileMapper,
        VendorProfileSearchRepository vendorProfileSearchRepository
    ) {
        this.vendorProfileRepository = vendorProfileRepository;
        this.vendorProfileMapper = vendorProfileMapper;
        this.vendorProfileSearchRepository = vendorProfileSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link VendorProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorProfileDTO> findByCriteria(VendorProfileCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VendorProfile> specification = createSpecification(criteria);
        return vendorProfileRepository.findAll(specification, page).map(vendorProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorProfileCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<VendorProfile> specification = createSpecification(criteria);
        return vendorProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link VendorProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VendorProfile> createSpecification(VendorProfileCriteria criteria) {
        Specification<VendorProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VendorProfile_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), VendorProfile_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), VendorProfile_.uid));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorId(), VendorProfile_.vendorId));
            }
            if (criteria.getVendorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorName(), VendorProfile_.vendorName));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), VendorProfile_.contact));
            }
            if (criteria.getEntryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntryDate(), VendorProfile_.entryDate));
            }
            if (criteria.getTradeCurrencyId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradeCurrencyId(), VendorProfile_.tradeCurrencyId));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), VendorProfile_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), VendorProfile_.address2));
            }
            if (criteria.getAddress3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress3(), VendorProfile_.address3));
            }
            if (criteria.getMailId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMailId(), VendorProfile_.mailId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), VendorProfile_.status));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRating(), VendorProfile_.rating));
            }
            if (criteria.getIsDeleteFlag() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsDeleteFlag(), VendorProfile_.isDeleteFlag));
            }
            if (criteria.getRelatedBuyerUid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelatedBuyerUid(), VendorProfile_.relatedBuyerUid));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), VendorProfile_.country));
            }
            if (criteria.getCountryFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryFlag(), VendorProfile_.countryFlag));
            }
            if (criteria.getBuyerRfqPricesDetailId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getBuyerRfqPricesDetailId(), root ->
                        root.join(VendorProfile_.buyerRfqPricesDetail, JoinType.LEFT).get(BuyerRfqPricesDetail_.id)
                    )
                );
            }
        }
        return specification;
    }
}
