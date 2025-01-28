package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputVersionDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteCustomerInputOutputVersion} and its DTO {@link NtQuoteCustomerInputOutputVersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteCustomerInputOutputVersionMapper
    extends EntityMapper<NtQuoteCustomerInputOutputVersionDTO, NtQuoteCustomerInputOutputVersion> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteCustomerInputOutputVersionDTO toDto(NtQuoteCustomerInputOutputVersion s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
