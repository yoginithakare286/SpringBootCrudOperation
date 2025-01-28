package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerPoDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteCustomerPo} and its DTO {@link NtQuoteCustomerPoDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteCustomerPoMapper extends EntityMapper<NtQuoteCustomerPoDTO, NtQuoteCustomerPo> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteCustomerPoDTO toDto(NtQuoteCustomerPo s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
