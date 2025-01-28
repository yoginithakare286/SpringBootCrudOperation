package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.NtQuote} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.NtQuoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nt-quotes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter quoteKey;

    private StringFilter salesPerson;

    private StringFilter customerName;

    private StringFilter quoteNumber;

    private StringFilter status;

    private StringFilter moldNumber;

    private StringFilter partNumber;

    private LocalDateFilter dueDate;

    private StringFilter moldManual;

    private StringFilter customerPo;

    private StringFilter vendorQuote;

    private StringFilter vendorPo;

    private StringFilter cadFile;

    private IntegerFilter quotedPrice;

    private StringFilter deliveryTime;

    private LocalDateFilter quoteDate;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter updatedBy;

    private InstantFilter updatedDate;

    private LongFilter projectConsiderationsId;

    private LongFilter contractReviewInformationId;

    private LongFilter customerInputOutputVersionId;

    private LongFilter partInformationMasterId;

    private LongFilter commentsId;

    private LongFilter termsConditionsId;

    private LongFilter projectApprovalId;

    private LongFilter partInformationVersionId;

    private LongFilter customerPoId;

    private LongFilter vendorQuoteId;

    private LongFilter vendorPoId;

    private LongFilter rfqDetailId;

    private LongFilter ntQuoteProjectApprovalId;

    private Boolean distinct;

    public NtQuoteCriteria() {}

    public NtQuoteCriteria(NtQuoteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.quoteKey = other.optionalQuoteKey().map(StringFilter::copy).orElse(null);
        this.salesPerson = other.optionalSalesPerson().map(StringFilter::copy).orElse(null);
        this.customerName = other.optionalCustomerName().map(StringFilter::copy).orElse(null);
        this.quoteNumber = other.optionalQuoteNumber().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.moldNumber = other.optionalMoldNumber().map(StringFilter::copy).orElse(null);
        this.partNumber = other.optionalPartNumber().map(StringFilter::copy).orElse(null);
        this.dueDate = other.optionalDueDate().map(LocalDateFilter::copy).orElse(null);
        this.moldManual = other.optionalMoldManual().map(StringFilter::copy).orElse(null);
        this.customerPo = other.optionalCustomerPo().map(StringFilter::copy).orElse(null);
        this.vendorQuote = other.optionalVendorQuote().map(StringFilter::copy).orElse(null);
        this.vendorPo = other.optionalVendorPo().map(StringFilter::copy).orElse(null);
        this.cadFile = other.optionalCadFile().map(StringFilter::copy).orElse(null);
        this.quotedPrice = other.optionalQuotedPrice().map(IntegerFilter::copy).orElse(null);
        this.deliveryTime = other.optionalDeliveryTime().map(StringFilter::copy).orElse(null);
        this.quoteDate = other.optionalQuoteDate().map(LocalDateFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedDate = other.optionalUpdatedDate().map(InstantFilter::copy).orElse(null);
        this.projectConsiderationsId = other.optionalProjectConsiderationsId().map(LongFilter::copy).orElse(null);
        this.contractReviewInformationId = other.optionalContractReviewInformationId().map(LongFilter::copy).orElse(null);
        this.customerInputOutputVersionId = other.optionalCustomerInputOutputVersionId().map(LongFilter::copy).orElse(null);
        this.partInformationMasterId = other.optionalPartInformationMasterId().map(LongFilter::copy).orElse(null);
        this.commentsId = other.optionalCommentsId().map(LongFilter::copy).orElse(null);
        this.termsConditionsId = other.optionalTermsConditionsId().map(LongFilter::copy).orElse(null);
        this.projectApprovalId = other.optionalProjectApprovalId().map(LongFilter::copy).orElse(null);
        this.partInformationVersionId = other.optionalPartInformationVersionId().map(LongFilter::copy).orElse(null);
        this.customerPoId = other.optionalCustomerPoId().map(LongFilter::copy).orElse(null);
        this.vendorQuoteId = other.optionalVendorQuoteId().map(LongFilter::copy).orElse(null);
        this.vendorPoId = other.optionalVendorPoId().map(LongFilter::copy).orElse(null);
        this.rfqDetailId = other.optionalRfqDetailId().map(LongFilter::copy).orElse(null);
        this.ntQuoteProjectApprovalId = other.optionalNtQuoteProjectApprovalId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NtQuoteCriteria copy() {
        return new NtQuoteCriteria(this);
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

    public StringFilter getQuoteKey() {
        return quoteKey;
    }

    public Optional<StringFilter> optionalQuoteKey() {
        return Optional.ofNullable(quoteKey);
    }

    public StringFilter quoteKey() {
        if (quoteKey == null) {
            setQuoteKey(new StringFilter());
        }
        return quoteKey;
    }

    public void setQuoteKey(StringFilter quoteKey) {
        this.quoteKey = quoteKey;
    }

    public StringFilter getSalesPerson() {
        return salesPerson;
    }

    public Optional<StringFilter> optionalSalesPerson() {
        return Optional.ofNullable(salesPerson);
    }

    public StringFilter salesPerson() {
        if (salesPerson == null) {
            setSalesPerson(new StringFilter());
        }
        return salesPerson;
    }

    public void setSalesPerson(StringFilter salesPerson) {
        this.salesPerson = salesPerson;
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

    public StringFilter getQuoteNumber() {
        return quoteNumber;
    }

    public Optional<StringFilter> optionalQuoteNumber() {
        return Optional.ofNullable(quoteNumber);
    }

    public StringFilter quoteNumber() {
        if (quoteNumber == null) {
            setQuoteNumber(new StringFilter());
        }
        return quoteNumber;
    }

    public void setQuoteNumber(StringFilter quoteNumber) {
        this.quoteNumber = quoteNumber;
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

    public StringFilter getMoldNumber() {
        return moldNumber;
    }

    public Optional<StringFilter> optionalMoldNumber() {
        return Optional.ofNullable(moldNumber);
    }

    public StringFilter moldNumber() {
        if (moldNumber == null) {
            setMoldNumber(new StringFilter());
        }
        return moldNumber;
    }

    public void setMoldNumber(StringFilter moldNumber) {
        this.moldNumber = moldNumber;
    }

    public StringFilter getPartNumber() {
        return partNumber;
    }

    public Optional<StringFilter> optionalPartNumber() {
        return Optional.ofNullable(partNumber);
    }

    public StringFilter partNumber() {
        if (partNumber == null) {
            setPartNumber(new StringFilter());
        }
        return partNumber;
    }

    public void setPartNumber(StringFilter partNumber) {
        this.partNumber = partNumber;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public Optional<LocalDateFilter> optionalDueDate() {
        return Optional.ofNullable(dueDate);
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            setDueDate(new LocalDateFilter());
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public StringFilter getMoldManual() {
        return moldManual;
    }

    public Optional<StringFilter> optionalMoldManual() {
        return Optional.ofNullable(moldManual);
    }

    public StringFilter moldManual() {
        if (moldManual == null) {
            setMoldManual(new StringFilter());
        }
        return moldManual;
    }

    public void setMoldManual(StringFilter moldManual) {
        this.moldManual = moldManual;
    }

    public StringFilter getCustomerPo() {
        return customerPo;
    }

    public Optional<StringFilter> optionalCustomerPo() {
        return Optional.ofNullable(customerPo);
    }

    public StringFilter customerPo() {
        if (customerPo == null) {
            setCustomerPo(new StringFilter());
        }
        return customerPo;
    }

    public void setCustomerPo(StringFilter customerPo) {
        this.customerPo = customerPo;
    }

    public StringFilter getVendorQuote() {
        return vendorQuote;
    }

    public Optional<StringFilter> optionalVendorQuote() {
        return Optional.ofNullable(vendorQuote);
    }

    public StringFilter vendorQuote() {
        if (vendorQuote == null) {
            setVendorQuote(new StringFilter());
        }
        return vendorQuote;
    }

    public void setVendorQuote(StringFilter vendorQuote) {
        this.vendorQuote = vendorQuote;
    }

    public StringFilter getVendorPo() {
        return vendorPo;
    }

    public Optional<StringFilter> optionalVendorPo() {
        return Optional.ofNullable(vendorPo);
    }

    public StringFilter vendorPo() {
        if (vendorPo == null) {
            setVendorPo(new StringFilter());
        }
        return vendorPo;
    }

    public void setVendorPo(StringFilter vendorPo) {
        this.vendorPo = vendorPo;
    }

    public StringFilter getCadFile() {
        return cadFile;
    }

    public Optional<StringFilter> optionalCadFile() {
        return Optional.ofNullable(cadFile);
    }

    public StringFilter cadFile() {
        if (cadFile == null) {
            setCadFile(new StringFilter());
        }
        return cadFile;
    }

    public void setCadFile(StringFilter cadFile) {
        this.cadFile = cadFile;
    }

    public IntegerFilter getQuotedPrice() {
        return quotedPrice;
    }

    public Optional<IntegerFilter> optionalQuotedPrice() {
        return Optional.ofNullable(quotedPrice);
    }

    public IntegerFilter quotedPrice() {
        if (quotedPrice == null) {
            setQuotedPrice(new IntegerFilter());
        }
        return quotedPrice;
    }

    public void setQuotedPrice(IntegerFilter quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public StringFilter getDeliveryTime() {
        return deliveryTime;
    }

    public Optional<StringFilter> optionalDeliveryTime() {
        return Optional.ofNullable(deliveryTime);
    }

    public StringFilter deliveryTime() {
        if (deliveryTime == null) {
            setDeliveryTime(new StringFilter());
        }
        return deliveryTime;
    }

    public void setDeliveryTime(StringFilter deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateFilter getQuoteDate() {
        return quoteDate;
    }

    public Optional<LocalDateFilter> optionalQuoteDate() {
        return Optional.ofNullable(quoteDate);
    }

    public LocalDateFilter quoteDate() {
        if (quoteDate == null) {
            setQuoteDate(new LocalDateFilter());
        }
        return quoteDate;
    }

    public void setQuoteDate(LocalDateFilter quoteDate) {
        this.quoteDate = quoteDate;
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

    public LongFilter getProjectConsiderationsId() {
        return projectConsiderationsId;
    }

    public Optional<LongFilter> optionalProjectConsiderationsId() {
        return Optional.ofNullable(projectConsiderationsId);
    }

    public LongFilter projectConsiderationsId() {
        if (projectConsiderationsId == null) {
            setProjectConsiderationsId(new LongFilter());
        }
        return projectConsiderationsId;
    }

    public void setProjectConsiderationsId(LongFilter projectConsiderationsId) {
        this.projectConsiderationsId = projectConsiderationsId;
    }

    public LongFilter getContractReviewInformationId() {
        return contractReviewInformationId;
    }

    public Optional<LongFilter> optionalContractReviewInformationId() {
        return Optional.ofNullable(contractReviewInformationId);
    }

    public LongFilter contractReviewInformationId() {
        if (contractReviewInformationId == null) {
            setContractReviewInformationId(new LongFilter());
        }
        return contractReviewInformationId;
    }

    public void setContractReviewInformationId(LongFilter contractReviewInformationId) {
        this.contractReviewInformationId = contractReviewInformationId;
    }

    public LongFilter getCustomerInputOutputVersionId() {
        return customerInputOutputVersionId;
    }

    public Optional<LongFilter> optionalCustomerInputOutputVersionId() {
        return Optional.ofNullable(customerInputOutputVersionId);
    }

    public LongFilter customerInputOutputVersionId() {
        if (customerInputOutputVersionId == null) {
            setCustomerInputOutputVersionId(new LongFilter());
        }
        return customerInputOutputVersionId;
    }

    public void setCustomerInputOutputVersionId(LongFilter customerInputOutputVersionId) {
        this.customerInputOutputVersionId = customerInputOutputVersionId;
    }

    public LongFilter getPartInformationMasterId() {
        return partInformationMasterId;
    }

    public Optional<LongFilter> optionalPartInformationMasterId() {
        return Optional.ofNullable(partInformationMasterId);
    }

    public LongFilter partInformationMasterId() {
        if (partInformationMasterId == null) {
            setPartInformationMasterId(new LongFilter());
        }
        return partInformationMasterId;
    }

    public void setPartInformationMasterId(LongFilter partInformationMasterId) {
        this.partInformationMasterId = partInformationMasterId;
    }

    public LongFilter getCommentsId() {
        return commentsId;
    }

    public Optional<LongFilter> optionalCommentsId() {
        return Optional.ofNullable(commentsId);
    }

    public LongFilter commentsId() {
        if (commentsId == null) {
            setCommentsId(new LongFilter());
        }
        return commentsId;
    }

    public void setCommentsId(LongFilter commentsId) {
        this.commentsId = commentsId;
    }

    public LongFilter getTermsConditionsId() {
        return termsConditionsId;
    }

    public Optional<LongFilter> optionalTermsConditionsId() {
        return Optional.ofNullable(termsConditionsId);
    }

    public LongFilter termsConditionsId() {
        if (termsConditionsId == null) {
            setTermsConditionsId(new LongFilter());
        }
        return termsConditionsId;
    }

    public void setTermsConditionsId(LongFilter termsConditionsId) {
        this.termsConditionsId = termsConditionsId;
    }

    public LongFilter getProjectApprovalId() {
        return projectApprovalId;
    }

    public Optional<LongFilter> optionalProjectApprovalId() {
        return Optional.ofNullable(projectApprovalId);
    }

    public LongFilter projectApprovalId() {
        if (projectApprovalId == null) {
            setProjectApprovalId(new LongFilter());
        }
        return projectApprovalId;
    }

    public void setProjectApprovalId(LongFilter projectApprovalId) {
        this.projectApprovalId = projectApprovalId;
    }

    public LongFilter getPartInformationVersionId() {
        return partInformationVersionId;
    }

    public Optional<LongFilter> optionalPartInformationVersionId() {
        return Optional.ofNullable(partInformationVersionId);
    }

    public LongFilter partInformationVersionId() {
        if (partInformationVersionId == null) {
            setPartInformationVersionId(new LongFilter());
        }
        return partInformationVersionId;
    }

    public void setPartInformationVersionId(LongFilter partInformationVersionId) {
        this.partInformationVersionId = partInformationVersionId;
    }

    public LongFilter getCustomerPoId() {
        return customerPoId;
    }

    public Optional<LongFilter> optionalCustomerPoId() {
        return Optional.ofNullable(customerPoId);
    }

    public LongFilter customerPoId() {
        if (customerPoId == null) {
            setCustomerPoId(new LongFilter());
        }
        return customerPoId;
    }

    public void setCustomerPoId(LongFilter customerPoId) {
        this.customerPoId = customerPoId;
    }

    public LongFilter getVendorQuoteId() {
        return vendorQuoteId;
    }

    public Optional<LongFilter> optionalVendorQuoteId() {
        return Optional.ofNullable(vendorQuoteId);
    }

    public LongFilter vendorQuoteId() {
        if (vendorQuoteId == null) {
            setVendorQuoteId(new LongFilter());
        }
        return vendorQuoteId;
    }

    public void setVendorQuoteId(LongFilter vendorQuoteId) {
        this.vendorQuoteId = vendorQuoteId;
    }

    public LongFilter getVendorPoId() {
        return vendorPoId;
    }

    public Optional<LongFilter> optionalVendorPoId() {
        return Optional.ofNullable(vendorPoId);
    }

    public LongFilter vendorPoId() {
        if (vendorPoId == null) {
            setVendorPoId(new LongFilter());
        }
        return vendorPoId;
    }

    public void setVendorPoId(LongFilter vendorPoId) {
        this.vendorPoId = vendorPoId;
    }

    public LongFilter getRfqDetailId() {
        return rfqDetailId;
    }

    public Optional<LongFilter> optionalRfqDetailId() {
        return Optional.ofNullable(rfqDetailId);
    }

    public LongFilter rfqDetailId() {
        if (rfqDetailId == null) {
            setRfqDetailId(new LongFilter());
        }
        return rfqDetailId;
    }

    public void setRfqDetailId(LongFilter rfqDetailId) {
        this.rfqDetailId = rfqDetailId;
    }

    public LongFilter getNtQuoteProjectApprovalId() {
        return ntQuoteProjectApprovalId;
    }

    public Optional<LongFilter> optionalNtQuoteProjectApprovalId() {
        return Optional.ofNullable(ntQuoteProjectApprovalId);
    }

    public LongFilter ntQuoteProjectApprovalId() {
        if (ntQuoteProjectApprovalId == null) {
            setNtQuoteProjectApprovalId(new LongFilter());
        }
        return ntQuoteProjectApprovalId;
    }

    public void setNtQuoteProjectApprovalId(LongFilter ntQuoteProjectApprovalId) {
        this.ntQuoteProjectApprovalId = ntQuoteProjectApprovalId;
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
        final NtQuoteCriteria that = (NtQuoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(quoteKey, that.quoteKey) &&
            Objects.equals(salesPerson, that.salesPerson) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(quoteNumber, that.quoteNumber) &&
            Objects.equals(status, that.status) &&
            Objects.equals(moldNumber, that.moldNumber) &&
            Objects.equals(partNumber, that.partNumber) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(moldManual, that.moldManual) &&
            Objects.equals(customerPo, that.customerPo) &&
            Objects.equals(vendorQuote, that.vendorQuote) &&
            Objects.equals(vendorPo, that.vendorPo) &&
            Objects.equals(cadFile, that.cadFile) &&
            Objects.equals(quotedPrice, that.quotedPrice) &&
            Objects.equals(deliveryTime, that.deliveryTime) &&
            Objects.equals(quoteDate, that.quoteDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(projectConsiderationsId, that.projectConsiderationsId) &&
            Objects.equals(contractReviewInformationId, that.contractReviewInformationId) &&
            Objects.equals(customerInputOutputVersionId, that.customerInputOutputVersionId) &&
            Objects.equals(partInformationMasterId, that.partInformationMasterId) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(termsConditionsId, that.termsConditionsId) &&
            Objects.equals(projectApprovalId, that.projectApprovalId) &&
            Objects.equals(partInformationVersionId, that.partInformationVersionId) &&
            Objects.equals(customerPoId, that.customerPoId) &&
            Objects.equals(vendorQuoteId, that.vendorQuoteId) &&
            Objects.equals(vendorPoId, that.vendorPoId) &&
            Objects.equals(rfqDetailId, that.rfqDetailId) &&
            Objects.equals(ntQuoteProjectApprovalId, that.ntQuoteProjectApprovalId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            srNo,
            uid,
            quoteKey,
            salesPerson,
            customerName,
            quoteNumber,
            status,
            moldNumber,
            partNumber,
            dueDate,
            moldManual,
            customerPo,
            vendorQuote,
            vendorPo,
            cadFile,
            quotedPrice,
            deliveryTime,
            quoteDate,
            createdBy,
            createdDate,
            updatedBy,
            updatedDate,
            projectConsiderationsId,
            contractReviewInformationId,
            customerInputOutputVersionId,
            partInformationMasterId,
            commentsId,
            termsConditionsId,
            projectApprovalId,
            partInformationVersionId,
            customerPoId,
            vendorQuoteId,
            vendorPoId,
            rfqDetailId,
            ntQuoteProjectApprovalId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuoteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalQuoteKey().map(f -> "quoteKey=" + f + ", ").orElse("") +
            optionalSalesPerson().map(f -> "salesPerson=" + f + ", ").orElse("") +
            optionalCustomerName().map(f -> "customerName=" + f + ", ").orElse("") +
            optionalQuoteNumber().map(f -> "quoteNumber=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalMoldNumber().map(f -> "moldNumber=" + f + ", ").orElse("") +
            optionalPartNumber().map(f -> "partNumber=" + f + ", ").orElse("") +
            optionalDueDate().map(f -> "dueDate=" + f + ", ").orElse("") +
            optionalMoldManual().map(f -> "moldManual=" + f + ", ").orElse("") +
            optionalCustomerPo().map(f -> "customerPo=" + f + ", ").orElse("") +
            optionalVendorQuote().map(f -> "vendorQuote=" + f + ", ").orElse("") +
            optionalVendorPo().map(f -> "vendorPo=" + f + ", ").orElse("") +
            optionalCadFile().map(f -> "cadFile=" + f + ", ").orElse("") +
            optionalQuotedPrice().map(f -> "quotedPrice=" + f + ", ").orElse("") +
            optionalDeliveryTime().map(f -> "deliveryTime=" + f + ", ").orElse("") +
            optionalQuoteDate().map(f -> "quoteDate=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedDate().map(f -> "updatedDate=" + f + ", ").orElse("") +
            optionalProjectConsiderationsId().map(f -> "projectConsiderationsId=" + f + ", ").orElse("") +
            optionalContractReviewInformationId().map(f -> "contractReviewInformationId=" + f + ", ").orElse("") +
            optionalCustomerInputOutputVersionId().map(f -> "customerInputOutputVersionId=" + f + ", ").orElse("") +
            optionalPartInformationMasterId().map(f -> "partInformationMasterId=" + f + ", ").orElse("") +
            optionalCommentsId().map(f -> "commentsId=" + f + ", ").orElse("") +
            optionalTermsConditionsId().map(f -> "termsConditionsId=" + f + ", ").orElse("") +
            optionalProjectApprovalId().map(f -> "projectApprovalId=" + f + ", ").orElse("") +
            optionalPartInformationVersionId().map(f -> "partInformationVersionId=" + f + ", ").orElse("") +
            optionalCustomerPoId().map(f -> "customerPoId=" + f + ", ").orElse("") +
            optionalVendorQuoteId().map(f -> "vendorQuoteId=" + f + ", ").orElse("") +
            optionalVendorPoId().map(f -> "vendorPoId=" + f + ", ").orElse("") +
            optionalRfqDetailId().map(f -> "rfqDetailId=" + f + ", ").orElse("") +
            optionalNtQuoteProjectApprovalId().map(f -> "ntQuoteProjectApprovalId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
