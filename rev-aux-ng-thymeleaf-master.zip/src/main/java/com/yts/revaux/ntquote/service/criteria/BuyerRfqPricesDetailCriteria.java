package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.BuyerRfqPricesDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /buyer-rfq-prices-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerRfqPricesDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter line;

    private StringFilter materialId;

    private IntegerFilter quantity;

    private BigDecimalFilter estUnitPrice;

    private BigDecimalFilter actUnitPrice;

    private StringFilter awardFlag;

    private StringFilter quoteId;

    private ZonedDateTimeFilter receivedDate;

    private BigDecimalFilter leadDays;

    private StringFilter rank;

    private IntegerFilter splitQuantityFlag;

    private StringFilter materialDescription;

    private StringFilter lastUpdated;

    private IntegerFilter inviteRaFlag;

    private ZonedDateTimeFilter awardAcceptancesDate;

    private ZonedDateTimeFilter orderAcceptancesDate;

    private IntegerFilter orderAcceptancesFlag;

    private StringFilter materialName;

    private StringFilter materialImage;

    private IntegerFilter technicalScrutinyFlag;

    private StringFilter vendorAttributes;

    private BigDecimalFilter marginFactor;

    private BigDecimalFilter fob;

    private BigDecimalFilter shippingFactor;

    private BigDecimalFilter freight;

    private BigDecimalFilter finalShipmentCost;

    private BigDecimalFilter tariff;

    private BigDecimalFilter calculatedTariffsCost;

    private BigDecimalFilter totalCumberlandPrice;

    private BigDecimalFilter landedPrice;

    private BigDecimalFilter approvalToGain;

    private StringFilter moldSizeMoldWeight;

    private BigDecimalFilter moldLifeExpectancy;

    private BigDecimalFilter totalCostComparison;

    private StringFilter length;

    private StringFilter width;

    private StringFilter guage;

    private StringFilter tolerance;

    private LongFilter rfqDetailId;

    private LongFilter vendorId;

    private Boolean distinct;

    public BuyerRfqPricesDetailCriteria() {}

    public BuyerRfqPricesDetailCriteria(BuyerRfqPricesDetailCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.line = other.optionalLine().map(StringFilter::copy).orElse(null);
        this.materialId = other.optionalMaterialId().map(StringFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(IntegerFilter::copy).orElse(null);
        this.estUnitPrice = other.optionalEstUnitPrice().map(BigDecimalFilter::copy).orElse(null);
        this.actUnitPrice = other.optionalActUnitPrice().map(BigDecimalFilter::copy).orElse(null);
        this.awardFlag = other.optionalAwardFlag().map(StringFilter::copy).orElse(null);
        this.quoteId = other.optionalQuoteId().map(StringFilter::copy).orElse(null);
        this.receivedDate = other.optionalReceivedDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.leadDays = other.optionalLeadDays().map(BigDecimalFilter::copy).orElse(null);
        this.rank = other.optionalRank().map(StringFilter::copy).orElse(null);
        this.splitQuantityFlag = other.optionalSplitQuantityFlag().map(IntegerFilter::copy).orElse(null);
        this.materialDescription = other.optionalMaterialDescription().map(StringFilter::copy).orElse(null);
        this.lastUpdated = other.optionalLastUpdated().map(StringFilter::copy).orElse(null);
        this.inviteRaFlag = other.optionalInviteRaFlag().map(IntegerFilter::copy).orElse(null);
        this.awardAcceptancesDate = other.optionalAwardAcceptancesDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.orderAcceptancesDate = other.optionalOrderAcceptancesDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.orderAcceptancesFlag = other.optionalOrderAcceptancesFlag().map(IntegerFilter::copy).orElse(null);
        this.materialName = other.optionalMaterialName().map(StringFilter::copy).orElse(null);
        this.materialImage = other.optionalMaterialImage().map(StringFilter::copy).orElse(null);
        this.technicalScrutinyFlag = other.optionalTechnicalScrutinyFlag().map(IntegerFilter::copy).orElse(null);
        this.vendorAttributes = other.optionalVendorAttributes().map(StringFilter::copy).orElse(null);
        this.marginFactor = other.optionalMarginFactor().map(BigDecimalFilter::copy).orElse(null);
        this.fob = other.optionalFob().map(BigDecimalFilter::copy).orElse(null);
        this.shippingFactor = other.optionalShippingFactor().map(BigDecimalFilter::copy).orElse(null);
        this.freight = other.optionalFreight().map(BigDecimalFilter::copy).orElse(null);
        this.finalShipmentCost = other.optionalFinalShipmentCost().map(BigDecimalFilter::copy).orElse(null);
        this.tariff = other.optionalTariff().map(BigDecimalFilter::copy).orElse(null);
        this.calculatedTariffsCost = other.optionalCalculatedTariffsCost().map(BigDecimalFilter::copy).orElse(null);
        this.totalCumberlandPrice = other.optionalTotalCumberlandPrice().map(BigDecimalFilter::copy).orElse(null);
        this.landedPrice = other.optionalLandedPrice().map(BigDecimalFilter::copy).orElse(null);
        this.approvalToGain = other.optionalApprovalToGain().map(BigDecimalFilter::copy).orElse(null);
        this.moldSizeMoldWeight = other.optionalMoldSizeMoldWeight().map(StringFilter::copy).orElse(null);
        this.moldLifeExpectancy = other.optionalMoldLifeExpectancy().map(BigDecimalFilter::copy).orElse(null);
        this.totalCostComparison = other.optionalTotalCostComparison().map(BigDecimalFilter::copy).orElse(null);
        this.length = other.optionalLength().map(StringFilter::copy).orElse(null);
        this.width = other.optionalWidth().map(StringFilter::copy).orElse(null);
        this.guage = other.optionalGuage().map(StringFilter::copy).orElse(null);
        this.tolerance = other.optionalTolerance().map(StringFilter::copy).orElse(null);
        this.rfqDetailId = other.optionalRfqDetailId().map(LongFilter::copy).orElse(null);
        this.vendorId = other.optionalVendorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BuyerRfqPricesDetailCriteria copy() {
        return new BuyerRfqPricesDetailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSrNo() {
        return srNo;
    }

    public Optional<IntegerFilter> optionalSrNo() {
        return Optional.ofNullable(srNo);
    }

    public IntegerFilter srNo() {
        if (srNo == null) {
            setSrNo(new IntegerFilter());
        }
        return srNo;
    }

    public void setSrNo(IntegerFilter srNo) {
        this.srNo = srNo;
    }

    public UUIDFilter getUid() {
        return uid;
    }

    public Optional<UUIDFilter> optionalUid() {
        return Optional.ofNullable(uid);
    }

    public UUIDFilter uid() {
        if (uid == null) {
            setUid(new UUIDFilter());
        }
        return uid;
    }

    public void setUid(UUIDFilter uid) {
        this.uid = uid;
    }

    public StringFilter getLine() {
        return line;
    }

    public Optional<StringFilter> optionalLine() {
        return Optional.ofNullable(line);
    }

    public StringFilter line() {
        if (line == null) {
            setLine(new StringFilter());
        }
        return line;
    }

    public void setLine(StringFilter line) {
        this.line = line;
    }

    public StringFilter getMaterialId() {
        return materialId;
    }

    public Optional<StringFilter> optionalMaterialId() {
        return Optional.ofNullable(materialId);
    }

    public StringFilter materialId() {
        if (materialId == null) {
            setMaterialId(new StringFilter());
        }
        return materialId;
    }

    public void setMaterialId(StringFilter materialId) {
        this.materialId = materialId;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public Optional<IntegerFilter> optionalQuantity() {
        return Optional.ofNullable(quantity);
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            setQuantity(new IntegerFilter());
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getEstUnitPrice() {
        return estUnitPrice;
    }

    public Optional<BigDecimalFilter> optionalEstUnitPrice() {
        return Optional.ofNullable(estUnitPrice);
    }

    public BigDecimalFilter estUnitPrice() {
        if (estUnitPrice == null) {
            setEstUnitPrice(new BigDecimalFilter());
        }
        return estUnitPrice;
    }

    public void setEstUnitPrice(BigDecimalFilter estUnitPrice) {
        this.estUnitPrice = estUnitPrice;
    }

    public BigDecimalFilter getActUnitPrice() {
        return actUnitPrice;
    }

    public Optional<BigDecimalFilter> optionalActUnitPrice() {
        return Optional.ofNullable(actUnitPrice);
    }

    public BigDecimalFilter actUnitPrice() {
        if (actUnitPrice == null) {
            setActUnitPrice(new BigDecimalFilter());
        }
        return actUnitPrice;
    }

    public void setActUnitPrice(BigDecimalFilter actUnitPrice) {
        this.actUnitPrice = actUnitPrice;
    }

    public StringFilter getAwardFlag() {
        return awardFlag;
    }

    public Optional<StringFilter> optionalAwardFlag() {
        return Optional.ofNullable(awardFlag);
    }

    public StringFilter awardFlag() {
        if (awardFlag == null) {
            setAwardFlag(new StringFilter());
        }
        return awardFlag;
    }

    public void setAwardFlag(StringFilter awardFlag) {
        this.awardFlag = awardFlag;
    }

    public StringFilter getQuoteId() {
        return quoteId;
    }

    public Optional<StringFilter> optionalQuoteId() {
        return Optional.ofNullable(quoteId);
    }

    public StringFilter quoteId() {
        if (quoteId == null) {
            setQuoteId(new StringFilter());
        }
        return quoteId;
    }

    public void setQuoteId(StringFilter quoteId) {
        this.quoteId = quoteId;
    }

    public ZonedDateTimeFilter getReceivedDate() {
        return receivedDate;
    }

    public Optional<ZonedDateTimeFilter> optionalReceivedDate() {
        return Optional.ofNullable(receivedDate);
    }

    public ZonedDateTimeFilter receivedDate() {
        if (receivedDate == null) {
            setReceivedDate(new ZonedDateTimeFilter());
        }
        return receivedDate;
    }

    public void setReceivedDate(ZonedDateTimeFilter receivedDate) {
        this.receivedDate = receivedDate;
    }

    public BigDecimalFilter getLeadDays() {
        return leadDays;
    }

    public Optional<BigDecimalFilter> optionalLeadDays() {
        return Optional.ofNullable(leadDays);
    }

    public BigDecimalFilter leadDays() {
        if (leadDays == null) {
            setLeadDays(new BigDecimalFilter());
        }
        return leadDays;
    }

    public void setLeadDays(BigDecimalFilter leadDays) {
        this.leadDays = leadDays;
    }

    public StringFilter getRank() {
        return rank;
    }

    public Optional<StringFilter> optionalRank() {
        return Optional.ofNullable(rank);
    }

    public StringFilter rank() {
        if (rank == null) {
            setRank(new StringFilter());
        }
        return rank;
    }

    public void setRank(StringFilter rank) {
        this.rank = rank;
    }

    public IntegerFilter getSplitQuantityFlag() {
        return splitQuantityFlag;
    }

    public Optional<IntegerFilter> optionalSplitQuantityFlag() {
        return Optional.ofNullable(splitQuantityFlag);
    }

    public IntegerFilter splitQuantityFlag() {
        if (splitQuantityFlag == null) {
            setSplitQuantityFlag(new IntegerFilter());
        }
        return splitQuantityFlag;
    }

    public void setSplitQuantityFlag(IntegerFilter splitQuantityFlag) {
        this.splitQuantityFlag = splitQuantityFlag;
    }

    public StringFilter getMaterialDescription() {
        return materialDescription;
    }

    public Optional<StringFilter> optionalMaterialDescription() {
        return Optional.ofNullable(materialDescription);
    }

    public StringFilter materialDescription() {
        if (materialDescription == null) {
            setMaterialDescription(new StringFilter());
        }
        return materialDescription;
    }

    public void setMaterialDescription(StringFilter materialDescription) {
        this.materialDescription = materialDescription;
    }

    public StringFilter getLastUpdated() {
        return lastUpdated;
    }

    public Optional<StringFilter> optionalLastUpdated() {
        return Optional.ofNullable(lastUpdated);
    }

    public StringFilter lastUpdated() {
        if (lastUpdated == null) {
            setLastUpdated(new StringFilter());
        }
        return lastUpdated;
    }

    public void setLastUpdated(StringFilter lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public IntegerFilter getInviteRaFlag() {
        return inviteRaFlag;
    }

    public Optional<IntegerFilter> optionalInviteRaFlag() {
        return Optional.ofNullable(inviteRaFlag);
    }

    public IntegerFilter inviteRaFlag() {
        if (inviteRaFlag == null) {
            setInviteRaFlag(new IntegerFilter());
        }
        return inviteRaFlag;
    }

    public void setInviteRaFlag(IntegerFilter inviteRaFlag) {
        this.inviteRaFlag = inviteRaFlag;
    }

    public ZonedDateTimeFilter getAwardAcceptancesDate() {
        return awardAcceptancesDate;
    }

    public Optional<ZonedDateTimeFilter> optionalAwardAcceptancesDate() {
        return Optional.ofNullable(awardAcceptancesDate);
    }

    public ZonedDateTimeFilter awardAcceptancesDate() {
        if (awardAcceptancesDate == null) {
            setAwardAcceptancesDate(new ZonedDateTimeFilter());
        }
        return awardAcceptancesDate;
    }

    public void setAwardAcceptancesDate(ZonedDateTimeFilter awardAcceptancesDate) {
        this.awardAcceptancesDate = awardAcceptancesDate;
    }

    public ZonedDateTimeFilter getOrderAcceptancesDate() {
        return orderAcceptancesDate;
    }

    public Optional<ZonedDateTimeFilter> optionalOrderAcceptancesDate() {
        return Optional.ofNullable(orderAcceptancesDate);
    }

    public ZonedDateTimeFilter orderAcceptancesDate() {
        if (orderAcceptancesDate == null) {
            setOrderAcceptancesDate(new ZonedDateTimeFilter());
        }
        return orderAcceptancesDate;
    }

    public void setOrderAcceptancesDate(ZonedDateTimeFilter orderAcceptancesDate) {
        this.orderAcceptancesDate = orderAcceptancesDate;
    }

    public IntegerFilter getOrderAcceptancesFlag() {
        return orderAcceptancesFlag;
    }

    public Optional<IntegerFilter> optionalOrderAcceptancesFlag() {
        return Optional.ofNullable(orderAcceptancesFlag);
    }

    public IntegerFilter orderAcceptancesFlag() {
        if (orderAcceptancesFlag == null) {
            setOrderAcceptancesFlag(new IntegerFilter());
        }
        return orderAcceptancesFlag;
    }

    public void setOrderAcceptancesFlag(IntegerFilter orderAcceptancesFlag) {
        this.orderAcceptancesFlag = orderAcceptancesFlag;
    }

    public StringFilter getMaterialName() {
        return materialName;
    }

    public Optional<StringFilter> optionalMaterialName() {
        return Optional.ofNullable(materialName);
    }

    public StringFilter materialName() {
        if (materialName == null) {
            setMaterialName(new StringFilter());
        }
        return materialName;
    }

    public void setMaterialName(StringFilter materialName) {
        this.materialName = materialName;
    }

    public StringFilter getMaterialImage() {
        return materialImage;
    }

    public Optional<StringFilter> optionalMaterialImage() {
        return Optional.ofNullable(materialImage);
    }

    public StringFilter materialImage() {
        if (materialImage == null) {
            setMaterialImage(new StringFilter());
        }
        return materialImage;
    }

    public void setMaterialImage(StringFilter materialImage) {
        this.materialImage = materialImage;
    }

    public IntegerFilter getTechnicalScrutinyFlag() {
        return technicalScrutinyFlag;
    }

    public Optional<IntegerFilter> optionalTechnicalScrutinyFlag() {
        return Optional.ofNullable(technicalScrutinyFlag);
    }

    public IntegerFilter technicalScrutinyFlag() {
        if (technicalScrutinyFlag == null) {
            setTechnicalScrutinyFlag(new IntegerFilter());
        }
        return technicalScrutinyFlag;
    }

    public void setTechnicalScrutinyFlag(IntegerFilter technicalScrutinyFlag) {
        this.technicalScrutinyFlag = technicalScrutinyFlag;
    }

    public StringFilter getVendorAttributes() {
        return vendorAttributes;
    }

    public Optional<StringFilter> optionalVendorAttributes() {
        return Optional.ofNullable(vendorAttributes);
    }

    public StringFilter vendorAttributes() {
        if (vendorAttributes == null) {
            setVendorAttributes(new StringFilter());
        }
        return vendorAttributes;
    }

    public void setVendorAttributes(StringFilter vendorAttributes) {
        this.vendorAttributes = vendorAttributes;
    }

    public BigDecimalFilter getMarginFactor() {
        return marginFactor;
    }

    public Optional<BigDecimalFilter> optionalMarginFactor() {
        return Optional.ofNullable(marginFactor);
    }

    public BigDecimalFilter marginFactor() {
        if (marginFactor == null) {
            setMarginFactor(new BigDecimalFilter());
        }
        return marginFactor;
    }

    public void setMarginFactor(BigDecimalFilter marginFactor) {
        this.marginFactor = marginFactor;
    }

    public BigDecimalFilter getFob() {
        return fob;
    }

    public Optional<BigDecimalFilter> optionalFob() {
        return Optional.ofNullable(fob);
    }

    public BigDecimalFilter fob() {
        if (fob == null) {
            setFob(new BigDecimalFilter());
        }
        return fob;
    }

    public void setFob(BigDecimalFilter fob) {
        this.fob = fob;
    }

    public BigDecimalFilter getShippingFactor() {
        return shippingFactor;
    }

    public Optional<BigDecimalFilter> optionalShippingFactor() {
        return Optional.ofNullable(shippingFactor);
    }

    public BigDecimalFilter shippingFactor() {
        if (shippingFactor == null) {
            setShippingFactor(new BigDecimalFilter());
        }
        return shippingFactor;
    }

    public void setShippingFactor(BigDecimalFilter shippingFactor) {
        this.shippingFactor = shippingFactor;
    }

    public BigDecimalFilter getFreight() {
        return freight;
    }

    public Optional<BigDecimalFilter> optionalFreight() {
        return Optional.ofNullable(freight);
    }

    public BigDecimalFilter freight() {
        if (freight == null) {
            setFreight(new BigDecimalFilter());
        }
        return freight;
    }

    public void setFreight(BigDecimalFilter freight) {
        this.freight = freight;
    }

    public BigDecimalFilter getFinalShipmentCost() {
        return finalShipmentCost;
    }

    public Optional<BigDecimalFilter> optionalFinalShipmentCost() {
        return Optional.ofNullable(finalShipmentCost);
    }

    public BigDecimalFilter finalShipmentCost() {
        if (finalShipmentCost == null) {
            setFinalShipmentCost(new BigDecimalFilter());
        }
        return finalShipmentCost;
    }

    public void setFinalShipmentCost(BigDecimalFilter finalShipmentCost) {
        this.finalShipmentCost = finalShipmentCost;
    }

    public BigDecimalFilter getTariff() {
        return tariff;
    }

    public Optional<BigDecimalFilter> optionalTariff() {
        return Optional.ofNullable(tariff);
    }

    public BigDecimalFilter tariff() {
        if (tariff == null) {
            setTariff(new BigDecimalFilter());
        }
        return tariff;
    }

    public void setTariff(BigDecimalFilter tariff) {
        this.tariff = tariff;
    }

    public BigDecimalFilter getCalculatedTariffsCost() {
        return calculatedTariffsCost;
    }

    public Optional<BigDecimalFilter> optionalCalculatedTariffsCost() {
        return Optional.ofNullable(calculatedTariffsCost);
    }

    public BigDecimalFilter calculatedTariffsCost() {
        if (calculatedTariffsCost == null) {
            setCalculatedTariffsCost(new BigDecimalFilter());
        }
        return calculatedTariffsCost;
    }

    public void setCalculatedTariffsCost(BigDecimalFilter calculatedTariffsCost) {
        this.calculatedTariffsCost = calculatedTariffsCost;
    }

    public BigDecimalFilter getTotalCumberlandPrice() {
        return totalCumberlandPrice;
    }

    public Optional<BigDecimalFilter> optionalTotalCumberlandPrice() {
        return Optional.ofNullable(totalCumberlandPrice);
    }

    public BigDecimalFilter totalCumberlandPrice() {
        if (totalCumberlandPrice == null) {
            setTotalCumberlandPrice(new BigDecimalFilter());
        }
        return totalCumberlandPrice;
    }

    public void setTotalCumberlandPrice(BigDecimalFilter totalCumberlandPrice) {
        this.totalCumberlandPrice = totalCumberlandPrice;
    }

    public BigDecimalFilter getLandedPrice() {
        return landedPrice;
    }

    public Optional<BigDecimalFilter> optionalLandedPrice() {
        return Optional.ofNullable(landedPrice);
    }

    public BigDecimalFilter landedPrice() {
        if (landedPrice == null) {
            setLandedPrice(new BigDecimalFilter());
        }
        return landedPrice;
    }

    public void setLandedPrice(BigDecimalFilter landedPrice) {
        this.landedPrice = landedPrice;
    }

    public BigDecimalFilter getApprovalToGain() {
        return approvalToGain;
    }

    public Optional<BigDecimalFilter> optionalApprovalToGain() {
        return Optional.ofNullable(approvalToGain);
    }

    public BigDecimalFilter approvalToGain() {
        if (approvalToGain == null) {
            setApprovalToGain(new BigDecimalFilter());
        }
        return approvalToGain;
    }

    public void setApprovalToGain(BigDecimalFilter approvalToGain) {
        this.approvalToGain = approvalToGain;
    }

    public StringFilter getMoldSizeMoldWeight() {
        return moldSizeMoldWeight;
    }

    public Optional<StringFilter> optionalMoldSizeMoldWeight() {
        return Optional.ofNullable(moldSizeMoldWeight);
    }

    public StringFilter moldSizeMoldWeight() {
        if (moldSizeMoldWeight == null) {
            setMoldSizeMoldWeight(new StringFilter());
        }
        return moldSizeMoldWeight;
    }

    public void setMoldSizeMoldWeight(StringFilter moldSizeMoldWeight) {
        this.moldSizeMoldWeight = moldSizeMoldWeight;
    }

    public BigDecimalFilter getMoldLifeExpectancy() {
        return moldLifeExpectancy;
    }

    public Optional<BigDecimalFilter> optionalMoldLifeExpectancy() {
        return Optional.ofNullable(moldLifeExpectancy);
    }

    public BigDecimalFilter moldLifeExpectancy() {
        if (moldLifeExpectancy == null) {
            setMoldLifeExpectancy(new BigDecimalFilter());
        }
        return moldLifeExpectancy;
    }

    public void setMoldLifeExpectancy(BigDecimalFilter moldLifeExpectancy) {
        this.moldLifeExpectancy = moldLifeExpectancy;
    }

    public BigDecimalFilter getTotalCostComparison() {
        return totalCostComparison;
    }

    public Optional<BigDecimalFilter> optionalTotalCostComparison() {
        return Optional.ofNullable(totalCostComparison);
    }

    public BigDecimalFilter totalCostComparison() {
        if (totalCostComparison == null) {
            setTotalCostComparison(new BigDecimalFilter());
        }
        return totalCostComparison;
    }

    public void setTotalCostComparison(BigDecimalFilter totalCostComparison) {
        this.totalCostComparison = totalCostComparison;
    }

    public StringFilter getLength() {
        return length;
    }

    public Optional<StringFilter> optionalLength() {
        return Optional.ofNullable(length);
    }

    public StringFilter length() {
        if (length == null) {
            setLength(new StringFilter());
        }
        return length;
    }

    public void setLength(StringFilter length) {
        this.length = length;
    }

    public StringFilter getWidth() {
        return width;
    }

    public Optional<StringFilter> optionalWidth() {
        return Optional.ofNullable(width);
    }

    public StringFilter width() {
        if (width == null) {
            setWidth(new StringFilter());
        }
        return width;
    }

    public void setWidth(StringFilter width) {
        this.width = width;
    }

    public StringFilter getGuage() {
        return guage;
    }

    public Optional<StringFilter> optionalGuage() {
        return Optional.ofNullable(guage);
    }

    public StringFilter guage() {
        if (guage == null) {
            setGuage(new StringFilter());
        }
        return guage;
    }

    public void setGuage(StringFilter guage) {
        this.guage = guage;
    }

    public StringFilter getTolerance() {
        return tolerance;
    }

    public Optional<StringFilter> optionalTolerance() {
        return Optional.ofNullable(tolerance);
    }

    public StringFilter tolerance() {
        if (tolerance == null) {
            setTolerance(new StringFilter());
        }
        return tolerance;
    }

    public void setTolerance(StringFilter tolerance) {
        this.tolerance = tolerance;
    }

    public LongFilter getRfqDetailId() {
        return rfqDetailId;
    }

    public Optional<LongFilter> optionalRfqDetailId() {
        return Optional.ofNullable(rfqDetailId);
    }

    public LongFilter rfqDetailId() {
        if (rfqDetailId == null) {
            setRfqDetailId(new LongFilter());
        }
        return rfqDetailId;
    }

    public void setRfqDetailId(LongFilter rfqDetailId) {
        this.rfqDetailId = rfqDetailId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public Optional<LongFilter> optionalVendorId() {
        return Optional.ofNullable(vendorId);
    }

    public LongFilter vendorId() {
        if (vendorId == null) {
            setVendorId(new LongFilter());
        }
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BuyerRfqPricesDetailCriteria that = (BuyerRfqPricesDetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(line, that.line) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(estUnitPrice, that.estUnitPrice) &&
            Objects.equals(actUnitPrice, that.actUnitPrice) &&
            Objects.equals(awardFlag, that.awardFlag) &&
            Objects.equals(quoteId, that.quoteId) &&
            Objects.equals(receivedDate, that.receivedDate) &&
            Objects.equals(leadDays, that.leadDays) &&
            Objects.equals(rank, that.rank) &&
            Objects.equals(splitQuantityFlag, that.splitQuantityFlag) &&
            Objects.equals(materialDescription, that.materialDescription) &&
            Objects.equals(lastUpdated, that.lastUpdated) &&
            Objects.equals(inviteRaFlag, that.inviteRaFlag) &&
            Objects.equals(awardAcceptancesDate, that.awardAcceptancesDate) &&
            Objects.equals(orderAcceptancesDate, that.orderAcceptancesDate) &&
            Objects.equals(orderAcceptancesFlag, that.orderAcceptancesFlag) &&
            Objects.equals(materialName, that.materialName) &&
            Objects.equals(materialImage, that.materialImage) &&
            Objects.equals(technicalScrutinyFlag, that.technicalScrutinyFlag) &&
            Objects.equals(vendorAttributes, that.vendorAttributes) &&
            Objects.equals(marginFactor, that.marginFactor) &&
            Objects.equals(fob, that.fob) &&
            Objects.equals(shippingFactor, that.shippingFactor) &&
            Objects.equals(freight, that.freight) &&
            Objects.equals(finalShipmentCost, that.finalShipmentCost) &&
            Objects.equals(tariff, that.tariff) &&
            Objects.equals(calculatedTariffsCost, that.calculatedTariffsCost) &&
            Objects.equals(totalCumberlandPrice, that.totalCumberlandPrice) &&
            Objects.equals(landedPrice, that.landedPrice) &&
            Objects.equals(approvalToGain, that.approvalToGain) &&
            Objects.equals(moldSizeMoldWeight, that.moldSizeMoldWeight) &&
            Objects.equals(moldLifeExpectancy, that.moldLifeExpectancy) &&
            Objects.equals(totalCostComparison, that.totalCostComparison) &&
            Objects.equals(length, that.length) &&
            Objects.equals(width, that.width) &&
            Objects.equals(guage, that.guage) &&
            Objects.equals(tolerance, that.tolerance) &&
            Objects.equals(rfqDetailId, that.rfqDetailId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            srNo,
            uid,
            line,
            materialId,
            quantity,
            estUnitPrice,
            actUnitPrice,
            awardFlag,
            quoteId,
            receivedDate,
            leadDays,
            rank,
            splitQuantityFlag,
            materialDescription,
            lastUpdated,
            inviteRaFlag,
            awardAcceptancesDate,
            orderAcceptancesDate,
            orderAcceptancesFlag,
            materialName,
            materialImage,
            technicalScrutinyFlag,
            vendorAttributes,
            marginFactor,
            fob,
            shippingFactor,
            freight,
            finalShipmentCost,
            tariff,
            calculatedTariffsCost,
            totalCumberlandPrice,
            landedPrice,
            approvalToGain,
            moldSizeMoldWeight,
            moldLifeExpectancy,
            totalCostComparison,
            length,
            width,
            guage,
            tolerance,
            rfqDetailId,
            vendorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerRfqPricesDetailCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalLine().map(f -> "line=" + f + ", ").orElse("") +
            optionalMaterialId().map(f -> "materialId=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalEstUnitPrice().map(f -> "estUnitPrice=" + f + ", ").orElse("") +
            optionalActUnitPrice().map(f -> "actUnitPrice=" + f + ", ").orElse("") +
            optionalAwardFlag().map(f -> "awardFlag=" + f + ", ").orElse("") +
            optionalQuoteId().map(f -> "quoteId=" + f + ", ").orElse("") +
            optionalReceivedDate().map(f -> "receivedDate=" + f + ", ").orElse("") +
            optionalLeadDays().map(f -> "leadDays=" + f + ", ").orElse("") +
            optionalRank().map(f -> "rank=" + f + ", ").orElse("") +
            optionalSplitQuantityFlag().map(f -> "splitQuantityFlag=" + f + ", ").orElse("") +
            optionalMaterialDescription().map(f -> "materialDescription=" + f + ", ").orElse("") +
            optionalLastUpdated().map(f -> "lastUpdated=" + f + ", ").orElse("") +
            optionalInviteRaFlag().map(f -> "inviteRaFlag=" + f + ", ").orElse("") +
            optionalAwardAcceptancesDate().map(f -> "awardAcceptancesDate=" + f + ", ").orElse("") +
            optionalOrderAcceptancesDate().map(f -> "orderAcceptancesDate=" + f + ", ").orElse("") +
            optionalOrderAcceptancesFlag().map(f -> "orderAcceptancesFlag=" + f + ", ").orElse("") +
            optionalMaterialName().map(f -> "materialName=" + f + ", ").orElse("") +
            optionalMaterialImage().map(f -> "materialImage=" + f + ", ").orElse("") +
            optionalTechnicalScrutinyFlag().map(f -> "technicalScrutinyFlag=" + f + ", ").orElse("") +
            optionalVendorAttributes().map(f -> "vendorAttributes=" + f + ", ").orElse("") +
            optionalMarginFactor().map(f -> "marginFactor=" + f + ", ").orElse("") +
            optionalFob().map(f -> "fob=" + f + ", ").orElse("") +
            optionalShippingFactor().map(f -> "shippingFactor=" + f + ", ").orElse("") +
            optionalFreight().map(f -> "freight=" + f + ", ").orElse("") +
            optionalFinalShipmentCost().map(f -> "finalShipmentCost=" + f + ", ").orElse("") +
            optionalTariff().map(f -> "tariff=" + f + ", ").orElse("") +
            optionalCalculatedTariffsCost().map(f -> "calculatedTariffsCost=" + f + ", ").orElse("") +
            optionalTotalCumberlandPrice().map(f -> "totalCumberlandPrice=" + f + ", ").orElse("") +
            optionalLandedPrice().map(f -> "landedPrice=" + f + ", ").orElse("") +
            optionalApprovalToGain().map(f -> "approvalToGain=" + f + ", ").orElse("") +
            optionalMoldSizeMoldWeight().map(f -> "moldSizeMoldWeight=" + f + ", ").orElse("") +
            optionalMoldLifeExpectancy().map(f -> "moldLifeExpectancy=" + f + ", ").orElse("") +
            optionalTotalCostComparison().map(f -> "totalCostComparison=" + f + ", ").orElse("") +
            optionalLength().map(f -> "length=" + f + ", ").orElse("") +
            optionalWidth().map(f -> "width=" + f + ", ").orElse("") +
            optionalGuage().map(f -> "guage=" + f + ", ").orElse("") +
            optionalTolerance().map(f -> "tolerance=" + f + ", ").orElse("") +
            optionalRfqDetailId().map(f -> "rfqDetailId=" + f + ", ").orElse("") +
            optionalVendorId().map(f -> "vendorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
