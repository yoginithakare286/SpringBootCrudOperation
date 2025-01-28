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
 * A NtQuote entity.***latest---*****
 */
@Entity
@Table(name = "nt_quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ntquote")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NtQuote implements Serializable {

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
    @Column(name = "quote_key", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String quoteKey;

    @Column(name = "sales_person")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String salesPerson;

    @Column(name = "customer_name")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String customerName;

    @Column(name = "quote_number")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String quoteNumber;

    @Column(name = "status")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String status;

    @Column(name = "mold_number")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String moldNumber;

    @Column(name = "part_number")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String partNumber;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "mold_manual")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String moldManual;

    @Column(name = "customer_po")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String customerPo;

    @Column(name = "vendor_quote")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String vendorQuote;

    @Column(name = "vendor_po")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String vendorPo;

    @Column(name = "cad_file")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String cadFile;

    @Column(name = "quoted_price")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer quotedPrice;

    @Column(name = "delivery_time")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String deliveryTime;

    @Column(name = "quote_date")
    private LocalDate quoteDate;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteProjectConsiderations> projectConsiderations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteContractReviewInformation> contractReviewInformations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteCustomerInputOutputVersion> customerInputOutputVersions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuotePartInformationMaster> partInformationMasters = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteComments> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteTermsConditions> termsConditions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtProjectApproval> projectApprovals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuotePartInformationVersion> partInformationVersions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteCustomerPo> customerPos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteVendorQuote> vendorQuotes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ntQuote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "ntQuote" }, allowSetters = true)
    private Set<NtQuoteVendorPo> vendorPos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "buyerRfqPricesDetail" }, allowSetters = true)
    private RfqDetail rfqDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ntQuotes" }, allowSetters = true)
    private NtQuoteProjectApproval ntQuoteProjectApproval;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NtQuote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSrNo() {
        return this.srNo;
    }

    public NtQuote srNo(Integer srNo) {
        this.setSrNo(srNo);
        return this;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public UUID getUid() {
        return this.uid;
    }

    public NtQuote uid(UUID uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getQuoteKey() {
        return this.quoteKey;
    }

    public NtQuote quoteKey(String quoteKey) {
        this.setQuoteKey(quoteKey);
        return this;
    }

    public void setQuoteKey(String quoteKey) {
        this.quoteKey = quoteKey;
    }

    public String getSalesPerson() {
        return this.salesPerson;
    }

    public NtQuote salesPerson(String salesPerson) {
        this.setSalesPerson(salesPerson);
        return this;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public NtQuote customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getQuoteNumber() {
        return this.quoteNumber;
    }

    public NtQuote quoteNumber(String quoteNumber) {
        this.setQuoteNumber(quoteNumber);
        return this;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public NtQuote status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMoldNumber() {
        return this.moldNumber;
    }

    public NtQuote moldNumber(String moldNumber) {
        this.setMoldNumber(moldNumber);
        return this;
    }

    public void setMoldNumber(String moldNumber) {
        this.moldNumber = moldNumber;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public NtQuote partNumber(String partNumber) {
        this.setPartNumber(partNumber);
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public NtQuote dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getMoldManual() {
        return this.moldManual;
    }

    public NtQuote moldManual(String moldManual) {
        this.setMoldManual(moldManual);
        return this;
    }

    public void setMoldManual(String moldManual) {
        this.moldManual = moldManual;
    }

    public String getCustomerPo() {
        return this.customerPo;
    }

    public NtQuote customerPo(String customerPo) {
        this.setCustomerPo(customerPo);
        return this;
    }

    public void setCustomerPo(String customerPo) {
        this.customerPo = customerPo;
    }

    public String getVendorQuote() {
        return this.vendorQuote;
    }

    public NtQuote vendorQuote(String vendorQuote) {
        this.setVendorQuote(vendorQuote);
        return this;
    }

    public void setVendorQuote(String vendorQuote) {
        this.vendorQuote = vendorQuote;
    }

    public String getVendorPo() {
        return this.vendorPo;
    }

    public NtQuote vendorPo(String vendorPo) {
        this.setVendorPo(vendorPo);
        return this;
    }

    public void setVendorPo(String vendorPo) {
        this.vendorPo = vendorPo;
    }

    public String getCadFile() {
        return this.cadFile;
    }

    public NtQuote cadFile(String cadFile) {
        this.setCadFile(cadFile);
        return this;
    }

    public void setCadFile(String cadFile) {
        this.cadFile = cadFile;
    }

    public Integer getQuotedPrice() {
        return this.quotedPrice;
    }

    public NtQuote quotedPrice(Integer quotedPrice) {
        this.setQuotedPrice(quotedPrice);
        return this;
    }

    public void setQuotedPrice(Integer quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    public NtQuote deliveryTime(String deliveryTime) {
        this.setDeliveryTime(deliveryTime);
        return this;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDate getQuoteDate() {
        return this.quoteDate;
    }

    public NtQuote quoteDate(LocalDate quoteDate) {
        this.setQuoteDate(quoteDate);
        return this;
    }

    public void setQuoteDate(LocalDate quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public NtQuote createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public NtQuote createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public NtQuote updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public NtQuote updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<NtQuoteProjectConsiderations> getProjectConsiderations() {
        return this.projectConsiderations;
    }

    public void setProjectConsiderations(Set<NtQuoteProjectConsiderations> ntQuoteProjectConsiderations) {
        if (this.projectConsiderations != null) {
            this.projectConsiderations.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteProjectConsiderations != null) {
            ntQuoteProjectConsiderations.forEach(i -> i.setNtQuote(this));
        }
        this.projectConsiderations = ntQuoteProjectConsiderations;
    }

    public NtQuote projectConsiderations(Set<NtQuoteProjectConsiderations> ntQuoteProjectConsiderations) {
        this.setProjectConsiderations(ntQuoteProjectConsiderations);
        return this;
    }

    public NtQuote addProjectConsiderations(NtQuoteProjectConsiderations ntQuoteProjectConsiderations) {
        this.projectConsiderations.add(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderations.setNtQuote(this);
        return this;
    }

    public NtQuote removeProjectConsiderations(NtQuoteProjectConsiderations ntQuoteProjectConsiderations) {
        this.projectConsiderations.remove(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderations.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteContractReviewInformation> getContractReviewInformations() {
        return this.contractReviewInformations;
    }

    public void setContractReviewInformations(Set<NtQuoteContractReviewInformation> ntQuoteContractReviewInformations) {
        if (this.contractReviewInformations != null) {
            this.contractReviewInformations.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteContractReviewInformations != null) {
            ntQuoteContractReviewInformations.forEach(i -> i.setNtQuote(this));
        }
        this.contractReviewInformations = ntQuoteContractReviewInformations;
    }

    public NtQuote contractReviewInformations(Set<NtQuoteContractReviewInformation> ntQuoteContractReviewInformations) {
        this.setContractReviewInformations(ntQuoteContractReviewInformations);
        return this;
    }

    public NtQuote addContractReviewInformation(NtQuoteContractReviewInformation ntQuoteContractReviewInformation) {
        this.contractReviewInformations.add(ntQuoteContractReviewInformation);
        ntQuoteContractReviewInformation.setNtQuote(this);
        return this;
    }

    public NtQuote removeContractReviewInformation(NtQuoteContractReviewInformation ntQuoteContractReviewInformation) {
        this.contractReviewInformations.remove(ntQuoteContractReviewInformation);
        ntQuoteContractReviewInformation.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteCustomerInputOutputVersion> getCustomerInputOutputVersions() {
        return this.customerInputOutputVersions;
    }

    public void setCustomerInputOutputVersions(Set<NtQuoteCustomerInputOutputVersion> ntQuoteCustomerInputOutputVersions) {
        if (this.customerInputOutputVersions != null) {
            this.customerInputOutputVersions.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteCustomerInputOutputVersions != null) {
            ntQuoteCustomerInputOutputVersions.forEach(i -> i.setNtQuote(this));
        }
        this.customerInputOutputVersions = ntQuoteCustomerInputOutputVersions;
    }

    public NtQuote customerInputOutputVersions(Set<NtQuoteCustomerInputOutputVersion> ntQuoteCustomerInputOutputVersions) {
        this.setCustomerInputOutputVersions(ntQuoteCustomerInputOutputVersions);
        return this;
    }

    public NtQuote addCustomerInputOutputVersion(NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion) {
        this.customerInputOutputVersions.add(ntQuoteCustomerInputOutputVersion);
        ntQuoteCustomerInputOutputVersion.setNtQuote(this);
        return this;
    }

    public NtQuote removeCustomerInputOutputVersion(NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion) {
        this.customerInputOutputVersions.remove(ntQuoteCustomerInputOutputVersion);
        ntQuoteCustomerInputOutputVersion.setNtQuote(null);
        return this;
    }

    public Set<NtQuotePartInformationMaster> getPartInformationMasters() {
        return this.partInformationMasters;
    }

    public void setPartInformationMasters(Set<NtQuotePartInformationMaster> ntQuotePartInformationMasters) {
        if (this.partInformationMasters != null) {
            this.partInformationMasters.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuotePartInformationMasters != null) {
            ntQuotePartInformationMasters.forEach(i -> i.setNtQuote(this));
        }
        this.partInformationMasters = ntQuotePartInformationMasters;
    }

    public NtQuote partInformationMasters(Set<NtQuotePartInformationMaster> ntQuotePartInformationMasters) {
        this.setPartInformationMasters(ntQuotePartInformationMasters);
        return this;
    }

    public NtQuote addPartInformationMaster(NtQuotePartInformationMaster ntQuotePartInformationMaster) {
        this.partInformationMasters.add(ntQuotePartInformationMaster);
        ntQuotePartInformationMaster.setNtQuote(this);
        return this;
    }

    public NtQuote removePartInformationMaster(NtQuotePartInformationMaster ntQuotePartInformationMaster) {
        this.partInformationMasters.remove(ntQuotePartInformationMaster);
        ntQuotePartInformationMaster.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteComments> getComments() {
        return this.comments;
    }

    public void setComments(Set<NtQuoteComments> ntQuoteComments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteComments != null) {
            ntQuoteComments.forEach(i -> i.setNtQuote(this));
        }
        this.comments = ntQuoteComments;
    }

    public NtQuote comments(Set<NtQuoteComments> ntQuoteComments) {
        this.setComments(ntQuoteComments);
        return this;
    }

    public NtQuote addComments(NtQuoteComments ntQuoteComments) {
        this.comments.add(ntQuoteComments);
        ntQuoteComments.setNtQuote(this);
        return this;
    }

    public NtQuote removeComments(NtQuoteComments ntQuoteComments) {
        this.comments.remove(ntQuoteComments);
        ntQuoteComments.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteTermsConditions> getTermsConditions() {
        return this.termsConditions;
    }

    public void setTermsConditions(Set<NtQuoteTermsConditions> ntQuoteTermsConditions) {
        if (this.termsConditions != null) {
            this.termsConditions.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteTermsConditions != null) {
            ntQuoteTermsConditions.forEach(i -> i.setNtQuote(this));
        }
        this.termsConditions = ntQuoteTermsConditions;
    }

    public NtQuote termsConditions(Set<NtQuoteTermsConditions> ntQuoteTermsConditions) {
        this.setTermsConditions(ntQuoteTermsConditions);
        return this;
    }

    public NtQuote addTermsConditions(NtQuoteTermsConditions ntQuoteTermsConditions) {
        this.termsConditions.add(ntQuoteTermsConditions);
        ntQuoteTermsConditions.setNtQuote(this);
        return this;
    }

    public NtQuote removeTermsConditions(NtQuoteTermsConditions ntQuoteTermsConditions) {
        this.termsConditions.remove(ntQuoteTermsConditions);
        ntQuoteTermsConditions.setNtQuote(null);
        return this;
    }

    public Set<NtProjectApproval> getProjectApprovals() {
        return this.projectApprovals;
    }

    public void setProjectApprovals(Set<NtProjectApproval> ntProjectApprovals) {
        if (this.projectApprovals != null) {
            this.projectApprovals.forEach(i -> i.setNtQuote(null));
        }
        if (ntProjectApprovals != null) {
            ntProjectApprovals.forEach(i -> i.setNtQuote(this));
        }
        this.projectApprovals = ntProjectApprovals;
    }

    public NtQuote projectApprovals(Set<NtProjectApproval> ntProjectApprovals) {
        this.setProjectApprovals(ntProjectApprovals);
        return this;
    }

    public NtQuote addProjectApproval(NtProjectApproval ntProjectApproval) {
        this.projectApprovals.add(ntProjectApproval);
        ntProjectApproval.setNtQuote(this);
        return this;
    }

    public NtQuote removeProjectApproval(NtProjectApproval ntProjectApproval) {
        this.projectApprovals.remove(ntProjectApproval);
        ntProjectApproval.setNtQuote(null);
        return this;
    }

    public Set<NtQuotePartInformationVersion> getPartInformationVersions() {
        return this.partInformationVersions;
    }

    public void setPartInformationVersions(Set<NtQuotePartInformationVersion> ntQuotePartInformationVersions) {
        if (this.partInformationVersions != null) {
            this.partInformationVersions.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuotePartInformationVersions != null) {
            ntQuotePartInformationVersions.forEach(i -> i.setNtQuote(this));
        }
        this.partInformationVersions = ntQuotePartInformationVersions;
    }

    public NtQuote partInformationVersions(Set<NtQuotePartInformationVersion> ntQuotePartInformationVersions) {
        this.setPartInformationVersions(ntQuotePartInformationVersions);
        return this;
    }

    public NtQuote addPartInformationVersion(NtQuotePartInformationVersion ntQuotePartInformationVersion) {
        this.partInformationVersions.add(ntQuotePartInformationVersion);
        ntQuotePartInformationVersion.setNtQuote(this);
        return this;
    }

    public NtQuote removePartInformationVersion(NtQuotePartInformationVersion ntQuotePartInformationVersion) {
        this.partInformationVersions.remove(ntQuotePartInformationVersion);
        ntQuotePartInformationVersion.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteCustomerPo> getCustomerPos() {
        return this.customerPos;
    }

    public void setCustomerPos(Set<NtQuoteCustomerPo> ntQuoteCustomerPos) {
        if (this.customerPos != null) {
            this.customerPos.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteCustomerPos != null) {
            ntQuoteCustomerPos.forEach(i -> i.setNtQuote(this));
        }
        this.customerPos = ntQuoteCustomerPos;
    }

    public NtQuote customerPos(Set<NtQuoteCustomerPo> ntQuoteCustomerPos) {
        this.setCustomerPos(ntQuoteCustomerPos);
        return this;
    }

    public NtQuote addCustomerPo(NtQuoteCustomerPo ntQuoteCustomerPo) {
        this.customerPos.add(ntQuoteCustomerPo);
        ntQuoteCustomerPo.setNtQuote(this);
        return this;
    }

    public NtQuote removeCustomerPo(NtQuoteCustomerPo ntQuoteCustomerPo) {
        this.customerPos.remove(ntQuoteCustomerPo);
        ntQuoteCustomerPo.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteVendorQuote> getVendorQuotes() {
        return this.vendorQuotes;
    }

    public void setVendorQuotes(Set<NtQuoteVendorQuote> ntQuoteVendorQuotes) {
        if (this.vendorQuotes != null) {
            this.vendorQuotes.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteVendorQuotes != null) {
            ntQuoteVendorQuotes.forEach(i -> i.setNtQuote(this));
        }
        this.vendorQuotes = ntQuoteVendorQuotes;
    }

    public NtQuote vendorQuotes(Set<NtQuoteVendorQuote> ntQuoteVendorQuotes) {
        this.setVendorQuotes(ntQuoteVendorQuotes);
        return this;
    }

    public NtQuote addVendorQuote(NtQuoteVendorQuote ntQuoteVendorQuote) {
        this.vendorQuotes.add(ntQuoteVendorQuote);
        ntQuoteVendorQuote.setNtQuote(this);
        return this;
    }

    public NtQuote removeVendorQuote(NtQuoteVendorQuote ntQuoteVendorQuote) {
        this.vendorQuotes.remove(ntQuoteVendorQuote);
        ntQuoteVendorQuote.setNtQuote(null);
        return this;
    }

    public Set<NtQuoteVendorPo> getVendorPos() {
        return this.vendorPos;
    }

    public void setVendorPos(Set<NtQuoteVendorPo> ntQuoteVendorPos) {
        if (this.vendorPos != null) {
            this.vendorPos.forEach(i -> i.setNtQuote(null));
        }
        if (ntQuoteVendorPos != null) {
            ntQuoteVendorPos.forEach(i -> i.setNtQuote(this));
        }
        this.vendorPos = ntQuoteVendorPos;
    }

    public NtQuote vendorPos(Set<NtQuoteVendorPo> ntQuoteVendorPos) {
        this.setVendorPos(ntQuoteVendorPos);
        return this;
    }

    public NtQuote addVendorPo(NtQuoteVendorPo ntQuoteVendorPo) {
        this.vendorPos.add(ntQuoteVendorPo);
        ntQuoteVendorPo.setNtQuote(this);
        return this;
    }

    public NtQuote removeVendorPo(NtQuoteVendorPo ntQuoteVendorPo) {
        this.vendorPos.remove(ntQuoteVendorPo);
        ntQuoteVendorPo.setNtQuote(null);
        return this;
    }

    public RfqDetail getRfqDetail() {
        return this.rfqDetail;
    }

    public void setRfqDetail(RfqDetail rfqDetail) {
        this.rfqDetail = rfqDetail;
    }

    public NtQuote rfqDetail(RfqDetail rfqDetail) {
        this.setRfqDetail(rfqDetail);
        return this;
    }

    public NtQuoteProjectApproval getNtQuoteProjectApproval() {
        return this.ntQuoteProjectApproval;
    }

    public void setNtQuoteProjectApproval(NtQuoteProjectApproval ntQuoteProjectApproval) {
        this.ntQuoteProjectApproval = ntQuoteProjectApproval;
    }

    public NtQuote ntQuoteProjectApproval(NtQuoteProjectApproval ntQuoteProjectApproval) {
        this.setNtQuoteProjectApproval(ntQuoteProjectApproval);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NtQuote)) {
            return false;
        }
        return getId() != null && getId().equals(((NtQuote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NtQuote{" +
            "id=" + getId() +
            ", srNo=" + getSrNo() +
            ", uid='" + getUid() + "'" +
            ", quoteKey='" + getQuoteKey() + "'" +
            ", salesPerson='" + getSalesPerson() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", quoteNumber='" + getQuoteNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", moldNumber='" + getMoldNumber() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", moldManual='" + getMoldManual() + "'" +
            ", customerPo='" + getCustomerPo() + "'" +
            ", vendorQuote='" + getVendorQuote() + "'" +
            ", vendorPo='" + getVendorPo() + "'" +
            ", cadFile='" + getCadFile() + "'" +
            ", quotedPrice=" + getQuotedPrice() +
            ", deliveryTime='" + getDeliveryTime() + "'" +
            ", quoteDate='" + getQuoteDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
