package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.PermissionMaster;
import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.service.dto.PermissionMasterDTO;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PermissionMaster} and its DTO {@link PermissionMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface PermissionMasterMapper extends EntityMapper<PermissionMasterDTO, PermissionMaster> {
    @Mapping(target = "revAuxUser", source = "revAuxUser", qualifiedByName = "revAuxUserId")
    PermissionMasterDTO toDto(PermissionMaster s);

    @Named("revAuxUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RevAuxUserDTO toDtoRevAuxUserId(RevAuxUser revAuxUser);
}
