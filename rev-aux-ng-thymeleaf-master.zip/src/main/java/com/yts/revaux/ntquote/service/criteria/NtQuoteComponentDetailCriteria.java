package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuoteComponentDetail} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteComponentDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-component-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteComponentDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter materialDescription;

    private StringFilter partNumber;

    private IntegerFilter eau;

    private StringFilter manufacturingLocation;

    private StringFilter fobLocation;

    private StringFilter packingRequirements;

    private StringFilter machineSize;

    private IntegerFilter cycleTime;

    private IntegerFilter partWeight;

    private IntegerFilter runnerWeight;

    private IntegerFilter cavities;

    private StringFilter comments;

    private StringFilter riskLevel;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private LongFilter materialPriceId;

    private Boolean distinct;

    public NtQuoteComponentDetailCriteria() {}

    public NtQuoteComponentDetailCriteria(NtQuoteComponentDetailCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.materialDescription = other.optionalMaterialDescription().map(StringFilter::copy).orElse(null);
        this.partNumber = other.optionalPartNumber().map(StringFilter::copy).orElse(null);
        this.eau = other.optionalEau().map(IntegerFilter::copy).orElse(null);
        this.manufacturingLocation = other.optionalManufacturingLocation().map(StringFilter::copy).orElse(null);
        this.fobLocation = other.optionalFobLocation().map(StringFilter::copy).orElse(null);
        this.packingRequirements = other.optionalPackingRequirements().map(StringFilter::copy).orElse(null);
        this.machineSize = other.optionalMachineSize().map(StringFilter::copy).orElse(null);
        this.cycleTime = other.optionalCycleTime().map(IntegerFilter::copy).orElse(null);
        this.partWeight = other.optionalPartWeight().map(IntegerFilter::copy).orElse(null);
        this.runnerWeight = other.optionalRunnerWeight().map(IntegerFilter::copy).orElse(null);
        this.cavities = other.optionalCavities().map(IntegerFilter::copy).orElse(null);
        this.comments = other.optionalComments().map(StringFilter::copy).orElse(null);
        this.riskLevel = other.optionalRiskLevel().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.materialPriceId = other.optionalMaterialPriceId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteComponentDetailCriteria copy() {
        return new NtQuoteComponentDetailCriteria(this);
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

    public StringFilter getManufacturingLocation() {
        return manufacturingLocation;
    }

    public Optional<StringFilter> optionalManufacturingLocation() {
        return Optional.ofNullable(manufacturingLocation);
    }

    public StringFilter manufacturingLocation() {
        if (manufacturingLocation == null) {
            setManufacturingLocation(new StringFilter());
        }
        return manufacturingLocation;
    }

    public void setManufacturingLocation(StringFilter manufacturingLocation) {
        this.manufacturingLocation = manufacturingLocation;
    }

    public StringFilter getFobLocation() {
        return fobLocation;
    }

    public Optional<StringFilter> optionalFobLocation() {
        return Optional.ofNullable(fobLocation);
    }

    public StringFilter fobLocation() {
        if (fobLocation == null) {
            setFobLocation(new StringFilter());
        }
        return fobLocation;
    }

    public void setFobLocation(StringFilter fobLocation) {
        this.fobLocation = fobLocation;
    }

    public StringFilter getPackingRequirements() {
        return packingRequirements;
    }

    public Optional<StringFilter> optionalPackingRequirements() {
        return Optional.ofNullable(packingRequirements);
    }

    public StringFilter packingRequirements() {
        if (packingRequirements == null) {
            setPackingRequirements(new StringFilter());
        }
        return packingRequirements;
    }

    public void setPackingRequirements(StringFilter packingRequirements) {
        this.packingRequirements = packingRequirements;
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

    public IntegerFilter getRunnerWeight() {
        return runnerWeight;
    }

    public Optional<IntegerFilter> optionalRunnerWeight() {
        return Optional.ofNullable(runnerWeight);
    }

    public IntegerFilter runnerWeight() {
        if (runnerWeight == null) {
            setRunnerWeight(new IntegerFilter());
        }
        return runnerWeight;
    }

    public void setRunnerWeight(IntegerFilter runnerWeight) {
        this.runnerWeight = runnerWeight;
    }

    public IntegerFilter getCavities() {
        return cavities;
    }

    public Optional<IntegerFilter> optionalCavities() {
        return Optional.ofNullable(cavities);
    }

    public IntegerFilter cavities() {
        if (cavities == null) {
            setCavities(new IntegerFilter());
        }
        return cavities;
    }

    public void setCavities(IntegerFilter cavities) {
        this.cavities = cavities;
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

    public StringFilter getRiskLevel() {
        return riskLevel;
    }

    public Optional<StringFilter> optionalRiskLevel() {
        return Optional.ofNullable(riskLevel);
    }

    public StringFilter riskLevel() {
        if (riskLevel == null) {
            setRiskLevel(new StringFilter());
        }
        return riskLevel;
    }

    public void setRiskLevel(StringFilter riskLevel) {
        this.riskLevel = riskLevel;
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

    public LongFilter getMaterialPriceId() {
        return materialPriceId;
    }

    public Optional<LongFilter> optionalMaterialPriceId() {
        return Optional.ofNullable(materialPriceId);
    }

    public LongFilter materialPriceId() {
        if (materialPriceId == null) {
            setMaterialPriceId(new LongFilter());
        }
        return materialPriceId;
    }

    public void setMaterialPriceId(LongFilter materialPriceId) {
        this.materialPriceId = materialPriceId;
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
        final NtQuoteComponentDetailCriteria that = (NtQuoteComponentDetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(materialDescription, that.materialDescription) &&
            Objects.equals(partNumber, that.partNumber) &&
            Objects.equals(eau, that.eau) &&
            Objects.equals(manufacturingLocation, that.manufacturingLocation) &&
            Objects.equals(fobLocation, that.fobLocation) &&
            Objects.equals(packingRequirements, that.packingRequirements) &&
            Objects.equals(machineSize, that.machineSize) &&
            Objects.equals(cycleTime, that.cycleTime) &&
            Objects.equals(partWeight, that.partWeight) &&
            Objects.equals(runnerWeight, that.runnerWeight) &&
            Objects.equals(cavities, that.cavities) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(riskLevel, that.riskLevel) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(ntQuoteId, that.ntQuoteId) &&
            Objects.equals(materialPriceId, that.materialPriceId) &&
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
            eau,
            manufacturingLocation,
            fobLocation,
            packingRequirements,
            machineSize,
            cycleTime,
            partWeight,
            runnerWeight,
            cavities,
            comments,
            riskLevel,
            createdBy,
            createdDate,
            updatedBy,
            updatedDate,
            ntQuoteId,
            materialPriceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteComponentDetailCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalMaterialDescription().map(f -> "materialDescription=" + f + ", ").orElse("") +
            optionalPartNumber().map(f -> "partNumber=" + f + ", ").orElse("") +
            optionalEau().map(f -> "eau=" + f + ", ").orElse("") +
            optionalManufacturingLocation().map(f -> "manufacturingLocation=" + f + ", ").orElse("") +
            optionalFobLocation().map(f -> "fobLocation=" + f + ", ").orElse("") +
            optionalPackingRequirements().map(f -> "packingRequirements=" + f + ", ").orElse("") +
            optionalMachineSize().map(f -> "machineSize=" + f + ", ").orElse("") +
            optionalCycleTime().map(f -> "cycleTime=" + f + ", ").orElse("") +
            optionalPartWeight().map(f -> "partWeight=" + f + ", ").orElse("") +
            optionalRunnerWeight().map(f -> "runnerWeight=" + f + ", ").orElse("") +
            optionalCavities().map(f -> "cavities=" + f + ", ").orElse("") +
            optionalComments().map(f -> "comments=" + f + ", ").orElse("") +
            optionalRiskLevel().map(f -> "riskLevel=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalMaterialPriceId().map(f -> "materialPriceId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
