/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "positions", catalog = "work", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"noofpositions"})})
@NamedQueries({
    @NamedQuery(name = "Positions.findAll", query = "SELECT p FROM Positions p")})
public class Positions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "noofpositions", nullable = false, length = 30)
    private String noofpositions;
    @OneToMany
    private Collection<Jobs> jobsCollection;

    public Positions() {
    }

    public Positions(Integer id) {
        this.id = id;
    }

    public Positions(Integer id, String noofpositions) {
        this.id = id;
        this.noofpositions = noofpositions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoofpositions() {
        return noofpositions;
    }

    public void setNoofpositions(String noofpositions) {
        this.noofpositions = noofpositions;
    }

    public Collection<Jobs> getJobsCollection() {
        return jobsCollection;
    }

    public void setJobsCollection(Collection<Jobs> jobsCollection) {
        this.jobsCollection = jobsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Positions)) {
            return false;
        }
        Positions other = (Positions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Positions[ id=" + id + " ]";
    }
    
}
