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
 * A NtQuoteCustomerInputOutputVersion.
 */
@Entity
@Table(name = "nt_quote_customer_input_output_version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquotecustomerinputoutputversion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerInputOutputVersion implements Serializable {

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

    @Column(name = "material_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materialId;

    @Column(name = "supplier")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String supplier;

    @Column(name = "est_annual_volume")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer estAnnualVolume;

    @Column(name = "est_production_run_yrs")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer estProductionRunYrs;

    @Column(name = "material_cost_lb", precision = 21, scale = 2)
    private BigDecimal materialCostLb;

    @Column(name = "part_weight_lb", precision = 21, scale = 2)
    private BigDecimal partWeightLb;

    @Column(name = "runner_weight_lb", precision = 21, scale = 2)
    private BigDecimal runnerWeightLb;

    @Column(name = "machine_size")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String machineSize;

    @Column(name = "machine_rate", precision = 21, scale = 2)
    private BigDecimal machineRate;

    @Column(name = "scrap_rate", precision = 21, scale = 2)
    private BigDecimal scrapRate;

    @Column(name = "machine_efficiency", precision = 21, scale = 2)
    private BigDecimal machineEfficiency;

    @Column(name = "fte")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String fte;

    @Column(name = "labor_rate", precision = 21, scale = 2)
    private BigDecimal laborRate;

    @Column(name = "number_of_cavities")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer numberOfCavities;

    @Column(name = "cycle_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer cycleTime;

    @Column(name = "purchase_component_cost_part", precision = 21, scale = 2)
    private BigDecimal purchaseComponentCostPart;

    @Column(name = "secondary_operation_external_process")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String secondaryOperationExternalProcess;

    @Column(name = "secondary_operation_labor_rate", precision = 21, scale = 2)
    private BigDecimal secondaryOperationLaborRate;

    @Column(name = "secondary_operation_machine_rate", precision = 21, scale = 2)
    private BigDecimal secondaryOperationMachineRate;

    @Column(name = "secondary_operation_cycle_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer secondaryOperationCycleTime;

    @Column(name = "external_operation_rate", precision = 21, scale = 2)
    private BigDecimal externalOperationRate;

    @Column(name = "preventative_maintenance_frequency")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer preventativeMaintenanceFrequency;

    @Column(name = "preventative_maintenance_cost")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer preventativeMaintenanceCost;

    @Column(name = "target_profit", precision = 21, scale = 2)
    private BigDecimal targetProfit;

    @Column(name = "target_material_markup", precision = 21, scale = 2)
    private BigDecimal targetMaterialMarkup;

    @Column(name = "actual_material_cost", precision = 21, scale = 2)
    private BigDecimal actualMaterialCost;

    @Column(name = "part_per_hours", precision = 21, scale = 2)
    private BigDecimal partPerHours;

    @Column(name = "est_lot_size", precision = 21, scale = 2)
    private BigDecimal estLotSize;

    @Column(name = "setup_hours", precision = 21, scale = 2)
    private BigDecimal setupHours;

    @Column(name = "external_operation_cost_per", precision = 21, scale = 2)
    private BigDecimal externalOperationCostPer;

    @Column(name = "external_machine_cost_per", precision = 21, scale = 2)
    private BigDecimal externalMachineCostPer;

    @Column(name = "extended_labor_cost_per", precision = 21, scale = 2)
    private BigDecimal extendedLaborCostPer;

    @Column(name = "extended_material_cost_per", precision = 21, scale = 2)
    private BigDecimal extendedMaterialCostPer;

    @Column(name = "pack_logistic_cost_per", precision = 21, scale = 2)
    private BigDecimal packLogisticCostPer;

    @Column(name = "total_production_cost", precision = 21, scale = 2)
    private BigDecimal totalProductionCost;

    @Column(name = "total_material_cost", precision = 21, scale = 2)
    private BigDecimal totalMaterialCost;

    @Column(name = "total_cost_sga_profit", precision = 21, scale = 2)
    private BigDecimal totalCostSgaProfit;

    @Column(name = "sga_rate", precision = 21, scale = 2)
    private BigDecimal sgaRate;

    @Column(name = "profit", precision = 21, scale = 2)
    private BigDecimal profit;

    @Column(name = "part_price", precision = 21, scale = 2)
    private BigDecimal partPrice;

    @Column(name = "total_cost", precision = 21, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "total_sales", precision = 21, scale = 2)
    private BigDecimal totalSales;

    @Column(name = "total_profit", precision = 21, scale = 2)
    private BigDecimal totalProfit;

    @Column(name = "cost_material", precision = 21, scale = 2)
    private BigDecimal costMaterial;

    @Column(name = "total_contribution_margin", precision = 21, scale = 2)
    private BigDecimal totalContributionMargin;

    @Column(name = "contribution_margin", precision = 21, scale = 2)
    private BigDecimal contributionMargin;

    @Column(name = "material_contribution_margin", precision = 21, scale = 2)
    private BigDecimal materialContributionMargin;

    @Column(name = "version")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer version;

    @Column(name = "comments")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String comments;

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

    public NtQuoteCustomerInputOutputVersion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuoteCustomerInputOutputVersion srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuoteCustomerInputOutputVersion uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getMaterialDescription() {
        return this.materialDescription;
    }

    public NtQuoteCustomerInputOutputVersion materialDescription(String materialDescription) {
        this.setMaterialDescription(materialDescription);
        return this;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public NtQuoteCustomerInputOutputVersion partNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getMaterialId() {
        return this.materialId;
    }

    public NtQuoteCustomerInputOutputVersion materialId(String materialId) {
        this.setMaterialId(materialId);
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public NtQuoteCustomerInputOutputVersion supplier(String supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getEstAnnualVolume() {
        return this.estAnnualVolume;
    }

    public NtQuoteCustomerInputOutputVersion estAnnualVolume(Integer estAnnualVolume) {
        this.setEstAnnualVolume(estAnnualVolume);
        return this;
    }

    public void setEstAnnualVolume(Integer estAnnualVolume) {
        this.estAnnualVolume = estAnnualVolume;
    }

    public Integer getEstProductionRunYrs() {
        return this.estProductionRunYrs;
    }

    public NtQuoteCustomerInputOutputVersion estProductionRunYrs(Integer estProductionRunYrs) {
        this.setEstProductionRunYrs(estProductionRunYrs);
        return this;
    }

    public void setEstProductionRunYrs(Integer estProductionRunYrs) {
        this.estProductionRunYrs = estProductionRunYrs;
    }

    public BigDecimal getMaterialCostLb() {
        return this.materialCostLb;
    }

    public NtQuoteCustomerInputOutputVersion materialCostLb(BigDecimal materialCostLb) {
        this.setMaterialCostLb(materialCostLb);
        return this;
    }

    public void setMaterialCostLb(BigDecimal materialCostLb) {
        this.materialCostLb = materialCostLb;
    }

    public BigDecimal getPartWeightLb() {
        return this.partWeightLb;
    }

    public NtQuoteCustomerInputOutputVersion partWeightLb(BigDecimal partWeightLb) {
        this.setPartWeightLb(partWeightLb);
        return this;
    }

    public void setPartWeightLb(BigDecimal partWeightLb) {
        this.partWeightLb = partWeightLb;
    }

    public BigDecimal getRunnerWeightLb() {
        return this.runnerWeightLb;
    }

    public NtQuoteCustomerInputOutputVersion runnerWeightLb(BigDecimal runnerWeightLb) {
        this.setRunnerWeightLb(runnerWeightLb);
        return this;
    }

    public void setRunnerWeightLb(BigDecimal runnerWeightLb) {
        this.runnerWeightLb = runnerWeightLb;
    }

    public String getMachineSize() {
        return this.machineSize;
    }

    public NtQuoteCustomerInputOutputVersion machineSize(String machineSize) {
        this.setMachineSize(machineSize);
        return this;
    }

    public void setMachineSize(String machineSize) {
        this.machineSize = machineSize;
    }

    public BigDecimal getMachineRate() {
        return this.machineRate;
    }

    public NtQuoteCustomerInputOutputVersion machineRate(BigDecimal machineRate) {
        this.setMachineRate(machineRate);
        return this;
    }

    public void setMachineRate(BigDecimal machineRate) {
        this.machineRate = machineRate;
    }

    public BigDecimal getScrapRate() {
        return this.scrapRate;
    }

    public NtQuoteCustomerInputOutputVersion scrapRate(BigDecimal scrapRate) {
        this.setScrapRate(scrapRate);
        return this;
    }

    public void setScrapRate(BigDecimal scrapRate) {
        this.scrapRate = scrapRate;
    }

    public BigDecimal getMachineEfficiency() {
        return this.machineEfficiency;
    }

    public NtQuoteCustomerInputOutputVersion machineEfficiency(BigDecimal machineEfficiency) {
        this.setMachineEfficiency(machineEfficiency);
        return this;
    }

    public void setMachineEfficiency(BigDecimal machineEfficiency) {
        this.machineEfficiency = machineEfficiency;
    }

    public String getFte() {
        return this.fte;
    }

    public NtQuoteCustomerInputOutputVersion fte(String fte) {
        this.setFte(fte);
        return this;
    }

    public void setFte(String fte) {
        this.fte = fte;
    }

    public BigDecimal getLaborRate() {
        return this.laborRate;
    }

    public NtQuoteCustomerInputOutputVersion laborRate(BigDecimal laborRate) {
        this.setLaborRate(laborRate);
        return this;
    }

    public void setLaborRate(BigDecimal laborRate) {
        this.laborRate = laborRate;
    }

    public Integer getNumberOfCavities() {
        return this.numberOfCavities;
    }

    public NtQuoteCustomerInputOutputVersion numberOfCavities(Integer numberOfCavities) {
        this.setNumberOfCavities(numberOfCavities);
        return this;
    }

    public void setNumberOfCavities(Integer numberOfCavities) {
        this.numberOfCavities = numberOfCavities;
    }

    public Integer getCycleTime() {
        return this.cycleTime;
    }

    public NtQuoteCustomerInputOutputVersion cycleTime(Integer cycleTime) {
        this.setCycleTime(cycleTime);
        return this;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getPurchaseComponentCostPart() {
        return this.purchaseComponentCostPart;
    }

    public NtQuoteCustomerInputOutputVersion purchaseComponentCostPart(BigDecimal purchaseComponentCostPart) {
        this.setPurchaseComponentCostPart(purchaseComponentCostPart);
        return this;
    }

    public void setPurchaseComponentCostPart(BigDecimal purchaseComponentCostPart) {
        this.purchaseComponentCostPart = purchaseComponentCostPart;
    }

    public String getSecondaryOperationExternalProcess() {
        return this.secondaryOperationExternalProcess;
    }

    public NtQuoteCustomerInputOutputVersion secondaryOperationExternalProcess(String secondaryOperationExternalProcess) {
        this.setSecondaryOperationExternalProcess(secondaryOperationExternalProcess);
        return this;
    }

    public void setSecondaryOperationExternalProcess(String secondaryOperationExternalProcess) {
        this.secondaryOperationExternalProcess = secondaryOperationExternalProcess;
    }

    public BigDecimal getSecondaryOperationLaborRate() {
        return this.secondaryOperationLaborRate;
    }

    public NtQuoteCustomerInputOutputVersion secondaryOperationLaborRate(BigDecimal secondaryOperationLaborRate) {
        this.setSecondaryOperationLaborRate(secondaryOperationLaborRate);
        return this;
    }

    public void setSecondaryOperationLaborRate(BigDecimal secondaryOperationLaborRate) {
        this.secondaryOperationLaborRate = secondaryOperationLaborRate;
    }

    public BigDecimal getSecondaryOperationMachineRate() {
        return this.secondaryOperationMachineRate;
    }

    public NtQuoteCustomerInputOutputVersion secondaryOperationMachineRate(BigDecimal secondaryOperationMachineRate) {
        this.setSecondaryOperationMachineRate(secondaryOperationMachineRate);
        return this;
    }

    public void setSecondaryOperationMachineRate(BigDecimal secondaryOperationMachineRate) {
        this.secondaryOperationMachineRate = secondaryOperationMachineRate;
    }

    public Integer getSecondaryOperationCycleTime() {
        return this.secondaryOperationCycleTime;
    }

    public NtQuoteCustomerInputOutputVersion secondaryOperationCycleTime(Integer secondaryOperationCycleTime) {
        this.setSecondaryOperationCycleTime(secondaryOperationCycleTime);
        return this;
    }

    public void setSecondaryOperationCycleTime(Integer secondaryOperationCycleTime) {
        this.secondaryOperationCycleTime = secondaryOperationCycleTime;
    }

    public BigDecimal getExternalOperationRate() {
        return this.externalOperationRate;
    }

    public NtQuoteCustomerInputOutputVersion externalOperationRate(BigDecimal externalOperationRate) {
        this.setExternalOperationRate(externalOperationRate);
        return this;
    }

    public void setExternalOperationRate(BigDecimal externalOperationRate) {
        this.externalOperationRate = externalOperationRate;
    }

    public Integer getPreventativeMaintenanceFrequency() {
        return this.preventativeMaintenanceFrequency;
    }

    public NtQuoteCustomerInputOutputVersion preventativeMaintenanceFrequency(Integer preventativeMaintenanceFrequency) {
        this.setPreventativeMaintenanceFrequency(preventativeMaintenanceFrequency);
        return this;
    }

    public void setPreventativeMaintenanceFrequency(Integer preventativeMaintenanceFrequency) {
        this.preventativeMaintenanceFrequency = preventativeMaintenanceFrequency;
    }

    public Integer getPreventativeMaintenanceCost() {
        return this.preventativeMaintenanceCost;
    }

    public NtQuoteCustomerInputOutputVersion preventativeMaintenanceCost(Integer preventativeMaintenanceCost) {
        this.setPreventativeMaintenanceCost(preventativeMaintenanceCost);
        return this;
    }

    public void setPreventativeMaintenanceCost(Integer preventativeMaintenanceCost) {
        this.preventativeMaintenanceCost = preventativeMaintenanceCost;
    }

    public BigDecimal getTargetProfit() {
        return this.targetProfit;
    }

    public NtQuoteCustomerInputOutputVersion targetProfit(BigDecimal targetProfit) {
        this.setTargetProfit(targetProfit);
        return this;
    }

    public void setTargetProfit(BigDecimal targetProfit) {
        this.targetProfit = targetProfit;
    }

    public BigDecimal getTargetMaterialMarkup() {
        return this.targetMaterialMarkup;
    }

    public NtQuoteCustomerInputOutputVersion targetMaterialMarkup(BigDecimal targetMaterialMarkup) {
        this.setTargetMaterialMarkup(targetMaterialMarkup);
        return this;
    }

    public void setTargetMaterialMarkup(BigDecimal targetMaterialMarkup) {
        this.targetMaterialMarkup = targetMaterialMarkup;
    }

    public BigDecimal getActualMaterialCost() {
        return this.actualMaterialCost;
    }

    public NtQuoteCustomerInputOutputVersion actualMaterialCost(BigDecimal actualMaterialCost) {
        this.setActualMaterialCost(actualMaterialCost);
        return this;
    }

    public void setActualMaterialCost(BigDecimal actualMaterialCost) {
        this.actualMaterialCost = actualMaterialCost;
    }

    public BigDecimal getPartPerHours() {
        return this.partPerHours;
    }

    public NtQuoteCustomerInputOutputVersion partPerHours(BigDecimal partPerHours) {
        this.setPartPerHours(partPerHours);
        return this;
    }

    public void setPartPerHours(BigDecimal partPerHours) {
        this.partPerHours = partPerHours;
    }

    public BigDecimal getEstLotSize() {
        return this.estLotSize;
    }

    public NtQuoteCustomerInputOutputVersion estLotSize(BigDecimal estLotSize) {
        this.setEstLotSize(estLotSize);
        return this;
    }

    public void setEstLotSize(BigDecimal estLotSize) {
        this.estLotSize = estLotSize;
    }

    public BigDecimal getSetupHours() {
        return this.setupHours;
    }

    public NtQuoteCustomerInputOutputVersion setupHours(BigDecimal setupHours) {
        this.setSetupHours(setupHours);
        return this;
    }

    public void setSetupHours(BigDecimal setupHours) {
        this.setupHours = setupHours;
    }

    public BigDecimal getExternalOperationCostPer() {
        return this.externalOperationCostPer;
    }

    public NtQuoteCustomerInputOutputVersion externalOperationCostPer(BigDecimal externalOperationCostPer) {
        this.setExternalOperationCostPer(externalOperationCostPer);
        return this;
    }

    public void setExternalOperationCostPer(BigDecimal externalOperationCostPer) {
        this.externalOperationCostPer = externalOperationCostPer;
    }

    public BigDecimal getExternalMachineCostPer() {
        return this.externalMachineCostPer;
    }

    public NtQuoteCustomerInputOutputVersion externalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.setExternalMachineCostPer(externalMachineCostPer);
        return this;
    }

    public void setExternalMachineCostPer(BigDecimal externalMachineCostPer) {
        this.externalMachineCostPer = externalMachineCostPer;
    }

    public BigDecimal getExtendedLaborCostPer() {
        return this.extendedLaborCostPer;
    }

    public NtQuoteCustomerInputOutputVersion extendedLaborCostPer(BigDecimal extendedLaborCostPer) {
        this.setExtendedLaborCostPer(extendedLaborCostPer);
        return this;
    }

    public void setExtendedLaborCostPer(BigDecimal extendedLaborCostPer) {
        this.extendedLaborCostPer = extendedLaborCostPer;
    }

    public BigDecimal getExtendedMaterialCostPer() {
        return this.extendedMaterialCostPer;
    }

    public NtQuoteCustomerInputOutputVersion extendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.setExtendedMaterialCostPer(extendedMaterialCostPer);
        return this;
    }

    public void setExtendedMaterialCostPer(BigDecimal extendedMaterialCostPer) {
        this.extendedMaterialCostPer = extendedMaterialCostPer;
    }

    public BigDecimal getPackLogisticCostPer() {
        return this.packLogisticCostPer;
    }

    public NtQuoteCustomerInputOutputVersion packLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.setPackLogisticCostPer(packLogisticCostPer);
        return this;
    }

    public void setPackLogisticCostPer(BigDecimal packLogisticCostPer) {
        this.packLogisticCostPer = packLogisticCostPer;
    }

    public BigDecimal getTotalProductionCost() {
        return this.totalProductionCost;
    }

    public NtQuoteCustomerInputOutputVersion totalProductionCost(BigDecimal totalProductionCost) {
        this.setTotalProductionCost(totalProductionCost);
        return this;
    }

    public void setTotalProductionCost(BigDecimal totalProductionCost) {
        this.totalProductionCost = totalProductionCost;
    }

    public BigDecimal getTotalMaterialCost() {
        return this.totalMaterialCost;
    }

    public NtQuoteCustomerInputOutputVersion totalMaterialCost(BigDecimal totalMaterialCost) {
        this.setTotalMaterialCost(totalMaterialCost);
        return this;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalCostSgaProfit() {
        return this.totalCostSgaProfit;
    }

    public NtQuoteCustomerInputOutputVersion totalCostSgaProfit(BigDecimal totalCostSgaProfit) {
        this.setTotalCostSgaProfit(totalCostSgaProfit);
        return this;
    }

    public void setTotalCostSgaProfit(BigDecimal totalCostSgaProfit) {
        this.totalCostSgaProfit = totalCostSgaProfit;
    }

    public BigDecimal getSgaRate() {
        return this.sgaRate;
    }

    public NtQuoteCustomerInputOutputVersion sgaRate(BigDecimal sgaRate) {
        this.setSgaRate(sgaRate);
        return this;
    }

    public void setSgaRate(BigDecimal sgaRate) {
        this.sgaRate = sgaRate;
    }

    public BigDecimal getProfit() {
        return this.profit;
    }

    public NtQuoteCustomerInputOutputVersion profit(BigDecimal profit) {
        this.setProfit(profit);
        return this;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPartPrice() {
        return this.partPrice;
    }

    public NtQuoteCustomerInputOutputVersion partPrice(BigDecimal partPrice) {
        this.setPartPrice(partPrice);
        return this;
    }

    public void setPartPrice(BigDecimal partPrice) {
        this.partPrice = partPrice;
    }

    public BigDecimal getTotalCost() {
        return this.totalCost;
    }

    public NtQuoteCustomerInputOutputVersion totalCost(BigDecimal totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalSales() {
        return this.totalSales;
    }

    public NtQuoteCustomerInputOutputVersion totalSales(BigDecimal totalSales) {
        this.setTotalSales(totalSales);
        return this;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalProfit() {
        return this.totalProfit;
    }

    public NtQuoteCustomerInputOutputVersion totalProfit(BigDecimal totalProfit) {
        this.setTotalProfit(totalProfit);
        return this;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getCostMaterial() {
        return this.costMaterial;
    }

    public NtQuoteCustomerInputOutputVersion costMaterial(BigDecimal costMaterial) {
        this.setCostMaterial(costMaterial);
        return this;
    }

    public void setCostMaterial(BigDecimal costMaterial) {
        this.costMaterial = costMaterial;
    }

    public BigDecimal getTotalContributionMargin() {
        return this.totalContributionMargin;
    }

    public NtQuoteCustomerInputOutputVersion totalContributionMargin(BigDecimal totalContributionMargin) {
        this.setTotalContributionMargin(totalContributionMargin);
        return this;
    }

    public void setTotalContributionMargin(BigDecimal totalContributionMargin) {
        this.totalContributionMargin = totalContributionMargin;
    }

    public BigDecimal getContributionMargin() {
        return this.contributionMargin;
    }

    public NtQuoteCustomerInputOutputVersion contributionMargin(BigDecimal contributionMargin) {
        this.setContributionMargin(contributionMargin);
        return this;
    }

    public void setContributionMargin(BigDecimal contributionMargin) {
        this.contributionMargin = contributionMargin;
    }

    public BigDecimal getMaterialContributionMargin() {
        return this.materialContributionMargin;
    }

    public NtQuoteCustomerInputOutputVersion materialContributionMargin(BigDecimal materialContributionMargin) {
        this.setMaterialContributionMargin(materialContributionMargin);
        return this;
    }

    public void setMaterialContributionMargin(BigDecimal materialContributionMargin) {
        this.materialContributionMargin = materialContributionMargin;
    }

    public Integer getVersion() {
        return this.version;
    }

    public NtQuoteCustomerInputOutputVersion version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getComments() {
        return this.comments;
    }

    public NtQuoteCustomerInputOutputVersion comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuoteCustomerInputOutputVersion createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuoteCustomerInputOutputVersion createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuoteCustomerInputOutputVersion updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuoteCustomerInputOutputVersion updatedDate(Instant updatedDate) {
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

    public NtQuoteCustomerInputOutputVersion ntQuote(NtQuote ntQuote) {
        this.setNtQuote(ntQuote);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteCustomerInputOutputVersion)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuoteCustomerInputOutputVersion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCustomerInputOutputVersion{" +
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
            "}";
    }
}
