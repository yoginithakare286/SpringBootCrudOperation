package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteCustomerInputOutputMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteCustomerInputOutputMasterRepository extends JpaRepository<NtQuoteCustomerInputOutputMaster, Long> {}
