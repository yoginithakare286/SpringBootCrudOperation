package com.yts.revaux.ntquote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RfqDetail.
 */
@Entity
@Table(name = "rfq_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rfqdetail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RfqDetail implements Serializable {

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

    @Column(name = "rfq_id")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rfqId;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "item_description")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String itemDescription;

    @Column(name = "rfq_status")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rfqStatus;

    @Column(name = "rfq_type")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String rfqType;

    @Column(name = "customer")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String customer;

    @Column(name = "rfq_received_date")
    private LocalDate rfqReceivedDate;

    @Column(name = "quote_due_date")
    private LocalDate quoteDueDate;

    @Column(name = "part")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String part;

    @Column(name = "buyer")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String buyer;

    @Column(name = "expected_launch")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String expectedLaunch;

    @Column(name = "requestor")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String requestor;

    @Column(name = "ra_status")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String raStatus;

    @Column(name = "is_delete")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer isDelete;

    @Column(name = "customer_feedback")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String customerFeedback;

    @JsonIgnoreProperties(value = { "rfqDetail", "vendor" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "rfqDetail")
    @org.springframework.data.annotation.Transient
    private BuyerRfqPricesDetail buyerRfqPricesDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RfqDetail id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public RfqDetail srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public RfqDetail uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getRfqId() {
        return this.rfqId;
    }

    public RfqDetail rfqId(String rfqId) {
        this.setRfqId(rfqId);
        return this;
    }

    public void setRfqId(String rfqId) {
        this.rfqId = rfqId;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public RfqDetail orderDate(LocalDate orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public RfqDetail startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public RfqDetail endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public RfqDetail itemDescription(String itemDescription) {
        this.setItemDescription(itemDescription);
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getRfqStatus() {
        return this.rfqStatus;
    }

    public RfqDetail rfqStatus(String rfqStatus) {
        this.setRfqStatus(rfqStatus);
        return this;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public String getRfqType() {
        return this.rfqType;
    }

    public RfqDetail rfqType(String rfqType) {
        this.setRfqType(rfqType);
        return this;
    }

    public void setRfqType(String rfqType) {
        this.rfqType = rfqType;
    }

    public String getCustomer() {
        return this.customer;
    }

    public RfqDetail customer(String customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getRfqReceivedDate() {
        return this.rfqReceivedDate;
    }

    public RfqDetail rfqReceivedDate(LocalDate rfqReceivedDate) {
        this.setRfqReceivedDate(rfqReceivedDate);
        return this;
    }

    public void setRfqReceivedDate(LocalDate rfqReceivedDate) {
        this.rfqReceivedDate = rfqReceivedDate;
    }

    public LocalDate getQuoteDueDate() {
        return this.quoteDueDate;
    }

    public RfqDetail quoteDueDate(LocalDate quoteDueDate) {
        this.setQuoteDueDate(quoteDueDate);
        return this;
    }

    public void setQuoteDueDate(LocalDate quoteDueDate) {
        this.quoteDueDate = quoteDueDate;
    }

    public String getPart() {
        return this.part;
    }

    public RfqDetail part(String part) {
        this.setPart(part);
        return this;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getBuyer() {
        return this.buyer;
    }

    public RfqDetail buyer(String buyer) {
        this.setBuyer(buyer);
        return this;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getExpectedLaunch() {
        return this.expectedLaunch;
    }

    public RfqDetail expectedLaunch(String expectedLaunch) {
        this.setExpectedLaunch(expectedLaunch);
        return this;
    }

    public void setExpectedLaunch(String expectedLaunch) {
        this.expectedLaunch = expectedLaunch;
    }

    public String getRequestor() {
        return this.requestor;
    }

    public RfqDetail requestor(String requestor) {
        this.setRequestor(requestor);
        return this;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getRaStatus() {
        return this.raStatus;
    }

    public RfqDetail raStatus(String raStatus) {
        this.setRaStatus(raStatus);
        return this;
    }

    public void setRaStatus(String raStatus) {
        this.raStatus = raStatus;
    }

    public Integer getIsDelete() {
        return this.isDelete;
    }

    public RfqDetail isDelete(Integer isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getCustomerFeedback() {
        return this.customerFeedback;
    }

    public RfqDetail customerFeedback(String customerFeedback) {
        this.setCustomerFeedback(customerFeedback);
        return this;
    }

    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    public BuyerRfqPricesDetail getBuyerRfqPricesDetail() {
        return this.buyerRfqPricesDetail;
    }

    public void setBuyerRfqPricesDetail(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        if (this.buyerRfqPricesDetail != null) {
            this.buyerRfqPricesDetail.setRfqDetail(null);
        }
        if (buyerRfqPricesDetail != null) {
            buyerRfqPricesDetail.setRfqDetail(this);
        }
        this.buyerRfqPricesDetail = buyerRfqPricesDetail;
    }

    public RfqDetail buyerRfqPricesDetail(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        this.setBuyerRfqPricesDetail(buyerRfqPricesDetail);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RfqDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((RfqDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RfqDetail{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", rfqId='" + getRfqId() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", itemDescription='" + getItemDescription() + "'" +
            ", rfqStatus='" + getRfqStatus() + "'" +
            ", rfqType='" + getRfqType() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", rfqReceivedDate='" + getRfqReceivedDate() + "'" +
            ", quoteDueDate='" + getQuoteDueDate() + "'" +
            ", part='" + getPart() + "'" +
            ", buyer='" + getBuyer() + "'" +
            ", expectedLaunch='" + getExpectedLaunch() + "'" +
            ", requestor='" + getRequestor() + "'" +
            ", raStatus='" + getRaStatus() + "'" +
            ", isDelete=" + getIsDelete() +
            ", customerFeedback='" + getCustomerFeedback() + "'" +
            "}";
    }
}
