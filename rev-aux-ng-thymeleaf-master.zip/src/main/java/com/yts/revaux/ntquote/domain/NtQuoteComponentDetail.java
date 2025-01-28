package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NtQuoteComponentDetail.
 */
@Entity
@Table(name = "nt_quote_component_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquotecomponentdetail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteComponentDetail implements Serializable {

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

    @Column(name = "material_description")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialDescription;

    @Column(name = "part_number")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String partNumber;

    @Column(name = "eau")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer eau;

    @Column(name = "manufacturing_location")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String manufacturingLocation;

    @Column(name = "fob_location")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String fobLocation;

    @Column(name = "packing_requirements")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String packingRequirements;

    @Column(name = "machine_size")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String machineSize;

    @Column(name = "cycle_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer cycleTime;

    @Column(name = "part_weight")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer partWeight;

    @Column(name = "runner_weight")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer runnerWeight;

    @Column(name = "cavities")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer cavities;

    @Column(name = "comments")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String comments;

    @Column(name = "risk_level")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String riskLevel;

    @Column(name = "created_by")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String updatedBy;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectConsiderations",
            "contractReviewInformations",
            "customerInputOutputVersions",
            "partInformationMasters",
            "comments",
            "termsConditions",
            "projectApprovals",
            "partInformationVersions",
            "customerPos",
            "vendorQuotes",
            "vendorPos",
            "rfqDetail",
            "ntQuoteProjectApproval",
        },
        allowSetters = true
    )
    private NtQuote ntQuote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "rfqDetail", "vendor" }, allowSetters = true)
    private BuyerRfqPricesDetail materialPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NtQuoteComponentDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuoteComponentDetail srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuoteComponentDetail uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getMaterialDescription() {
        return this.materialDescription;
    }

    public NtQuoteComponentDetail materialDescription(String materialDescription) {
        this.setMaterialDescription(materialDescription);
        return this;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public NtQuoteComponentDetail partNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getEau() {
        return this.eau;
    }

    public NtQuoteComponentDetail eau(Integer eau) {
        this.setEau(eau);
        return this;
    }

    public void setEau(Integer eau) {
        this.eau = eau;
    }

    public String getManufacturingLocation() {
        return this.manufacturingLocation;
    }

    public NtQuoteComponentDetail manufacturingLocation(String manufacturingLocation) {
        this.setManufacturingLocation(manufacturingLocation);
        return this;
    }

    public void setManufacturingLocation(String manufacturingLocation) {
        this.manufacturingLocation = manufacturingLocation;
    }

    public String getFobLocation() {
        return this.fobLocation;
    }

    public NtQuoteComponentDetail fobLocation(String fobLocation) {
        this.setFobLocation(fobLocation);
        return this;
    }

    public void setFobLocation(String fobLocation) {
        this.fobLocation = fobLocation;
    }

    public String getPackingRequirements() {
        return this.packingRequirements;
    }

    public NtQuoteComponentDetail packingRequirements(String packingRequirements) {
        this.setPackingRequirements(packingRequirements);
        return this;
    }

    public void setPackingRequirements(String packingRequirements) {
        this.packingRequirements = packingRequirements;
    }

    public String getMachineSize() {
        return this.machineSize;
    }

    public NtQuoteComponentDetail machineSize(String machineSize) {
        this.setMachineSize(machineSize);
        return this;
    }

    public void setMachineSize(String machineSize) {
        this.machineSize = machineSize;
    }

    public Integer getCycleTime() {
        return this.cycleTime;
    }

    public NtQuoteComponentDetail cycleTime(Integer cycleTime) {
        this.setCycleTime(cycleTime);
        return this;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getPartWeight() {
        return this.partWeight;
    }

    public NtQuoteComponentDetail partWeight(Integer partWeight) {
        this.setPartWeight(partWeight);
        return this;
    }

    public void setPartWeight(Integer partWeight) {
        this.partWeight = partWeight;
    }

    public Integer getRunnerWeight() {
        return this.runnerWeight;
    }

    public NtQuoteComponentDetail runnerWeight(Integer runnerWeight) {
        this.setRunnerWeight(runnerWeight);
        return this;
    }

    public void setRunnerWeight(Integer runnerWeight) {
        this.runnerWeight = runnerWeight;
    }

    public Integer getCavities() {
        return this.cavities;
    }

    public NtQuoteComponentDetail cavities(Integer cavities) {
        this.setCavities(cavities);
        return this;
    }

    public void setCavities(Integer cavities) {
        this.cavities = cavities;
    }

    public String getComments() {
        return this.comments;
    }

    public NtQuoteComponentDetail comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRiskLevel() {
        return this.riskLevel;
    }

    public NtQuoteComponentDetail riskLevel(String riskLevel) {
        this.setRiskLevel(riskLevel);
        return this;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuoteComponentDetail createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuoteComponentDetail createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuoteComponentDetail updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuoteComponentDetail updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public NtQuote getNtQuote() {
        return this.ntQuote;
    }

    public void setNtQuote(NtQuote ntQuote) {
        this.ntQuote = ntQuote;
    }

    public NtQuoteComponentDetail ntQuote(NtQuote ntQuote) {
        this.setNtQuote(ntQuote);
        return this;
    }

    public BuyerRfqPricesDetail getMaterialPrice() {
        return this.materialPrice;
    }

    public void setMaterialPrice(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        this.materialPrice = buyerRfqPricesDetail;
    }

    public NtQuoteComponentDetail materialPrice(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        this.setMaterialPrice(buyerRfqPricesDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteComponentDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuoteComponentDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteComponentDetail{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", eau=" + getEau() +
            ", manufacturingLocation='" + getManufacturingLocation() + "'" +
            ", fobLocation='" + getFobLocation() + "'" +
            ", packingRequirements='" + getPackingRequirements() + "'" +
            ", machineSize='" + getMachineSize() + "'" +
            ", cycleTime=" + getCycleTime() +
            ", partWeight=" + getPartWeight() +
            ", runnerWeight=" + getRunnerWeight() +
            ", cavities=" + getCavities() +
            ", comments='" + getComments() + "'" +
            ", riskLevel='" + getRiskLevel() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
