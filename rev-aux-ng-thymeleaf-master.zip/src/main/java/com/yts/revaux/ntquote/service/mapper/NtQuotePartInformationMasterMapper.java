package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuotePartInformationMaster} and its DTO {@link NtQuotePartInformationMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuotePartInformationMasterMapper extends EntityMapper<NtQuotePartInformationMasterDTO, NtQuotePartInformationMaster> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuotePartInformationMasterDTO toDto(NtQuotePartInformationMaster s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
