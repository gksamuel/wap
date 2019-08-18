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
@Table(name = "applications", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a"),
    @NamedQuery(name = "Applications.findApplication", query = "SELECT a FROM Applications a where a.jobid = :jobid and a.seekerid = :seekerid")
})
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @Size(max = 2147483647)
    @Column(name = "letter", length = 2147483647)
    private String letter;
    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "viewed", nullable = false)
    private boolean viewed;
    @Column(name = "commentdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentdate;
    @Size(max = 100)
    @Column(name = "attachment", length = 100)
    private String attachment;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne
    private Users userid;
    @JoinColumn(name = "seekerid", referencedColumnName = "id")
    @ManyToOne
    private Seekers seekerid;
    @JoinColumn(name = "jobid", referencedColumnName = "id")
    @ManyToOne
    private Jobs jobid;

    public Applications() {
    }

    public Applications(Integer id) {
        this.id = id;
    }

    public Applications(Integer id, Date datemodified, boolean viewed) {
        this.id = id;
        this.datemodified = datemodified;
        this.viewed = viewed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Date getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(Date commentdate) {
        this.commentdate = commentdate;
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
        if (!(object instanceof Applications)) {
            return false;
        }
        Applications other = (Applications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Applications[ id=" + id + " ]";
    }

}
