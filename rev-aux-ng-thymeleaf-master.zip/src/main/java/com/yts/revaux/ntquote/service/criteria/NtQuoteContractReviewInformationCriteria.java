package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteContractReviewInformationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-contract-review-informations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteContractReviewInformationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter contractNumber;

    private StringFilter revision;

    private LocalDateFilter reviewDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private Boolean distinct;

    public NtQuoteContractReviewInformationCriteria() {}

    public NtQuoteContractReviewInformationCriteria(NtQuoteContractReviewInformationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.contractNumber = other.optionalContractNumber().map(StringFilter::copy).orElse(null);
        this.revision = other.optionalRevision().map(StringFilter::copy).orElse(null);
        this.reviewDate = other.optionalReviewDate().map(LocalDateFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteContractReviewInformationCriteria copy() {
        return new NtQuoteContractReviewInformationCriteria(this);
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

    public StringFilter getContractNumber() {
        return contractNumber;
    }

    public Optional<StringFilter> optionalContractNumber() {
        return Optional.ofNullable(contractNumber);
    }

    public StringFilter contractNumber() {
        if (contractNumber == null) {
            setContractNumber(new StringFilter());
        }
        return contractNumber;
    }

    public void setContractNumber(StringFilter contractNumber) {
        this.contractNumber = contractNumber;
    }

    public StringFilter getRevision() {
        return revision;
    }

    public Optional<StringFilter> optionalRevision() {
        return Optional.ofNullable(revision);
    }

    public StringFilter revision() {
        if (revision == null) {
            setRevision(new StringFilter());
        }
        return revision;
    }

    public void setRevision(StringFilter revision) {
        this.revision = revision;
    }

    public LocalDateFilter getReviewDate() {
        return reviewDate;
    }

    public Optional<LocalDateFilter> optionalReviewDate() {
        return Optional.ofNullable(reviewDate);
    }

    public LocalDateFilter reviewDate() {
        if (reviewDate == null) {
            setReviewDate(new LocalDateFilter());
        }
        return reviewDate;
    }

    public void setReviewDate(LocalDateFilter reviewDate) {
        this.reviewDate = reviewDate;
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
        final NtQuoteContractReviewInformationCriteria that = (NtQuoteContractReviewInformationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(contractNumber, that.contractNumber) &&
            Objects.equals(revision, that.revision) &&
            Objects.equals(reviewDate, that.reviewDate) &&
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
            contractNumber,
            revision,
            reviewDate,
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
        return "NtQuoteContractReviewInformationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalContractNumber().map(f -> "contractNumber=" + f + ", ").orElse("") +
            optionalRevision().map(f -> "revision=" + f + ", ").orElse("") +
            optionalReviewDate().map(f -> "reviewDate=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
