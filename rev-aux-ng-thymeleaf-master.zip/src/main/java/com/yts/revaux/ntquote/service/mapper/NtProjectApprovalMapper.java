package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtProjectApproval;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.service.dto.NtProjectApprovalDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtProjectApproval} and its DTO {@link NtProjectApprovalDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtProjectApprovalMapper extends EntityMapper<NtProjectApprovalDTO, NtProjectApproval> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtProjectApprovalDTO toDto(NtProjectApproval s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
