package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteProjectConsiderations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteProjectConsiderationsRepository
    extends JpaRepository<NtQuoteProjectConsiderations, Long>, JpaSpecificationExecutor<NtQuoteProjectConsiderations> {}
