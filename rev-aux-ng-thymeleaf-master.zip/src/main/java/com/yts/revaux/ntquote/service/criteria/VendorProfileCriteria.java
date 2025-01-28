package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.VendorProfile} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.VendorProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendor-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VendorProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter vendorId;

    private StringFilter vendorName;

    private StringFilter contact;

    private ZonedDateTimeFilter entryDate;

    private StringFilter tradeCurrencyId;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter address3;

    private StringFilter mailId;

    private StringFilter status;

    private StringFilter rating;

    private IntegerFilter isDeleteFlag;

    private StringFilter relatedBuyerUid;

    private StringFilter country;

    private StringFilter countryFlag;

    private LongFilter buyerRfqPricesDetailId;

    private Boolean distinct;

    public VendorProfileCriteria() {}

    public VendorProfileCriteria(VendorProfileCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.vendorId = other.optionalVendorId().map(StringFilter::copy).orElse(null);
        this.vendorName = other.optionalVendorName().map(StringFilter::copy).orElse(null);
        this.contact = other.optionalContact().map(StringFilter::copy).orElse(null);
        this.entryDate = other.optionalEntryDate().map(ZonedDateTimeFilter::copy).orElse(null);
        this.tradeCurrencyId = other.optionalTradeCurrencyId().map(StringFilter::copy).orElse(null);
        this.address1 = other.optionalAddress1().map(StringFilter::copy).orElse(null);
        this.address2 = other.optionalAddress2().map(StringFilter::copy).orElse(null);
        this.address3 = other.optionalAddress3().map(StringFilter::copy).orElse(null);
        this.mailId = other.optionalMailId().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.rating = other.optionalRating().map(StringFilter::copy).orElse(null);
        this.isDeleteFlag = other.optionalIsDeleteFlag().map(IntegerFilter::copy).orElse(null);
        this.relatedBuyerUid = other.optionalRelatedBuyerUid().map(StringFilter::copy).orElse(null);
        this.country = other.optionalCountry().map(StringFilter::copy).orElse(null);
        this.countryFlag = other.optionalCountryFlag().map(StringFilter::copy).orElse(null);
        this.buyerRfqPricesDetailId = other.optionalBuyerRfqPricesDetailId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VendorProfileCriteria copy() {
        return new VendorProfileCriteria(this);
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

    public StringFilter getVendorId() {
        return vendorId;
    }

    public Optional<StringFilter> optionalVendorId() {
        return Optional.ofNullable(vendorId);
    }

    public StringFilter vendorId() {
        if (vendorId == null) {
            setVendorId(new StringFilter());
        }
        return vendorId;
    }

    public void setVendorId(StringFilter vendorId) {
        this.vendorId = vendorId;
    }

    public StringFilter getVendorName() {
        return vendorName;
    }

    public Optional<StringFilter> optionalVendorName() {
        return Optional.ofNullable(vendorName);
    }

    public StringFilter vendorName() {
        if (vendorName == null) {
            setVendorName(new StringFilter());
        }
        return vendorName;
    }

    public void setVendorName(StringFilter vendorName) {
        this.vendorName = vendorName;
    }

    public StringFilter getContact() {
        return contact;
    }

    public Optional<StringFilter> optionalContact() {
        return Optional.ofNullable(contact);
    }

    public StringFilter contact() {
        if (contact == null) {
            setContact(new StringFilter());
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public ZonedDateTimeFilter getEntryDate() {
        return entryDate;
    }

    public Optional<ZonedDateTimeFilter> optionalEntryDate() {
        return Optional.ofNullable(entryDate);
    }

    public ZonedDateTimeFilter entryDate() {
        if (entryDate == null) {
            setEntryDate(new ZonedDateTimeFilter());
        }
        return entryDate;
    }

    public void setEntryDate(ZonedDateTimeFilter entryDate) {
        this.entryDate = entryDate;
    }

    public StringFilter getTradeCurrencyId() {
        return tradeCurrencyId;
    }

    public Optional<StringFilter> optionalTradeCurrencyId() {
        return Optional.ofNullable(tradeCurrencyId);
    }

    public StringFilter tradeCurrencyId() {
        if (tradeCurrencyId == null) {
            setTradeCurrencyId(new StringFilter());
        }
        return tradeCurrencyId;
    }

    public void setTradeCurrencyId(StringFilter tradeCurrencyId) {
        this.tradeCurrencyId = tradeCurrencyId;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public Optional<StringFilter> optionalAddress1() {
        return Optional.ofNullable(address1);
    }

    public StringFilter address1() {
        if (address1 == null) {
            setAddress1(new StringFilter());
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public Optional<StringFilter> optionalAddress2() {
        return Optional.ofNullable(address2);
    }

    public StringFilter address2() {
        if (address2 == null) {
            setAddress2(new StringFilter());
        }
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getAddress3() {
        return address3;
    }

    public Optional<StringFilter> optionalAddress3() {
        return Optional.ofNullable(address3);
    }

    public StringFilter address3() {
        if (address3 == null) {
            setAddress3(new StringFilter());
        }
        return address3;
    }

    public void setAddress3(StringFilter address3) {
        this.address3 = address3;
    }

    public StringFilter getMailId() {
        return mailId;
    }

    public Optional<StringFilter> optionalMailId() {
        return Optional.ofNullable(mailId);
    }

    public StringFilter mailId() {
        if (mailId == null) {
            setMailId(new StringFilter());
        }
        return mailId;
    }

    public void setMailId(StringFilter mailId) {
        this.mailId = mailId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public Optional<StringFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public StringFilter status() {
        if (status == null) {
            setStatus(new StringFilter());
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getRating() {
        return rating;
    }

    public Optional<StringFilter> optionalRating() {
        return Optional.ofNullable(rating);
    }

    public StringFilter rating() {
        if (rating == null) {
            setRating(new StringFilter());
        }
        return rating;
    }

    public void setRating(StringFilter rating) {
        this.rating = rating;
    }

    public IntegerFilter getIsDeleteFlag() {
        return isDeleteFlag;
    }

    public Optional<IntegerFilter> optionalIsDeleteFlag() {
        return Optional.ofNullable(isDeleteFlag);
    }

    public IntegerFilter isDeleteFlag() {
        if (isDeleteFlag == null) {
            setIsDeleteFlag(new IntegerFilter());
        }
        return isDeleteFlag;
    }

    public void setIsDeleteFlag(IntegerFilter isDeleteFlag) {
        this.isDeleteFlag = isDeleteFlag;
    }

    public StringFilter getRelatedBuyerUid() {
        return relatedBuyerUid;
    }

    public Optional<StringFilter> optionalRelatedBuyerUid() {
        return Optional.ofNullable(relatedBuyerUid);
    }

    public StringFilter relatedBuyerUid() {
        if (relatedBuyerUid == null) {
            setRelatedBuyerUid(new StringFilter());
        }
        return relatedBuyerUid;
    }

    public void setRelatedBuyerUid(StringFilter relatedBuyerUid) {
        this.relatedBuyerUid = relatedBuyerUid;
    }

    public StringFilter getCountry() {
        return country;
    }

    public Optional<StringFilter> optionalCountry() {
        return Optional.ofNullable(country);
    }

    public StringFilter country() {
        if (country == null) {
            setCountry(new StringFilter());
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCountryFlag() {
        return countryFlag;
    }

    public Optional<StringFilter> optionalCountryFlag() {
        return Optional.ofNullable(countryFlag);
    }

    public StringFilter countryFlag() {
        if (countryFlag == null) {
            setCountryFlag(new StringFilter());
        }
        return countryFlag;
    }

    public void setCountryFlag(StringFilter countryFlag) {
        this.countryFlag = countryFlag;
    }

    public LongFilter getBuyerRfqPricesDetailId() {
        return buyerRfqPricesDetailId;
    }

    public Optional<LongFilter> optionalBuyerRfqPricesDetailId() {
        return Optional.ofNullable(buyerRfqPricesDetailId);
    }

    public LongFilter buyerRfqPricesDetailId() {
        if (buyerRfqPricesDetailId == null) {
            setBuyerRfqPricesDetailId(new LongFilter());
        }
        return buyerRfqPricesDetailId;
    }

    public void setBuyerRfqPricesDetailId(LongFilter buyerRfqPricesDetailId) {
        this.buyerRfqPricesDetailId = buyerRfqPricesDetailId;
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
        final VendorProfileCriteria that = (VendorProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(vendorName, that.vendorName) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(entryDate, that.entryDate) &&
            Objects.equals(tradeCurrencyId, that.tradeCurrencyId) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(address3, that.address3) &&
            Objects.equals(mailId, that.mailId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(isDeleteFlag, that.isDeleteFlag) &&
            Objects.equals(relatedBuyerUid, that.relatedBuyerUid) &&
            Objects.equals(country, that.country) &&
            Objects.equals(countryFlag, that.countryFlag) &&
            Objects.equals(buyerRfqPricesDetailId, that.buyerRfqPricesDetailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            srNo,
            uid,
            vendorId,
            vendorName,
            contact,
            entryDate,
            tradeCurrencyId,
            address1,
            address2,
            address3,
            mailId,
            status,
            rating,
            isDeleteFlag,
            relatedBuyerUid,
            country,
            countryFlag,
            buyerRfqPricesDetailId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorProfileCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalVendorId().map(f -> "vendorId=" + f + ", ").orElse("") +
            optionalVendorName().map(f -> "vendorName=" + f + ", ").orElse("") +
            optionalContact().map(f -> "contact=" + f + ", ").orElse("") +
            optionalEntryDate().map(f -> "entryDate=" + f + ", ").orElse("") +
            optionalTradeCurrencyId().map(f -> "tradeCurrencyId=" + f + ", ").orElse("") +
            optionalAddress1().map(f -> "address1=" + f + ", ").orElse("") +
            optionalAddress2().map(f -> "address2=" + f + ", ").orElse("") +
            optionalAddress3().map(f -> "address3=" + f + ", ").orElse("") +
            optionalMailId().map(f -> "mailId=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalRating().map(f -> "rating=" + f + ", ").orElse("") +
            optionalIsDeleteFlag().map(f -> "isDeleteFlag=" + f + ", ").orElse("") +
            optionalRelatedBuyerUid().map(f -> "relatedBuyerUid=" + f + ", ").orElse("") +
            optionalCountry().map(f -> "country=" + f + ", ").orElse("") +
            optionalCountryFlag().map(f -> "countryFlag=" + f + ", ").orElse("") +
            optionalBuyerRfqPricesDetailId().map(f -> "buyerRfqPricesDetailId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
