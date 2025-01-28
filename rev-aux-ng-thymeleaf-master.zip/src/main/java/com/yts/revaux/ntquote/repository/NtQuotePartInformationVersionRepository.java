package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuotePartInformationVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuotePartInformationVersionRepository
    extends JpaRepository<NtQuotePartInformationVersion, Long>, JpaSpecificationExecutor<NtQuotePartInformationVersion> {}
