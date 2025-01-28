package com.yts.revaux.ntquote.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.yts.revaux.ntquote.domain.VendorProfile} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorProfileDTO implements Serializable {

    private Long id;

    private Integer srNo;

    @NotNull
    private UUID uid;

    @NotNull
    private String vendorId;

    private String vendorName;

    private String contact;

    private ZonedDateTime entryDate;

    private String tradeCurrencyId;

    private String address1;

    private String address2;

    private String address3;

    private String mailId;

    private String status;

    private String rating;

    private Integer isDeleteFlag;

    private String relatedBuyerUid;

    private String country;

    private String countryFlag;

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

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ZonedDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getTradeCurrencyId() {
        return tradeCurrencyId;
    }

    public void setTradeCurrencyId(String tradeCurrencyId) {
        this.tradeCurrencyId = tradeCurrencyId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getIsDeleteFlag() {
        return isDeleteFlag;
    }

    public void setIsDeleteFlag(Integer isDeleteFlag) {
        this.isDeleteFlag = isDeleteFlag;
    }

    public String getRelatedBuyerUid() {
        return relatedBuyerUid;
    }

    public void setRelatedBuyerUid(String relatedBuyerUid) {
        this.relatedBuyerUid = relatedBuyerUid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorProfileDTO)) {
            return false;
        }

        VendorProfileDTO vendorProfileDTO = (VendorProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vendorProfileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorProfileDTO{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", vendorId='" + getVendorId() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", contact='" + getContact() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", tradeCurrencyId='" + getTradeCurrencyId() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", mailId='" + getMailId() + "'" +
            ", status='" + getStatus() + "'" +
            ", rating='" + getRating() + "'" +
            ", isDeleteFlag=" + getIsDeleteFlag() +
            ", relatedBuyerUid='" + getRelatedBuyerUid() + "'" +
            ", country='" + getCountry() + "'" +
            ", countryFlag='" + getCountryFlag() + "'" +
            "}";
    }
}
