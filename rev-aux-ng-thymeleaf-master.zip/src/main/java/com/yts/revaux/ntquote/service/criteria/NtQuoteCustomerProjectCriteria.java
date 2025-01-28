package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerProject} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteCustomerProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quote-customer-projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerProjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter qsf;

    private StringFilter rev;

    private ZonedDateTimeFilter date;

    private StringFilter customerName;

    private StringFilter contactName;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter overallProjectRiskEvaluation;

    private ZonedDateTimeFilter assessmentDate;

    private ZonedDateTimeFilter reAssessmentDate;

    private StringFilter projectName;

    private StringFilter projectInformation;

    private StringFilter projectManager;

    private StringFilter projectRequirement;

    private StringFilter lengthOfProject;

    private StringFilter newMold;

    private StringFilter transferMold;

    private LocalDateFilter contactReviewDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter ntQuoteId;

    private Boolean distinct;

    public NtQuoteCustomerProjectCriteria() {}

    public NtQuoteCustomerProjectCriteria(NtQuoteCustomerProjectCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.qsf = other.optionalQsf().map(StringFilter::copy).orElse(null);
        this.rev = other.optionalRev().map(StringFilter::copy).orElse(null);
        this.date = other.optionalDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.customerName = other.optionalCustomerName().map(StringFilter::copy).orElse(null);
        this.contactName = other.optionalContactName().map(StringFilter::copy).orElse(null);
        this.phone = other.optionalPhone().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.overallProjectRiskEvaluation = other.optionalOverallProjectRiskEvaluation().map(StringFilter::copy).orElse(null);
        this.assessmentDate = other.optionalAssessmentDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.reAssessmentDate = other.optionalReAssessmentDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.projectName = other.optionalProjectName().map(StringFilter::copy).orElse(null);
        this.projectInformation = other.optionalProjectInformation().map(StringFilter::copy).orElse(null);
        this.projectManager = other.optionalProjectManager().map(StringFilter::copy).orElse(null);
        this.projectRequirement = other.optionalProjectRequirement().map(StringFilter::copy).orElse(null);
        this.lengthOfProject = other.optionalLengthOfProject().map(StringFilter::copy).orElse(null);
        this.newMold = other.optionalNewMold().map(StringFilter::copy).orElse(null);
        this.transferMold = other.optionalTransferMold().map(StringFilter::copy).orElse(null);
        this.contactReviewDate = other.optionalContactReviewDate().map(LocalDateFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.ntQuoteId = other.optionalNtQuoteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteCustomerProjectCriteria copy() {
        return new NtQuoteCustomerProjectCriteria(this);
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

    public StringFilter getQsf() {
        return qsf;
    }

    public Optional<StringFilter> optionalQsf() {
        return Optional.ofNullable(qsf);
    }

    public StringFilter qsf() {
        if (qsf == null) {
            setQsf(new StringFilter());
        }
        return qsf;
    }

    public void setQsf(StringFilter qsf) {
        this.qsf = qsf;
    }

    public StringFilter getRev() {
        return rev;
    }

    public Optional<StringFilter> optionalRev() {
        return Optional.ofNullable(rev);
    }

    public StringFilter rev() {
        if (rev == null) {
            setRev(new StringFilter());
        }
        return rev;
    }

    public void setRev(StringFilter rev) {
        this.rev = rev;
    }

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public Optional<ZonedDateTimeFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public ZonedDateTimeFilter date() {
        if (date == null) {
            setDate(new ZonedDateTimeFilter());
        }
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public Optional<StringFilter> optionalCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public StringFilter customerName() {
        if (customerName == null) {
            setCustomerName(new StringFilter());
        }
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public Optional<StringFilter> optionalContactName() {
        return Optional.ofNullable(contactName);
    }

    public StringFilter contactName() {
        if (contactName == null) {
            setContactName(new StringFilter());
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public Optional<StringFilter> optionalPhone() {
        return Optional.ofNullable(phone);
    }

    public StringFilter phone() {
        if (phone == null) {
            setPhone(new StringFilter());
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getOverallProjectRiskEvaluation() {
        return overallProjectRiskEvaluation;
    }

    public Optional<StringFilter> optionalOverallProjectRiskEvaluation() {
        return Optional.ofNullable(overallProjectRiskEvaluation);
    }

    public StringFilter overallProjectRiskEvaluation() {
        if (overallProjectRiskEvaluation == null) {
            setOverallProjectRiskEvaluation(new StringFilter());
        }
        return overallProjectRiskEvaluation;
    }

    public void setOverallProjectRiskEvaluation(StringFilter overallProjectRiskEvaluation) {
        this.overallProjectRiskEvaluation = overallProjectRiskEvaluation;
    }

    public ZonedDateTimeFilter getAssessmentDate() {
        return assessmentDate;
    }

    public Optional<ZonedDateTimeFilter> optionalAssessmentDate() {
        return Optional.ofNullable(assessmentDate);
    }

    public ZonedDateTimeFilter assessmentDate() {
        if (assessmentDate == null) {
            setAssessmentDate(new ZonedDateTimeFilter());
        }
        return assessmentDate;
    }

    public void setAssessmentDate(ZonedDateTimeFilter assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public ZonedDateTimeFilter getReAssessmentDate() {
        return reAssessmentDate;
    }

    public Optional<ZonedDateTimeFilter> optionalReAssessmentDate() {
        return Optional.ofNullable(reAssessmentDate);
    }

    public ZonedDateTimeFilter reAssessmentDate() {
        if (reAssessmentDate == null) {
            setReAssessmentDate(new ZonedDateTimeFilter());
        }
        return reAssessmentDate;
    }

    public void setReAssessmentDate(ZonedDateTimeFilter reAssessmentDate) {
        this.reAssessmentDate = reAssessmentDate;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public Optional<StringFilter> optionalProjectName() {
        return Optional.ofNullable(projectName);
    }

    public StringFilter projectName() {
        if (projectName == null) {
            setProjectName(new StringFilter());
        }
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public StringFilter getProjectInformation() {
        return projectInformation;
    }

    public Optional<StringFilter> optionalProjectInformation() {
        return Optional.ofNullable(projectInformation);
    }

    public StringFilter projectInformation() {
        if (projectInformation == null) {
            setProjectInformation(new StringFilter());
        }
        return projectInformation;
    }

    public void setProjectInformation(StringFilter projectInformation) {
        this.projectInformation = projectInformation;
    }

    public StringFilter getProjectManager() {
        return projectManager;
    }

    public Optional<StringFilter> optionalProjectManager() {
        return Optional.ofNullable(projectManager);
    }

    public StringFilter projectManager() {
        if (projectManager == null) {
            setProjectManager(new StringFilter());
        }
        return projectManager;
    }

    public void setProjectManager(StringFilter projectManager) {
        this.projectManager = projectManager;
    }

    public StringFilter getProjectRequirement() {
        return projectRequirement;
    }

    public Optional<StringFilter> optionalProjectRequirement() {
        return Optional.ofNullable(projectRequirement);
    }

    public StringFilter projectRequirement() {
        if (projectRequirement == null) {
            setProjectRequirement(new StringFilter());
        }
        return projectRequirement;
    }

    public void setProjectRequirement(StringFilter projectRequirement) {
        this.projectRequirement = projectRequirement;
    }

    public StringFilter getLengthOfProject() {
        return lengthOfProject;
    }

    public Optional<StringFilter> optionalLengthOfProject() {
        return Optional.ofNullable(lengthOfProject);
    }

    public StringFilter lengthOfProject() {
        if (lengthOfProject == null) {
            setLengthOfProject(new StringFilter());
        }
        return lengthOfProject;
    }

    public void setLengthOfProject(StringFilter lengthOfProject) {
        this.lengthOfProject = lengthOfProject;
    }

    public StringFilter getNewMold() {
        return newMold;
    }

    public Optional<StringFilter> optionalNewMold() {
        return Optional.ofNullable(newMold);
    }

    public StringFilter newMold() {
        if (newMold == null) {
            setNewMold(new StringFilter());
        }
        return newMold;
    }

    public void setNewMold(StringFilter newMold) {
        this.newMold = newMold;
    }

    public StringFilter getTransferMold() {
        return transferMold;
    }

    public Optional<StringFilter> optionalTransferMold() {
        return Optional.ofNullable(transferMold);
    }

    public StringFilter transferMold() {
        if (transferMold == null) {
            setTransferMold(new StringFilter());
        }
        return transferMold;
    }

    public void setTransferMold(StringFilter transferMold) {
        this.transferMold = transferMold;
    }

    public LocalDateFilter getContactReviewDate() {
        return contactReviewDate;
    }

    public Optional<LocalDateFilter> optionalContactReviewDate() {
        return Optional.ofNullable(contactReviewDate);
    }

    public LocalDateFilter contactReviewDate() {
        if (contactReviewDate == null) {
            setContactReviewDate(new LocalDateFilter());
        }
        return contactReviewDate;
    }

    public void setContactReviewDate(LocalDateFilter contactReviewDate) {
        this.contactReviewDate = contactReviewDate;
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
        final NtQuoteCustomerProjectCriteria that = (NtQuoteCustomerProjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(qsf, that.qsf) &&
            Objects.equals(rev, that.rev) &&
            Objects.equals(date, that.date) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(overallProjectRiskEvaluation, that.overallProjectRiskEvaluation) &&
            Objects.equals(assessmentDate, that.assessmentDate) &&
            Objects.equals(reAssessmentDate, that.reAssessmentDate) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(projectInformation, that.projectInformation) &&
            Objects.equals(projectManager, that.projectManager) &&
            Objects.equals(projectRequirement, that.projectRequirement) &&
            Objects.equals(lengthOfProject, that.lengthOfProject) &&
            Objects.equals(newMold, that.newMold) &&
            Objects.equals(transferMold, that.transferMold) &&
            Objects.equals(contactReviewDate, that.contactReviewDate) &&
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
            qsf,
            rev,
            date,
            customerName,
            contactName,
            phone,
            email,
            overallProjectRiskEvaluation,
            assessmentDate,
            reAssessmentDate,
            projectName,
            projectInformation,
            projectManager,
            projectRequirement,
            lengthOfProject,
            newMold,
            transferMold,
            contactReviewDate,
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
        return "NtQuoteCustomerProjectCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalQsf().map(f -> "qsf=" + f + ", ").orElse("") +
            optionalRev().map(f -> "rev=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalCustomerName().map(f -> "customerName=" + f + ", ").orElse("") +
            optionalContactName().map(f -> "contactName=" + f + ", ").orElse("") +
            optionalPhone().map(f -> "phone=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalOverallProjectRiskEvaluation().map(f -> "overallProjectRiskEvaluation=" + f + ", ").orElse("") +
            optionalAssessmentDate().map(f -> "assessmentDate=" + f + ", ").orElse("") +
            optionalReAssessmentDate().map(f -> "reAssessmentDate=" + f + ", ").orElse("") +
            optionalProjectName().map(f -> "projectName=" + f + ", ").orElse("") +
            optionalProjectInformation().map(f -> "projectInformation=" + f + ", ").orElse("") +
            optionalProjectManager().map(f -> "projectManager=" + f + ", ").orElse("") +
            optionalProjectRequirement().map(f -> "projectRequirement=" + f + ", ").orElse("") +
            optionalLengthOfProject().map(f -> "lengthOfProject=" + f + ", ").orElse("") +
            optionalNewMold().map(f -> "newMold=" + f + ", ").orElse("") +
            optionalTransferMold().map(f -> "transferMold=" + f + ", ").orElse("") +
            optionalContactReviewDate().map(f -> "contactReviewDate=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalNtQuoteId().map(f -> "ntQuoteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
