package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerInputOutputVersionDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String materialDescription;

    private String partNumber;

    private String materialId;

    private String supplier;

    private Integer estAnnualVolume;

    private Integer estProductionRunYrs;

    private BigDecimal materialCostLb;

    private BigDecimal partWeightLb;

    private BigDecimal runnerWeightLb;

    private String machineSize;

    private BigDecimal machineRate;

    private BigDecimal scrapRate;

    private BigDecimal machineEfficiency;

    private String fte;

    private BigDecimal laborRate;

    private Integer numberOfCavities;

    private Integer cycleTime;

    private BigDecimal purchaseComponentCostPart;

    private String secondaryOperationExternalProcess;

    private BigDecimal secondaryOperationLaborRate;

    private BigDecimal secondaryOperationMachineRate;

    private Integer secondaryOperationCycleTime;

    private BigDecimal externalOperationRate;

    private Integer preventativeMaintenanceFrequency;

    private Integer preventativeMaintenanceCost;

    private BigDecimal targetProfit;

    private BigDecimal targetMaterialMarkup;

    private BigDecimal actualMaterialCost;

    private BigDecimal partPerHours;

    private BigDecimal estLotSize;

    private BigDecimal setupHours;

    private BigDecimal externalOperationCostPer;

    private BigDecimal externalMachineCostPer;

    private BigDecimal extendedLaborCostPer;

    private BigDecimal extendedMaterialCostPer;

    private BigDecimal packLogisticCostPer;

    private BigDecimal totalProductionCost;

    private BigDecimal totalMaterialCost;

    private BigDecimal totalCostSgaProfit;

    private BigDecimal sgaRate;

    private BigDecimal profit;

    private BigDecimal partPrice;

    private BigDecimal totalCost;

    private BigDecimal totalSales;

    private BigDecimal totalProfit;

    private BigDecimal costMaterial;

    private BigDecimal totalContributionMargin;

    private BigDecimal contributionMargin;

    private BigDecimal materialContributionMargin;

    private Integer version;

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

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getEstAnnualVolume() {
        return estAnnualVolume;
    }

    public void setEstAnnualVolume(Integer estAnnualVolume) {
        this.estAnnualVolume = estAnnualVolume;
    }

    public Integer getEstProductionRunYrs() {
        return estProductionRunYrs;
    }

    public void setEstProductionRunYrs(Integer estProductionRunYrs) {
        this.estProductionRunYrs = estProductionRunYrs;
    }

    public BigDecimal getMaterialCostLb() {
        return materialCostLb;
    }

    public void setMaterialCostLb(BigDecimal materialCostLb) {
        this.materialCostLb = materialCostLb;
    }

    public BigDecimal getPartWeightLb() {
        return partWeightLb;
    }

    public void setPartWeightLb(BigDecimal partWeightLb) {
        this.partWeightLb = partWeightLb;
    }

    public BigDecimal getRunnerWeightLb() {
        return runnerWeightLb;
    }

    public void setRunnerWeightLb(BigDecimal runnerWeightLb) {
        this.runnerWeightLb = runnerWeightLb;
    }

    public String getMachineSize() {
        return machineSize;
    }

    public void setMachineSize(String machineSize) {
        this.machineSize = machineSize;
    }

    public BigDecimal getMachineRate() {
        return machineRate;
    }

    public void setMachineRate(BigDecimal machineRate) {
        this.machineRate = machineRate;
    }

    public BigDecimal getScrapRate() {
        return scrapRate;
    }

    public void setScrapRate(BigDecimal scrapRate) {
        this.scrapRate = scrapRate;
    }

    public BigDecimal getMachineEfficiency() {
        return machineEfficiency;
    }

    public void setMachineEfficiency(BigDecimal machineEfficiency) {
        this.machineEfficiency = machineEfficiency;
    }

    public String getFte() {
        return fte;
    }

    public void setFte(String fte) {
        this.fte = fte;
    }

    public BigDecimal getLaborRate() {
        return laborRate;
    }

    public void setLaborRate(BigDecimal laborRate) {
        this.laborRate = laborRate;
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

    public BigDecimal getPurchaseComponentCostPart() {
        return purchaseComponentCostPart;
    }

    public void setPurchaseComponentCostPart(BigDecimal purchaseComponentCostPart) {
        this.purchaseComponentCostPart = purchaseComponentCostPart;
    }

    public String getSecondaryOperationExternalProcess() {
        return secondaryOperationExternalProcess;
    }

    public void setSecondaryOperationExternalProcess(String secondaryOperationExternalProcess) {
        this.secondaryOperationExternalProcess = secondaryOperationExternalProcess;
    }

    public BigDecimal getSecondaryOperationLaborRate() {
        return secondaryOperationLaborRate;
    }

    public void setSecondaryOperationLaborRate(BigDecimal secondaryOperationLaborRate) {
        this.secondaryOperationLaborRate = secondaryOperationLaborRate;
    }

    public BigDecimal getSecondaryOperationMachineRate() {
        return secondaryOperationMachineRate;
    }

    public void setSecondaryOperationMachineRate(BigDecimal secondaryOperationMachineRate) {
        this.secondaryOperationMachineRate = secondaryOperationMachineRate;
    }

    public Integer getSecondaryOperationCycleTime() {
        return secondaryOperationCycleTime;
    }

    public void setSecondaryOperationCycleTime(Integer secondaryOperationCycleTime) {
        this.secondaryOperationCycleTime = secondaryOperationCycleTime;
    }

    public BigDecimal getExternalOperationRate() {
        return externalOperationRate;
    }

    public void setExternalOperationRate(BigDecimal externalOperationRate) {
        this.externalOperationRate = externalOperationRate;
    }

    public Integer getPreventativeMaintenanceFrequency() {
        return preventativeMaintenanceFrequency;
    }

    public void setPreventativeMaintenanceFrequency(Integer preventativeMaintenanceFrequency) {
        this.preventativeMaintenanceFrequency = preventativeMaintenanceFrequency;
    }

    public Integer getPreventativeMaintenanceCost() {
        return preventativeMaintenanceCost;
    }

    public void setPreventativeMaintenanceCost(Integer preventativeMaintenanceCost) {
        this.preventativeMaintenanceCost = preventativeMaintenanceCost;
    }

    public BigDecimal getTargetProfit() {
        return targetProfit;
    }

    public void setTargetProfit(BigDecimal targetProfit) {
        this.targetProfit = targetProfit;
    }

    public BigDecimal getTargetMaterialMarkup() {
        return targetMaterialMarkup;
    }

    public void setTargetMaterialMarkup(BigDecimal targetMaterialMarkup) {
        this.targetMaterialMarkup = targetMaterialMarkup;
    }

    public BigDecimal getActualMaterialCost() {
        return actualMaterialCost;
    }

    public void setActualMaterialCost(BigDecimal actualMaterialCost) {
        this.actualMaterialCost = actualMaterialCost;
    }

    public BigDecimal getPartPerHours() {
        return partPerHours;
    }

    public void setPartPerHours(BigDecimal partPerHours) {
        this.partPerHours = partPerHours;
    }

    public BigDecimal getEstLotSize() {
        return estLotSize;
    }

    public void setEstLotSize(BigDecimal estLotSize) {
        this.estLotSize = estLotSize;
    }

    public BigDecimal getSetupHours() {
        return setupHours;
    }

    public void setSetupHours(BigDecimal setupHours) {
        this.setupHours = setupHours;
    }

    public BigDecimal getExternalOperationCostPer() {
        return externalOperationCostPer;
    }

    public void setExternalOperationCostPer(BigDecimal externalOperationCostPer) {
        this.externalOperationCostPer = externalOperationCostPer;
    }

    public BigDecimal getExternalMachineCostPer() {
        return externalMachineCostPer;
    }

    public void setExternalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.externalMachineCostPer = externalMachineCostPer;
    }

    public BigDecimal getExtendedLaborCostPer() {
        return extendedLaborCostPer;
    }

    public void setExtendedLaborCostPer(BigDecimal extendedLaborCostPer) {
        this.extendedLaborCostPer = extendedLaborCostPer;
    }

    public BigDecimal getExtendedMaterialCostPer() {
        return extendedMaterialCostPer;
    }

    public void setExtendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.extendedMaterialCostPer = extendedMaterialCostPer;
    }

    public BigDecimal getPackLogisticCostPer() {
        return packLogisticCostPer;
    }

    public void setPackLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.packLogisticCostPer = packLogisticCostPer;
    }

    public BigDecimal getTotalProductionCost() {
        return totalProductionCost;
    }

    public void setTotalProductionCost(BigDecimal totalProductionCost) {
        this.totalProductionCost = totalProductionCost;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalCostSgaProfit() {
        return totalCostSgaProfit;
    }

    public void setTotalCostSgaProfit(BigDecimal totalCostSgaProfit) {
        this.totalCostSgaProfit = totalCostSgaProfit;
    }

    public BigDecimal getSgaRate() {
        return sgaRate;
    }

    public void setSgaRate(BigDecimal sgaRate) {
        this.sgaRate = sgaRate;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(BigDecimal partPrice) {
        this.partPrice = partPrice;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getCostMaterial() {
        return costMaterial;
    }

    public void setCostMaterial(BigDecimal costMaterial) {
        this.costMaterial = costMaterial;
    }

    public BigDecimal getTotalContributionMargin() {
        return totalContributionMargin;
    }

    public void setTotalContributionMargin(BigDecimal totalContributionMargin) {
        this.totalContributionMargin = totalContributionMargin;
    }

    public BigDecimal getContributionMargin() {
        return contributionMargin;
    }

    public void setContributionMargin(BigDecimal contributionMargin) {
        this.contributionMargin = contributionMargin;
    }

    public BigDecimal getMaterialContributionMargin() {
        return materialContributionMargin;
    }

    public void setMaterialContributionMargin(BigDecimal materialContributionMargin) {
        this.materialContributionMargin = materialContributionMargin;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        if (!(o instanceof NtQuoteCustomerInputOutputVersionDTO)) {
            return false;
        }

        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = (NtQuoteCustomerInputOutputVersionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ntQuoteCustomerInputOutputVersionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCustomerInputOutputVersionDTO{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", supplier='" + getSupplier() + "'" +
            ", estAnnualVolume=" + getEstAnnualVolume() +
            ", estProductionRunYrs=" + getEstProductionRunYrs() +
            ", materialCostLb=" + getMaterialCostLb() +
            ", partWeightLb=" + getPartWeightLb() +
            ", runnerWeightLb=" + getRunnerWeightLb() +
            ", machineSize='" + getMachineSize() + "'" +
            ", machineRate=" + getMachineRate() +
            ", scrapRate=" + getScrapRate() +
            ", machineEfficiency=" + getMachineEfficiency() +
            ", fte='" + getFte() + "'" +
            ", laborRate=" + getLaborRate() +
            ", numberOfCavities=" + getNumberOfCavities() +
            ", cycleTime=" + getCycleTime() +
            ", purchaseComponentCostPart=" + getPurchaseComponentCostPart() +
            ", secondaryOperationExternalProcess='" + getSecondaryOperationExternalProcess() + "'" +
            ", secondaryOperationLaborRate=" + getSecondaryOperationLaborRate() +
            ", secondaryOperationMachineRate=" + getSecondaryOperationMachineRate() +
            ", secondaryOperationCycleTime=" + getSecondaryOperationCycleTime() +
            ", externalOperationRate=" + getExternalOperationRate() +
            ", preventativeMaintenanceFrequency=" + getPreventativeMaintenanceFrequency() +
            ", preventativeMaintenanceCost=" + getPreventativeMaintenanceCost() +
            ", targetProfit=" + getTargetProfit() +
            ", targetMaterialMarkup=" + getTargetMaterialMarkup() +
            ", actualMaterialCost=" + getActualMaterialCost() +
            ", partPerHours=" + getPartPerHours() +
            ", estLotSize=" + getEstLotSize() +
            ", setupHours=" + getSetupHours() +
            ", externalOperationCostPer=" + getExternalOperationCostPer() +
            ", externalMachineCostPer=" + getExternalMachineCostPer() +
            ", extendedLaborCostPer=" + getExtendedLaborCostPer() +
            ", extendedMaterialCostPer=" + getExtendedMaterialCostPer() +
            ", packLogisticCostPer=" + getPackLogisticCostPer() +
            ", totalProductionCost=" + getTotalProductionCost() +
            ", totalMaterialCost=" + getTotalMaterialCost() +
            ", totalCostSgaProfit=" + getTotalCostSgaProfit() +
            ", sgaRate=" + getSgaRate() +
            ", profit=" + getProfit() +
            ", partPrice=" + getPartPrice() +
            ", totalCost=" + getTotalCost() +
            ", totalSales=" + getTotalSales() +
            ", totalProfit=" + getTotalProfit() +
            ", costMaterial=" + getCostMaterial() +
            ", totalContributionMargin=" + getTotalContributionMargin() +
            ", contributionMargin=" + getContributionMargin() +
            ", materialContributionMargin=" + getMaterialContributionMargin() +
            ", version=" + getVersion() +
            ", comments='" + getComments() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", ntQuote=" + getNtQuote() +
            "}";
    }
}
