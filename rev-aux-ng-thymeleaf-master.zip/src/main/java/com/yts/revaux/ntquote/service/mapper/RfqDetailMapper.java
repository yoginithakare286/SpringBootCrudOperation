package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RfqDetail} and its DTO {@link RfqDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface RfqDetailMapper extends EntityMapper<RfqDetailDTO, RfqDetail> {}
