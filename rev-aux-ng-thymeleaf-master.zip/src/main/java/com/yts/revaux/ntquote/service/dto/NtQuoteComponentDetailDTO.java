package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.NtQuoteComponentDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteComponentDetailDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    private String materialDescription;

    private String partNumber;

    private Integer eau;

    private String manufacturingLocation;

    private String fobLocation;

    private String packingRequirements;

    private String machineSize;

    private Integer cycleTime;

    private Integer partWeight;

    private Integer runnerWeight;

    private Integer cavities;

    private String comments;

    private String riskLevel;

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;

    private NtQuoteDTO ntQuote;

    private BuyerRfqPricesDetailDTO materialPrice;

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

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getEau() {
        return eau;
    }

    public void setEau(Integer eau) {
        this.eau = eau;
    }

    public String getManufacturingLocation() {
        return manufacturingLocation;
    }

    public void setManufacturingLocation(String manufacturingLocation) {
        this.manufacturingLocation = manufacturingLocation;
    }

    public String getFobLocation() {
        return fobLocation;
    }

    public void setFobLocation(String fobLocation) {
        this.fobLocation = fobLocation;
    }

    public String getPackingRequirements() {
        return packingRequirements;
    }

    public void setPackingRequirements(String packingRequirements) {
        this.packingRequirements = packingRequirements;
    }

    public String getMachineSize() {
        return machineSize;
    }

    public void setMachineSize(String machineSize) {
        this.machineSize = machineSize;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getPartWeight() {
        return partWeight;
    }

    public void setPartWeight(Integer partWeight) {
        this.partWeight = partWeight;
    }

    public Integer getRunnerWeight() {
        return runnerWeight;
    }

    public void setRunnerWeight(Integer runnerWeight) {
        this.runnerWeight = runnerWeight;
    }

    public Integer getCavities() {
        return cavities;
    }

    public void setCavities(Integer cavities) {
        this.cavities = cavities;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
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

    public BuyerRfqPricesDetailDTO getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(BuyerRfqPricesDetailDTO materialPrice) {
        this.materialPrice = materialPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuoteComponentDetailDTO)) {
            return false;
        }

        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = (NtQuoteComponentDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ntQuoteComponentDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteComponentDetailDTO{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", materialDescription='" + getMaterialDescription() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", eau=" + getEau() +
            ", manufacturingLocation='" + getManufacturingLocation() + "'" +
            ", fobLocation='" + getFobLocation() + "'" +
            ", packingRequirements='" + getPackingRequirements() + "'" +
            ", machineSize='" + getMachineSize() + "'" +
            ", cycleTime=" + getCycleTime() +
            ", partWeight=" + getPartWeight() +
            ", runnerWeight=" + getRunnerWeight() +
            ", cavities=" + getCavities() +
            ", comments='" + getComments() + "'" +
            ", riskLevel='" + getRiskLevel() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", ntQuote=" + getNtQuote() +
            ", materialPrice=" + getMaterialPrice() +
            "}";
    }
}
