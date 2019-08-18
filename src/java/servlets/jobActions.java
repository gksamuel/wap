/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Abuse;
import entities.Applications;
import entities.Coverletters;
import entities.Jobs;
import entities.Seekers;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessions.Seeker;
import sessions.Utils;
import sessions.Work;
import utils.Db;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "jobActions", urlPatterns = {"/jobActions"})
public class jobActions extends HttpServlet {

    private static final long serialVersionUID = -6873610562754174239L;
    private UserTransaction utx;
    private EntityManager em;
    private String errorMessage = "";
    private Abuse abuse;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                Class cls = Class.forName(jobActions.class.getName());
                Class partypes[] = new Class[1];
                partypes[0] = HttpServletRequest.class;
                Method meth = cls.getMethod(request.getParameter("jobAction"), partypes);
                jobActions methobj = new jobActions();
                Object arglist[] = new Object[1];
                arglist[0] = request;
                Object retobj = meth.invoke(methobj, arglist);
                String retval = (String) retobj;
                response.sendRedirect(retval);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                System.err.println(e);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String applyJob(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Seeker seekerSession = (Seeker) session.getAttribute("userSession");
        Work jobSession = (Work) session.getAttribute("jobSession");
        if (jobSession == null) {
            jobSession = new Work();
            session.setAttribute("jobSession", jobSession);
        }
        try {

            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Jobs job = (Jobs) em.createNamedQuery("Jobs.findById").setParameter("id", Integer.valueOf(request.getParameter("jobid"))).getSingleResult();
            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", seekerSession.getId()).getSingleResult();

            List<Applications> applications = (List<Applications>) em.createNamedQuery("Applications.findApplication")
                    .setParameter("jobid", job)
                    .setParameter("seekerid", seeker)
                    .getResultList();

            Logger.getLogger(jobActions.class.getName()).log(Level.SEVERE, "Results = {0}", new Object[]{String.valueOf(applications.size())});
            if (applications.isEmpty()) {
                Applications application = new Applications();
                Integer letterID = Integer.valueOf(request.getParameter("letter"));
                String letterContents = "";
                if (letterID > 0) {
                    Coverletters letter = (Coverletters) em.createNamedQuery("Coverletters.findById").setParameter("id", letterID).getSingleResult();
                    letterContents = letter.getLetter();

                }
                application.setLetter(letterContents);
                application.setJobid(job);
                application.setSeekerid(seeker);
                application.setDatemodified(new Date());
                em.persist(application);
                utx.commit();
                jobSession.setMessage("Your application has been sent");
                session.setAttribute("jobSession", jobSession);
            } else {
                jobSession.setMessage("You have already applied for this job");
                session.setAttribute("jobSession", jobSession);
            }
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(jobActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/m/jobs/applied.jsp";
    }

    public String reportAbuse(HttpServletRequest request) {
        errorMessage = "Your feedback has been ";
        try {
            if (request.getParameter("comment").length() == 0) {
                errorMessage = errorMessage + "Please enter your observations.";
                setMessage(request, errorMessage);
            } else {
                HttpSession session = request.getSession();
                Seeker seekerSession = (Seeker) session.getAttribute("userSession");
                Logger.getLogger(jobActions.class.getName()).log(Level.SEVERE, "User id is {0}", String.valueOf(seekerSession.getId()));
                Db db = new Db(utx, em);
                utx = db.getUtx();
                em = db.getEm();
                utx.begin();
                Seekers user = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", seekerSession.getId()).getSingleResult();
                Jobs job = (Jobs) em.createNamedQuery("Jobs.findById").setParameter("id", Integer.valueOf(request.getParameter("jobid"))).getSingleResult();
                List<Abuse> abuseList = (List<Abuse>) em.createNamedQuery("Abuse.findAbuse").setParameter("jobid", job).setParameter("seekerid", user).setMaxResults(1).getResultList();
                if (abuseList.size() > 0) {
                    abuse = (Abuse) abuseList.get(0);
                    errorMessage = errorMessage.concat("updated");
                } else {
                    abuse = new Abuse();
                    errorMessage = errorMessage.concat("submitted");
                }
                abuse.setSeekerid(user);
                abuse.setComment(request.getParameter("comment"));
                abuse.setJobid(job);
                abuse.setDatesubmitted(new Date());
                em.merge(abuse);
                utx.commit();
                setMessage(request, errorMessage);
            }
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(jobActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        String returnPage = "/m/jobs/job.jsp?id=" + request.getParameter("jobid");
        return returnPage;
    }

    public void setMessage(HttpServletRequest request, String message) {
        HttpSession session = request.getSession();
        Utils utilSession = (Utils) session.getAttribute("utilSession");
        if (utilSession == null) {
            utilSession = new Utils();
        }
        utilSession.setMessage(message);
    }
}
