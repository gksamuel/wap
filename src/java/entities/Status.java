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
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "status", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
    @NamedQuery(name = "Status.findByID", query = "SELECT s FROM Status s where s.id = :id")
})
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 30)
    @Column(name = "description", length = 30)
    private String description;
    @OneToMany
    private Collection<Seekers> seekersCollection;
    @OneToMany
    private Collection<Company> companyCollection;

    public Status() {
    }

    public Status(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Seekers> getSeekersCollection() {
        return seekersCollection;
    }

    public void setSeekersCollection(Collection<Seekers> seekersCollection) {
        this.seekersCollection = seekersCollection;
    }

    public Collection<Company> getCompanyCollection() {
        return companyCollection;
    }

    public void setCompanyCollection(Collection<Company> companyCollection) {
        this.companyCollection = companyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Status[ id=" + id + " ]";
    }
    
}
