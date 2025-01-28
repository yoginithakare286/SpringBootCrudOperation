package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NtQuoteCustomerProject.
 */
@Entity
@Table(name = "nt_quote_customer_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquotecustomerproject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCustomerProject implements Serializable {

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

    @Column(name = "qsf")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String qsf;

    @Column(name = "rev")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rev;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "customer_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String customerName;

    @Column(name = "contact_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String contactName;

    @Column(name = "phone")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String phone;

    @Column(name = "email")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String email;

    @Column(name = "overall_project_risk_evaluation")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String overallProjectRiskEvaluation;

    @Column(name = "assessment_date")
    private ZonedDateTime assessmentDate;

    @Column(name = "re_assessment_date")
    private ZonedDateTime reAssessmentDate;

    @Column(name = "project_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String projectName;

    @Column(name = "project_information")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String projectInformation;

    @Column(name = "project_manager")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String projectManager;

    @Column(name = "project_requirement")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String projectRequirement;

    @Column(name = "length_of_project")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String lengthOfProject;

    @Column(name = "new_mold")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String newMold;

    @Column(name = "transfer_mold")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String transferMold;

    @Column(name = "contact_review_date")
    private LocalDate contactReviewDate;

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

    public NtQuoteCustomerProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuoteCustomerProject srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuoteCustomerProject uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getQsf() {
        return this.qsf;
    }

    public NtQuoteCustomerProject qsf(String qsf) {
        this.setQsf(qsf);
        return this;
    }

    public void setQsf(String qsf) {
        this.qsf = qsf;
    }

    public String getRev() {
        return this.rev;
    }

    public NtQuoteCustomerProject rev(String rev) {
        this.setRev(rev);
        return this;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public NtQuoteCustomerProject date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public NtQuoteCustomerProject customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public NtQuoteCustomerProject contactName(String contactName) {
        this.setContactName(contactName);
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return this.phone;
    }

    public NtQuoteCustomerProject phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public NtQuoteCustomerProject email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOverallProjectRiskEvaluation() {
        return this.overallProjectRiskEvaluation;
    }

    public NtQuoteCustomerProject overallProjectRiskEvaluation(String overallProjectRiskEvaluation) {
        this.setOverallProjectRiskEvaluation(overallProjectRiskEvaluation);
        return this;
    }

    public void setOverallProjectRiskEvaluation(String overallProjectRiskEvaluation) {
        this.overallProjectRiskEvaluation = overallProjectRiskEvaluation;
    }

    public ZonedDateTime getAssessmentDate() {
        return this.assessmentDate;
    }

    public NtQuoteCustomerProject assessmentDate(ZonedDateTime assessmentDate) {
        this.setAssessmentDate(assessmentDate);
        return this;
    }

    public void setAssessmentDate(ZonedDateTime assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public ZonedDateTime getReAssessmentDate() {
        return this.reAssessmentDate;
    }

    public NtQuoteCustomerProject reAssessmentDate(ZonedDateTime reAssessmentDate) {
        this.setReAssessmentDate(reAssessmentDate);
        return this;
    }

    public void setReAssessmentDate(ZonedDateTime reAssessmentDate) {
        this.reAssessmentDate = reAssessmentDate;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public NtQuoteCustomerProject projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectInformation() {
        return this.projectInformation;
    }

    public NtQuoteCustomerProject projectInformation(String projectInformation) {
        this.setProjectInformation(projectInformation);
        return this;
    }

    public void setProjectInformation(String projectInformation) {
        this.projectInformation = projectInformation;
    }

    public String getProjectManager() {
        return this.projectManager;
    }

    public NtQuoteCustomerProject projectManager(String projectManager) {
        this.setProjectManager(projectManager);
        return this;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectRequirement() {
        return this.projectRequirement;
    }

    public NtQuoteCustomerProject projectRequirement(String projectRequirement) {
        this.setProjectRequirement(projectRequirement);
        return this;
    }

    public void setProjectRequirement(String projectRequirement) {
        this.projectRequirement = projectRequirement;
    }

    public String getLengthOfProject() {
        return this.lengthOfProject;
    }

    public NtQuoteCustomerProject lengthOfProject(String lengthOfProject) {
        this.setLengthOfProject(lengthOfProject);
        return this;
    }

    public void setLengthOfProject(String lengthOfProject) {
        this.lengthOfProject = lengthOfProject;
    }

    public String getNewMold() {
        return this.newMold;
    }

    public NtQuoteCustomerProject newMold(String newMold) {
        this.setNewMold(newMold);
        return this;
    }

    public void setNewMold(String newMold) {
        this.newMold = newMold;
    }

    public String getTransferMold() {
        return this.transferMold;
    }

    public NtQuoteCustomerProject transferMold(String transferMold) {
        this.setTransferMold(transferMold);
        return this;
    }

    public void setTransferMold(String transferMold) {
        this.transferMold = transferMold;
    }

    public LocalDate getContactReviewDate() {
        return this.contactReviewDate;
    }

    public NtQuoteCustomerProject contactReviewDate(LocalDate contactReviewDate) {
        this.setContactReviewDate(contactReviewDate);
        return this;
    }

    public void setContactReviewDate(LocalDate contactReviewDate) {
        this.contactReviewDate = contactReviewDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuoteCustomerProject createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuoteCustomerProject createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuoteCustomerProject updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuoteCustomerProject updatedDate(Instant updatedDate) {
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

    public NtQuoteCustomerProject ntQuote(NtQuote ntQuote) {
        this.setNtQuote(ntQuote);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteCustomerProject)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuoteCustomerProject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCustomerProject{" +
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
            "}";
    }
}
