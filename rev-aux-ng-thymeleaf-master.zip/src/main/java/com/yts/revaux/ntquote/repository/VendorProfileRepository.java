package com.yts.revaux.ntquote.repository;

import com.yts.revaux.ntquote.domain.VendorProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VendorProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorProfileRepository extends JpaRepository<VendorProfile, Long>, JpaSpecificationExecutor<VendorProfile> {}
