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
@Table(name = "tip", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Tip.findAll", query = "SELECT t FROM Tip t")})
public class Tip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "submittedby", length = 100)
    private String submittedby;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tip", nullable = false, length = 255)
    private String tip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate", nullable = false)
    private int rate;
    @Column(name = "datesubmited")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesubmited;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Tiprate> tiprateCollection;

    public Tip() {
    }

    public Tip(Integer id) {
        this.id = id;
    }

    public Tip(Integer id, String tip, int rate) {
        this.id = id;
        this.tip = tip;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubmittedby() {
        return submittedby;
    }

    public void setSubmittedby(String submittedby) {
        this.submittedby = submittedby;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getDatesubmited() {
        return datesubmited;
    }

    public void setDatesubmited(Date datesubmited) {
        this.datesubmited = datesubmited;
    }

    public Collection<Tiprate> getTiprateCollection() {
        return tiprateCollection;
    }

    public void setTiprateCollection(Collection<Tiprate> tiprateCollection) {
        this.tiprateCollection = tiprateCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tip)) {
            return false;
        }
        Tip other = (Tip) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Tip[ id=" + id + " ]";
    }
    
}
