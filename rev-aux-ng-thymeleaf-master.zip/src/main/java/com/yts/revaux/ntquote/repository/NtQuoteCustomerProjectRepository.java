package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtQuoteCustomerProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtQuoteCustomerProjectRepository
    extends JpaRepository<NtQuoteCustomerProject, Long>, JpaSpecificationExecutor<NtQuoteCustomerProject> {}
