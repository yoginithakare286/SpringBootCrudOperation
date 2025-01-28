package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerRfqPricesDetailDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String line;

    @NotNull
    private String materialId;

    private Integer quantity;

    private BigDecimal estUnitPrice;

    private BigDecimal actUnitPrice;

    private String awardFlag;

    private String quoteId;

    private ZonedDateTime receivedDate;

    private BigDecimal leadDays;

    private String rank;

    private Integer splitQuantityFlag;

    private String materialDescription;

    private String lastUpdated;

    private Integer inviteRaFlag;

    private ZonedDateTime awardAcceptancesDate;

    private ZonedDateTime orderAcceptancesDate;

    private Integer orderAcceptancesFlag;

    private String materialName;

    private String materialImage;

    private Integer technicalScrutinyFlag;

    private String vendorAttributes;

    private BigDecimal marginFactor;

    private BigDecimal fob;

    private BigDecimal shippingFactor;

    private BigDecimal freight;

    private BigDecimal finalShipmentCost;

    private BigDecimal tariff;

    private BigDecimal calculatedTariffsCost;

    private BigDecimal totalCumberlandPrice;

    private BigDecimal landedPrice;

    private BigDecimal approvalToGain;

    private String moldSizeMoldWeight;

    private BigDecimal moldLifeExpectancy;

    private BigDecimal totalCostComparison;

    private String length;

    private String width;

    private String guage;

    private String tolerance;

    private RfqDetailDTO rfqDetail;

    private VendorProfileDTO vendor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getEstUnitPrice() {
        return estUnitPrice;
    }

    public void setEstUnitPrice(BigDecimal estUnitPrice) {
        this.estUnitPrice = estUnitPrice;
    }

    public BigDecimal getActUnitPrice() {
        return actUnitPrice;
    }

    public void setActUnitPrice(BigDecimal actUnitPrice) {
        this.actUnitPrice = actUnitPrice;
    }

    public String getAwardFlag() {
        return awardFlag;
    }

    public void setAwardFlag(String awardFlag) {
        this.awardFlag = awardFlag;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public ZonedDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public BigDecimal getLeadDays() {
        return leadDays;
    }

    public void setLeadDays(BigDecimal leadDays) {
        this.leadDays = leadDays;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getSplitQuantityFlag() {
        return splitQuantityFlag;
    }

    public void setSplitQuantityFlag(Integer splitQuantityFlag) {
        this.splitQuantityFlag = splitQuantityFlag;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getInviteRaFlag() {
        return inviteRaFlag;
    }

    public void setInviteRaFlag(Integer inviteRaFlag) {
        this.inviteRaFlag = inviteRaFlag;
    }

    public ZonedDateTime getAwardAcceptancesDate() {
        return awardAcceptancesDate;
    }

    public void setAwardAcceptancesDate(ZonedDateTime awardAcceptancesDate) {
        this.awardAcceptancesDate = awardAcceptancesDate;
    }

    public ZonedDateTime getOrderAcceptancesDate() {
        return orderAcceptancesDate;
    }

    public void setOrderAcceptancesDate(ZonedDateTime orderAcceptancesDate) {
        this.orderAcceptancesDate = orderAcceptancesDate;
    }

    public Integer getOrderAcceptancesFlag() {
        return orderAcceptancesFlag;
    }

    public void setOrderAcceptancesFlag(Integer orderAcceptancesFlag) {
        this.orderAcceptancesFlag = orderAcceptancesFlag;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

    public Integer getTechnicalScrutinyFlag() {
        return technicalScrutinyFlag;
    }

    public void setTechnicalScrutinyFlag(Integer technicalScrutinyFlag) {
        this.technicalScrutinyFlag = technicalScrutinyFlag;
    }

    public String getVendorAttributes() {
        return vendorAttributes;
    }

    public void setVendorAttributes(String vendorAttributes) {
        this.vendorAttributes = vendorAttributes;
    }

    public BigDecimal getMarginFactor() {
        return marginFactor;
    }

    public void setMarginFactor(BigDecimal marginFactor) {
        this.marginFactor = marginFactor;
    }

    public BigDecimal getFob() {
        return fob;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    public BigDecimal getShippingFactor() {
        return shippingFactor;
    }

    public void setShippingFactor(BigDecimal shippingFactor) {
        this.shippingFactor = shippingFactor;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getFinalShipmentCost() {
        return finalShipmentCost;
    }

    public void setFinalShipmentCost(BigDecimal finalShipmentCost) {
        this.finalShipmentCost = finalShipmentCost;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public BigDecimal getCalculatedTariffsCost() {
        return calculatedTariffsCost;
    }

    public void setCalculatedTariffsCost(BigDecimal calculatedTariffsCost) {
        this.calculatedTariffsCost = calculatedTariffsCost;
    }

    public BigDecimal getTotalCumberlandPrice() {
        return totalCumberlandPrice;
    }

    public void setTotalCumberlandPrice(BigDecimal totalCumberlandPrice) {
        this.totalCumberlandPrice = totalCumberlandPrice;
    }

    public BigDecimal getLandedPrice() {
        return landedPrice;
    }

    public void setLandedPrice(BigDecimal landedPrice) {
        this.landedPrice = landedPrice;
    }

    public BigDecimal getApprovalToGain() {
        return approvalToGain;
    }

    public void setApprovalToGain(BigDecimal approvalToGain) {
        this.approvalToGain = approvalToGain;
    }

    public String getMoldSizeMoldWeight() {
        return moldSizeMoldWeight;
    }

    public void setMoldSizeMoldWeight(String moldSizeMoldWeight) {
        this.moldSizeMoldWeight = moldSizeMoldWeight;
    }

    public BigDecimal getMoldLifeExpectancy() {
        return moldLifeExpectancy;
    }

    public void setMoldLifeExpectancy(BigDecimal moldLifeExpectancy) {
        this.moldLifeExpectancy = moldLifeExpectancy;
    }

    public BigDecimal getTotalCostComparison() {
        return totalCostComparison;
    }

    public void setTotalCostComparison(BigDecimal totalCostComparison) {
        this.totalCostComparison = totalCostComparison;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getGuage() {
        return guage;
    }

    public void setGuage(String guage) {
        this.guage = guage;
    }

    public String getTolerance() {
        return tolerance;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public RfqDetailDTO getRfqDetail() {
        return rfqDetail;
    }

    public void setRfqDetail(RfqDetailDTO rfqDetail) {
        this.rfqDetail = rfqDetail;
    }

    public VendorProfileDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorProfileDTO vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyerRfqPricesDetailDTO)) {
            return false;
        }

        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = (BuyerRfqPricesDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyerRfqPricesDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerRfqPricesDetailDTO{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", line='" + getLine() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", quantity=" + getQuantity() +
            ", estUnitPrice=" + getEstUnitPrice() +
            ", actUnitPrice=" + getActUnitPrice() +
            ", awardFlag='" + getAwardFlag() + "'" +
            ", quoteId='" + getQuoteId() + "'" +
            ", receivedDate='" + getReceivedDate() + "'" +
            ", leadDays=" + getLeadDays() +
            ", rank='" + getRank() + "'" +
            ", splitQuantityFlag=" + getSplitQuantityFlag() +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", inviteRaFlag=" + getInviteRaFlag() +
            ", awardAcceptancesDate='" + getAwardAcceptancesDate() + "'" +
            ", orderAcceptancesDate='" + getOrderAcceptancesDate() + "'" +
            ", orderAcceptancesFlag=" + getOrderAcceptancesFlag() +
            ", materialName='" + getMaterialName() + "'" +
            ", materialImage='" + getMaterialImage() + "'" +
            ", technicalScrutinyFlag=" + getTechnicalScrutinyFlag() +
            ", vendorAttributes='" + getVendorAttributes() + "'" +
            ", marginFactor=" + getMarginFactor() +
            ", fob=" + getFob() +
            ", shippingFactor=" + getShippingFactor() +
            ", freight=" + getFreight() +
            ", finalShipmentCost=" + getFinalShipmentCost() +
            ", tariff=" + getTariff() +
            ", calculatedTariffsCost=" + getCalculatedTariffsCost() +
            ", totalCumberlandPrice=" + getTotalCumberlandPrice() +
            ", landedPrice=" + getLandedPrice() +
            ", approvalToGain=" + getApprovalToGain() +
            ", moldSizeMoldWeight='" + getMoldSizeMoldWeight() + "'" +
            ", moldLifeExpectancy=" + getMoldLifeExpectancy() +
            ", totalCostComparison=" + getTotalCostComparison() +
            ", length='" + getLength() + "'" +
            ", width='" + getWidth() + "'" +
            ", guage='" + getGuage() + "'" +
            ", tolerance='" + getTolerance() + "'" +
            ", rfqDetail=" + getRfqDetail() +
            ", vendor=" + getVendor() +
            "}";
    }
}
