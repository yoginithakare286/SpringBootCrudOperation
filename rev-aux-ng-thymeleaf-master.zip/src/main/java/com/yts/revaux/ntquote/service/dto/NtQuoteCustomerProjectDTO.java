package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerProject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerProjectDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String qsf;

    private String rev;

    private ZonedDateTime date;

    private String customerName;

    private String contactName;

    private String phone;

    private String email;

    private String overallProjectRiskEvaluation;

    private ZonedDateTime assessmentDate;

    private ZonedDateTime reAssessmentDate;

    private String projectName;

    private String projectInformation;

    private String projectManager;

    private String projectRequirement;

    private String lengthOfProject;

    private String newMold;

    private String transferMold;

    private LocalDate contactReviewDate;

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

    public String getQsf() {
        return qsf;
    }

    public void setQsf(String qsf) {
        this.qsf = qsf;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOverallProjectRiskEvaluation() {
        return overallProjectRiskEvaluation;
    }

    public void setOverallProjectRiskEvaluation(String overallProjectRiskEvaluation) {
        this.overallProjectRiskEvaluation = overallProjectRiskEvaluation;
    }

    public ZonedDateTime getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(ZonedDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public ZonedDateTime getReAssessmentDate() {
        return reAssessmentDate;
    }

    public void setReAssessmentDate(ZonedDateTime reAssessmentDate) {
        this.reAssessmentDate = reAssessmentDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectInformation() {
        return projectInformation;
    }

    public void setProjectInformation(String projectInformation) {
        this.projectInformation = projectInformation;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectRequirement() {
        return projectRequirement;
    }

    public void setProjectRequirement(String projectRequirement) {
        this.projectRequirement = projectRequirement;
    }

    public String getLengthOfProject() {
        return lengthOfProject;
    }

    public void setLengthOfProject(String lengthOfProject) {
        this.lengthOfProject = lengthOfProject;
    }

    public String getNewMold() {
        return newMold;
    }

    public void setNewMold(String newMold) {
        this.newMold = newMold;
    }

    public String getTransferMold() {
        return transferMold;
    }

    public void setTransferMold(String transferMold) {
        this.transferMold = transferMold;
    }

    public LocalDate getContactReviewDate() {
        return contactReviewDate;
    }

    public void setContactReviewDate(LocalDate contactReviewDate) {
        this.contactReviewDate = contactReviewDate;
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
        if (!(o instanceof NtQuoteCustomerProjectDTO)) {
            return false;
        }

        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = (NtQuoteCustomerProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ntQuoteCustomerProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCustomerProjectDTO{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", qsf='" + getQsf() + "'" +
            ", rev='" + getRev() + "'" +
            ", date='" + getDate() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", overallProjectRiskEvaluation='" + getOverallProjectRiskEvaluation() + "'" +
            ", assessmentDate='" + getAssessmentDate() + "'" +
            ", reAssessmentDate='" + getReAssessmentDate() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", projectInformation='" + getProjectInformation() + "'" +
            ", projectManager='" + getProjectManager() + "'" +
            ", projectRequirement='" + getProjectRequirement() + "'" +
            ", lengthOfProject='" + getLengthOfProject() + "'" +
            ", newMold='" + getNewMold() + "'" +
            ", transferMold='" + getTransferMold() + "'" +
            ", contactReviewDate='" + getContactReviewDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", ntQuote=" + getNtQuote() +
            "}";
    }
}
