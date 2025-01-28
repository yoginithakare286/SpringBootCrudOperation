package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuote} and its DTO {@link NtQuoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteMapper extends EntityMapper<NtQuoteDTO, NtQuote> {
    @Mapping(target = "rfqDetail", source = "rfqDetail", qualifiedByName = "rfqDetailId")
    @Mapping(target = "ntQuoteProjectApproval", source = "ntQuoteProjectApproval", qualifiedByName = "ntQuoteProjectApprovalId")
    NtQuoteDTO toDto(NtQuote s);

    @Named("rfqDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RfqDetailDTO toDtoRfqDetailId(RfqDetail rfqDetail);

    @Named("ntQuoteProjectApprovalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteProjectApprovalDTO toDtoNtQuoteProjectApprovalId(NtQuoteProjectApproval ntQuoteProjectApproval);
}
