package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.*;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import com.yts.revaux.ntquote.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RevAuxUser} and its DTO {@link RevAuxUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface RevAuxUserMapper extends EntityMapper<RevAuxUserDTO, RevAuxUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    RevAuxUserDTO toDto(RevAuxUser s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "activated", source = "activated")
    @Mapping(target = "langKey", source = "langKey")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "lastModifiedBy", source = "lastModifiedBy")
    @Mapping(target = "lastModifiedDate", source = "lastModifiedDate")
    UserDTO toDtoUserLogin(User user);
}
