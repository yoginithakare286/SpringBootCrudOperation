package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectConsiderationsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteProjectConsiderations} and its DTO {@link NtQuoteProjectConsiderationsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteProjectConsiderationsMapper extends EntityMapper<NtQuoteProjectConsiderationsDTO, NtQuoteProjectConsiderations> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteProjectConsiderationsDTO toDto(NtQuoteProjectConsiderations s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
