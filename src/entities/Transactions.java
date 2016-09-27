/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByTransactionID", query = "SELECT t FROM Transactions t WHERE t.transactionID = :transactionID"),
    @NamedQuery(name = "Transactions.findBySourceNumber", query = "SELECT t FROM Transactions t WHERE t.sourceNumber = :sourceNumber"),
    @NamedQuery(name = "Transactions.findByDestinationNumber", query = "SELECT t FROM Transactions t WHERE t.destinationNumber = :destinationNumber"),
    @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transactions.findBySourceNetwork", query = "SELECT t FROM Transactions t WHERE t.sourceNetwork = :sourceNetwork"),
    @NamedQuery(name = "Transactions.findByDestinationNetwork", query = "SELECT t FROM Transactions t WHERE t.destinationNetwork = :destinationNetwork"),
    @NamedQuery(name = "Transactions.findByRequestDate", query = "SELECT t FROM Transactions t WHERE t.requestDate = :requestDate"),
    @NamedQuery(name = "Transactions.findByStatus", query = "SELECT t FROM Transactions t WHERE t.status = :status"),
    @NamedQuery(name = "Transactions.findPending", query = "SELECT t FROM Transactions t WHERE t.amount = :amount  and t.sourceNumber= :sourceNumber and t.status=0"),
    @NamedQuery(name = "Transactions.findByDestinationNetworkANDStatus", query = "SELECT t FROM Transactions t WHERE t.destinationNetwork= :destinationNetwork and t.status=1"),
    @NamedQuery(name = "Transactions.findByDateUpdated", query = "SELECT t FROM Transactions t WHERE t.dateUpdated = :dateUpdated")})
public class Transactions implements Serializable {
    @Lob
    @Column(name = "ConfirmationMessage")
    private String confirmationMessage;
    @Lob
    @Column(name = "messageUUID")
    private String messageUUID;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TransactionID")
    private Integer transactionID;
    @Column(name = "SourceNumber")
    private String sourceNumber;
    @Column(name = "DestinationNumber")
    private String destinationNumber;
    @Column(name = "Amount")
    private String amount;
    @Column(name = "SourceNetwork")
    private String sourceNetwork;
    @Column(name = "DestinationNetwork")
    private String destinationNetwork;
    @Column(name = "RequestDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(name = "Status")
    private Integer status;
    @Lob
    @Column(name = "TransactionDetails")
    private String transactionDetails;
    @Lob
    @Column(name = "RequestPayload")
    private String requestPayload;
    @Column(name = "DateUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    public Transactions() {
    }

    public Transactions(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Transactions(String sourceNo,String destNo,String amount,String srcNetwork,String destNetwork,String ReqPayload,String messageUUID)
    {
      this.sourceNumber=sourceNo;
      this.destinationNumber=destNo;
      this.amount=amount;
      this.sourceNetwork=srcNetwork;
      this.destinationNetwork=destNetwork;
      this.requestPayload=ReqPayload;
      this.requestDate=new Date();
      this.messageUUID=messageUUID;
      this.status=0;
    }
    
    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSourceNetwork() {
        return sourceNetwork;
    }

    public void setSourceNetwork(String sourceNetwork) {
        this.sourceNetwork = sourceNetwork;
    }

    public String getDestinationNetwork() {
        return destinationNetwork;
    }

    public void setDestinationNetwork(String destinationNetwork) {
        this.destinationNetwork = destinationNetwork;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionID != null ? transactionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transactionID == null && other.transactionID != null) || (this.transactionID != null && !this.transactionID.equals(other.transactionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transactions[ transactionID=" + transactionID + " ]";
    }

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(String messageUUID) {
        this.messageUUID = messageUUID;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }
    
}
