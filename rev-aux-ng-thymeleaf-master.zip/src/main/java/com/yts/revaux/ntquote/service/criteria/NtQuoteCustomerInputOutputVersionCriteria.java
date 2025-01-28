package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteCustomerInputOutputVersionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-customer-input-output-versions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerInputOutputVersionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter materialDescription;

    private StringFilter partNumber;

    private StringFilter materialId;

    private StringFilter supplier;

    private IntegerFilter estAnnualVolume;

    private IntegerFilter estProductionRunYrs;

    private BigDecimalFilter materialCostLb;

    private BigDecimalFilter partWeightLb;

    private BigDecimalFilter runnerWeightLb;

    private StringFilter machineSize;

    private BigDecimalFilter machineRate;

    private BigDecimalFilter scrapRate;

    private BigDecimalFilter machineEfficiency;

    private StringFilter fte;

    private BigDecimalFilter laborRate;

    private IntegerFilter numberOfCavities;

    private IntegerFilter cycleTime;

    private BigDecimalFilter purchaseComponentCostPart;

    private StringFilter secondaryOperationExternalProcess;

    private BigDecimalFilter secondaryOperationLaborRate;

    private BigDecimalFilter secondaryOperationMachineRate;

    private IntegerFilter secondaryOperationCycleTime;

    private BigDecimalFilter externalOperationRate;

    private IntegerFilter preventativeMaintenanceFrequency;

    private IntegerFilter preventativeMaintenanceCost;

    private BigDecimalFilter targetProfit;

    private BigDecimalFilter targetMaterialMarkup;

    private BigDecimalFilter actualMaterialCost;

    private BigDecimalFilter partPerHours;

    private BigDecimalFilter estLotSize;

    private BigDecimalFilter setupHours;

    private BigDecimalFilter externalOperationCostPer;

    private BigDecimalFilter externalMachineCostPer;

    private BigDecimalFilter extendedLaborCostPer;

    private BigDecimalFilter extendedMaterialCostPer;

    private BigDecimalFilter packLogisticCostPer;

    private BigDecimalFilter totalProductionCost;

    private BigDecimalFilter totalMaterialCost;

    private BigDecimalFilter totalCostSgaProfit;

    private BigDecimalFilter sgaRate;

    private BigDecimalFilter profit;

    private BigDecimalFilter partPrice;

    private BigDecimalFilter totalCost;

    private BigDecimalFilter totalSales;

    private BigDecimalFilter totalProfit;

    private BigDecimalFilter costMaterial;

    private BigDecimalFilter totalContributionMargin;

    private BigDecimalFilter contributionMargin;

    private BigDecimalFilter materialContributionMargin;

    private IntegerFilter version;

    private StringFilter comments;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private Boolean distinct;

    public NtQuoteCustomerInputOutputVersionCriteria() {}

    public NtQuoteCustomerInputOutputVersionCriteria(NtQuoteCustomerInputOutputVersionCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.materialDescription = other.optionalMaterialDescription().map(StringFilter::copy).orElse(null);
        this.partNumber = other.optionalPartNumber().map(StringFilter::copy).orElse(null);
        this.materialId = other.optionalMaterialId().map(StringFilter::copy).orElse(null);
        this.supplier = other.optionalSupplier().map(StringFilter::copy).orElse(null);
        this.estAnnualVolume = other.optionalEstAnnualVolume().map(IntegerFilter::copy).orElse(null);
        this.estProductionRunYrs = other.optionalEstProductionRunYrs().map(IntegerFilter::copy).orElse(null);
        this.materialCostLb = other.optionalMaterialCostLb().map(BigDecimalFilter::copy).orElse(null);
        this.partWeightLb = other.optionalPartWeightLb().map(BigDecimalFilter::copy).orElse(null);
        this.runnerWeightLb = other.optionalRunnerWeightLb().map(BigDecimalFilter::copy).orElse(null);
        this.machineSize = other.optionalMachineSize().map(StringFilter::copy).orElse(null);
        this.machineRate = other.optionalMachineRate().map(BigDecimalFilter::copy).orElse(null);
        this.scrapRate = other.optionalScrapRate().map(BigDecimalFilter::copy).orElse(null);
        this.machineEfficiency = other.optionalMachineEfficiency().map(BigDecimalFilter::copy).orElse(null);
        this.fte = other.optionalFte().map(StringFilter::copy).orElse(null);
        this.laborRate = other.optionalLaborRate().map(BigDecimalFilter::copy).orElse(null);
        this.numberOfCavities = other.optionalNumberOfCavities().map(IntegerFilter::copy).orElse(null);
        this.cycleTime = other.optionalCycleTime().map(IntegerFilter::copy).orElse(null);
        this.purchaseComponentCostPart = other.optionalPurchaseComponentCostPart().map(BigDecimalFilter::copy).orElse(null);
        this.secondaryOperationExternalProcess = other.optionalSecondaryOperationExternalProcess().map(StringFilter::copy).orElse(null);
        this.secondaryOperationLaborRate = other.optionalSecondaryOperationLaborRate().map(BigDecimalFilter::copy).orElse(null);
        this.secondaryOperationMachineRate = other.optionalSecondaryOperationMachineRate().map(BigDecimalFilter::copy).orElse(null);
        this.secondaryOperationCycleTime = other.optionalSecondaryOperationCycleTime().map(IntegerFilter::copy).orElse(null);
        this.externalOperationRate = other.optionalExternalOperationRate().map(BigDecimalFilter::copy).orElse(null);
        this.preventativeMaintenanceFrequency = other.optionalPreventativeMaintenanceFrequency().map(IntegerFilter::copy).orElse(null);
        this.preventativeMaintenanceCost = other.optionalPreventativeMaintenanceCost().map(IntegerFilter::copy).orElse(null);
        this.targetProfit = other.optionalTargetProfit().map(BigDecimalFilter::copy).orElse(null);
        this.targetMaterialMarkup = other.optionalTargetMaterialMarkup().map(BigDecimalFilter::copy).orElse(null);
        this.actualMaterialCost = other.optionalActualMaterialCost().map(BigDecimalFilter::copy).orElse(null);
        this.partPerHours = other.optionalPartPerHours().map(BigDecimalFilter::copy).orElse(null);
        this.estLotSize = other.optionalEstLotSize().map(BigDecimalFilter::copy).orElse(null);
        this.setupHours = other.optionalSetupHours().map(BigDecimalFilter::copy).orElse(null);
        this.externalOperationCostPer = other.optionalExternalOperationCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.externalMachineCostPer = other.optionalExternalMachineCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.extendedLaborCostPer = other.optionalExtendedLaborCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.extendedMaterialCostPer = other.optionalExtendedMaterialCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.packLogisticCostPer = other.optionalPackLogisticCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.totalProductionCost = other.optionalTotalProductionCost().map(BigDecimalFilter::copy).orElse(null);
        this.totalMaterialCost = other.optionalTotalMaterialCost().map(BigDecimalFilter::copy).orElse(null);
        this.totalCostSgaProfit = other.optionalTotalCostSgaProfit().map(BigDecimalFilter::copy).orElse(null);
        this.sgaRate = other.optionalSgaRate().map(BigDecimalFilter::copy).orElse(null);
        this.profit = other.optionalProfit().map(BigDecimalFilter::copy).orElse(null);
        this.partPrice = other.optionalPartPrice().map(BigDecimalFilter::copy).orElse(null);
        this.totalCost = other.optionalTotalCost().map(BigDecimalFilter::copy).orElse(null);
        this.totalSales = other.optionalTotalSales().map(BigDecimalFilter::copy).orElse(null);
        this.totalProfit = other.optionalTotalProfit().map(BigDecimalFilter::copy).orElse(null);
        this.costMaterial = other.optionalCostMaterial().map(BigDecimalFilter::copy).orElse(null);
        this.totalContributionMargin = other.optionalTotalContributionMargin().map(BigDecimalFilter::copy).orElse(null);
        this.contributionMargin = other.optionalContributionMargin().map(BigDecimalFilter::copy).orElse(null);
        this.materialContributionMargin = other.optionalMaterialContributionMargin().map(BigDecimalFilter::copy).orElse(null);
        this.version = other.optionalVersion().map(IntegerFilter::copy).orElse(null);
        this.comments = other.optionalComments().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteCustomerInputOutputVersionCriteria copy() {
        return new NtQuoteCustomerInputOutputVersionCriteria(this);
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

    public StringFilter getPartNumber() {
        return partNumber;
    }

    public Optional<StringFilter> optionalPartNumber() {
        return Optional.ofNullable(partNumber);
    }

    public StringFilter partNumber() {
        if (partNumber == null) {
            setPartNumber(new StringFilter());
        }
        return partNumber;
    }

    public void setPartNumber(StringFilter partNumber) {
        this.partNumber = partNumber;
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

    public StringFilter getSupplier() {
        return supplier;
    }

    public Optional<StringFilter> optionalSupplier() {
        return Optional.ofNullable(supplier);
    }

    public StringFilter supplier() {
        if (supplier == null) {
            setSupplier(new StringFilter());
        }
        return supplier;
    }

    public void setSupplier(StringFilter supplier) {
        this.supplier = supplier;
    }

    public IntegerFilter getEstAnnualVolume() {
        return estAnnualVolume;
    }

    public Optional<IntegerFilter> optionalEstAnnualVolume() {
        return Optional.ofNullable(estAnnualVolume);
    }

    public IntegerFilter estAnnualVolume() {
        if (estAnnualVolume == null) {
            setEstAnnualVolume(new IntegerFilter());
        }
        return estAnnualVolume;
    }

    public void setEstAnnualVolume(IntegerFilter estAnnualVolume) {
        this.estAnnualVolume = estAnnualVolume;
    }

    public IntegerFilter getEstProductionRunYrs() {
        return estProductionRunYrs;
    }

    public Optional<IntegerFilter> optionalEstProductionRunYrs() {
        return Optional.ofNullable(estProductionRunYrs);
    }

    public IntegerFilter estProductionRunYrs() {
        if (estProductionRunYrs == null) {
            setEstProductionRunYrs(new IntegerFilter());
        }
        return estProductionRunYrs;
    }

    public void setEstProductionRunYrs(IntegerFilter estProductionRunYrs) {
        this.estProductionRunYrs = estProductionRunYrs;
    }

    public BigDecimalFilter getMaterialCostLb() {
        return materialCostLb;
    }

    public Optional<BigDecimalFilter> optionalMaterialCostLb() {
        return Optional.ofNullable(materialCostLb);
    }

    public BigDecimalFilter materialCostLb() {
        if (materialCostLb == null) {
            setMaterialCostLb(new BigDecimalFilter());
        }
        return materialCostLb;
    }

    public void setMaterialCostLb(BigDecimalFilter materialCostLb) {
        this.materialCostLb = materialCostLb;
    }

    public BigDecimalFilter getPartWeightLb() {
        return partWeightLb;
    }

    public Optional<BigDecimalFilter> optionalPartWeightLb() {
        return Optional.ofNullable(partWeightLb);
    }

    public BigDecimalFilter partWeightLb() {
        if (partWeightLb == null) {
            setPartWeightLb(new BigDecimalFilter());
        }
        return partWeightLb;
    }

    public void setPartWeightLb(BigDecimalFilter partWeightLb) {
        this.partWeightLb = partWeightLb;
    }

    public BigDecimalFilter getRunnerWeightLb() {
        return runnerWeightLb;
    }

    public Optional<BigDecimalFilter> optionalRunnerWeightLb() {
        return Optional.ofNullable(runnerWeightLb);
    }

    public BigDecimalFilter runnerWeightLb() {
        if (runnerWeightLb == null) {
            setRunnerWeightLb(new BigDecimalFilter());
        }
        return runnerWeightLb;
    }

    public void setRunnerWeightLb(BigDecimalFilter runnerWeightLb) {
        this.runnerWeightLb = runnerWeightLb;
    }

    public StringFilter getMachineSize() {
        return machineSize;
    }

    public Optional<StringFilter> optionalMachineSize() {
        return Optional.ofNullable(machineSize);
    }

    public StringFilter machineSize() {
        if (machineSize == null) {
            setMachineSize(new StringFilter());
        }
        return machineSize;
    }

    public void setMachineSize(StringFilter machineSize) {
        this.machineSize = machineSize;
    }

    public BigDecimalFilter getMachineRate() {
        return machineRate;
    }

    public Optional<BigDecimalFilter> optionalMachineRate() {
        return Optional.ofNullable(machineRate);
    }

    public BigDecimalFilter machineRate() {
        if (machineRate == null) {
            setMachineRate(new BigDecimalFilter());
        }
        return machineRate;
    }

    public void setMachineRate(BigDecimalFilter machineRate) {
        this.machineRate = machineRate;
    }

    public BigDecimalFilter getScrapRate() {
        return scrapRate;
    }

    public Optional<BigDecimalFilter> optionalScrapRate() {
        return Optional.ofNullable(scrapRate);
    }

    public BigDecimalFilter scrapRate() {
        if (scrapRate == null) {
            setScrapRate(new BigDecimalFilter());
        }
        return scrapRate;
    }

    public void setScrapRate(BigDecimalFilter scrapRate) {
        this.scrapRate = scrapRate;
    }

    public BigDecimalFilter getMachineEfficiency() {
        return machineEfficiency;
    }

    public Optional<BigDecimalFilter> optionalMachineEfficiency() {
        return Optional.ofNullable(machineEfficiency);
    }

    public BigDecimalFilter machineEfficiency() {
        if (machineEfficiency == null) {
            setMachineEfficiency(new BigDecimalFilter());
        }
        return machineEfficiency;
    }

    public void setMachineEfficiency(BigDecimalFilter machineEfficiency) {
        this.machineEfficiency = machineEfficiency;
    }

    public StringFilter getFte() {
        return fte;
    }

    public Optional<StringFilter> optionalFte() {
        return Optional.ofNullable(fte);
    }

    public StringFilter fte() {
        if (fte == null) {
            setFte(new StringFilter());
        }
        return fte;
    }

    public void setFte(StringFilter fte) {
        this.fte = fte;
    }

    public BigDecimalFilter getLaborRate() {
        return laborRate;
    }

    public Optional<BigDecimalFilter> optionalLaborRate() {
        return Optional.ofNullable(laborRate);
    }

    public BigDecimalFilter laborRate() {
        if (laborRate == null) {
            setLaborRate(new BigDecimalFilter());
        }
        return laborRate;
    }

    public void setLaborRate(BigDecimalFilter laborRate) {
        this.laborRate = laborRate;
    }

    public IntegerFilter getNumberOfCavities() {
        return numberOfCavities;
    }

    public Optional<IntegerFilter> optionalNumberOfCavities() {
        return Optional.ofNullable(numberOfCavities);
    }

    public IntegerFilter numberOfCavities() {
        if (numberOfCavities == null) {
            setNumberOfCavities(new IntegerFilter());
        }
        return numberOfCavities;
    }

    public void setNumberOfCavities(IntegerFilter numberOfCavities) {
        this.numberOfCavities = numberOfCavities;
    }

    public IntegerFilter getCycleTime() {
        return cycleTime;
    }

    public Optional<IntegerFilter> optionalCycleTime() {
        return Optional.ofNullable(cycleTime);
    }

    public IntegerFilter cycleTime() {
        if (cycleTime == null) {
            setCycleTime(new IntegerFilter());
        }
        return cycleTime;
    }

    public void setCycleTime(IntegerFilter cycleTime) {
        this.cycleTime = cycleTime;
    }

    public BigDecimalFilter getPurchaseComponentCostPart() {
        return purchaseComponentCostPart;
    }

    public Optional<BigDecimalFilter> optionalPurchaseComponentCostPart() {
        return Optional.ofNullable(purchaseComponentCostPart);
    }

    public BigDecimalFilter purchaseComponentCostPart() {
        if (purchaseComponentCostPart == null) {
            setPurchaseComponentCostPart(new BigDecimalFilter());
        }
        return purchaseComponentCostPart;
    }

    public void setPurchaseComponentCostPart(BigDecimalFilter purchaseComponentCostPart) {
        this.purchaseComponentCostPart = purchaseComponentCostPart;
    }

    public StringFilter getSecondaryOperationExternalProcess() {
        return secondaryOperationExternalProcess;
    }

    public Optional<StringFilter> optionalSecondaryOperationExternalProcess() {
        return Optional.ofNullable(secondaryOperationExternalProcess);
    }

    public StringFilter secondaryOperationExternalProcess() {
        if (secondaryOperationExternalProcess == null) {
            setSecondaryOperationExternalProcess(new StringFilter());
        }
        return secondaryOperationExternalProcess;
    }

    public void setSecondaryOperationExternalProcess(StringFilter secondaryOperationExternalProcess) {
        this.secondaryOperationExternalProcess = secondaryOperationExternalProcess;
    }

    public BigDecimalFilter getSecondaryOperationLaborRate() {
        return secondaryOperationLaborRate;
    }

    public Optional<BigDecimalFilter> optionalSecondaryOperationLaborRate() {
        return Optional.ofNullable(secondaryOperationLaborRate);
    }

    public BigDecimalFilter secondaryOperationLaborRate() {
        if (secondaryOperationLaborRate == null) {
            setSecondaryOperationLaborRate(new BigDecimalFilter());
        }
        return secondaryOperationLaborRate;
    }

    public void setSecondaryOperationLaborRate(BigDecimalFilter secondaryOperationLaborRate) {
        this.secondaryOperationLaborRate = secondaryOperationLaborRate;
    }

    public BigDecimalFilter getSecondaryOperationMachineRate() {
        return secondaryOperationMachineRate;
    }

    public Optional<BigDecimalFilter> optionalSecondaryOperationMachineRate() {
        return Optional.ofNullable(secondaryOperationMachineRate);
    }

    public BigDecimalFilter secondaryOperationMachineRate() {
        if (secondaryOperationMachineRate == null) {
            setSecondaryOperationMachineRate(new BigDecimalFilter());
        }
        return secondaryOperationMachineRate;
    }

    public void setSecondaryOperationMachineRate(BigDecimalFilter secondaryOperationMachineRate) {
        this.secondaryOperationMachineRate = secondaryOperationMachineRate;
    }

    public IntegerFilter getSecondaryOperationCycleTime() {
        return secondaryOperationCycleTime;
    }

    public Optional<IntegerFilter> optionalSecondaryOperationCycleTime() {
        return Optional.ofNullable(secondaryOperationCycleTime);
    }

    public IntegerFilter secondaryOperationCycleTime() {
        if (secondaryOperationCycleTime == null) {
            setSecondaryOperationCycleTime(new IntegerFilter());
        }
        return secondaryOperationCycleTime;
    }

    public void setSecondaryOperationCycleTime(IntegerFilter secondaryOperationCycleTime) {
        this.secondaryOperationCycleTime = secondaryOperationCycleTime;
    }

    public BigDecimalFilter getExternalOperationRate() {
        return externalOperationRate;
    }

    public Optional<BigDecimalFilter> optionalExternalOperationRate() {
        return Optional.ofNullable(externalOperationRate);
    }

    public BigDecimalFilter externalOperationRate() {
        if (externalOperationRate == null) {
            setExternalOperationRate(new BigDecimalFilter());
        }
        return externalOperationRate;
    }

    public void setExternalOperationRate(BigDecimalFilter externalOperationRate) {
        this.externalOperationRate = externalOperationRate;
    }

    public IntegerFilter getPreventativeMaintenanceFrequency() {
        return preventativeMaintenanceFrequency;
    }

    public Optional<IntegerFilter> optionalPreventativeMaintenanceFrequency() {
        return Optional.ofNullable(preventativeMaintenanceFrequency);
    }

    public IntegerFilter preventativeMaintenanceFrequency() {
        if (preventativeMaintenanceFrequency == null) {
            setPreventativeMaintenanceFrequency(new IntegerFilter());
        }
        return preventativeMaintenanceFrequency;
    }

    public void setPreventativeMaintenanceFrequency(IntegerFilter preventativeMaintenanceFrequency) {
        this.preventativeMaintenanceFrequency = preventativeMaintenanceFrequency;
    }

    public IntegerFilter getPreventativeMaintenanceCost() {
        return preventativeMaintenanceCost;
    }

    public Optional<IntegerFilter> optionalPreventativeMaintenanceCost() {
        return Optional.ofNullable(preventativeMaintenanceCost);
    }

    public IntegerFilter preventativeMaintenanceCost() {
        if (preventativeMaintenanceCost == null) {
            setPreventativeMaintenanceCost(new IntegerFilter());
        }
        return preventativeMaintenanceCost;
    }

    public void setPreventativeMaintenanceCost(IntegerFilter preventativeMaintenanceCost) {
        this.preventativeMaintenanceCost = preventativeMaintenanceCost;
    }

    public BigDecimalFilter getTargetProfit() {
        return targetProfit;
    }

    public Optional<BigDecimalFilter> optionalTargetProfit() {
        return Optional.ofNullable(targetProfit);
    }

    public BigDecimalFilter targetProfit() {
        if (targetProfit == null) {
            setTargetProfit(new BigDecimalFilter());
        }
        return targetProfit;
    }

    public void setTargetProfit(BigDecimalFilter targetProfit) {
        this.targetProfit = targetProfit;
    }

    public BigDecimalFilter getTargetMaterialMarkup() {
        return targetMaterialMarkup;
    }

    public Optional<BigDecimalFilter> optionalTargetMaterialMarkup() {
        return Optional.ofNullable(targetMaterialMarkup);
    }

    public BigDecimalFilter targetMaterialMarkup() {
        if (targetMaterialMarkup == null) {
            setTargetMaterialMarkup(new BigDecimalFilter());
        }
        return targetMaterialMarkup;
    }

    public void setTargetMaterialMarkup(BigDecimalFilter targetMaterialMarkup) {
        this.targetMaterialMarkup = targetMaterialMarkup;
    }

    public BigDecimalFilter getActualMaterialCost() {
        return actualMaterialCost;
    }

    public Optional<BigDecimalFilter> optionalActualMaterialCost() {
        return Optional.ofNullable(actualMaterialCost);
    }

    public BigDecimalFilter actualMaterialCost() {
        if (actualMaterialCost == null) {
            setActualMaterialCost(new BigDecimalFilter());
        }
        return actualMaterialCost;
    }

    public void setActualMaterialCost(BigDecimalFilter actualMaterialCost) {
        this.actualMaterialCost = actualMaterialCost;
    }

    public BigDecimalFilter getPartPerHours() {
        return partPerHours;
    }

    public Optional<BigDecimalFilter> optionalPartPerHours() {
        return Optional.ofNullable(partPerHours);
    }

    public BigDecimalFilter partPerHours() {
        if (partPerHours == null) {
            setPartPerHours(new BigDecimalFilter());
        }
        return partPerHours;
    }

    public void setPartPerHours(BigDecimalFilter partPerHours) {
        this.partPerHours = partPerHours;
    }

    public BigDecimalFilter getEstLotSize() {
        return estLotSize;
    }

    public Optional<BigDecimalFilter> optionalEstLotSize() {
        return Optional.ofNullable(estLotSize);
    }

    public BigDecimalFilter estLotSize() {
        if (estLotSize == null) {
            setEstLotSize(new BigDecimalFilter());
        }
        return estLotSize;
    }

    public void setEstLotSize(BigDecimalFilter estLotSize) {
        this.estLotSize = estLotSize;
    }

    public BigDecimalFilter getSetupHours() {
        return setupHours;
    }

    public Optional<BigDecimalFilter> optionalSetupHours() {
        return Optional.ofNullable(setupHours);
    }

    public BigDecimalFilter setupHours() {
        if (setupHours == null) {
            setSetupHours(new BigDecimalFilter());
        }
        return setupHours;
    }

    public void setSetupHours(BigDecimalFilter setupHours) {
        this.setupHours = setupHours;
    }

    public BigDecimalFilter getExternalOperationCostPer() {
        return externalOperationCostPer;
    }

    public Optional<BigDecimalFilter> optionalExternalOperationCostPer() {
        return Optional.ofNullable(externalOperationCostPer);
    }

    public BigDecimalFilter externalOperationCostPer() {
        if (externalOperationCostPer == null) {
            setExternalOperationCostPer(new BigDecimalFilter());
        }
        return externalOperationCostPer;
    }

    public void setExternalOperationCostPer(BigDecimalFilter externalOperationCostPer) {
        this.externalOperationCostPer = externalOperationCostPer;
    }

    public BigDecimalFilter getExternalMachineCostPer() {
        return externalMachineCostPer;
    }

    public Optional<BigDecimalFilter> optionalExternalMachineCostPer() {
        return Optional.ofNullable(externalMachineCostPer);
    }

    public BigDecimalFilter externalMachineCostPer() {
        if (externalMachineCostPer == null) {
            setExternalMachineCostPer(new BigDecimalFilter());
        }
        return externalMachineCostPer;
    }

    public void setExternalMachineCostPer(BigDecimalFilter externalMachineCostPer) {
        this.externalMachineCostPer = externalMachineCostPer;
    }

    public BigDecimalFilter getExtendedLaborCostPer() {
        return extendedLaborCostPer;
    }

    public Optional<BigDecimalFilter> optionalExtendedLaborCostPer() {
        return Optional.ofNullable(extendedLaborCostPer);
    }

    public BigDecimalFilter extendedLaborCostPer() {
        if (extendedLaborCostPer == null) {
            setExtendedLaborCostPer(new BigDecimalFilter());
        }
        return extendedLaborCostPer;
    }

    public void setExtendedLaborCostPer(BigDecimalFilter extendedLaborCostPer) {
        this.extendedLaborCostPer = extendedLaborCostPer;
    }

    public BigDecimalFilter getExtendedMaterialCostPer() {
        return extendedMaterialCostPer;
    }

    public Optional<BigDecimalFilter> optionalExtendedMaterialCostPer() {
        return Optional.ofNullable(extendedMaterialCostPer);
    }

    public BigDecimalFilter extendedMaterialCostPer() {
        if (extendedMaterialCostPer == null) {
            setExtendedMaterialCostPer(new BigDecimalFilter());
        }
        return extendedMaterialCostPer;
    }

    public void setExtendedMaterialCostPer(BigDecimalFilter extendedMaterialCostPer) {
        this.extendedMaterialCostPer = extendedMaterialCostPer;
    }

    public BigDecimalFilter getPackLogisticCostPer() {
        return packLogisticCostPer;
    }

    public Optional<BigDecimalFilter> optionalPackLogisticCostPer() {
        return Optional.ofNullable(packLogisticCostPer);
    }

    public BigDecimalFilter packLogisticCostPer() {
        if (packLogisticCostPer == null) {
            setPackLogisticCostPer(new BigDecimalFilter());
        }
        return packLogisticCostPer;
    }

    public void setPackLogisticCostPer(BigDecimalFilter packLogisticCostPer) {
        this.packLogisticCostPer = packLogisticCostPer;
    }

    public BigDecimalFilter getTotalProductionCost() {
        return totalProductionCost;
    }

    public Optional<BigDecimalFilter> optionalTotalProductionCost() {
        return Optional.ofNullable(totalProductionCost);
    }

    public BigDecimalFilter totalProductionCost() {
        if (totalProductionCost == null) {
            setTotalProductionCost(new BigDecimalFilter());
        }
        return totalProductionCost;
    }

    public void setTotalProductionCost(BigDecimalFilter totalProductionCost) {
        this.totalProductionCost = totalProductionCost;
    }

    public BigDecimalFilter getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public Optional<BigDecimalFilter> optionalTotalMaterialCost() {
        return Optional.ofNullable(totalMaterialCost);
    }

    public BigDecimalFilter totalMaterialCost() {
        if (totalMaterialCost == null) {
            setTotalMaterialCost(new BigDecimalFilter());
        }
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimalFilter totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimalFilter getTotalCostSgaProfit() {
        return totalCostSgaProfit;
    }

    public Optional<BigDecimalFilter> optionalTotalCostSgaProfit() {
        return Optional.ofNullable(totalCostSgaProfit);
    }

    public BigDecimalFilter totalCostSgaProfit() {
        if (totalCostSgaProfit == null) {
            setTotalCostSgaProfit(new BigDecimalFilter());
        }
        return totalCostSgaProfit;
    }

    public void setTotalCostSgaProfit(BigDecimalFilter totalCostSgaProfit) {
        this.totalCostSgaProfit = totalCostSgaProfit;
    }

    public BigDecimalFilter getSgaRate() {
        return sgaRate;
    }

    public Optional<BigDecimalFilter> optionalSgaRate() {
        return Optional.ofNullable(sgaRate);
    }

    public BigDecimalFilter sgaRate() {
        if (sgaRate == null) {
            setSgaRate(new BigDecimalFilter());
        }
        return sgaRate;
    }

    public void setSgaRate(BigDecimalFilter sgaRate) {
        this.sgaRate = sgaRate;
    }

    public BigDecimalFilter getProfit() {
        return profit;
    }

    public Optional<BigDecimalFilter> optionalProfit() {
        return Optional.ofNullable(profit);
    }

    public BigDecimalFilter profit() {
        if (profit == null) {
            setProfit(new BigDecimalFilter());
        }
        return profit;
    }

    public void setProfit(BigDecimalFilter profit) {
        this.profit = profit;
    }

    public BigDecimalFilter getPartPrice() {
        return partPrice;
    }

    public Optional<BigDecimalFilter> optionalPartPrice() {
        return Optional.ofNullable(partPrice);
    }

    public BigDecimalFilter partPrice() {
        if (partPrice == null) {
            setPartPrice(new BigDecimalFilter());
        }
        return partPrice;
    }

    public void setPartPrice(BigDecimalFilter partPrice) {
        this.partPrice = partPrice;
    }

    public BigDecimalFilter getTotalCost() {
        return totalCost;
    }

    public Optional<BigDecimalFilter> optionalTotalCost() {
        return Optional.ofNullable(totalCost);
    }

    public BigDecimalFilter totalCost() {
        if (totalCost == null) {
            setTotalCost(new BigDecimalFilter());
        }
        return totalCost;
    }

    public void setTotalCost(BigDecimalFilter totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimalFilter getTotalSales() {
        return totalSales;
    }

    public Optional<BigDecimalFilter> optionalTotalSales() {
        return Optional.ofNullable(totalSales);
    }

    public BigDecimalFilter totalSales() {
        if (totalSales == null) {
            setTotalSales(new BigDecimalFilter());
        }
        return totalSales;
    }

    public void setTotalSales(BigDecimalFilter totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimalFilter getTotalProfit() {
        return totalProfit;
    }

    public Optional<BigDecimalFilter> optionalTotalProfit() {
        return Optional.ofNullable(totalProfit);
    }

    public BigDecimalFilter totalProfit() {
        if (totalProfit == null) {
            setTotalProfit(new BigDecimalFilter());
        }
        return totalProfit;
    }

    public void setTotalProfit(BigDecimalFilter totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimalFilter getCostMaterial() {
        return costMaterial;
    }

    public Optional<BigDecimalFilter> optionalCostMaterial() {
        return Optional.ofNullable(costMaterial);
    }

    public BigDecimalFilter costMaterial() {
        if (costMaterial == null) {
            setCostMaterial(new BigDecimalFilter());
        }
        return costMaterial;
    }

    public void setCostMaterial(BigDecimalFilter costMaterial) {
        this.costMaterial = costMaterial;
    }

    public BigDecimalFilter getTotalContributionMargin() {
        return totalContributionMargin;
    }

    public Optional<BigDecimalFilter> optionalTotalContributionMargin() {
        return Optional.ofNullable(totalContributionMargin);
    }

    public BigDecimalFilter totalContributionMargin() {
        if (totalContributionMargin == null) {
            setTotalContributionMargin(new BigDecimalFilter());
        }
        return totalContributionMargin;
    }

    public void setTotalContributionMargin(BigDecimalFilter totalContributionMargin) {
        this.totalContributionMargin = totalContributionMargin;
    }

    public BigDecimalFilter getContributionMargin() {
        return contributionMargin;
    }

    public Optional<BigDecimalFilter> optionalContributionMargin() {
        return Optional.ofNullable(contributionMargin);
    }

    public BigDecimalFilter contributionMargin() {
        if (contributionMargin == null) {
            setContributionMargin(new BigDecimalFilter());
        }
        return contributionMargin;
    }

    public void setContributionMargin(BigDecimalFilter contributionMargin) {
        this.contributionMargin = contributionMargin;
    }

    public BigDecimalFilter getMaterialContributionMargin() {
        return materialContributionMargin;
    }

    public Optional<BigDecimalFilter> optionalMaterialContributionMargin() {
        return Optional.ofNullable(materialContributionMargin);
    }

    public BigDecimalFilter materialContributionMargin() {
        if (materialContributionMargin == null) {
            setMaterialContributionMargin(new BigDecimalFilter());
        }
        return materialContributionMargin;
    }

    public void setMaterialContributionMargin(BigDecimalFilter materialContributionMargin) {
        this.materialContributionMargin = materialContributionMargin;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public Optional<IntegerFilter> optionalVersion() {
        return Optional.ofNullable(version);
    }

    public IntegerFilter version() {
        if (version == null) {
            setVersion(new IntegerFilter());
        }
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public StringFilter getComments() {
        return comments;
    }

    public Optional<StringFilter> optionalComments() {
        return Optional.ofNullable(comments);
    }

    public StringFilter comments() {
        if (comments == null) {
            setComments(new StringFilter());
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<StringFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new StringFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public Optional<StringFilter> optionalUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }

    public StringFilter updatedBy() {
        if (updatedBy == null) {
            setUpdatedBy(new StringFilter());
        }
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedDate() {
        return updatedDate;
    }

    public Optional<InstantFilter> optionalUpdatedDate() {
        return Optional.ofNullable(updatedDate);
    }

    public InstantFilter updatedDate() {
        if (updatedDate == null) {
            setUpdatedDate(new InstantFilter());
        }
        return updatedDate;
    }

    public void setUpdatedDate(InstantFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LongFilter getNtQuoteId() {
        return ntQuoteId;
    }

    public Optional<LongFilter> optionalNtQuoteId() {
        return Optional.ofNullable(ntQuoteId);
    }

    public LongFilter ntQuoteId() {
        if (ntQuoteId == null) {
            setNtQuoteId(new LongFilter());
        }
        return ntQuoteId;
    }

    public void setNtQuoteId(LongFilter ntQuoteId) {
        this.ntQuoteId = ntQuoteId;
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
        final NtQuoteCustomerInputOutputVersionCriteria that = (NtQuoteCustomerInputOutputVersionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(materialDescription, that.materialDescription) &&
            Objects.equals(partNumber, that.partNumber) &&
            Objects.equals(materialId, that.materialId) &&
            Objects.equals(supplier, that.supplier) &&
            Objects.equals(estAnnualVolume, that.estAnnualVolume) &&
            Objects.equals(estProductionRunYrs, that.estProductionRunYrs) &&
            Objects.equals(materialCostLb, that.materialCostLb) &&
            Objects.equals(partWeightLb, that.partWeightLb) &&
            Objects.equals(runnerWeightLb, that.runnerWeightLb) &&
            Objects.equals(machineSize, that.machineSize) &&
            Objects.equals(machineRate, that.machineRate) &&
            Objects.equals(scrapRate, that.scrapRate) &&
            Objects.equals(machineEfficiency, that.machineEfficiency) &&
            Objects.equals(fte, that.fte) &&
            Objects.equals(laborRate, that.laborRate) &&
            Objects.equals(numberOfCavities, that.numberOfCavities) &&
            Objects.equals(cycleTime, that.cycleTime) &&
            Objects.equals(purchaseComponentCostPart, that.purchaseComponentCostPart) &&
            Objects.equals(secondaryOperationExternalProcess, that.secondaryOperationExternalProcess) &&
            Objects.equals(secondaryOperationLaborRate, that.secondaryOperationLaborRate) &&
            Objects.equals(secondaryOperationMachineRate, that.secondaryOperationMachineRate) &&
            Objects.equals(secondaryOperationCycleTime, that.secondaryOperationCycleTime) &&
            Objects.equals(externalOperationRate, that.externalOperationRate) &&
            Objects.equals(preventativeMaintenanceFrequency, that.preventativeMaintenanceFrequency) &&
            Objects.equals(preventativeMaintenanceCost, that.preventativeMaintenanceCost) &&
            Objects.equals(targetProfit, that.targetProfit) &&
            Objects.equals(targetMaterialMarkup, that.targetMaterialMarkup) &&
            Objects.equals(actualMaterialCost, that.actualMaterialCost) &&
            Objects.equals(partPerHours, that.partPerHours) &&
            Objects.equals(estLotSize, that.estLotSize) &&
            Objects.equals(setupHours, that.setupHours) &&
            Objects.equals(externalOperationCostPer, that.externalOperationCostPer) &&
            Objects.equals(externalMachineCostPer, that.externalMachineCostPer) &&
            Objects.equals(extendedLaborCostPer, that.extendedLaborCostPer) &&
            Objects.equals(extendedMaterialCostPer, that.extendedMaterialCostPer) &&
            Objects.equals(packLogisticCostPer, that.packLogisticCostPer) &&
            Objects.equals(totalProductionCost, that.totalProductionCost) &&
            Objects.equals(totalMaterialCost, that.totalMaterialCost) &&
            Objects.equals(totalCostSgaProfit, that.totalCostSgaProfit) &&
            Objects.equals(sgaRate, that.sgaRate) &&
            Objects.equals(profit, that.profit) &&
            Objects.equals(partPrice, that.partPrice) &&
            Objects.equals(totalCost, that.totalCost) &&
            Objects.equals(totalSales, that.totalSales) &&
            Objects.equals(totalProfit, that.totalProfit) &&
            Objects.equals(costMaterial, that.costMaterial) &&
            Objects.equals(totalContributionMargin, that.totalContributionMargin) &&
            Objects.equals(contributionMargin, that.contributionMargin) &&
            Objects.equals(materialContributionMargin, that.materialContributionMargin) &&
            Objects.equals(version, that.version) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(ntQuoteId, that.ntQuoteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            srNo,
            uid,
            materialDescription,
            partNumber,
            materialId,
            supplier,
            estAnnualVolume,
            estProductionRunYrs,
            materialCostLb,
            partWeightLb,
            runnerWeightLb,
            machineSize,
            machineRate,
            scrapRate,
            machineEfficiency,
            fte,
            laborRate,
            numberOfCavities,
            cycleTime,
            purchaseComponentCostPart,
            secondaryOperationExternalProcess,
            secondaryOperationLaborRate,
            secondaryOperationMachineRate,
            secondaryOperationCycleTime,
            externalOperationRate,
            preventativeMaintenanceFrequency,
            preventativeMaintenanceCost,
            targetProfit,
            targetMaterialMarkup,
            actualMaterialCost,
            partPerHours,
            estLotSize,
            setupHours,
            externalOperationCostPer,
            externalMachineCostPer,
            extendedLaborCostPer,
            extendedMaterialCostPer,
            packLogisticCostPer,
            totalProductionCost,
            totalMaterialCost,
            totalCostSgaProfit,
            sgaRate,
            profit,
            partPrice,
            totalCost,
            totalSales,
            totalProfit,
            costMaterial,
            totalContributionMargin,
            contributionMargin,
            materialContributionMargin,
            version,
            comments,
            createdBy,
            createdDate,
            updatedBy,
            updatedDate,
            ntQuoteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCustomerInputOutputVersionCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalMaterialDescription().map(f -> "materialDescription=" + f + ", ").orElse("") +
            optionalPartNumber().map(f -> "partNumber=" + f + ", ").orElse("") +
            optionalMaterialId().map(f -> "materialId=" + f + ", ").orElse("") +
            optionalSupplier().map(f -> "supplier=" + f + ", ").orElse("") +
            optionalEstAnnualVolume().map(f -> "estAnnualVolume=" + f + ", ").orElse("") +
            optionalEstProductionRunYrs().map(f -> "estProductionRunYrs=" + f + ", ").orElse("") +
            optionalMaterialCostLb().map(f -> "materialCostLb=" + f + ", ").orElse("") +
            optionalPartWeightLb().map(f -> "partWeightLb=" + f + ", ").orElse("") +
            optionalRunnerWeightLb().map(f -> "runnerWeightLb=" + f + ", ").orElse("") +
            optionalMachineSize().map(f -> "machineSize=" + f + ", ").orElse("") +
            optionalMachineRate().map(f -> "machineRate=" + f + ", ").orElse("") +
            optionalScrapRate().map(f -> "scrapRate=" + f + ", ").orElse("") +
            optionalMachineEfficiency().map(f -> "machineEfficiency=" + f + ", ").orElse("") +
            optionalFte().map(f -> "fte=" + f + ", ").orElse("") +
            optionalLaborRate().map(f -> "laborRate=" + f + ", ").orElse("") +
            optionalNumberOfCavities().map(f -> "numberOfCavities=" + f + ", ").orElse("") +
            optionalCycleTime().map(f -> "cycleTime=" + f + ", ").orElse("") +
            optionalPurchaseComponentCostPart().map(f -> "purchaseComponentCostPart=" + f + ", ").orElse("") +
            optionalSecondaryOperationExternalProcess().map(f -> "secondaryOperationExternalProcess=" + f + ", ").orElse("") +
            optionalSecondaryOperationLaborRate().map(f -> "secondaryOperationLaborRate=" + f + ", ").orElse("") +
            optionalSecondaryOperationMachineRate().map(f -> "secondaryOperationMachineRate=" + f + ", ").orElse("") +
            optionalSecondaryOperationCycleTime().map(f -> "secondaryOperationCycleTime=" + f + ", ").orElse("") +
            optionalExternalOperationRate().map(f -> "externalOperationRate=" + f + ", ").orElse("") +
            optionalPreventativeMaintenanceFrequency().map(f -> "preventativeMaintenanceFrequency=" + f + ", ").orElse("") +
            optionalPreventativeMaintenanceCost().map(f -> "preventativeMaintenanceCost=" + f + ", ").orElse("") +
            optionalTargetProfit().map(f -> "targetProfit=" + f + ", ").orElse("") +
            optionalTargetMaterialMarkup().map(f -> "targetMaterialMarkup=" + f + ", ").orElse("") +
            optionalActualMaterialCost().map(f -> "actualMaterialCost=" + f + ", ").orElse("") +
            optionalPartPerHours().map(f -> "partPerHours=" + f + ", ").orElse("") +
            optionalEstLotSize().map(f -> "estLotSize=" + f + ", ").orElse("") +
            optionalSetupHours().map(f -> "setupHours=" + f + ", ").orElse("") +
            optionalExternalOperationCostPer().map(f -> "externalOperationCostPer=" + f + ", ").orElse("") +
            optionalExternalMachineCostPer().map(f -> "externalMachineCostPer=" + f + ", ").orElse("") +
            optionalExtendedLaborCostPer().map(f -> "extendedLaborCostPer=" + f + ", ").orElse("") +
            optionalExtendedMaterialCostPer().map(f -> "extendedMaterialCostPer=" + f + ", ").orElse("") +
            optionalPackLogisticCostPer().map(f -> "packLogisticCostPer=" + f + ", ").orElse("") +
            optionalTotalProductionCost().map(f -> "totalProductionCost=" + f + ", ").orElse("") +
            optionalTotalMaterialCost().map(f -> "totalMaterialCost=" + f + ", ").orElse("") +
            optionalTotalCostSgaProfit().map(f -> "totalCostSgaProfit=" + f + ", ").orElse("") +
            optionalSgaRate().map(f -> "sgaRate=" + f + ", ").orElse("") +
            optionalProfit().map(f -> "profit=" + f + ", ").orElse("") +
            optionalPartPrice().map(f -> "partPrice=" + f + ", ").orElse("") +
            optionalTotalCost().map(f -> "totalCost=" + f + ", ").orElse("") +
            optionalTotalSales().map(f -> "totalSales=" + f + ", ").orElse("") +
            optionalTotalProfit().map(f -> "totalProfit=" + f + ", ").orElse("") +
            optionalCostMaterial().map(f -> "costMaterial=" + f + ", ").orElse("") +
            optionalTotalContributionMargin().map(f -> "totalContributionMargin=" + f + ", ").orElse("") +
            optionalContributionMargin().map(f -> "contributionMargin=" + f + ", ").orElse("") +
            optionalMaterialContributionMargin().map(f -> "materialContributionMargin=" + f + ", ").orElse("") +
            optionalVersion().map(f -> "version=" + f + ", ").orElse("") +
            optionalComments().map(f -> "comments=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
