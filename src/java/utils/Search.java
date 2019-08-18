/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Jobs;
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
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

/**
 *
 * @author gachanja
 */
@Named("search")
@SessionScoped
public class Search implements Serializable {

    private static final long serialVersionUID = -3510304122543880348L;
    private List<Jobs> jobList;
    private String keyword;
    private Integer recordCount = 0;
    private UserTransaction utx;
    private EntityManager em;

    public List<Jobs> getJobList() {
        return jobList;
    }

    public void setJobList(List<Jobs> jobList) {
        this.jobList = jobList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }


    public void search() {
        try {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, "Searching for {0}", keyword);
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
            final QueryBuilder b = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Jobs.class).get();
            org.apache.lucene.search.Query luceneQuery = b.keyword()
                    .fuzzy()
                    .withThreshold(0.8f)
                    .withPrefixLength(1)
                    .onField("title").boostedTo(6)
                    .andField("referenceno").boostedTo(5)
                    .andField("summary").boostedTo(4)
                    .andField("jobdescription").boostedTo(4)
                    .andField("qualifications").boostedTo(3)
                    .andField("qualifications").boostedTo(2)
                    .andField("instructions").boostedTo(1)
                    .matching(keyword).createQuery();
            javax.persistence.Query fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery,Jobs.class);
            jobList = (List<Jobs>) fullTextQuery.getResultList();
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, "Searching for {0}", String.valueOf(jobList.size()));
            setRecordCount(jobList.size());
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
