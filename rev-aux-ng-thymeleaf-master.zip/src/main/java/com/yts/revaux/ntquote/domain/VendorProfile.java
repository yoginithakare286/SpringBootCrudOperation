package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VendorProfile.
 */
@Entity
@Table(name = "vendor_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vendorprofile")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorProfile implements Serializable {

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

    @NotNull
    @Column(name = "vendor_id", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String vendorId;

    @Column(name = "vendor_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String vendorName;

    @Column(name = "contact")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String contact;

    @Column(name = "entry_date")
    private ZonedDateTime entryDate;

    @Column(name = "trade_currency_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String tradeCurrencyId;

    @Column(name = "address_1")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String address1;

    @Column(name = "address_2")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String address2;

    @Column(name = "address_3")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String address3;

    @Column(name = "mail_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String mailId;

    @Column(name = "status")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String status;

    @Column(name = "rating")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rating;

    @Column(name = "is_delete_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer isDeleteFlag;

    @Column(name = "related_buyer_uid")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String relatedBuyerUid;

    @Column(name = "country")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String country;

    @Column(name = "country_flag")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String countryFlag;

    @JsonIgnoreProperties(value = { "rfqDetail", "vendor" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "vendor")
    @org.springframework.data.annotation.Transient
    private BuyerRfqPricesDetail buyerRfqPricesDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VendorProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public VendorProfile srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public VendorProfile uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public VendorProfile vendorId(String vendorId) {
        this.setVendorId(vendorId);
        return this;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public VendorProfile vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getContact() {
        return this.contact;
    }

    public VendorProfile contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ZonedDateTime getEntryDate() {
        return this.entryDate;
    }

    public VendorProfile entryDate(ZonedDateTime entryDate) {
        this.setEntryDate(entryDate);
        return this;
    }

    public void setEntryDate(ZonedDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getTradeCurrencyId() {
        return this.tradeCurrencyId;
    }

    public VendorProfile tradeCurrencyId(String tradeCurrencyId) {
        this.setTradeCurrencyId(tradeCurrencyId);
        return this;
    }

    public void setTradeCurrencyId(String tradeCurrencyId) {
        this.tradeCurrencyId = tradeCurrencyId;
    }

    public String getAddress1() {
        return this.address1;
    }

    public VendorProfile address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public VendorProfile address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return this.address3;
    }

    public VendorProfile address3(String address3) {
        this.setAddress3(address3);
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getMailId() {
        return this.mailId;
    }

    public VendorProfile mailId(String mailId) {
        this.setMailId(mailId);
        return this;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getStatus() {
        return this.status;
    }

    public VendorProfile status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return this.rating;
    }

    public VendorProfile rating(String rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getIsDeleteFlag() {
        return this.isDeleteFlag;
    }

    public VendorProfile isDeleteFlag(Integer isDeleteFlag) {
        this.setIsDeleteFlag(isDeleteFlag);
        return this;
    }

    public void setIsDeleteFlag(Integer isDeleteFlag) {
        this.isDeleteFlag = isDeleteFlag;
    }

    public String getRelatedBuyerUid() {
        return this.relatedBuyerUid;
    }

    public VendorProfile relatedBuyerUid(String relatedBuyerUid) {
        this.setRelatedBuyerUid(relatedBuyerUid);
        return this;
    }

    public void setRelatedBuyerUid(String relatedBuyerUid) {
        this.relatedBuyerUid = relatedBuyerUid;
    }

    public String getCountry() {
        return this.country;
    }

    public VendorProfile country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryFlag() {
        return this.countryFlag;
    }

    public VendorProfile countryFlag(String countryFlag) {
        this.setCountryFlag(countryFlag);
        return this;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public BuyerRfqPricesDetail getBuyerRfqPricesDetail() {
        return this.buyerRfqPricesDetail;
    }

    public void setBuyerRfqPricesDetail(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        if (this.buyerRfqPricesDetail != null) {
            this.buyerRfqPricesDetail.setVendor(null);
        }
        if (buyerRfqPricesDetail != null) {
            buyerRfqPricesDetail.setVendor(this);
        }
        this.buyerRfqPricesDetail = buyerRfqPricesDetail;
    }

    public VendorProfile buyerRfqPricesDetail(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        this.setBuyerRfqPricesDetail(buyerRfqPricesDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorProfile)) {
            return false;
        }
        return getId() != null && getId().equals(((VendorProfile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorProfile{" +
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
