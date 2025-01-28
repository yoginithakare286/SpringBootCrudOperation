package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.RfqDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RfqDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfqDetailRepository extends JpaRepository<RfqDetail, Long>, JpaSpecificationExecutor<RfqDetail> {}
