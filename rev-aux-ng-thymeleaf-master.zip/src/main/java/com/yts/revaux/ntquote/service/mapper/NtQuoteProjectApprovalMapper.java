package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteProjectApproval} and its DTO {@link NtQuoteProjectApprovalDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteProjectApprovalMapper extends EntityMapper<NtQuoteProjectApprovalDTO, NtQuoteProjectApproval> {}
