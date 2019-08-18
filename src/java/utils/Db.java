/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author gachanja
 */
@PersistenceContext(unitName = "wapPU", name = "persistence/EntityManager")
@Stateless
public class Db {

    @Resource
    private UserTransaction utx;
    @PersistenceContext(unitName = "wapPU")
    private EntityManager em;

    public Db() {
    }

    public Db(UserTransaction utx, EntityManager em) {
        try {
            Context ic = new InitialContext();
            this.utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
            this.em = (EntityManager) ic.lookup("java:comp/env/persistence/EntityManager");
        } catch (NamingException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UserTransaction getUtx() {
        return utx;
    }

    public void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
