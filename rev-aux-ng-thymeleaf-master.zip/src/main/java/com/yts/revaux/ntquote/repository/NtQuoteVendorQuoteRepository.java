package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteVendorQuote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteVendorQuoteRepository
    extends JpaRepository<NtQuoteVendorQuote, Long>, JpaSpecificationExecutor<NtQuoteVendorQuote> {}
