package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteTermsConditions;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteTermsConditionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteTermsConditions} and its DTO {@link NtQuoteTermsConditionsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteTermsConditionsMapper extends EntityMapper<NtQuoteTermsConditionsDTO, NtQuoteTermsConditions> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteTermsConditionsDTO toDto(NtQuoteTermsConditions s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
