package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteComments;
import com.yts.revaux.ntquote.service.dto.NtQuoteCommentsDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteComments} and its DTO {@link NtQuoteCommentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteCommentsMapper extends EntityMapper<NtQuoteCommentsDTO, NtQuoteComments> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteCommentsDTO toDto(NtQuoteComments s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
