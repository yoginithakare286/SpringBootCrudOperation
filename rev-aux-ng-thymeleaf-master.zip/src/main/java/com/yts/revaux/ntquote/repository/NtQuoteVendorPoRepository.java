package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteVendorPo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteVendorPoRepository extends JpaRepository<NtQuoteVendorPo, Long>, JpaSpecificationExecutor<NtQuoteVendorPo> {}
