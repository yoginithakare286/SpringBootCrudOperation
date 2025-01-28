package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VendorProfile} and its DTO {@link VendorProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface VendorProfileMapper extends EntityMapper<VendorProfileDTO, VendorProfile> {}
