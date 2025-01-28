package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteCustomerInputOutputMaster} and its DTO {@link NtQuoteCustomerInputOutputMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteCustomerInputOutputMasterMapper
    extends EntityMapper<NtQuoteCustomerInputOutputMasterDTO, NtQuoteCustomerInputOutputMaster> {}
