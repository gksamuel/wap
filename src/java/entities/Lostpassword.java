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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "lostpassword", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Lostpassword.findAll", query = "SELECT l FROM Lostpassword l"),
    @NamedQuery(name = "Lostpassword.findRecord", query = "SELECT l FROM Lostpassword l where l.code = :code and l.username = :username")

})
public class Lostpassword implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "code", nullable = false, length = 6)
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Column(name = "datemodified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;

    public Lostpassword() {
    }

    public Lostpassword(Integer id) {
        this.id = id;
    }

    public Lostpassword(Integer id, String code, String username) {
        this.id = id;
        this.code = code;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Lostpassword)) {
            return false;
        }
        Lostpassword other = (Lostpassword) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Lostpassword[ id=" + id + " ]";
    }
    
}
