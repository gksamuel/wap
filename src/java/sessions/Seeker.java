/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import entities.Seekers;
import java.util.Date;
import utils.Db;
import utils.Encode;

/**
 *
 * @author gachanja
 */
@Named("seeker")
@SessionScoped
public class Seeker implements Serializable {

    private static final long serialVersionUID = -3510304122582880348L;
    private String username;
    private String password;
    private Integer id;
    private Date expiry;
    private String mobile;
    private Integer status;

    private UserTransaction utx;
    private EntityManager em;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Seeker login(String u, String p) {
        Seeker user = new Seeker();
        try {
            Encode encode = new Encode();
            setPassword(encode.endodesha1(p));
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            List<Seekers> seekers = (List<Seekers>) em.createNamedQuery("Seekers.authenticate").setParameter("username", u).setParameter("password", getPassword()).getResultList();
            if (seekers.size() == 1) {
                Seekers seeker = (Seekers) seekers.get(0);
                user.setUsername(seeker.getUsername());
                user.setId(seeker.getId());
                user.setMobile(seeker.getMobile());
                user.setExpiry(seeker.getExpiry());
                user.setStatus(seeker.getStatusid().getId());

            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(Seeker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
