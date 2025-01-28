package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.NtQuoteProjectApproval} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteProjectApprovalDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String approvedBy;

    private LocalDate approvalDate;

    private String programManager;

    private String engineering;

    private String quality;

    private String materials;

    private String plantManager;

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;

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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getProgramManager() {
        return programManager;
    }

    public void setProgramManager(String programManager) {
        this.programManager = programManager;
    }

    public String getEngineering() {
        return engineering;
    }

    public void setEngineering(String engineering) {
        this.engineering = engineering;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getPlantManager() {
        return plantManager;
    }

    public void setPlantManager(String plantManager) {
        this.plantManager = plantManager;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteProjectApprovalDTO)) {
            return false;
        }

        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = (NtQuoteProjectApprovalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ntQuoteProjectApprovalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteProjectApprovalDTO{" +
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
