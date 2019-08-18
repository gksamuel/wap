/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "paypal", catalog = "work", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"txn_id"})})
@NamedQueries({
    @NamedQuery(name = "Paypal.findAll", query = "SELECT p FROM Paypal p")})
public class Paypal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "txn_id", nullable = false, length = 20)
    private String txnId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "mc_gross", nullable = false, precision = 7, scale = 2)
    private BigDecimal mcGross;
    @Size(max = 30)
    @Column(name = "protection_eligibility", length = 30)
    private String protectionEligibility;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "payer_id", nullable = false, length = 100)
    private String payerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "payer_email", nullable = false, length = 100)
    private String payerEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "residence_country", nullable = false, length = 100)
    private String residenceCountry;
    @Size(max = 20)
    @Column(name = "payer_status", length = 20)
    private String payerStatus;
    @Column(name = "tax", precision = 7, scale = 2)
    private BigDecimal tax;
    @Column(name = "payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Size(max = 20)
    @Column(name = "option_selection1", length = 20)
    private String optionSelection1;
    @Size(max = 20)
    @Column(name = "payment_status", length = 20)
    private String paymentStatus;
    @Size(max = 20)
    @Column(name = "charset", length = 20)
    private String charset;
    @Column(name = "mc_fee", precision = 7, scale = 2)
    private BigDecimal mcFee;
    @Size(max = 100)
    @Column(name = "custom", length = 100)
    private String custom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "receiver_email", nullable = false, length = 100)
    private String receiverEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "business", nullable = false, length = 100)
    private String business;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "option_name1", nullable = false, length = 100)
    private String optionName1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "payment_type", nullable = false, length = 20)
    private String paymentType;
    @Column(name = "btn_id")
    private Integer btnId;
    @Column(name = "payment_fee", precision = 7, scale = 2)
    private BigDecimal paymentFee;
    @Column(name = "shipping_discount", precision = 7, scale = 2)
    private BigDecimal shippingDiscount;
    @Column(name = "insurance_amount", precision = 7, scale = 2)
    private BigDecimal insuranceAmount;
    @Size(max = 13)
    @Column(name = "receiver_id", length = 13)
    private String receiverId;
    @Size(max = 30)
    @Column(name = "pending_reason", length = 30)
    private String pendingReason;
    @Size(max = 30)
    @Column(name = "txn_type", length = 30)
    private String txnType;
    @Size(max = 30)
    @Column(name = "item_name", length = 30)
    private String itemName;
    @Column(name = "discount", precision = 7, scale = 2)
    private BigDecimal discount;
    @Size(max = 20)
    @Column(name = "mc_currency", length = 20)
    private String mcCurrency;
    @Size(max = 100)
    @Column(name = "item_number", length = 100)
    private String itemNumber;
    @Size(max = 30)
    @Column(name = "shipping_method", length = 30)
    private String shippingMethod;
    @Column(name = "handling_amount", precision = 7, scale = 2)
    private BigDecimal handlingAmount;
    @Size(max = 100)
    @Column(name = "transaction_subject", length = 100)
    private String transactionSubject;
    @Column(name = "payment_gross", precision = 7, scale = 2)
    private BigDecimal paymentGross;
    @Column(name = "shipping", precision = 7, scale = 2)
    private BigDecimal shipping;
    @Column(name = "processtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processtime;
    @Column(name = "fromtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromtime;
    @Column(name = "totime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date totime;
    @JoinColumn(name = "seekerid", referencedColumnName = "id")
    @ManyToOne
    private Seekers seekerid;
    @JoinColumn(name = "companyid", referencedColumnName = "id")
    @ManyToOne
    private Company companyid;

    public Paypal() {
    }

    public Paypal(Integer id) {
        this.id = id;
    }

    public Paypal(Integer id, String txnId, BigDecimal mcGross, String payerId, String firstName, String lastName, String payerEmail, String residenceCountry, String receiverEmail, String business, int quantity, String optionName1, String paymentType) {
        this.id = id;
        this.txnId = txnId;
        this.mcGross = mcGross;
        this.payerId = payerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.payerEmail = payerEmail;
        this.residenceCountry = residenceCountry;
        this.receiverEmail = receiverEmail;
        this.business = business;
        this.quantity = quantity;
        this.optionName1 = optionName1;
        this.paymentType = paymentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public BigDecimal getMcGross() {
        return mcGross;
    }

    public void setMcGross(BigDecimal mcGross) {
        this.mcGross = mcGross;
    }

    public String getProtectionEligibility() {
        return protectionEligibility;
    }

    public void setProtectionEligibility(String protectionEligibility) {
        this.protectionEligibility = protectionEligibility;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(String residenceCountry) {
        this.residenceCountry = residenceCountry;
    }

    public String getPayerStatus() {
        return payerStatus;
    }

    public void setPayerStatus(String payerStatus) {
        this.payerStatus = payerStatus;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOptionSelection1() {
        return optionSelection1;
    }

    public void setOptionSelection1(String optionSelection1) {
        this.optionSelection1 = optionSelection1;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public BigDecimal getMcFee() {
        return mcFee;
    }

    public void setMcFee(BigDecimal mcFee) {
        this.mcFee = mcFee;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOptionName1() {
        return optionName1;
    }

    public void setOptionName1(String optionName1) {
        this.optionName1 = optionName1;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }

    public BigDecimal getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(BigDecimal paymentFee) {
        this.paymentFee = paymentFee;
    }

    public BigDecimal getShippingDiscount() {
        return shippingDiscount;
    }

    public void setShippingDiscount(BigDecimal shippingDiscount) {
        this.shippingDiscount = shippingDiscount;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getPendingReason() {
        return pendingReason;
    }

    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getMcCurrency() {
        return mcCurrency;
    }

    public void setMcCurrency(String mcCurrency) {
        this.mcCurrency = mcCurrency;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public BigDecimal getHandlingAmount() {
        return handlingAmount;
    }

    public void setHandlingAmount(BigDecimal handlingAmount) {
        this.handlingAmount = handlingAmount;
    }

    public String getTransactionSubject() {
        return transactionSubject;
    }

    public void setTransactionSubject(String transactionSubject) {
        this.transactionSubject = transactionSubject;
    }

    public BigDecimal getPaymentGross() {
        return paymentGross;
    }

    public void setPaymentGross(BigDecimal paymentGross) {
        this.paymentGross = paymentGross;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public Date getFromtime() {
        return fromtime;
    }

    public void setFromtime(Date fromtime) {
        this.fromtime = fromtime;
    }

    public Date getTotime() {
        return totime;
    }

    public void setTotime(Date totime) {
        this.totime = totime;
    }

    public Seekers getSeekerid() {
        return seekerid;
    }

    public void setSeekerid(Seekers seekerid) {
        this.seekerid = seekerid;
    }

    public Company getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Company companyid) {
        this.companyid = companyid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Paypal)) {
            return false;
        }
        Paypal other = (Paypal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Paypal[ id=" + id + " ]";
    }
    
}
