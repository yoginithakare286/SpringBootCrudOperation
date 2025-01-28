package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuotePartInformationMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-part-information-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuotePartInformationMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter materialDescription;

    private StringFilter partNumber;

    private StringFilter cadFile;

    private IntegerFilter eau;

    private IntegerFilter partWeight;

    private StringFilter materialType;

    private BigDecimalFilter materialCost;

    private BigDecimalFilter extendedMaterialCostPer;

    private BigDecimalFilter externalMachineCostPer;

    private BigDecimalFilter purchaseComponentCost;

    private BigDecimalFilter secondaryExternalOperationCost;

    private BigDecimalFilter overhead;

    private BigDecimalFilter packLogisticCostPer;

    private StringFilter machineSizeTons;

    private IntegerFilter numberOfCavities;

    private IntegerFilter cycleTime;

    private BigDecimalFilter perUnit;

    private BigDecimalFilter totalPricePerChina;

    private BigDecimalFilter totalPriceBudget;

    private BigDecimalFilter grainBudget;

    private BigDecimalFilter dogatingFixtureBudget;

    private BigDecimalFilter gaugeBudget;

    private BigDecimalFilter eoat;

    private BigDecimalFilter chinaTariffBudget;

    private BigDecimalFilter totalToolingBudget;

    private StringFilter leadTime;

    private StringFilter toolingNotes;

    private StringFilter partDescription;

    private StringFilter jobId;

    private StringFilter moldId;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private Boolean distinct;

    public NtQuotePartInformationMasterCriteria() {}

    public NtQuotePartInformationMasterCriteria(NtQuotePartInformationMasterCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.materialDescription = other.optionalMaterialDescription().map(StringFilter::copy).orElse(null);
        this.partNumber = other.optionalPartNumber().map(StringFilter::copy).orElse(null);
        this.cadFile = other.optionalCadFile().map(StringFilter::copy).orElse(null);
        this.eau = other.optionalEau().map(IntegerFilter::copy).orElse(null);
        this.partWeight = other.optionalPartWeight().map(IntegerFilter::copy).orElse(null);
        this.materialType = other.optionalMaterialType().map(StringFilter::copy).orElse(null);
        this.materialCost = other.optionalMaterialCost().map(BigDecimalFilter::copy).orElse(null);
        this.extendedMaterialCostPer = other.optionalExtendedMaterialCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.externalMachineCostPer = other.optionalExternalMachineCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.purchaseComponentCost = other.optionalPurchaseComponentCost().map(BigDecimalFilter::copy).orElse(null);
        this.secondaryExternalOperationCost = other.optionalSecondaryExternalOperationCost().map(BigDecimalFilter::copy).orElse(null);
        this.overhead = other.optionalOverhead().map(BigDecimalFilter::copy).orElse(null);
        this.packLogisticCostPer = other.optionalPackLogisticCostPer().map(BigDecimalFilter::copy).orElse(null);
        this.machineSizeTons = other.optionalMachineSizeTons().map(StringFilter::copy).orElse(null);
        this.numberOfCavities = other.optionalNumberOfCavities().map(IntegerFilter::copy).orElse(null);
        this.cycleTime = other.optionalCycleTime().map(IntegerFilter::copy).orElse(null);
        this.perUnit = other.optionalPerUnit().map(BigDecimalFilter::copy).orElse(null);
        this.totalPricePerChina = other.optionalTotalPricePerChina().map(BigDecimalFilter::copy).orElse(null);
        this.totalPriceBudget = other.optionalTotalPriceBudget().map(BigDecimalFilter::copy).orElse(null);
        this.grainBudget = other.optionalGrainBudget().map(BigDecimalFilter::copy).orElse(null);
        this.dogatingFixtureBudget = other.optionalDogatingFixtureBudget().map(BigDecimalFilter::copy).orElse(null);
        this.gaugeBudget = other.optionalGaugeBudget().map(BigDecimalFilter::copy).orElse(null);
        this.eoat = other.optionalEoat().map(BigDecimalFilter::copy).orElse(null);
        this.chinaTariffBudget = other.optionalChinaTariffBudget().map(BigDecimalFilter::copy).orElse(null);
        this.totalToolingBudget = other.optionalTotalToolingBudget().map(BigDecimalFilter::copy).orElse(null);
        this.leadTime = other.optionalLeadTime().map(StringFilter::copy).orElse(null);
        this.toolingNotes = other.optionalToolingNotes().map(StringFilter::copy).orElse(null);
        this.partDescription = other.optionalPartDescription().map(StringFilter::copy).orElse(null);
        this.jobId = other.optionalJobId().map(StringFilter::copy).orElse(null);
        this.moldId = other.optionalMoldId().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuotePartInformationMasterCriteria copy() {
        return new NtQuotePartInformationMasterCriteria(this);
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

    public StringFilter getCadFile() {
        return cadFile;
    }

    public Optional<StringFilter> optionalCadFile() {
        return Optional.ofNullable(cadFile);
    }

    public StringFilter cadFile() {
        if (cadFile == null) {
            setCadFile(new StringFilter());
        }
        return cadFile;
    }

    public void setCadFile(StringFilter cadFile) {
        this.cadFile = cadFile;
    }

    public IntegerFilter getEau() {
        return eau;
    }

    public Optional<IntegerFilter> optionalEau() {
        return Optional.ofNullable(eau);
    }

    public IntegerFilter eau() {
        if (eau == null) {
            setEau(new IntegerFilter());
        }
        return eau;
    }

    public void setEau(IntegerFilter eau) {
        this.eau = eau;
    }

    public IntegerFilter getPartWeight() {
        return partWeight;
    }

    public Optional<IntegerFilter> optionalPartWeight() {
        return Optional.ofNullable(partWeight);
    }

    public IntegerFilter partWeight() {
        if (partWeight == null) {
            setPartWeight(new IntegerFilter());
        }
        return partWeight;
    }

    public void setPartWeight(IntegerFilter partWeight) {
        this.partWeight = partWeight;
    }

    public StringFilter getMaterialType() {
        return materialType;
    }

    public Optional<StringFilter> optionalMaterialType() {
        return Optional.ofNullable(materialType);
    }

    public StringFilter materialType() {
        if (materialType == null) {
            setMaterialType(new StringFilter());
        }
        return materialType;
    }

    public void setMaterialType(StringFilter materialType) {
        this.materialType = materialType;
    }

    public BigDecimalFilter getMaterialCost() {
        return materialCost;
    }

    public Optional<BigDecimalFilter> optionalMaterialCost() {
        return Optional.ofNullable(materialCost);
    }

    public BigDecimalFilter materialCost() {
        if (materialCost == null) {
            setMaterialCost(new BigDecimalFilter());
        }
        return materialCost;
    }

    public void setMaterialCost(BigDecimalFilter materialCost) {
        this.materialCost = materialCost;
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

    public BigDecimalFilter getPurchaseComponentCost() {
        return purchaseComponentCost;
    }

    public Optional<BigDecimalFilter> optionalPurchaseComponentCost() {
        return Optional.ofNullable(purchaseComponentCost);
    }

    public BigDecimalFilter purchaseComponentCost() {
        if (purchaseComponentCost == null) {
            setPurchaseComponentCost(new BigDecimalFilter());
        }
        return purchaseComponentCost;
    }

    public void setPurchaseComponentCost(BigDecimalFilter purchaseComponentCost) {
        this.purchaseComponentCost = purchaseComponentCost;
    }

    public BigDecimalFilter getSecondaryExternalOperationCost() {
        return secondaryExternalOperationCost;
    }

    public Optional<BigDecimalFilter> optionalSecondaryExternalOperationCost() {
        return Optional.ofNullable(secondaryExternalOperationCost);
    }

    public BigDecimalFilter secondaryExternalOperationCost() {
        if (secondaryExternalOperationCost == null) {
            setSecondaryExternalOperationCost(new BigDecimalFilter());
        }
        return secondaryExternalOperationCost;
    }

    public void setSecondaryExternalOperationCost(BigDecimalFilter secondaryExternalOperationCost) {
        this.secondaryExternalOperationCost = secondaryExternalOperationCost;
    }

    public BigDecimalFilter getOverhead() {
        return overhead;
    }

    public Optional<BigDecimalFilter> optionalOverhead() {
        return Optional.ofNullable(overhead);
    }

    public BigDecimalFilter overhead() {
        if (overhead == null) {
            setOverhead(new BigDecimalFilter());
        }
        return overhead;
    }

    public void setOverhead(BigDecimalFilter overhead) {
        this.overhead = overhead;
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

    public StringFilter getMachineSizeTons() {
        return machineSizeTons;
    }

    public Optional<StringFilter> optionalMachineSizeTons() {
        return Optional.ofNullable(machineSizeTons);
    }

    public StringFilter machineSizeTons() {
        if (machineSizeTons == null) {
            setMachineSizeTons(new StringFilter());
        }
        return machineSizeTons;
    }

    public void setMachineSizeTons(StringFilter machineSizeTons) {
        this.machineSizeTons = machineSizeTons;
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

    public BigDecimalFilter getPerUnit() {
        return perUnit;
    }

    public Optional<BigDecimalFilter> optionalPerUnit() {
        return Optional.ofNullable(perUnit);
    }

    public BigDecimalFilter perUnit() {
        if (perUnit == null) {
            setPerUnit(new BigDecimalFilter());
        }
        return perUnit;
    }

    public void setPerUnit(BigDecimalFilter perUnit) {
        this.perUnit = perUnit;
    }

    public BigDecimalFilter getTotalPricePerChina() {
        return totalPricePerChina;
    }

    public Optional<BigDecimalFilter> optionalTotalPricePerChina() {
        return Optional.ofNullable(totalPricePerChina);
    }

    public BigDecimalFilter totalPricePerChina() {
        if (totalPricePerChina == null) {
            setTotalPricePerChina(new BigDecimalFilter());
        }
        return totalPricePerChina;
    }

    public void setTotalPricePerChina(BigDecimalFilter totalPricePerChina) {
        this.totalPricePerChina = totalPricePerChina;
    }

    public BigDecimalFilter getTotalPriceBudget() {
        return totalPriceBudget;
    }

    public Optional<BigDecimalFilter> optionalTotalPriceBudget() {
        return Optional.ofNullable(totalPriceBudget);
    }

    public BigDecimalFilter totalPriceBudget() {
        if (totalPriceBudget == null) {
            setTotalPriceBudget(new BigDecimalFilter());
        }
        return totalPriceBudget;
    }

    public void setTotalPriceBudget(BigDecimalFilter totalPriceBudget) {
        this.totalPriceBudget = totalPriceBudget;
    }

    public BigDecimalFilter getGrainBudget() {
        return grainBudget;
    }

    public Optional<BigDecimalFilter> optionalGrainBudget() {
        return Optional.ofNullable(grainBudget);
    }

    public BigDecimalFilter grainBudget() {
        if (grainBudget == null) {
            setGrainBudget(new BigDecimalFilter());
        }
        return grainBudget;
    }

    public void setGrainBudget(BigDecimalFilter grainBudget) {
        this.grainBudget = grainBudget;
    }

    public BigDecimalFilter getDogatingFixtureBudget() {
        return dogatingFixtureBudget;
    }

    public Optional<BigDecimalFilter> optionalDogatingFixtureBudget() {
        return Optional.ofNullable(dogatingFixtureBudget);
    }

    public BigDecimalFilter dogatingFixtureBudget() {
        if (dogatingFixtureBudget == null) {
            setDogatingFixtureBudget(new BigDecimalFilter());
        }
        return dogatingFixtureBudget;
    }

    public void setDogatingFixtureBudget(BigDecimalFilter dogatingFixtureBudget) {
        this.dogatingFixtureBudget = dogatingFixtureBudget;
    }

    public BigDecimalFilter getGaugeBudget() {
        return gaugeBudget;
    }

    public Optional<BigDecimalFilter> optionalGaugeBudget() {
        return Optional.ofNullable(gaugeBudget);
    }

    public BigDecimalFilter gaugeBudget() {
        if (gaugeBudget == null) {
            setGaugeBudget(new BigDecimalFilter());
        }
        return gaugeBudget;
    }

    public void setGaugeBudget(BigDecimalFilter gaugeBudget) {
        this.gaugeBudget = gaugeBudget;
    }

    public BigDecimalFilter getEoat() {
        return eoat;
    }

    public Optional<BigDecimalFilter> optionalEoat() {
        return Optional.ofNullable(eoat);
    }

    public BigDecimalFilter eoat() {
        if (eoat == null) {
            setEoat(new BigDecimalFilter());
        }
        return eoat;
    }

    public void setEoat(BigDecimalFilter eoat) {
        this.eoat = eoat;
    }

    public BigDecimalFilter getChinaTariffBudget() {
        return chinaTariffBudget;
    }

    public Optional<BigDecimalFilter> optionalChinaTariffBudget() {
        return Optional.ofNullable(chinaTariffBudget);
    }

    public BigDecimalFilter chinaTariffBudget() {
        if (chinaTariffBudget == null) {
            setChinaTariffBudget(new BigDecimalFilter());
        }
        return chinaTariffBudget;
    }

    public void setChinaTariffBudget(BigDecimalFilter chinaTariffBudget) {
        this.chinaTariffBudget = chinaTariffBudget;
    }

    public BigDecimalFilter getTotalToolingBudget() {
        return totalToolingBudget;
    }

    public Optional<BigDecimalFilter> optionalTotalToolingBudget() {
        return Optional.ofNullable(totalToolingBudget);
    }

    public BigDecimalFilter totalToolingBudget() {
        if (totalToolingBudget == null) {
            setTotalToolingBudget(new BigDecimalFilter());
        }
        return totalToolingBudget;
    }

    public void setTotalToolingBudget(BigDecimalFilter totalToolingBudget) {
        this.totalToolingBudget = totalToolingBudget;
    }

    public StringFilter getLeadTime() {
        return leadTime;
    }

    public Optional<StringFilter> optionalLeadTime() {
        return Optional.ofNullable(leadTime);
    }

    public StringFilter leadTime() {
        if (leadTime == null) {
            setLeadTime(new StringFilter());
        }
        return leadTime;
    }

    public void setLeadTime(StringFilter leadTime) {
        this.leadTime = leadTime;
    }

    public StringFilter getToolingNotes() {
        return toolingNotes;
    }

    public Optional<StringFilter> optionalToolingNotes() {
        return Optional.ofNullable(toolingNotes);
    }

    public StringFilter toolingNotes() {
        if (toolingNotes == null) {
            setToolingNotes(new StringFilter());
        }
        return toolingNotes;
    }

    public void setToolingNotes(StringFilter toolingNotes) {
        this.toolingNotes = toolingNotes;
    }

    public StringFilter getPartDescription() {
        return partDescription;
    }

    public Optional<StringFilter> optionalPartDescription() {
        return Optional.ofNullable(partDescription);
    }

    public StringFilter partDescription() {
        if (partDescription == null) {
            setPartDescription(new StringFilter());
        }
        return partDescription;
    }

    public void setPartDescription(StringFilter partDescription) {
        this.partDescription = partDescription;
    }

    public StringFilter getJobId() {
        return jobId;
    }

    public Optional<StringFilter> optionalJobId() {
        return Optional.ofNullable(jobId);
    }

    public StringFilter jobId() {
        if (jobId == null) {
            setJobId(new StringFilter());
        }
        return jobId;
    }

    public void setJobId(StringFilter jobId) {
        this.jobId = jobId;
    }

    public StringFilter getMoldId() {
        return moldId;
    }

    public Optional<StringFilter> optionalMoldId() {
        return Optional.ofNullable(moldId);
    }

    public StringFilter moldId() {
        if (moldId == null) {
            setMoldId(new StringFilter());
        }
        return moldId;
    }

    public void setMoldId(StringFilter moldId) {
        this.moldId = moldId;
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
        final NtQuotePartInformationMasterCriteria that = (NtQuotePartInformationMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(materialDescription, that.materialDescription) &&
            Objects.equals(partNumber, that.partNumber) &&
            Objects.equals(cadFile, that.cadFile) &&
            Objects.equals(eau, that.eau) &&
            Objects.equals(partWeight, that.partWeight) &&
            Objects.equals(materialType, that.materialType) &&
            Objects.equals(materialCost, that.materialCost) &&
            Objects.equals(extendedMaterialCostPer, that.extendedMaterialCostPer) &&
            Objects.equals(externalMachineCostPer, that.externalMachineCostPer) &&
            Objects.equals(purchaseComponentCost, that.purchaseComponentCost) &&
            Objects.equals(secondaryExternalOperationCost, that.secondaryExternalOperationCost) &&
            Objects.equals(overhead, that.overhead) &&
            Objects.equals(packLogisticCostPer, that.packLogisticCostPer) &&
            Objects.equals(machineSizeTons, that.machineSizeTons) &&
            Objects.equals(numberOfCavities, that.numberOfCavities) &&
            Objects.equals(cycleTime, that.cycleTime) &&
            Objects.equals(perUnit, that.perUnit) &&
            Objects.equals(totalPricePerChina, that.totalPricePerChina) &&
            Objects.equals(totalPriceBudget, that.totalPriceBudget) &&
            Objects.equals(grainBudget, that.grainBudget) &&
            Objects.equals(dogatingFixtureBudget, that.dogatingFixtureBudget) &&
            Objects.equals(gaugeBudget, that.gaugeBudget) &&
            Objects.equals(eoat, that.eoat) &&
            Objects.equals(chinaTariffBudget, that.chinaTariffBudget) &&
            Objects.equals(totalToolingBudget, that.totalToolingBudget) &&
            Objects.equals(leadTime, that.leadTime) &&
            Objects.equals(toolingNotes, that.toolingNotes) &&
            Objects.equals(partDescription, that.partDescription) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(moldId, that.moldId) &&
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
            cadFile,
            eau,
            partWeight,
            materialType,
            materialCost,
            extendedMaterialCostPer,
            externalMachineCostPer,
            purchaseComponentCost,
            secondaryExternalOperationCost,
            overhead,
            packLogisticCostPer,
            machineSizeTons,
            numberOfCavities,
            cycleTime,
            perUnit,
            totalPricePerChina,
            totalPriceBudget,
            grainBudget,
            dogatingFixtureBudget,
            gaugeBudget,
            eoat,
            chinaTariffBudget,
            totalToolingBudget,
            leadTime,
            toolingNotes,
            partDescription,
            jobId,
            moldId,
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
        return "NtQuotePartInformationMasterCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalMaterialDescription().map(f -> "materialDescription=" + f + ", ").orElse("") +
            optionalPartNumber().map(f -> "partNumber=" + f + ", ").orElse("") +
            optionalCadFile().map(f -> "cadFile=" + f + ", ").orElse("") +
            optionalEau().map(f -> "eau=" + f + ", ").orElse("") +
            optionalPartWeight().map(f -> "partWeight=" + f + ", ").orElse("") +
            optionalMaterialType().map(f -> "materialType=" + f + ", ").orElse("") +
            optionalMaterialCost().map(f -> "materialCost=" + f + ", ").orElse("") +
            optionalExtendedMaterialCostPer().map(f -> "extendedMaterialCostPer=" + f + ", ").orElse("") +
            optionalExternalMachineCostPer().map(f -> "externalMachineCostPer=" + f + ", ").orElse("") +
            optionalPurchaseComponentCost().map(f -> "purchaseComponentCost=" + f + ", ").orElse("") +
            optionalSecondaryExternalOperationCost().map(f -> "secondaryExternalOperationCost=" + f + ", ").orElse("") +
            optionalOverhead().map(f -> "overhead=" + f + ", ").orElse("") +
            optionalPackLogisticCostPer().map(f -> "packLogisticCostPer=" + f + ", ").orElse("") +
            optionalMachineSizeTons().map(f -> "machineSizeTons=" + f + ", ").orElse("") +
            optionalNumberOfCavities().map(f -> "numberOfCavities=" + f + ", ").orElse("") +
            optionalCycleTime().map(f -> "cycleTime=" + f + ", ").orElse("") +
            optionalPerUnit().map(f -> "perUnit=" + f + ", ").orElse("") +
            optionalTotalPricePerChina().map(f -> "totalPricePerChina=" + f + ", ").orElse("") +
            optionalTotalPriceBudget().map(f -> "totalPriceBudget=" + f + ", ").orElse("") +
            optionalGrainBudget().map(f -> "grainBudget=" + f + ", ").orElse("") +
            optionalDogatingFixtureBudget().map(f -> "dogatingFixtureBudget=" + f + ", ").orElse("") +
            optionalGaugeBudget().map(f -> "gaugeBudget=" + f + ", ").orElse("") +
            optionalEoat().map(f -> "eoat=" + f + ", ").orElse("") +
            optionalChinaTariffBudget().map(f -> "chinaTariffBudget=" + f + ", ").orElse("") +
            optionalTotalToolingBudget().map(f -> "totalToolingBudget=" + f + ", ").orElse("") +
            optionalLeadTime().map(f -> "leadTime=" + f + ", ").orElse("") +
            optionalToolingNotes().map(f -> "toolingNotes=" + f + ", ").orElse("") +
            optionalPartDescription().map(f -> "partDescription=" + f + ", ").orElse("") +
            optionalJobId().map(f -> "jobId=" + f + ", ").orElse("") +
            optionalMoldId().map(f -> "moldId=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
