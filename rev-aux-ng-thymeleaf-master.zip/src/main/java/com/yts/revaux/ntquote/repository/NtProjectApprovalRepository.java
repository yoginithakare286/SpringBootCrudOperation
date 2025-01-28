package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.NtProjectApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NtProjectApproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NtProjectApprovalRepository extends JpaRepository<NtProjectApproval, Long>, JpaSpecificationExecutor<NtProjectApproval> {}
