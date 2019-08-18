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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author gachanja
 */
@Entity
@Indexed(index = "work.objects.Jobs") 
@Table(name = "jobs", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Jobs.findAll", query = "SELECT j FROM Jobs j"),
    @NamedQuery(name = "Jobs.findById", query = "SELECT j FROM Jobs j where j.id = :id"),
    @NamedQuery(name = "Jobs.findByCategory", query = "SELECT j FROM Jobs j where j.categoryid = :categoryid ORDER BY j.id desc")
})
public class Jobs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    private Integer id;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Size(max = 20)
    @Column(name = "referenceno", length = 20)
    private String referenceno;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)    
    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)    
    @Size(max = 255)
    @Column(name = "summary", length = 255)
    private String summary;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)    
    @Size(max = 2147483647)
    @Column(name = "jobdescription", length = 2147483647)
    private String jobdescription;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 2147483647)
    @Column(name = "qualifications", length = 2147483647)
    private String qualifications;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 2147483647)
    @Column(name = "experience", length = 2147483647)
    private String experience;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 2147483647)
    @Column(name = "instructions", length = 2147483647)
    private String instructions;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "maxapplications")
    private Integer maxapplications;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @Size(max = 100)
    @Column(name = "attachment", length = 100)
    private String attachment;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private Users userid;
    @JoinColumn(name = "salaryid", referencedColumnName = "id")
    @ManyToOne
    private Salary salaryid;
    @JoinColumn(name = "positionsid", referencedColumnName = "id")
    @ManyToOne
    private Positions positionsid;
    @JoinColumn(name = "companyid", referencedColumnName = "id")
    @ManyToOne
    private Company companyid;
    @JoinColumn(name = "categoryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryid;
    @JoinColumn(name = "accountid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Accounts accountid;
    @OneToMany
    private Collection<Thirdshortlist> thirdshortlistCollection;
    @OneToMany
    private Collection<Secondshortlist> secondshortlistCollection;
    @OneToMany
    private Collection<Firstshortlist> firstshortlistCollection;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Tellafriend> tellafriendCollection;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Abuse> abuseCollection;
    @OneToMany
    private Collection<Applications> applicationsCollection;

    public Jobs() {
    }

    public Jobs(Integer id) {
        this.id = id;
    }

    public Jobs(Integer id, Date datemodified) {
        this.id = id;
        this.datemodified = datemodified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMaxapplications() {
        return maxapplications;
    }

    public void setMaxapplications(Integer maxapplications) {
        this.maxapplications = maxapplications;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    public Salary getSalaryid() {
        return salaryid;
    }

    public void setSalaryid(Salary salaryid) {
        this.salaryid = salaryid;
    }

    public Positions getPositionsid() {
        return positionsid;
    }

    public void setPositionsid(Positions positionsid) {
        this.positionsid = positionsid;
    }

    public Company getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Company companyid) {
        this.companyid = companyid;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
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

    public Collection<Tellafriend> getTellafriendCollection() {
        return tellafriendCollection;
    }

    public void setTellafriendCollection(Collection<Tellafriend> tellafriendCollection) {
        this.tellafriendCollection = tellafriendCollection;
    }

    public Collection<Abuse> getAbuseCollection() {
        return abuseCollection;
    }

    public void setAbuseCollection(Collection<Abuse> abuseCollection) {
        this.abuseCollection = abuseCollection;
    }

    public Collection<Applications> getApplicationsCollection() {
        return applicationsCollection;
    }

    public void setApplicationsCollection(Collection<Applications> applicationsCollection) {
        this.applicationsCollection = applicationsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Jobs)) {
            return false;
        }
        Jobs other = (Jobs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Jobs[ id=" + id + " ]";
    }

}
