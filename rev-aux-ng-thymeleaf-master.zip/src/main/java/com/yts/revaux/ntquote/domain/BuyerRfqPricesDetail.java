package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BuyerRfqPricesDetail.
 */
@Entity
@Table(name = "buyer_rfq_prices_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "buyerrfqpricesdetail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerRfqPricesDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sr_no")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer srNo;

    @NotNull
    @Column(name = "uid", nullable = false)
    private UUID uid;

    @Column(name = "line")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String line;

    @NotNull
    @Column(name = "material_id", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialId;

    @Column(name = "quantity")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer quantity;

    @Column(name = "est_unit_price", precision = 21, scale = 2)
    private BigDecimal estUnitPrice;

    @Column(name = "act_unit_price", precision = 21, scale = 2)
    private BigDecimal actUnitPrice;

    @Column(name = "award_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String awardFlag;

    @Column(name = "quote_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String quoteId;

    @Column(name = "received_date")
    private ZonedDateTime receivedDate;

    @Column(name = "lead_days", precision = 21, scale = 2)
    private BigDecimal leadDays;

    @Column(name = "rank")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rank;

    @Column(name = "split_quantity_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer splitQuantityFlag;

    @Column(name = "material_description")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialDescription;

    @Column(name = "last_updated")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String lastUpdated;

    @Column(name = "invite_ra_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer inviteRaFlag;

    @Column(name = "award_acceptances_date")
    private ZonedDateTime awardAcceptancesDate;

    @Column(name = "order_acceptances_date")
    private ZonedDateTime orderAcceptancesDate;

    @Column(name = "order_acceptances_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer orderAcceptancesFlag;

    @Column(name = "material_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialName;

    @Column(name = "material_image")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialImage;

    @Column(name = "technical_scrutiny_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer technicalScrutinyFlag;

    @Column(name = "vendor_attributes")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String vendorAttributes;

    @Column(name = "margin_factor", precision = 21, scale = 2)
    private BigDecimal marginFactor;

    @Column(name = "fob", precision = 21, scale = 2)
    private BigDecimal fob;

    @Column(name = "shipping_factor", precision = 21, scale = 2)
    private BigDecimal shippingFactor;

    @Column(name = "freight", precision = 21, scale = 2)
    private BigDecimal freight;

    @Column(name = "final_shipment_cost", precision = 21, scale = 2)
    private BigDecimal finalShipmentCost;

    @Column(name = "tariff", precision = 21, scale = 2)
    private BigDecimal tariff;

    @Column(name = "calculated_tariffs_cost", precision = 21, scale = 2)
    private BigDecimal calculatedTariffsCost;

    @Column(name = "total_cumberland_price", precision = 21, scale = 2)
    private BigDecimal totalCumberlandPrice;

    @Column(name = "landed_price", precision = 21, scale = 2)
    private BigDecimal landedPrice;

    @Column(name = "approval_to_gain", precision = 21, scale = 2)
    private BigDecimal approvalToGain;

    @Column(name = "mold_size_mold_weight")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String moldSizeMoldWeight;

    @Column(name = "mold_life_expectancy", precision = 21, scale = 2)
    private BigDecimal moldLifeExpectancy;

    @Column(name = "total_cost_comparison", precision = 21, scale = 2)
    private BigDecimal totalCostComparison;

    @Column(name = "length")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String length;

    @Column(name = "width")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String width;

    @Column(name = "guage")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String guage;

    @Column(name = "tolerance")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String tolerance;

    @JsonIgnoreProperties(value = { "buyerRfqPricesDetail" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private RfqDetail rfqDetail;

    @JsonIgnoreProperties(value = { "buyerRfqPricesDetail" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private VendorProfile vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BuyerRfqPricesDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public BuyerRfqPricesDetail srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public BuyerRfqPricesDetail uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getLine() {
        return this.line;
    }

    public BuyerRfqPricesDetail line(String line) {
        this.setLine(line);
        return this;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMaterialId() {
        return this.materialId;
    }

    public BuyerRfqPricesDetail materialId(String materialId) {
        this.setMaterialId(materialId);
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public BuyerRfqPricesDetail quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getEstUnitPrice() {
        return this.estUnitPrice;
    }

    public BuyerRfqPricesDetail estUnitPrice(BigDecimal estUnitPrice) {
        this.setEstUnitPrice(estUnitPrice);
        return this;
    }

    public void setEstUnitPrice(BigDecimal estUnitPrice) {
        this.estUnitPrice = estUnitPrice;
    }

    public BigDecimal getActUnitPrice() {
        return this.actUnitPrice;
    }

    public BuyerRfqPricesDetail actUnitPrice(BigDecimal actUnitPrice) {
        this.setActUnitPrice(actUnitPrice);
        return this;
    }

    public void setActUnitPrice(BigDecimal actUnitPrice) {
        this.actUnitPrice = actUnitPrice;
    }

    public String getAwardFlag() {
        return this.awardFlag;
    }

    public BuyerRfqPricesDetail awardFlag(String awardFlag) {
        this.setAwardFlag(awardFlag);
        return this;
    }

    public void setAwardFlag(String awardFlag) {
        this.awardFlag = awardFlag;
    }

    public String getQuoteId() {
        return this.quoteId;
    }

    public BuyerRfqPricesDetail quoteId(String quoteId) {
        this.setQuoteId(quoteId);
        return this;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public ZonedDateTime getReceivedDate() {
        return this.receivedDate;
    }

    public BuyerRfqPricesDetail receivedDate(ZonedDateTime receivedDate) {
        this.setReceivedDate(receivedDate);
        return this;
    }

    public void setReceivedDate(ZonedDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public BigDecimal getLeadDays() {
        return this.leadDays;
    }

    public BuyerRfqPricesDetail leadDays(BigDecimal leadDays) {
        this.setLeadDays(leadDays);
        return this;
    }

    public void setLeadDays(BigDecimal leadDays) {
        this.leadDays = leadDays;
    }

    public String getRank() {
        return this.rank;
    }

    public BuyerRfqPricesDetail rank(String rank) {
        this.setRank(rank);
        return this;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getSplitQuantityFlag() {
        return this.splitQuantityFlag;
    }

    public BuyerRfqPricesDetail splitQuantityFlag(Integer splitQuantityFlag) {
        this.setSplitQuantityFlag(splitQuantityFlag);
        return this;
    }

    public void setSplitQuantityFlag(Integer splitQuantityFlag) {
        this.splitQuantityFlag = splitQuantityFlag;
    }

    public String getMaterialDescription() {
        return this.materialDescription;
    }

    public BuyerRfqPricesDetail materialDescription(String materialDescription) {
        this.setMaterialDescription(materialDescription);
        return this;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public BuyerRfqPricesDetail lastUpdated(String lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getInviteRaFlag() {
        return this.inviteRaFlag;
    }

    public BuyerRfqPricesDetail inviteRaFlag(Integer inviteRaFlag) {
        this.setInviteRaFlag(inviteRaFlag);
        return this;
    }

    public void setInviteRaFlag(Integer inviteRaFlag) {
        this.inviteRaFlag = inviteRaFlag;
    }

    public ZonedDateTime getAwardAcceptancesDate() {
        return this.awardAcceptancesDate;
    }

    public BuyerRfqPricesDetail awardAcceptancesDate(ZonedDateTime awardAcceptancesDate) {
        this.setAwardAcceptancesDate(awardAcceptancesDate);
        return this;
    }

    public void setAwardAcceptancesDate(ZonedDateTime awardAcceptancesDate) {
        this.awardAcceptancesDate = awardAcceptancesDate;
    }

    public ZonedDateTime getOrderAcceptancesDate() {
        return this.orderAcceptancesDate;
    }

    public BuyerRfqPricesDetail orderAcceptancesDate(ZonedDateTime orderAcceptancesDate) {
        this.setOrderAcceptancesDate(orderAcceptancesDate);
        return this;
    }

    public void setOrderAcceptancesDate(ZonedDateTime orderAcceptancesDate) {
        this.orderAcceptancesDate = orderAcceptancesDate;
    }

    public Integer getOrderAcceptancesFlag() {
        return this.orderAcceptancesFlag;
    }

    public BuyerRfqPricesDetail orderAcceptancesFlag(Integer orderAcceptancesFlag) {
        this.setOrderAcceptancesFlag(orderAcceptancesFlag);
        return this;
    }

    public void setOrderAcceptancesFlag(Integer orderAcceptancesFlag) {
        this.orderAcceptancesFlag = orderAcceptancesFlag;
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public BuyerRfqPricesDetail materialName(String materialName) {
        this.setMaterialName(materialName);
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialImage() {
        return this.materialImage;
    }

    public BuyerRfqPricesDetail materialImage(String materialImage) {
        this.setMaterialImage(materialImage);
        return this;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

    public Integer getTechnicalScrutinyFlag() {
        return this.technicalScrutinyFlag;
    }

    public BuyerRfqPricesDetail technicalScrutinyFlag(Integer technicalScrutinyFlag) {
        this.setTechnicalScrutinyFlag(technicalScrutinyFlag);
        return this;
    }

    public void setTechnicalScrutinyFlag(Integer technicalScrutinyFlag) {
        this.technicalScrutinyFlag = technicalScrutinyFlag;
    }

    public String getVendorAttributes() {
        return this.vendorAttributes;
    }

    public BuyerRfqPricesDetail vendorAttributes(String vendorAttributes) {
        this.setVendorAttributes(vendorAttributes);
        return this;
    }

    public void setVendorAttributes(String vendorAttributes) {
        this.vendorAttributes = vendorAttributes;
    }

    public BigDecimal getMarginFactor() {
        return this.marginFactor;
    }

    public BuyerRfqPricesDetail marginFactor(BigDecimal marginFactor) {
        this.setMarginFactor(marginFactor);
        return this;
    }

    public void setMarginFactor(BigDecimal marginFactor) {
        this.marginFactor = marginFactor;
    }

    public BigDecimal getFob() {
        return this.fob;
    }

    public BuyerRfqPricesDetail fob(BigDecimal fob) {
        this.setFob(fob);
        return this;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    public BigDecimal getShippingFactor() {
        return this.shippingFactor;
    }

    public BuyerRfqPricesDetail shippingFactor(BigDecimal shippingFactor) {
        this.setShippingFactor(shippingFactor);
        return this;
    }

    public void setShippingFactor(BigDecimal shippingFactor) {
        this.shippingFactor = shippingFactor;
    }

    public BigDecimal getFreight() {
        return this.freight;
    }

    public BuyerRfqPricesDetail freight(BigDecimal freight) {
        this.setFreight(freight);
        return this;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getFinalShipmentCost() {
        return this.finalShipmentCost;
    }

    public BuyerRfqPricesDetail finalShipmentCost(BigDecimal finalShipmentCost) {
        this.setFinalShipmentCost(finalShipmentCost);
        return this;
    }

    public void setFinalShipmentCost(BigDecimal finalShipmentCost) {
        this.finalShipmentCost = finalShipmentCost;
    }

    public BigDecimal getTariff() {
        return this.tariff;
    }

    public BuyerRfqPricesDetail tariff(BigDecimal tariff) {
        this.setTariff(tariff);
        return this;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public BigDecimal getCalculatedTariffsCost() {
        return this.calculatedTariffsCost;
    }

    public BuyerRfqPricesDetail calculatedTariffsCost(BigDecimal calculatedTariffsCost) {
        this.setCalculatedTariffsCost(calculatedTariffsCost);
        return this;
    }

    public void setCalculatedTariffsCost(BigDecimal calculatedTariffsCost) {
        this.calculatedTariffsCost = calculatedTariffsCost;
    }

    public BigDecimal getTotalCumberlandPrice() {
        return this.totalCumberlandPrice;
    }

    public BuyerRfqPricesDetail totalCumberlandPrice(BigDecimal totalCumberlandPrice) {
        this.setTotalCumberlandPrice(totalCumberlandPrice);
        return this;
    }

    public void setTotalCumberlandPrice(BigDecimal totalCumberlandPrice) {
        this.totalCumberlandPrice = totalCumberlandPrice;
    }

    public BigDecimal getLandedPrice() {
        return this.landedPrice;
    }

    public BuyerRfqPricesDetail landedPrice(BigDecimal landedPrice) {
        this.setLandedPrice(landedPrice);
        return this;
    }

    public void setLandedPrice(BigDecimal landedPrice) {
        this.landedPrice = landedPrice;
    }

    public BigDecimal getApprovalToGain() {
        return this.approvalToGain;
    }

    public BuyerRfqPricesDetail approvalToGain(BigDecimal approvalToGain) {
        this.setApprovalToGain(approvalToGain);
        return this;
    }

    public void setApprovalToGain(BigDecimal approvalToGain) {
        this.approvalToGain = approvalToGain;
    }

    public String getMoldSizeMoldWeight() {
        return this.moldSizeMoldWeight;
    }

    public BuyerRfqPricesDetail moldSizeMoldWeight(String moldSizeMoldWeight) {
        this.setMoldSizeMoldWeight(moldSizeMoldWeight);
        return this;
    }

    public void setMoldSizeMoldWeight(String moldSizeMoldWeight) {
        this.moldSizeMoldWeight = moldSizeMoldWeight;
    }

    public BigDecimal getMoldLifeExpectancy() {
        return this.moldLifeExpectancy;
    }

    public BuyerRfqPricesDetail moldLifeExpectancy(BigDecimal moldLifeExpectancy) {
        this.setMoldLifeExpectancy(moldLifeExpectancy);
        return this;
    }

    public void setMoldLifeExpectancy(BigDecimal moldLifeExpectancy) {
        this.moldLifeExpectancy = moldLifeExpectancy;
    }

    public BigDecimal getTotalCostComparison() {
        return this.totalCostComparison;
    }

    public BuyerRfqPricesDetail totalCostComparison(BigDecimal totalCostComparison) {
        this.setTotalCostComparison(totalCostComparison);
        return this;
    }

    public void setTotalCostComparison(BigDecimal totalCostComparison) {
        this.totalCostComparison = totalCostComparison;
    }

    public String getLength() {
        return this.length;
    }

    public BuyerRfqPricesDetail length(String length) {
        this.setLength(length);
        return this;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return this.width;
    }

    public BuyerRfqPricesDetail width(String width) {
        this.setWidth(width);
        return this;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getGuage() {
        return this.guage;
    }

    public BuyerRfqPricesDetail guage(String guage) {
        this.setGuage(guage);
        return this;
    }

    public void setGuage(String guage) {
        this.guage = guage;
    }

    public String getTolerance() {
        return this.tolerance;
    }

    public BuyerRfqPricesDetail tolerance(String tolerance) {
        this.setTolerance(tolerance);
        return this;
    }

    public void setTolerance(String tolerance) {
        this.tolerance = tolerance;
    }

    public RfqDetail getRfqDetail() {
        return this.rfqDetail;
    }

    public void setRfqDetail(RfqDetail rfqDetail) {
        this.rfqDetail = rfqDetail;
    }

    public BuyerRfqPricesDetail rfqDetail(RfqDetail rfqDetail) {
        this.setRfqDetail(rfqDetail);
        return this;
    }

    public VendorProfile getVendor() {
        return this.vendor;
    }

    public void setVendor(VendorProfile vendorProfile) {
        this.vendor = vendorProfile;
    }

    public BuyerRfqPricesDetail vendor(VendorProfile vendorProfile) {
        this.setVendor(vendorProfile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyerRfqPricesDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((BuyerRfqPricesDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerRfqPricesDetail{" +
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
            "}";
    }
}
