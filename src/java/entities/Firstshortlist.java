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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "firstshortlist", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Firstshortlist.findAll", query = "SELECT f FROM Firstshortlist f")})
public class Firstshortlist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "internalcomment", length = 255)
    private String internalcomment;
    @Size(max = 255)
    @Column(name = "seekercomment", length = 255)
    private String seekercomment;
    @Size(max = 255)
    @Column(name = "publiccomment", length = 255)
    private String publiccomment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @Column(name = "hired")
    private Boolean hired;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private Users userid;
    @JoinColumn(name = "seekerid", referencedColumnName = "id")
    @ManyToOne
    private Seekers seekerid;
    @JoinColumn(name = "jobid", referencedColumnName = "id")
    @ManyToOne
    private Jobs jobid;

    public Firstshortlist() {
    }

    public Firstshortlist(Integer id) {
        this.id = id;
    }

    public Firstshortlist(Integer id, Date datemodified) {
        this.id = id;
        this.datemodified = datemodified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInternalcomment() {
        return internalcomment;
    }

    public void setInternalcomment(String internalcomment) {
        this.internalcomment = internalcomment;
    }

    public String getSeekercomment() {
        return seekercomment;
    }

    public void setSeekercomment(String seekercomment) {
        this.seekercomment = seekercomment;
    }

    public String getPubliccomment() {
        return publiccomment;
    }

    public void setPubliccomment(String publiccomment) {
        this.publiccomment = publiccomment;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public Boolean getHired() {
        return hired;
    }

    public void setHired(Boolean hired) {
        this.hired = hired;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    public Seekers getSeekerid() {
        return seekerid;
    }

    public void setSeekerid(Seekers seekerid) {
        this.seekerid = seekerid;
    }

    public Jobs getJobid() {
        return jobid;
    }

    public void setJobid(Jobs jobid) {
        this.jobid = jobid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Firstshortlist)) {
            return false;
        }
        Firstshortlist other = (Firstshortlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Firstshortlist[ id=" + id + " ]";
    }
    
}
