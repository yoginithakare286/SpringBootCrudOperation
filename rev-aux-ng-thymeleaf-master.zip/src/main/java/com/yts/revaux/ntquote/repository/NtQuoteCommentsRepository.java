package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteComments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteComments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteCommentsRepository extends JpaRepository<NtQuoteComments, Long> {}
