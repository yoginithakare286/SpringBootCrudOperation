package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteProjectApproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteProjectApprovalRepository
    extends JpaRepository<NtQuoteProjectApproval, Long>, JpaSpecificationExecutor<NtQuoteProjectApproval> {}
