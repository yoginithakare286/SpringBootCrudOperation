package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuyerRfqPricesDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyerRfqPricesDetailRepository
    extends JpaRepository<BuyerRfqPricesDetail, Long>, JpaSpecificationExecutor<BuyerRfqPricesDetail> {}
