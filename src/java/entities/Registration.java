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
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "registration", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r"),
    @NamedQuery(name = "Registration.findByUsername", query = "SELECT r FROM Registration r where r.username = :username"),
    @NamedQuery(name = "Registration.findByRecord", query = "SELECT r FROM Registration r where r.username = :username and r.code = :code"),
    @NamedQuery(name = "Registration.findByEmail", query = "SELECT r FROM Registration r where r.email = :email")

})
public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;
    @Size(max = 100)
    @Column(name = "username", length = 100)
    private String username;
    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;
    @Size(max = 100)
    @Column(name = "passphrase", length = 100)
    private String passphrase;
    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;
    @Size(max = 6)
    @Column(name = "code", length = 6)
    private String code;
    @JoinColumn(name = "groupid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Groups groupid;

    public Registration() {
    }

    public Registration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Groups getGroupid() {
        return groupid;
    }

    public void setGroupid(Groups groupid) {
        this.groupid = groupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Registration[ id=" + id + " ]";
    }
    
}
