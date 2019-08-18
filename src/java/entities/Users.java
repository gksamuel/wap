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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "users", catalog = "work", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;
    @Column(name = "admin")
    private Boolean admin;
    @Column(name = "active")
    private Boolean active;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @OneToMany
    private Collection<Jobs> jobsCollection;
    @JoinColumn(name = "groupid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Groups groupid;
    @JoinColumn(name = "companyid", referencedColumnName = "id")
    @ManyToOne
    private Company companyid;
    @JoinColumn(name = "accountid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Accounts accountid;
    @OneToMany
    private Collection<Thirdshortlist> thirdshortlistCollection;
    @OneToMany
    private Collection<Secondshortlist> secondshortlistCollection;
    @OneToMany
    private Collection<Firstshortlist> firstshortlistCollection;
    @OneToMany
    private Collection<Contact> contactCollection;
    @OneToMany
    private Collection<Applications> applicationsCollection;
    @OneToMany
    private Collection<Tiprate> tiprateCollection;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String username, String email, String password, Date datemodified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.datemodified = datemodified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public Collection<Jobs> getJobsCollection() {
        return jobsCollection;
    }

    public void setJobsCollection(Collection<Jobs> jobsCollection) {
        this.jobsCollection = jobsCollection;
    }

    public Groups getGroupid() {
        return groupid;
    }

    public void setGroupid(Groups groupid) {
        this.groupid = groupid;
    }

    public Company getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Company companyid) {
        this.companyid = companyid;
    }

    public Accounts getAccountid() {
        return accountid;
    }

    public void setAccountid(Accounts accountid) {
        this.accountid = accountid;
    }

    public Collection<Thirdshortlist> getThirdshortlistCollection() {
        return thirdshortlistCollection;
    }

    public void setThirdshortlistCollection(Collection<Thirdshortlist> thirdshortlistCollection) {
        this.thirdshortlistCollection = thirdshortlistCollection;
    }

    public Collection<Secondshortlist> getSecondshortlistCollection() {
        return secondshortlistCollection;
    }

    public void setSecondshortlistCollection(Collection<Secondshortlist> secondshortlistCollection) {
        this.secondshortlistCollection = secondshortlistCollection;
    }

    public Collection<Firstshortlist> getFirstshortlistCollection() {
        return firstshortlistCollection;
    }

    public void setFirstshortlistCollection(Collection<Firstshortlist> firstshortlistCollection) {
        this.firstshortlistCollection = firstshortlistCollection;
    }

    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    public Collection<Applications> getApplicationsCollection() {
        return applicationsCollection;
    }

    public void setApplicationsCollection(Collection<Applications> applicationsCollection) {
        this.applicationsCollection = applicationsCollection;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Users[ id=" + id + " ]";
    }
    
}
