/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "paypaltarrifs", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Paypaltarrifs.findAll", query = "SELECT p FROM Paypaltarrifs p")})
public class Paypaltarrifs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tarrif", nullable = false, length = 20)
    private String tarrif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "description", nullable = false, length = 20)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "days", nullable = false)
    private int days;
    @Basic(optional = false)
    @NotNull
    @Column(name = "membership", nullable = false)
    private int membership;

    public Paypaltarrifs() {
    }

    public Paypaltarrifs(Integer id) {
        this.id = id;
    }

    public Paypaltarrifs(Integer id, String tarrif, String description, int days, int membership) {
        this.id = id;
        this.tarrif = tarrif;
        this.description = description;
        this.days = days;
        this.membership = membership;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarrif() {
        return tarrif;
    }

    public void setTarrif(String tarrif) {
        this.tarrif = tarrif;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Paypaltarrifs)) {
            return false;
        }
        Paypaltarrifs other = (Paypaltarrifs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Paypaltarrifs[ id=" + id + " ]";
    }
    
}
