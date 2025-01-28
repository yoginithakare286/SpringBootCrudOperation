package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteContractReviewInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteContractReviewInformationRepository
    extends JpaRepository<NtQuoteContractReviewInformation, Long>, JpaSpecificationExecutor<NtQuoteContractReviewInformation> {}
