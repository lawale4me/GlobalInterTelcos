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
@Table(name = "othermessages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Othermessages.findAll", query = "SELECT o FROM Othermessages o"),
    @NamedQuery(name = "Othermessages.findByOtherMessagesID", query = "SELECT o FROM Othermessages o WHERE o.otherMessagesID = :otherMessagesID"),
    @NamedQuery(name = "Othermessages.findByMessage", query = "SELECT o FROM Othermessages o WHERE o.message = :message"),
    @NamedQuery(name = "Othermessages.findByMsgDate", query = "SELECT o FROM Othermessages o WHERE o.msgDate = :msgDate"),
    @NamedQuery(name = "Othermessages.findByModemnumber", query = "SELECT o FROM Othermessages o WHERE o.modemnumber = :modemnumber")})
public class Othermessages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OtherMessagesID")
    private Integer otherMessagesID;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "msgDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgDate;
    @Basic(optional = false)
    @Column(name = "MODEMNUMBER")
    private String modemnumber;

    public Othermessages() {
    }

    public Othermessages(Integer otherMessagesID) {
        this.otherMessagesID = otherMessagesID;
    }

    public Othermessages(Integer otherMessagesID, String message, Date msgDate, String modemnumber) {
        this.otherMessagesID = otherMessagesID;
        this.message = message;
        this.msgDate = msgDate;
        this.modemnumber = modemnumber;
    }

    public Othermessages(String message,  String modemnumber) {
        
        this.message = message;
        this.msgDate = new Date();
        this.modemnumber = modemnumber;
    }
    
    public Integer getOtherMessagesID() {
        return otherMessagesID;
    }

    public void setOtherMessagesID(Integer otherMessagesID) {
        this.otherMessagesID = otherMessagesID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public String getModemnumber() {
        return modemnumber;
    }

    public void setModemnumber(String modemnumber) {
        this.modemnumber = modemnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otherMessagesID != null ? otherMessagesID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Othermessages)) {
            return false;
        }
        Othermessages other = (Othermessages) object;
        if ((this.otherMessagesID == null && other.otherMessagesID != null) || (this.otherMessagesID != null && !this.otherMessagesID.equals(other.otherMessagesID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Othermessages[ otherMessagesID=" + otherMessagesID + " ]";
    }
    
}
