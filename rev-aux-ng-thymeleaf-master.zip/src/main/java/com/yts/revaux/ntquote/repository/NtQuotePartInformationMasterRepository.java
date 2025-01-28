package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuotePartInformationMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuotePartInformationMasterRepository
    extends JpaRepository<NtQuotePartInformationMaster, Long>, JpaSpecificationExecutor<NtQuotePartInformationMaster> {}
