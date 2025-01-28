package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.PermissionMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PermissionMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermissionMasterRepository extends JpaRepository<PermissionMaster, Long>, JpaSpecificationExecutor<PermissionMaster> {}
