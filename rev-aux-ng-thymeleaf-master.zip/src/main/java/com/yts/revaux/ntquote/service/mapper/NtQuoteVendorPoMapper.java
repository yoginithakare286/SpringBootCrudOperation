package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorPoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteVendorPo} and its DTO {@link NtQuoteVendorPoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteVendorPoMapper extends EntityMapper<NtQuoteVendorPoDTO, NtQuoteVendorPo> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteVendorPoDTO toDto(NtQuoteVendorPo s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
