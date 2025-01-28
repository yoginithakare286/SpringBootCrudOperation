package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteCustomerInputOutputVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteCustomerInputOutputVersionRepository
    extends JpaRepository<NtQuoteCustomerInputOutputVersion, Long>, JpaSpecificationExecutor<NtQuoteCustomerInputOutputVersion> {}
