package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.repository.RfqDetailRepository;
import com.yts.revaux.ntquote.repository.search.RfqDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import com.yts.revaux.ntquote.service.mapper.RfqDetailMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.RfqDetail}.
 */
@Service
@Transactional
public class RfqDetailService {

    private static final Logger LOG = LoggerFactory.getLogger(RfqDetailService.class);

    private final RfqDetailRepository rfqDetailRepository;

    private final RfqDetailMapper rfqDetailMapper;

    private final RfqDetailSearchRepository rfqDetailSearchRepository;

    public RfqDetailService(
        RfqDetailRepository rfqDetailRepository,
        RfqDetailMapper rfqDetailMapper,
        RfqDetailSearchRepository rfqDetailSearchRepository
    ) {
        this.rfqDetailRepository = rfqDetailRepository;
        this.rfqDetailMapper = rfqDetailMapper;
        this.rfqDetailSearchRepository = rfqDetailSearchRepository;
    }

    /**
     * Save a rfqDetail.
     *
     * @param rfqDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public RfqDetailDTO save(RfqDetailDTO rfqDetailDTO) {
        LOG.debug("Request to save RfqDetail : {}", rfqDetailDTO);
        RfqDetail rfqDetail = rfqDetailMapper.toEntity(rfqDetailDTO);
        rfqDetail = rfqDetailRepository.save(rfqDetail);
        rfqDetailSearchRepository.index(rfqDetail);
        return rfqDetailMapper.toDto(rfqDetail);
    }

    /**
     * Update a rfqDetail.
     *
     * @param rfqDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public RfqDetailDTO update(RfqDetailDTO rfqDetailDTO) {
        LOG.debug("Request to update RfqDetail : {}", rfqDetailDTO);
        RfqDetail rfqDetail = rfqDetailMapper.toEntity(rfqDetailDTO);
        rfqDetail = rfqDetailRepository.save(rfqDetail);
        rfqDetailSearchRepository.index(rfqDetail);
        return rfqDetailMapper.toDto(rfqDetail);
    }

    /**
     * Partially update a rfqDetail.
     *
     * @param rfqDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RfqDetailDTO> partialUpdate(RfqDetailDTO rfqDetailDTO) {
        LOG.debug("Request to partially update RfqDetail : {}", rfqDetailDTO);

        return rfqDetailRepository
            .findById(rfqDetailDTO.getId())
            .map(existingRfqDetail -> {
                rfqDetailMapper.partialUpdate(existingRfqDetail, rfqDetailDTO);

                return existingRfqDetail;
            })
            .map(rfqDetailRepository::save)
            .map(savedRfqDetail -> {
                rfqDetailSearchRepository.index(savedRfqDetail);
                return savedRfqDetail;
            })
            .map(rfqDetailMapper::toDto);
    }

    /**
     *  Get all the rfqDetails where BuyerRfqPricesDetail is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RfqDetailDTO> findAllWhereBuyerRfqPricesDetailIsNull() {
        LOG.debug("Request to get all rfqDetails where BuyerRfqPricesDetail is null");
        return StreamSupport.stream(rfqDetailRepository.findAll().spliterator(), false)
            .filter(rfqDetail -> rfqDetail.getBuyerRfqPricesDetail() == null)
            .map(rfqDetailMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rfqDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RfqDetailDTO> findOne(Long id) {
        LOG.debug("Request to get RfqDetail : {}", id);
        return rfqDetailRepository.findById(id).map(rfqDetailMapper::toDto);
    }

    /**
     * Delete the rfqDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete RfqDetail : {}", id);
        rfqDetailRepository.deleteById(id);
        rfqDetailSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the rfqDetail corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RfqDetailDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of RfqDetails for query {}", query);
        return rfqDetailSearchRepository.search(query, pageable).map(rfqDetailMapper::toDto);
    }
}
