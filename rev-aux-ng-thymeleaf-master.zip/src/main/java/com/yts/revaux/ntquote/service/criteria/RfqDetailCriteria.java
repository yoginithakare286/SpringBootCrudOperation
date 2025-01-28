package com.yts.revaux.ntquote.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.yts.revaux.ntquote.domain.RfqDetail} entity. This class is used
 * in {@link com.yts.revaux.ntquote.web.rest.RfqDetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rfq-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RfqDetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter srNo;

    private UUIDFilter uid;

    private StringFilter rfqId;

    private LocalDateFilter orderDate;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter itemDescription;

    private StringFilter rfqStatus;

    private StringFilter rfqType;

    private StringFilter customer;

    private LocalDateFilter rfqReceivedDate;

    private LocalDateFilter quoteDueDate;

    private StringFilter part;

    private StringFilter buyer;

    private StringFilter expectedLaunch;

    private StringFilter requestor;

    private StringFilter raStatus;

    private IntegerFilter isDelete;

    private StringFilter customerFeedback;

    private LongFilter buyerRfqPricesDetailId;

    private Boolean distinct;

    public RfqDetailCriteria() {}

    public RfqDetailCriteria(RfqDetailCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.srNo = other.optionalSrNo().map(IntegerFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(UUIDFilter::copy).orElse(null);
        this.rfqId = other.optionalRfqId().map(StringFilter::copy).orElse(null);
        this.orderDate = other.optionalOrderDate().map(LocalDateFilter::copy).orElse(null);
        this.startDate = other.optionalStartDate().map(LocalDateFilter::copy).orElse(null);
        this.endDate = other.optionalEndDate().map(LocalDateFilter::copy).orElse(null);
        this.itemDescription = other.optionalItemDescription().map(StringFilter::copy).orElse(null);
        this.rfqStatus = other.optionalRfqStatus().map(StringFilter::copy).orElse(null);
        this.rfqType = other.optionalRfqType().map(StringFilter::copy).orElse(null);
        this.customer = other.optionalCustomer().map(StringFilter::copy).orElse(null);
        this.rfqReceivedDate = other.optionalRfqReceivedDate().map(LocalDateFilter::copy).orElse(null);
        this.quoteDueDate = other.optionalQuoteDueDate().map(LocalDateFilter::copy).orElse(null);
        this.part = other.optionalPart().map(StringFilter::copy).orElse(null);
        this.buyer = other.optionalBuyer().map(StringFilter::copy).orElse(null);
        this.expectedLaunch = other.optionalExpectedLaunch().map(StringFilter::copy).orElse(null);
        this.requestor = other.optionalRequestor().map(StringFilter::copy).orElse(null);
        this.raStatus = other.optionalRaStatus().map(StringFilter::copy).orElse(null);
        this.isDelete = other.optionalIsDelete().map(IntegerFilter::copy).orElse(null);
        this.customerFeedback = other.optionalCustomerFeedback().map(StringFilter::copy).orElse(null);
        this.buyerRfqPricesDetailId = other.optionalBuyerRfqPricesDetailId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public RfqDetailCriteria copy() {
        return new RfqDetailCriteria(this);
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

    public StringFilter getRfqId() {
        return rfqId;
    }

    public Optional<StringFilter> optionalRfqId() {
        return Optional.ofNullable(rfqId);
    }

    public StringFilter rfqId() {
        if (rfqId == null) {
            setRfqId(new StringFilter());
        }
        return rfqId;
    }

    public void setRfqId(StringFilter rfqId) {
        this.rfqId = rfqId;
    }

    public LocalDateFilter getOrderDate() {
        return orderDate;
    }

    public Optional<LocalDateFilter> optionalOrderDate() {
        return Optional.ofNullable(orderDate);
    }

    public LocalDateFilter orderDate() {
        if (orderDate == null) {
            setOrderDate(new LocalDateFilter());
        }
        return orderDate;
    }

    public void setOrderDate(LocalDateFilter orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public Optional<LocalDateFilter> optionalStartDate() {
        return Optional.ofNullable(startDate);
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            setStartDate(new LocalDateFilter());
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public Optional<LocalDateFilter> optionalEndDate() {
        return Optional.ofNullable(endDate);
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            setEndDate(new LocalDateFilter());
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getItemDescription() {
        return itemDescription;
    }

    public Optional<StringFilter> optionalItemDescription() {
        return Optional.ofNullable(itemDescription);
    }

    public StringFilter itemDescription() {
        if (itemDescription == null) {
            setItemDescription(new StringFilter());
        }
        return itemDescription;
    }

    public void setItemDescription(StringFilter itemDescription) {
        this.itemDescription = itemDescription;
    }

    public StringFilter getRfqStatus() {
        return rfqStatus;
    }

    public Optional<StringFilter> optionalRfqStatus() {
        return Optional.ofNullable(rfqStatus);
    }

    public StringFilter rfqStatus() {
        if (rfqStatus == null) {
            setRfqStatus(new StringFilter());
        }
        return rfqStatus;
    }

    public void setRfqStatus(StringFilter rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public StringFilter getRfqType() {
        return rfqType;
    }

    public Optional<StringFilter> optionalRfqType() {
        return Optional.ofNullable(rfqType);
    }

    public StringFilter rfqType() {
        if (rfqType == null) {
            setRfqType(new StringFilter());
        }
        return rfqType;
    }

    public void setRfqType(StringFilter rfqType) {
        this.rfqType = rfqType;
    }

    public StringFilter getCustomer() {
        return customer;
    }

    public Optional<StringFilter> optionalCustomer() {
        return Optional.ofNullable(customer);
    }

    public StringFilter customer() {
        if (customer == null) {
            setCustomer(new StringFilter());
        }
        return customer;
    }

    public void setCustomer(StringFilter customer) {
        this.customer = customer;
    }

    public LocalDateFilter getRfqReceivedDate() {
        return rfqReceivedDate;
    }

    public Optional<LocalDateFilter> optionalRfqReceivedDate() {
        return Optional.ofNullable(rfqReceivedDate);
    }

    public LocalDateFilter rfqReceivedDate() {
        if (rfqReceivedDate == null) {
            setRfqReceivedDate(new LocalDateFilter());
        }
        return rfqReceivedDate;
    }

    public void setRfqReceivedDate(LocalDateFilter rfqReceivedDate) {
        this.rfqReceivedDate = rfqReceivedDate;
    }

    public LocalDateFilter getQuoteDueDate() {
        return quoteDueDate;
    }

    public Optional<LocalDateFilter> optionalQuoteDueDate() {
        return Optional.ofNullable(quoteDueDate);
    }

    public LocalDateFilter quoteDueDate() {
        if (quoteDueDate == null) {
            setQuoteDueDate(new LocalDateFilter());
        }
        return quoteDueDate;
    }

    public void setQuoteDueDate(LocalDateFilter quoteDueDate) {
        this.quoteDueDate = quoteDueDate;
    }

    public StringFilter getPart() {
        return part;
    }

    public Optional<StringFilter> optionalPart() {
        return Optional.ofNullable(part);
    }

    public StringFilter part() {
        if (part == null) {
            setPart(new StringFilter());
        }
        return part;
    }

    public void setPart(StringFilter part) {
        this.part = part;
    }

    public StringFilter getBuyer() {
        return buyer;
    }

    public Optional<StringFilter> optionalBuyer() {
        return Optional.ofNullable(buyer);
    }

    public StringFilter buyer() {
        if (buyer == null) {
            setBuyer(new StringFilter());
        }
        return buyer;
    }

    public void setBuyer(StringFilter buyer) {
        this.buyer = buyer;
    }

    public StringFilter getExpectedLaunch() {
        return expectedLaunch;
    }

    public Optional<StringFilter> optionalExpectedLaunch() {
        return Optional.ofNullable(expectedLaunch);
    }

    public StringFilter expectedLaunch() {
        if (expectedLaunch == null) {
            setExpectedLaunch(new StringFilter());
        }
        return expectedLaunch;
    }

    public void setExpectedLaunch(StringFilter expectedLaunch) {
        this.expectedLaunch = expectedLaunch;
    }

    public StringFilter getRequestor() {
        return requestor;
    }

    public Optional<StringFilter> optionalRequestor() {
        return Optional.ofNullable(requestor);
    }

    public StringFilter requestor() {
        if (requestor == null) {
            setRequestor(new StringFilter());
        }
        return requestor;
    }

    public void setRequestor(StringFilter requestor) {
        this.requestor = requestor;
    }

    public StringFilter getRaStatus() {
        return raStatus;
    }

    public Optional<StringFilter> optionalRaStatus() {
        return Optional.ofNullable(raStatus);
    }

    public StringFilter raStatus() {
        if (raStatus == null) {
            setRaStatus(new StringFilter());
        }
        return raStatus;
    }

    public void setRaStatus(StringFilter raStatus) {
        this.raStatus = raStatus;
    }

    public IntegerFilter getIsDelete() {
        return isDelete;
    }

    public Optional<IntegerFilter> optionalIsDelete() {
        return Optional.ofNullable(isDelete);
    }

    public IntegerFilter isDelete() {
        if (isDelete == null) {
            setIsDelete(new IntegerFilter());
        }
        return isDelete;
    }

    public void setIsDelete(IntegerFilter isDelete) {
        this.isDelete = isDelete;
    }

    public StringFilter getCustomerFeedback() {
        return customerFeedback;
    }

    public Optional<StringFilter> optionalCustomerFeedback() {
        return Optional.ofNullable(customerFeedback);
    }

    public StringFilter customerFeedback() {
        if (customerFeedback == null) {
            setCustomerFeedback(new StringFilter());
        }
        return customerFeedback;
    }

    public void setCustomerFeedback(StringFilter customerFeedback) {
        this.customerFeedback = customerFeedback;
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
        final RfqDetailCriteria that = (RfqDetailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(srNo, that.srNo) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(rfqId, that.rfqId) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(itemDescription, that.itemDescription) &&
            Objects.equals(rfqStatus, that.rfqStatus) &&
            Objects.equals(rfqType, that.rfqType) &&
            Objects.equals(customer, that.customer) &&
            Objects.equals(rfqReceivedDate, that.rfqReceivedDate) &&
            Objects.equals(quoteDueDate, that.quoteDueDate) &&
            Objects.equals(part, that.part) &&
            Objects.equals(buyer, that.buyer) &&
            Objects.equals(expectedLaunch, that.expectedLaunch) &&
            Objects.equals(requestor, that.requestor) &&
            Objects.equals(raStatus, that.raStatus) &&
            Objects.equals(isDelete, that.isDelete) &&
            Objects.equals(customerFeedback, that.customerFeedback) &&
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
            rfqId,
            orderDate,
            startDate,
            endDate,
            itemDescription,
            rfqStatus,
            rfqType,
            customer,
            rfqReceivedDate,
            quoteDueDate,
            part,
            buyer,
            expectedLaunch,
            requestor,
            raStatus,
            isDelete,
            customerFeedback,
            buyerRfqPricesDetailId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RfqDetailCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSrNo().map(f -> "srNo=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalRfqId().map(f -> "rfqId=" + f + ", ").orElse("") +
            optionalOrderDate().map(f -> "orderDate=" + f + ", ").orElse("") +
            optionalStartDate().map(f -> "startDate=" + f + ", ").orElse("") +
            optionalEndDate().map(f -> "endDate=" + f + ", ").orElse("") +
            optionalItemDescription().map(f -> "itemDescription=" + f + ", ").orElse("") +
            optionalRfqStatus().map(f -> "rfqStatus=" + f + ", ").orElse("") +
            optionalRfqType().map(f -> "rfqType=" + f + ", ").orElse("") +
            optionalCustomer().map(f -> "customer=" + f + ", ").orElse("") +
            optionalRfqReceivedDate().map(f -> "rfqReceivedDate=" + f + ", ").orElse("") +
            optionalQuoteDueDate().map(f -> "quoteDueDate=" + f + ", ").orElse("") +
            optionalPart().map(f -> "part=" + f + ", ").orElse("") +
            optionalBuyer().map(f -> "buyer=" + f + ", ").orElse("") +
            optionalExpectedLaunch().map(f -> "expectedLaunch=" + f + ", ").orElse("") +
            optionalRequestor().map(f -> "requestor=" + f + ", ").orElse("") +
            optionalRaStatus().map(f -> "raStatus=" + f + ", ").orElse("") +
            optionalIsDelete().map(f -> "isDelete=" + f + ", ").orElse("") +
            optionalCustomerFeedback().map(f -> "customerFeedback=" + f + ", ").orElse("") +
            optionalBuyerRfqPricesDetailId().map(f -> "buyerRfqPricesDetailId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
