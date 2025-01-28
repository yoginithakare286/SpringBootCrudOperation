package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NtQuotePartInformationMaster.
 */
@Entity
@Table(name = "nt_quote_part_information_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquotepartinformationmaster")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuotePartInformationMaster implements Serializable {

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

    @Column(name = "cad_file")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String cadFile;

    @Column(name = "eau")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer eau;

    @Column(name = "part_weight")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer partWeight;

    @Column(name = "material_type")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialType;

    @Column(name = "material_cost", precision = 21, scale = 2)
    private BigDecimal materialCost;

    @Column(name = "extended_material_cost_per", precision = 21, scale = 2)
    private BigDecimal extendedMaterialCostPer;

    @Column(name = "external_machine_cost_per", precision = 21, scale = 2)
    private BigDecimal externalMachineCostPer;

    @Column(name = "purchase_component_cost", precision = 21, scale = 2)
    private BigDecimal purchaseComponentCost;

    @Column(name = "secondary_external_operation_cost", precision = 21, scale = 2)
    private BigDecimal secondaryExternalOperationCost;

    @Column(name = "overhead", precision = 21, scale = 2)
    private BigDecimal overhead;

    @Column(name = "pack_logistic_cost_per", precision = 21, scale = 2)
    private BigDecimal packLogisticCostPer;

    @Column(name = "machine_size_tons")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String machineSizeTons;

    @Column(name = "number_of_cavities")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer numberOfCavities;

    @Column(name = "cycle_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer cycleTime;

    @Column(name = "per_unit", precision = 21, scale = 2)
    private BigDecimal perUnit;

    @Column(name = "total_price_per_china", precision = 21, scale = 2)
    private BigDecimal totalPricePerChina;

    @Column(name = "total_price_budget", precision = 21, scale = 2)
    private BigDecimal totalPriceBudget;

    @Column(name = "grain_budget", precision = 21, scale = 2)
    private BigDecimal grainBudget;

    @Column(name = "dogating_fixture_budget", precision = 21, scale = 2)
    private BigDecimal dogatingFixtureBudget;

    @Column(name = "gauge_budget", precision = 21, scale = 2)
    private BigDecimal gaugeBudget;

    @Column(name = "eoat", precision = 21, scale = 2)
    private BigDecimal eoat;

    @Column(name = "china_tariff_budget", precision = 21, scale = 2)
    private BigDecimal chinaTariffBudget;

    @Column(name = "total_tooling_budget", precision = 21, scale = 2)
    private BigDecimal totalToolingBudget;

    @Column(name = "lead_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String leadTime;

    @Column(name = "tooling_notes")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String toolingNotes;

    @Column(name = "part_description")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String partDescription;

    @Column(name = "job_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String jobId;

    @Column(name = "mold_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String moldId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NtQuotePartInformationMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuotePartInformationMaster srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuotePartInformationMaster uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getMaterialDescription() {
        return this.materialDescription;
    }

    public NtQuotePartInformationMaster materialDescription(String materialDescription) {
        this.setMaterialDescription(materialDescription);
        return this;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public NtQuotePartInformationMaster partNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCadFile() {
        return this.cadFile;
    }

    public NtQuotePartInformationMaster cadFile(String cadFile) {
        this.setCadFile(cadFile);
        return this;
    }

    public void setCadFile(String cadFile) {
        this.cadFile = cadFile;
    }

    public Integer getEau() {
        return this.eau;
    }

    public NtQuotePartInformationMaster eau(Integer eau) {
        this.setEau(eau);
        return this;
    }

    public void setEau(Integer eau) {
        this.eau = eau;
    }

    public Integer getPartWeight() {
        return this.partWeight;
    }

    public NtQuotePartInformationMaster partWeight(Integer partWeight) {
        this.setPartWeight(partWeight);
        return this;
    }

    public void setPartWeight(Integer partWeight) {
        this.partWeight = partWeight;
    }

    public String getMaterialType() {
        return this.materialType;
    }

    public NtQuotePartInformationMaster materialType(String materialType) {
        this.setMaterialType(materialType);
        return this;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getMaterialCost() {
        return this.materialCost;
    }

    public NtQuotePartInformationMaster materialCost(BigDecimal materialCost) {
        this.setMaterialCost(materialCost);
        return this;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getExtendedMaterialCostPer() {
        return this.extendedMaterialCostPer;
    }

    public NtQuotePartInformationMaster extendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.setExtendedMaterialCostPer(extendedMaterialCostPer);
        return this;
    }

    public void setExtendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.extendedMaterialCostPer = extendedMaterialCostPer;
    }

    public BigDecimal getExternalMachineCostPer() {
        return this.externalMachineCostPer;
    }

    public NtQuotePartInformationMaster externalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.setExternalMachineCostPer(externalMachineCostPer);
        return this;
    }

    public void setExternalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.externalMachineCostPer = externalMachineCostPer;
    }

    public BigDecimal getPurchaseComponentCost() {
        return this.purchaseComponentCost;
    }

    public NtQuotePartInformationMaster purchaseComponentCost(BigDecimal purchaseComponentCost) {
        this.setPurchaseComponentCost(purchaseComponentCost);
        return this;
    }

    public void setPurchaseComponentCost(BigDecimal purchaseComponentCost) {
        this.purchaseComponentCost = purchaseComponentCost;
    }

    public BigDecimal getSecondaryExternalOperationCost() {
        return this.secondaryExternalOperationCost;
    }

    public NtQuotePartInformationMaster secondaryExternalOperationCost(BigDecimal secondaryExternalOperationCost) {
        this.setSecondaryExternalOperationCost(secondaryExternalOperationCost);
        return this;
    }

    public void setSecondaryExternalOperationCost(BigDecimal secondaryExternalOperationCost) {
        this.secondaryExternalOperationCost = secondaryExternalOperationCost;
    }

    public BigDecimal getOverhead() {
        return this.overhead;
    }

    public NtQuotePartInformationMaster overhead(BigDecimal overhead) {
        this.setOverhead(overhead);
        return this;
    }

    public void setOverhead(BigDecimal overhead) {
        this.overhead = overhead;
    }

    public BigDecimal getPackLogisticCostPer() {
        return this.packLogisticCostPer;
    }

    public NtQuotePartInformationMaster packLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.setPackLogisticCostPer(packLogisticCostPer);
        return this;
    }

    public void setPackLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.packLogisticCostPer = packLogisticCostPer;
    }

    public String getMachineSizeTons() {
        return this.machineSizeTons;
    }

    public NtQuotePartInformationMaster machineSizeTons(String machineSizeTons) {
        this.setMachineSizeTons(machineSizeTons);
        return this;
    }

    public void setMachineSizeTons(String machineSizeTons) {
        this.machineSizeTons = machineSizeTons;
    }

    public Integer getNumberOfCavities() {
        return this.numberOfCavities;
    }

    public NtQuotePartInformationMaster numberOfCavities(Integer numberOfCavities) {
        this.setNumberOfCavities(numberOfCavities);
        return this;
    }

    public void setNumberOfCavities(Integer numberOfCavities) {
        this.numberOfCavities = numberOfCavities;
    }

    public Integer getCycleTime() {
        return this.cycleTime;
    }

    public NtQuotePartInformationMaster cycleTime(Integer cycleTime) {
        this.setCycleTime(cycleTime);
        return this;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getPerUnit() {
        return this.perUnit;
    }

    public NtQuotePartInformationMaster perUnit(BigDecimal perUnit) {
        this.setPerUnit(perUnit);
        return this;
    }

    public void setPerUnit(BigDecimal perUnit) {
        this.perUnit = perUnit;
    }

    public BigDecimal getTotalPricePerChina() {
        return this.totalPricePerChina;
    }

    public NtQuotePartInformationMaster totalPricePerChina(BigDecimal totalPricePerChina) {
        this.setTotalPricePerChina(totalPricePerChina);
        return this;
    }

    public void setTotalPricePerChina(BigDecimal totalPricePerChina) {
        this.totalPricePerChina = totalPricePerChina;
    }

    public BigDecimal getTotalPriceBudget() {
        return this.totalPriceBudget;
    }

    public NtQuotePartInformationMaster totalPriceBudget(BigDecimal totalPriceBudget) {
        this.setTotalPriceBudget(totalPriceBudget);
        return this;
    }

    public void setTotalPriceBudget(BigDecimal totalPriceBudget) {
        this.totalPriceBudget = totalPriceBudget;
    }

    public BigDecimal getGrainBudget() {
        return this.grainBudget;
    }

    public NtQuotePartInformationMaster grainBudget(BigDecimal grainBudget) {
        this.setGrainBudget(grainBudget);
        return this;
    }

    public void setGrainBudget(BigDecimal grainBudget) {
        this.grainBudget = grainBudget;
    }

    public BigDecimal getDogatingFixtureBudget() {
        return this.dogatingFixtureBudget;
    }

    public NtQuotePartInformationMaster dogatingFixtureBudget(BigDecimal dogatingFixtureBudget) {
        this.setDogatingFixtureBudget(dogatingFixtureBudget);
        return this;
    }

    public void setDogatingFixtureBudget(BigDecimal dogatingFixtureBudget) {
        this.dogatingFixtureBudget = dogatingFixtureBudget;
    }

    public BigDecimal getGaugeBudget() {
        return this.gaugeBudget;
    }

    public NtQuotePartInformationMaster gaugeBudget(BigDecimal gaugeBudget) {
        this.setGaugeBudget(gaugeBudget);
        return this;
    }

    public void setGaugeBudget(BigDecimal gaugeBudget) {
        this.gaugeBudget = gaugeBudget;
    }

    public BigDecimal getEoat() {
        return this.eoat;
    }

    public NtQuotePartInformationMaster eoat(BigDecimal eoat) {
        this.setEoat(eoat);
        return this;
    }

    public void setEoat(BigDecimal eoat) {
        this.eoat = eoat;
    }

    public BigDecimal getChinaTariffBudget() {
        return this.chinaTariffBudget;
    }

    public NtQuotePartInformationMaster chinaTariffBudget(BigDecimal chinaTariffBudget) {
        this.setChinaTariffBudget(chinaTariffBudget);
        return this;
    }

    public void setChinaTariffBudget(BigDecimal chinaTariffBudget) {
        this.chinaTariffBudget = chinaTariffBudget;
    }

    public BigDecimal getTotalToolingBudget() {
        return this.totalToolingBudget;
    }

    public NtQuotePartInformationMaster totalToolingBudget(BigDecimal totalToolingBudget) {
        this.setTotalToolingBudget(totalToolingBudget);
        return this;
    }

    public void setTotalToolingBudget(BigDecimal totalToolingBudget) {
        this.totalToolingBudget = totalToolingBudget;
    }

    public String getLeadTime() {
        return this.leadTime;
    }

    public NtQuotePartInformationMaster leadTime(String leadTime) {
        this.setLeadTime(leadTime);
        return this;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getToolingNotes() {
        return this.toolingNotes;
    }

    public NtQuotePartInformationMaster toolingNotes(String toolingNotes) {
        this.setToolingNotes(toolingNotes);
        return this;
    }

    public void setToolingNotes(String toolingNotes) {
        this.toolingNotes = toolingNotes;
    }

    public String getPartDescription() {
        return this.partDescription;
    }

    public NtQuotePartInformationMaster partDescription(String partDescription) {
        this.setPartDescription(partDescription);
        return this;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getJobId() {
        return this.jobId;
    }

    public NtQuotePartInformationMaster jobId(String jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getMoldId() {
        return this.moldId;
    }

    public NtQuotePartInformationMaster moldId(String moldId) {
        this.setMoldId(moldId);
        return this;
    }

    public void setMoldId(String moldId) {
        this.moldId = moldId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuotePartInformationMaster createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuotePartInformationMaster createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuotePartInformationMaster updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuotePartInformationMaster updatedDate(Instant updatedDate) {
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

    public NtQuotePartInformationMaster ntQuote(NtQuote ntQuote) {
        this.setNtQuote(ntQuote);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuotePartInformationMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuotePartInformationMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuotePartInformationMaster{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", cadFile='" + getCadFile() + "'" +
            ", eau=" + getEau() +
            ", partWeight=" + getPartWeight() +
            ", materialType='" + getMaterialType() + "'" +
            ", materialCost=" + getMaterialCost() +
            ", extendedMaterialCostPer=" + getExtendedMaterialCostPer() +
            ", externalMachineCostPer=" + getExternalMachineCostPer() +
            ", purchaseComponentCost=" + getPurchaseComponentCost() +
            ", secondaryExternalOperationCost=" + getSecondaryExternalOperationCost() +
            ", overhead=" + getOverhead() +
            ", packLogisticCostPer=" + getPackLogisticCostPer() +
            ", machineSizeTons='" + getMachineSizeTons() + "'" +
            ", numberOfCavities=" + getNumberOfCavities() +
            ", cycleTime=" + getCycleTime() +
            ", perUnit=" + getPerUnit() +
            ", totalPricePerChina=" + getTotalPricePerChina() +
            ", totalPriceBudget=" + getTotalPriceBudget() +
            ", grainBudget=" + getGrainBudget() +
            ", dogatingFixtureBudget=" + getDogatingFixtureBudget() +
            ", gaugeBudget=" + getGaugeBudget() +
            ", eoat=" + getEoat() +
            ", chinaTariffBudget=" + getChinaTariffBudget() +
            ", totalToolingBudget=" + getTotalToolingBudget() +
            ", leadTime='" + getLeadTime() + "'" +
            ", toolingNotes='" + getToolingNotes() + "'" +
            ", partDescription='" + getPartDescription() + "'" +
            ", jobId='" + getJobId() + "'" +
            ", moldId='" + getMoldId() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
