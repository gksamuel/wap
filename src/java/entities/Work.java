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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "work", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Work.findAll", query = "SELECT w FROM Work w"),
    @NamedQuery(name = "Work.findById", query = "SELECT w FROM Work w WHERE w.id = :id")
})
public class Work implements Serializable {

    private static final long serialVersionUID = 2046657964230059193L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    private Integer id;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "company", length = 100)
    private String company;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "website", length = 100)
    private String website;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;
    @Size(max = 15)
    @Column(name = "companyphone", length = 15)
    private String companyphone;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "location", length = 100)
    private String location;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "position", length = 100)
    private String position;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 300)
    @Column(name = "jobdescription", length = 300)
    private String jobdescription;
    @Size(max = 100)
    @Column(name = "refereename", length = 100)
    private String refereename;
    @Size(max = 100)
    @Column(name = "refereeemail", length = 100)
    private String refereeemail;
    @Size(max = 12)
    @Column(name = "refereemobile", length = 12)
    private String refereemobile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @ContainedIn
    @JoinColumn(name = "seekerid", referencedColumnName = "id")
    @ManyToOne
    private Seekers seekerid;

    public Work() {
    }

    public Work(Integer id) {
        this.id = id;
    }

    public Work(Integer id, Date datemodified) {
        this.id = id;
        this.datemodified = datemodified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyphone() {
        return companyphone;
    }

    public void setCompanyphone(String companyphone) {
        this.companyphone = companyphone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getRefereename() {
        return refereename;
    }

    public void setRefereename(String refereename) {
        this.refereename = refereename;
    }

    public String getRefereeemail() {
        return refereeemail;
    }

    public void setRefereeemail(String refereeemail) {
        this.refereeemail = refereeemail;
    }

    public String getRefereemobile() {
        return refereemobile;
    }

    public void setRefereemobile(String refereemobile) {
        this.refereemobile = refereemobile;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public Seekers getSeekerid() {
        return seekerid;
    }

    public void setSeekerid(Seekers seekerid) {
        this.seekerid = seekerid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Work)) {
            return false;
        }
        Work other = (Work) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Work[ id=" + id + " ]";
    }

}
