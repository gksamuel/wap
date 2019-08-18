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
@Table(name = "coverletters", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Coverletters.findAll", query = "SELECT c FROM Coverletters c"),
    @NamedQuery(name = "Coverletters.findById", query = "SELECT c FROM Coverletters c where c.id = :id")
})
public class Coverletters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Size(max = 2147483647)
    @Column(name = "letter", length = 2147483647)
    private String letter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @ContainedIn
    @JoinColumn(name = "seekerid", referencedColumnName = "id")
    @ManyToOne
    private Seekers seekerid;

    public Coverletters() {
    }

    public Coverletters(Integer id) {
        this.id = id;
    }

    public Coverletters(Integer id, Date datemodified) {
        this.id = id;
        this.datemodified = datemodified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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
        if (!(object instanceof Coverletters)) {
            return false;
        }
        Coverletters other = (Coverletters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Coverletters[ id=" + id + " ]";
    }

}
