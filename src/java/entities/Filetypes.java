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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "filetypes", catalog = "work", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"filetype"})})
@NamedQueries({
    @NamedQuery(name = "Filetypes.findAll", query = "SELECT f FROM Filetypes f")})
public class Filetypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "filetype", nullable = false, length = 100)
    private String filetype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "uploads", nullable = false)
    private boolean uploads;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cv", nullable = false)
    private boolean cv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "image", nullable = false)
    private boolean image;

    public Filetypes() {
    }

    public Filetypes(Integer id) {
        this.id = id;
    }

    public Filetypes(Integer id, String filetype, String description, boolean uploads, boolean cv, boolean image) {
        this.id = id;
        this.filetype = filetype;
        this.description = description;
        this.uploads = uploads;
        this.cv = cv;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean getUploads() {
        return uploads;
    }

    public void setUploads(boolean uploads) {
        this.uploads = uploads;
    }

    public boolean getCv() {
        return cv;
    }

    public void setCv(boolean cv) {
        this.cv = cv;
    }

    public boolean getImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Filetypes)) {
            return false;
        }
        Filetypes other = (Filetypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Filetypes[ id=" + id + " ]";
    }
    
}
