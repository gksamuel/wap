/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "inmessage", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Inmessage.findAll", query = "SELECT i FROM Inmessage i")})
public class Inmessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "source", nullable = false, length = 15)
    private String source;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 320)
    @Column(name = "message", nullable = false, length = 320)
    private String message;
    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Mpesa> mpesaCollection;

    public Inmessage() {
    }

    public Inmessage(Integer id) {
        this.id = id;
    }

    public Inmessage(Integer id, String source, String message) {
        this.id = id;
        this.source = source;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Collection<Mpesa> getMpesaCollection() {
        return mpesaCollection;
    }

    public void setMpesaCollection(Collection<Mpesa> mpesaCollection) {
        this.mpesaCollection = mpesaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Inmessage)) {
            return false;
        }
        Inmessage other = (Inmessage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Inmessage[ id=" + id + " ]";
    }
    
}
