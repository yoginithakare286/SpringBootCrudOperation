package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteCustomerPo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteCustomerPoRepository extends JpaRepository<NtQuoteCustomerPo, Long>, JpaSpecificationExecutor<NtQuoteCustomerPo> {}
