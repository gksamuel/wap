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
@Table(name = "tellafriend", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Tellafriend.findAll", query = "SELECT t FROM Tellafriend t")})
public class Tellafriend implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "friend", nullable = false, length = 100)
    private String friend;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datecreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @JoinColumn(name = "seekerid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Seekers seekerid;
    @JoinColumn(name = "jobid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Jobs jobid;

    public Tellafriend() {
    }

    public Tellafriend(Integer id) {
        this.id = id;
    }

    public Tellafriend(Integer id, String friend, Date datecreated, String name) {
        this.id = id;
        this.friend = friend;
        this.datecreated = datecreated;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof Tellafriend)) {
            return false;
        }
        Tellafriend other = (Tellafriend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Tellafriend[ id=" + id + " ]";
    }
    
}
