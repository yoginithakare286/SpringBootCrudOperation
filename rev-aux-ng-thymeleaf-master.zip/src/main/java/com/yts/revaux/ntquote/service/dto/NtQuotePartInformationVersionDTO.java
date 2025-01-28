package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuotePartInformationVersionDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String materialDescription;

    private String partNumber;

    private String cadFile;

    private Integer eau;

    private Integer partWeight;

    private String materialType;

    private BigDecimal materialCost;

    private BigDecimal extendedMaterialCostPer;

    private BigDecimal externalMachineCostPer;

    private BigDecimal purchaseComponentCost;

    private BigDecimal secondaryExternalOperationCost;

    private BigDecimal overhead;

    private BigDecimal packLogisticCostPer;

    private String machineSizeTons;

    private Integer numberOfCavities;

    private Integer cycleTime;

    private BigDecimal perUnit;

    private BigDecimal totalPricePerChina;

    private BigDecimal totalPriceBudget;

    private BigDecimal grainBudget;

    private BigDecimal dogatingFixtureBudget;

    private BigDecimal gaugeBudget;

    private BigDecimal eoat;

    private BigDecimal chinaTariffBudget;

    private BigDecimal totalToolingBudget;

    private String leadTime;

    private String toolingNotes;

    private String partDescription;

    private String jobId;

    private String moldId;

    private String quoteType;

    private String comments;

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;

    private NtQuoteDTO ntQuote;

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

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCadFile() {
        return cadFile;
    }

    public void setCadFile(String cadFile) {
        this.cadFile = cadFile;
    }

    public Integer getEau() {
        return eau;
    }

    public void setEau(Integer eau) {
        this.eau = eau;
    }

    public Integer getPartWeight() {
        return partWeight;
    }

    public void setPartWeight(Integer partWeight) {
        this.partWeight = partWeight;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getExtendedMaterialCostPer() {
        return extendedMaterialCostPer;
    }

    public void setExtendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.extendedMaterialCostPer = extendedMaterialCostPer;
    }

    public BigDecimal getExternalMachineCostPer() {
        return externalMachineCostPer;
    }

    public void setExternalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.externalMachineCostPer = externalMachineCostPer;
    }

    public BigDecimal getPurchaseComponentCost() {
        return purchaseComponentCost;
    }

    public void setPurchaseComponentCost(BigDecimal purchaseComponentCost) {
        this.purchaseComponentCost = purchaseComponentCost;
    }

    public BigDecimal getSecondaryExternalOperationCost() {
        return secondaryExternalOperationCost;
    }

    public void setSecondaryExternalOperationCost(BigDecimal secondaryExternalOperationCost) {
        this.secondaryExternalOperationCost = secondaryExternalOperationCost;
    }

    public BigDecimal getOverhead() {
        return overhead;
    }

    public void setOverhead(BigDecimal overhead) {
        this.overhead = overhead;
    }

    public BigDecimal getPackLogisticCostPer() {
        return packLogisticCostPer;
    }

    public void setPackLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.packLogisticCostPer = packLogisticCostPer;
    }

    public String getMachineSizeTons() {
        return machineSizeTons;
    }

    public void setMachineSizeTons(String machineSizeTons) {
        this.machineSizeTons = machineSizeTons;
    }

    public Integer getNumberOfCavities() {
        return numberOfCavities;
    }

    public void setNumberOfCavities(Integer numberOfCavities) {
        this.numberOfCavities = numberOfCavities;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getPerUnit() {
        return perUnit;
    }

    public void setPerUnit(BigDecimal perUnit) {
        this.perUnit = perUnit;
    }

    public BigDecimal getTotalPricePerChina() {
        return totalPricePerChina;
    }

    public void setTotalPricePerChina(BigDecimal totalPricePerChina) {
        this.totalPricePerChina = totalPricePerChina;
    }

    public BigDecimal getTotalPriceBudget() {
        return totalPriceBudget;
    }

    public void setTotalPriceBudget(BigDecimal totalPriceBudget) {
        this.totalPriceBudget = totalPriceBudget;
    }

    public BigDecimal getGrainBudget() {
        return grainBudget;
    }

    public void setGrainBudget(BigDecimal grainBudget) {
        this.grainBudget = grainBudget;
    }

    public BigDecimal getDogatingFixtureBudget() {
        return dogatingFixtureBudget;
    }

    public void setDogatingFixtureBudget(BigDecimal dogatingFixtureBudget) {
        this.dogatingFixtureBudget = dogatingFixtureBudget;
    }

    public BigDecimal getGaugeBudget() {
        return gaugeBudget;
    }

    public void setGaugeBudget(BigDecimal gaugeBudget) {
        this.gaugeBudget = gaugeBudget;
    }

    public BigDecimal getEoat() {
        return eoat;
    }

    public void setEoat(BigDecimal eoat) {
        this.eoat = eoat;
    }

    public BigDecimal getChinaTariffBudget() {
        return chinaTariffBudget;
    }

    public void setChinaTariffBudget(BigDecimal chinaTariffBudget) {
        this.chinaTariffBudget = chinaTariffBudget;
    }

    public BigDecimal getTotalToolingBudget() {
        return totalToolingBudget;
    }

    public void setTotalToolingBudget(BigDecimal totalToolingBudget) {
        this.totalToolingBudget = totalToolingBudget;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getToolingNotes() {
        return toolingNotes;
    }

    public void setToolingNotes(String toolingNotes) {
        this.toolingNotes = toolingNotes;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getMoldId() {
        return moldId;
    }

    public void setMoldId(String moldId) {
        this.moldId = moldId;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public NtQuoteDTO getNtQuote() {
        return ntQuote;
    }

    public void setNtQuote(NtQuoteDTO ntQuote) {
        this.ntQuote = ntQuote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuotePartInformationVersionDTO)) {
            return false;
        }

        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = (NtQuotePartInformationVersionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ntQuotePartInformationVersionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuotePartInformationVersionDTO{" +
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
            ", quoteType='" + getQuoteType() + "'" +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", ntQuote=" + getNtQuote() +
            "}";
    }
}
