package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuoteProjectApproval} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteProjectApprovalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-project-approvals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteProjectApprovalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter approvedBy;

    private LocalDateFilter approvalDate;

    private StringFilter programManager;

    private StringFilter engineering;

    private StringFilter quality;

    private StringFilter materials;

    private StringFilter plantManager;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private Boolean distinct;

    public NtQuoteProjectApprovalCriteria() {}

    public NtQuoteProjectApprovalCriteria(NtQuoteProjectApprovalCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.approvedBy = other.optionalApprovedBy().map(StringFilter::copy).orElse(null);
        this.approvalDate = other.optionalApprovalDate().map(LocalDateFilter::copy).orElse(null);
        this.programManager = other.optionalProgramManager().map(StringFilter::copy).orElse(null);
        this.engineering = other.optionalEngineering().map(StringFilter::copy).orElse(null);
        this.quality = other.optionalQuality().map(StringFilter::copy).orElse(null);
        this.materials = other.optionalMaterials().map(StringFilter::copy).orElse(null);
        this.plantManager = other.optionalPlantManager().map(StringFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteProjectApprovalCriteria copy() {
        return new NtQuoteProjectApprovalCriteria(this);
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

    public StringFilter getApprovedBy() {
        return approvedBy;
    }

    public Optional<StringFilter> optionalApprovedBy() {
        return Optional.ofNullable(approvedBy);
    }

    public StringFilter approvedBy() {
        if (approvedBy == null) {
            setApprovedBy(new StringFilter());
        }
        return approvedBy;
    }

    public void setApprovedBy(StringFilter approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateFilter getApprovalDate() {
        return approvalDate;
    }

    public Optional<LocalDateFilter> optionalApprovalDate() {
        return Optional.ofNullable(approvalDate);
    }

    public LocalDateFilter approvalDate() {
        if (approvalDate == null) {
            setApprovalDate(new LocalDateFilter());
        }
        return approvalDate;
    }

    public void setApprovalDate(LocalDateFilter approvalDate) {
        this.approvalDate = approvalDate;
    }

    public StringFilter getProgramManager() {
        return programManager;
    }

    public Optional<StringFilter> optionalProgramManager() {
        return Optional.ofNullable(programManager);
    }

    public StringFilter programManager() {
        if (programManager == null) {
            setProgramManager(new StringFilter());
        }
        return programManager;
    }

    public void setProgramManager(StringFilter programManager) {
        this.programManager = programManager;
    }

    public StringFilter getEngineering() {
        return engineering;
    }

    public Optional<StringFilter> optionalEngineering() {
        return Optional.ofNullable(engineering);
    }

    public StringFilter engineering() {
        if (engineering == null) {
            setEngineering(new StringFilter());
        }
        return engineering;
    }

    public void setEngineering(StringFilter engineering) {
        this.engineering = engineering;
    }

    public StringFilter getQuality() {
        return quality;
    }

    public Optional<StringFilter> optionalQuality() {
        return Optional.ofNullable(quality);
    }

    public StringFilter quality() {
        if (quality == null) {
            setQuality(new StringFilter());
        }
        return quality;
    }

    public void setQuality(StringFilter quality) {
        this.quality = quality;
    }

    public StringFilter getMaterials() {
        return materials;
    }

    public Optional<StringFilter> optionalMaterials() {
        return Optional.ofNullable(materials);
    }

    public StringFilter materials() {
        if (materials == null) {
            setMaterials(new StringFilter());
        }
        return materials;
    }

    public void setMaterials(StringFilter materials) {
        this.materials = materials;
    }

    public StringFilter getPlantManager() {
        return plantManager;
    }

    public Optional<StringFilter> optionalPlantManager() {
        return Optional.ofNullable(plantManager);
    }

    public StringFilter plantManager() {
        if (plantManager == null) {
            setPlantManager(new StringFilter());
        }
        return plantManager;
    }

    public void setPlantManager(StringFilter plantManager) {
        this.plantManager = plantManager;
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
        final NtQuoteProjectApprovalCriteria that = (NtQuoteProjectApprovalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(approvedBy, that.approvedBy) &&
            Objects.equals(approvalDate, that.approvalDate) &&
            Objects.equals(programManager, that.programManager) &&
            Objects.equals(engineering, that.engineering) &&
            Objects.equals(quality, that.quality) &&
            Objects.equals(materials, that.materials) &&
            Objects.equals(plantManager, that.plantManager) &&
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
            approvedBy,
            approvalDate,
            programManager,
            engineering,
            quality,
            materials,
            plantManager,
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
        return "NtQuoteProjectApprovalCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalApprovedBy().map(f -> "approvedBy=" + f + ", ").orElse("") +
            optionalApprovalDate().map(f -> "approvalDate=" + f + ", ").orElse("") +
            optionalProgramManager().map(f -> "programManager=" + f + ", ").orElse("") +
            optionalEngineering().map(f -> "engineering=" + f + ", ").orElse("") +
            optionalQuality().map(f -> "quality=" + f + ", ").orElse("") +
            optionalMaterials().map(f -> "materials=" + f + ", ").orElse("") +
            optionalPlantManager().map(f -> "plantManager=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
