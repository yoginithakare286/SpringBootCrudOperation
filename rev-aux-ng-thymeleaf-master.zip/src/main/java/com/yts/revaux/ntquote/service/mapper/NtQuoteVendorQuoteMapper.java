package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteVendorQuote} and its DTO {@link NtQuoteVendorQuoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteVendorQuoteMapper extends EntityMapper<NtQuoteVendorQuoteDTO, NtQuoteVendorQuote> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteVendorQuoteDTO toDto(NtQuoteVendorQuote s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
