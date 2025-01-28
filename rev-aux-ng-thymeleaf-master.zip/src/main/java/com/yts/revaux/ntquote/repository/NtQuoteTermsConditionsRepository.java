package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteTermsConditions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteTermsConditions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteTermsConditionsRepository extends JpaRepository<NtQuoteTermsConditions, Long> {}
