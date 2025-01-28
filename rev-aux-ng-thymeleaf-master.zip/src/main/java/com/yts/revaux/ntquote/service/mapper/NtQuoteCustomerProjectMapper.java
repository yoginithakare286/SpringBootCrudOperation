package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerProjectDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteCustomerProject} and its DTO {@link NtQuoteCustomerProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteCustomerProjectMapper extends EntityMapper<NtQuoteCustomerProjectDTO, NtQuoteCustomerProject> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteCustomerProjectDTO toDto(NtQuoteCustomerProject s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
