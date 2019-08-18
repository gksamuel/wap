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
@Table(name = "files", catalog = "work", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Files.findAll", query = "SELECT f FROM Files f")})
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "filename", nullable = false, length = 100)
    private String filename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "originalfilename", nullable = false, length = 100)
    private String originalfilename;
    @Column(name = "filesize")
    private Integer filesize;
    @Size(max = 100)
    @Column(name = "filetype", length = 100)
    private String filetype;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.YES)
    @Size(max = 1)
    @Column(name = "metadata", length = 1)
    private String metadata;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datemodified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datemodified;
    @Basic(optional = false)
    @NotNull
    @Column(name = "viewable", nullable = false)
    private boolean viewable;
    @ContainedIn
    @JoinColumn(name = "seekerid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Seekers seekerid;
    @JoinColumn(name = "filecategoryid", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Filecategory filecategoryid;

    public Files() {
    }

    public Files(Integer id) {
        this.id = id;
    }

    public Files(Integer id, String filename, String originalfilename, Date datemodified, boolean viewable) {
        this.id = id;
        this.filename = filename;
        this.originalfilename = originalfilename;
        this.datemodified = datemodified;
        this.viewable = viewable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalfilename() {
        return originalfilename;
    }

    public void setOriginalfilename(String originalfilename) {
        this.originalfilename = originalfilename;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public boolean getViewable() {
        return viewable;
    }

    public void setViewable(boolean viewable) {
        this.viewable = viewable;
    }

    public Seekers getSeekerid() {
        return seekerid;
    }

    public void setSeekerid(Seekers seekerid) {
        this.seekerid = seekerid;
    }

    public Filecategory getFilecategoryid() {
        return filecategoryid;
    }

    public void setFilecategoryid(Filecategory filecategoryid) {
        this.filecategoryid = filecategoryid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Files)) {
            return false;
        }
        Files other = (Files) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Files[ id=" + id + " ]";
    }

}
