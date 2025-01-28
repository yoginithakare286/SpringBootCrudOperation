package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import com.yts.revaux.ntquote.service.dto.NtQuoteContractReviewInformationDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteContractReviewInformation} and its DTO {@link NtQuoteContractReviewInformationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteContractReviewInformationMapper
    extends EntityMapper<NtQuoteContractReviewInformationDTO, NtQuoteContractReviewInformation> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    NtQuoteContractReviewInformationDTO toDto(NtQuoteContractReviewInformation s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);
}
