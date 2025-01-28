package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.repository.VendorProfileRepository;
import com.yts.revaux.ntquote.repository.search.VendorProfileSearchRepository;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
import com.yts.revaux.ntquote.service.mapper.VendorProfileMapper;
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
 * Service Implementation for managing {@link com.yts.revaux.ntquote.domain.VendorProfile}.
 */
@Service
@Transactional
public class VendorProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(VendorProfileService.class);

    private final VendorProfileRepository vendorProfileRepository;

    private final VendorProfileMapper vendorProfileMapper;

    private final VendorProfileSearchRepository vendorProfileSearchRepository;

    public VendorProfileService(
        VendorProfileRepository vendorProfileRepository,
        VendorProfileMapper vendorProfileMapper,
        VendorProfileSearchRepository vendorProfileSearchRepository
    ) {
        this.vendorProfileRepository = vendorProfileRepository;
        this.vendorProfileMapper = vendorProfileMapper;
        this.vendorProfileSearchRepository = vendorProfileSearchRepository;
    }

    /**
     * Save a vendorProfile.
     *
     * @param vendorProfileDTO the entity to save.
     * @return the persisted entity.
     */
    public VendorProfileDTO save(VendorProfileDTO vendorProfileDTO) {
        LOG.debug("Request to save VendorProfile : {}", vendorProfileDTO);
        VendorProfile vendorProfile = vendorProfileMapper.toEntity(vendorProfileDTO);
        vendorProfile = vendorProfileRepository.save(vendorProfile);
        vendorProfileSearchRepository.index(vendorProfile);
        return vendorProfileMapper.toDto(vendorProfile);
    }

    /**
     * Update a vendorProfile.
     *
     * @param vendorProfileDTO the entity to save.
     * @return the persisted entity.
     */
    public VendorProfileDTO update(VendorProfileDTO vendorProfileDTO) {
        LOG.debug("Request to update VendorProfile : {}", vendorProfileDTO);
        VendorProfile vendorProfile = vendorProfileMapper.toEntity(vendorProfileDTO);
        vendorProfile = vendorProfileRepository.save(vendorProfile);
        vendorProfileSearchRepository.index(vendorProfile);
        return vendorProfileMapper.toDto(vendorProfile);
    }

    /**
     * Partially update a vendorProfile.
     *
     * @param vendorProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VendorProfileDTO> partialUpdate(VendorProfileDTO vendorProfileDTO) {
        LOG.debug("Request to partially update VendorProfile : {}", vendorProfileDTO);

        return vendorProfileRepository
            .findById(vendorProfileDTO.getId())
            .map(existingVendorProfile -> {
                vendorProfileMapper.partialUpdate(existingVendorProfile, vendorProfileDTO);

                return existingVendorProfile;
            })
            .map(vendorProfileRepository::save)
            .map(savedVendorProfile -> {
                vendorProfileSearchRepository.index(savedVendorProfile);
                return savedVendorProfile;
            })
            .map(vendorProfileMapper::toDto);
    }

    /**
     *  Get all the vendorProfiles where BuyerRfqPricesDetail is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VendorProfileDTO> findAllWhereBuyerRfqPricesDetailIsNull() {
        LOG.debug("Request to get all vendorProfiles where BuyerRfqPricesDetail is null");
        return StreamSupport.stream(vendorProfileRepository.findAll().spliterator(), false)
            .filter(vendorProfile -> vendorProfile.getBuyerRfqPricesDetail() == null)
            .map(vendorProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vendorProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VendorProfileDTO> findOne(Long id) {
        LOG.debug("Request to get VendorProfile : {}", id);
        return vendorProfileRepository.findById(id).map(vendorProfileMapper::toDto);
    }

    /**
     * Delete the vendorProfile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete VendorProfile : {}", id);
        vendorProfileRepository.deleteById(id);
        vendorProfileSearchRepository.deleteFromIndexById(id);
    }

    /**
     * Search for the vendorProfile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorProfileDTO> search(String query, Pageable pageable) {
        LOG.debug("Request to search for a page of VendorProfiles for query {}", query);
        return vendorProfileSearchRepository.search(query, pageable).map(vendorProfileMapper::toDto);
    }
}
