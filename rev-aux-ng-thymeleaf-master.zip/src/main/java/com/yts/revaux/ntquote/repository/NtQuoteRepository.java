package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteRepository extends JpaRepository<NtQuote, Long>, JpaSpecificationExecutor<NtQuote> {}
