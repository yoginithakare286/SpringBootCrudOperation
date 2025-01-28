package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationVersionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuotePartInformationVersion} and its DTO {@link NtQuotePartInformationVersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuotePartInformationVersionMapper extends EntityMapper<NtQuotePartInformationVersionDTO, NtQuotePartInformationVersion> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuotePartInformationVersionDTO toDto(NtQuotePartInformationVersion s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
