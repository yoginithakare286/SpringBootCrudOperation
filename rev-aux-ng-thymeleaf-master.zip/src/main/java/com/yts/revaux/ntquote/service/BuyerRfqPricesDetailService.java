package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.repository.BuyerRfqPricesDetailRepository;
import com.yts.revaux.ntquote.repository.search.BuyerRfqPricesDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
import com.yts.revaux.ntquote.service.mapper.BuyerRfqPricesDetailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail}.
 */
@Service
@Transactional
public class BuyerRfqPricesDetailService {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerRfqPricesDetailService.class);

    private final BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository;

    private final BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper;

    private final BuyerRfqPricesDetailSearchRepository buyerRfqPricesDetailSearchRepository;

    public BuyerRfqPricesDetailService(
        BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository,
        BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper,
        BuyerRfqPricesDetailSearchRepository buyerRfqPricesDetailSearchRepository
    ) {
        this.buyerRfqPricesDetailRepository = buyerRfqPricesDetailRepository;
        this.buyerRfqPricesDetailMapper = buyerRfqPricesDetailMapper;
        this.buyerRfqPricesDetailSearchRepository = buyerRfqPricesDetailSearchRepository;
    }

    /**
     * Save a buyerRfqPricesDetail.
     *
     * @param buyerRfqPricesDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public BuyerRfqPricesDetailDTO save(BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO) {
        LOG.debug("Request to save BuyerRfqPricesDetail : {}", buyerRfqPricesDetailDTO);
        BuyerRfqPricesDetail buyerRfqPricesDetail = buyerRfqPricesDetailMapper.toEntity(buyerRfqPricesDetailDTO);
        buyerRfqPricesDetail = buyerRfqPricesDetailRepository.save(buyerRfqPricesDetail);
        buyerRfqPricesDetailSearchRepository.index(buyerRfqPricesDetail);
        return buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);
    }

    /**
     * Update a buyerRfqPricesDetail.
     *
     * @param buyerRfqPricesDetailDTO the entity to save.
     * @return the persisted entity.
     */
    public BuyerRfqPricesDetailDTO update(BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO) {
        LOG.debug("Request to update BuyerRfqPricesDetail : {}", buyerRfqPricesDetailDTO);
        BuyerRfqPricesDetail buyerRfqPricesDetail = buyerRfqPricesDetailMapper.toEntity(buyerRfqPricesDetailDTO);
        buyerRfqPricesDetail = buyerRfqPricesDetailRepository.save(buyerRfqPricesDetail);
        buyerRfqPricesDetailSearchRepository.index(buyerRfqPricesDetail);
        return buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);
    }

    /**
     * Partially update a buyerRfqPricesDetail.
     *
     * @param buyerRfqPricesDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BuyerRfqPricesDetailDTO> partialUpdate(BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO) {
        LOG.debug("Request to partially update BuyerRfqPricesDetail : {}", buyerRfqPricesDetailDTO);

        return buyerRfqPricesDetailRepository
            .findById(buyerRfqPricesDetailDTO.getId())
            .map(existingBuyerRfqPricesDetail -> {
                buyerRfqPricesDetailMapper.partialUpdate(existingBuyerRfqPricesDetail, buyerRfqPricesDetailDTO);

                return existingBuyerRfqPricesDetail;
            })
            .map(buyerRfqPricesDetailRepository::save)
            .map(savedBuyerRfqPricesDetail -> {
                buyerRfqPricesDetailSearchRepository.index(savedBuyerRfqPricesDetail);
                return savedBuyerRfqPricesDetail;
            })
            .map(buyerRfqPricesDetailMapper::toDto);
    }

    /**
     * Get one buyerRfqPricesDetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuyerRfqPricesDetailDTO> findOne(Long id) {
        LOG.debug("Request to get BuyerRfqPricesDetail : {}", id);
        return buyerRfqPricesDetailRepository.findById(id).map(buyerRfqPricesDetailMapper::toDto);
    }

    /**
     * Delete the buyerRfqPricesDetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete BuyerRfqPricesDetail : {}", id);
        buyerRfqPricesDetailRepository.deleteById(id);
        buyerRfqPricesDetailSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the buyerRfqPricesDetail corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BuyerRfqPricesDetailDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of BuyerRfqPricesDetails for query {}", query);
        return buyerRfqPricesDetailSearchRepository.search(query, pageable).map(buyerRfqPricesDetailMapper::toDto);
    }
}
