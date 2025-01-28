package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NtQuoteProjectApproval.
 */
@Entity
@Table(name = "nt_quote_project_approval")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquoteprojectapproval")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteProjectApproval implements Serializable {

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

    @Column(name = "approved_by")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "program_manager")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String programManager;

    @Column(name = "engineering")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String engineering;

    @Column(name = "quality")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String quality;

    @Column(name = "materials")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String materials;

    @Column(name = "plant_manager")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String plantManager;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuoteProjectApproval")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
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
    private Set<NtQuote> ntQuotes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NtQuoteProjectApproval id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuoteProjectApproval srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuoteProjectApproval uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getApprovedBy() {
        return this.approvedBy;
    }

    public NtQuoteProjectApproval approvedBy(String approvedBy) {
        this.setApprovedBy(approvedBy);
        return this;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovalDate() {
        return this.approvalDate;
    }

    public NtQuoteProjectApproval approvalDate(LocalDate approvalDate) {
        this.setApprovalDate(approvalDate);
        return this;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getProgramManager() {
        return this.programManager;
    }

    public NtQuoteProjectApproval programManager(String programManager) {
        this.setProgramManager(programManager);
        return this;
    }

    public void setProgramManager(String programManager) {
        this.programManager = programManager;
    }

    public String getEngineering() {
        return this.engineering;
    }

    public NtQuoteProjectApproval engineering(String engineering) {
        this.setEngineering(engineering);
        return this;
    }

    public void setEngineering(String engineering) {
        this.engineering = engineering;
    }

    public String getQuality() {
        return this.quality;
    }

    public NtQuoteProjectApproval quality(String quality) {
        this.setQuality(quality);
        return this;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMaterials() {
        return this.materials;
    }

    public NtQuoteProjectApproval materials(String materials) {
        this.setMaterials(materials);
        return this;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getPlantManager() {
        return this.plantManager;
    }

    public NtQuoteProjectApproval plantManager(String plantManager) {
        this.setPlantManager(plantManager);
        return this;
    }

    public void setPlantManager(String plantManager) {
        this.plantManager = plantManager;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuoteProjectApproval createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuoteProjectApproval createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuoteProjectApproval updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuoteProjectApproval updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<NtQuote> getNtQuotes() {
        return this.ntQuotes;
    }

    public void setNtQuotes(Set<NtQuote> ntQuotes) {
        if (this.ntQuotes != null) {
            this.ntQuotes.forEach(i -> i.setNtQuoteProjectApproval(null));
        }
        if (ntQuotes != null) {
            ntQuotes.forEach(i -> i.setNtQuoteProjectApproval(this));
        }
        this.ntQuotes = ntQuotes;
    }

    public NtQuoteProjectApproval ntQuotes(Set<NtQuote> ntQuotes) {
        this.setNtQuotes(ntQuotes);
        return this;
    }

    public NtQuoteProjectApproval addNtQuote(NtQuote ntQuote) {
        this.ntQuotes.add(ntQuote);
        ntQuote.setNtQuoteProjectApproval(this);
        return this;
    }

    public NtQuoteProjectApproval removeNtQuote(NtQuote ntQuote) {
        this.ntQuotes.remove(ntQuote);
        ntQuote.setNtQuoteProjectApproval(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteProjectApproval)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuoteProjectApproval) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteProjectApproval{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", approvedBy='" + getApprovedBy() + "'" +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", programManager='" + getProgramManager() + "'" +
            ", engineering='" + getEngineering() + "'" +
            ", quality='" + getQuality() + "'" +
            ", materials='" + getMaterials() + "'" +
            ", plantManager='" + getPlantManager() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
