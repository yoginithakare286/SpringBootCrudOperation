package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteComponentDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteComponentDetailRepository
    extends JpaRepository<NtQuoteComponentDetail, Long>, JpaSpecificationExecutor<NtQuoteComponentDetail> {}
