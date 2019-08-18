/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Seekers;
import entities.Work;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import sessions.Utils;
import utils.Db;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "workActions", urlPatterns = {"/workActions"})
public class workActions extends HttpServlet {

    private static final long serialVersionUID = 297849619576959638L;
    private UserTransaction utx;
    private EntityManager em;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
    private String errorMessage = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(request.getParameter("workActions"));
            try {
                Class cls = Class.forName(workActions.class.getName());
                Class partypes[] = new Class[1];
                partypes[0] = HttpServletRequest.class;
                Method meth = cls.getMethod(request.getParameter("workActions"), partypes);
                workActions methobj = new workActions();
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

    public String updateWork(HttpServletRequest request) {
        if (validateWork(request) == false) {
            return "/m/work/edit.jsp?id=" + request.getParameter("id");
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Work work = (Work) em.createNamedQuery("Work.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            work.setStartdate(sdf.parse("01 " + request.getParameter("startmonth") + " " + request.getParameter("startyear")));
            work.setEnddate(sdf.parse("01 " + request.getParameter("endmonth") + " " + request.getParameter("endyear")));
            work.setCompany(request.getParameter("company"));
            work.setWebsite(request.getParameter("website"));
            work.setAddress(request.getParameter("address"));
            work.setCompanyphone(request.getParameter("companyphone"));
            work.setLocation(request.getParameter("location"));
            work.setPosition(request.getParameter("position"));
            work.setJobdescription(request.getParameter("jobdescription"));
            work.setRefereename(request.getParameter("refereename"));
            work.setRefereeemail(request.getParameter("refereeemail"));
            work.setRefereemobile(request.getParameter("refereemobile"));
            work.setDatemodified(new Date());
            em.persist(work);
            utx.commit();

            errorMessage = "Record updated successfully";
            setMessage(request, errorMessage);
            return "/m/work/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | ParseException ex) {
            Logger.getLogger(workActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public String createWork(HttpServletRequest request) {
        try {
            if (validateWork(request) == false) {
                return "/m/work/create.jsp";
            }
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Work work = new Work();
            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            work.setSeekerid(seeker);
            work.setStartdate(sdf.parse("01 " + request.getParameter("startmonth") + " " + request.getParameter("startyear")));
            work.setEnddate(sdf.parse("01 " + request.getParameter("endmonth") + " " + request.getParameter("endyear")));
            work.setCompany(request.getParameter("company"));
            work.setWebsite(request.getParameter("website"));
            work.setAddress(request.getParameter("address"));
            work.setCompanyphone(request.getParameter("companyphone"));
            work.setLocation(request.getParameter("location"));
            work.setPosition(request.getParameter("position"));
            work.setJobdescription(request.getParameter("jobdescription"));
            work.setRefereename(request.getParameter("refereename"));
            work.setRefereeemail(request.getParameter("refereeemail"));
            work.setRefereemobile(request.getParameter("refereemobile"));
            work.setDatemodified(new Date());
            em.persist(work);
            utx.commit();
            errorMessage = "Record created successfully";
            setMessage(request, errorMessage);
            return "/m/work/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | ParseException ex) {
            Logger.getLogger(workActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public boolean validateWork(HttpServletRequest request) {
        try {
            if (request.getParameter("position").length() == 0) {
                errorMessage = errorMessage + "Please enter your job title.<br/>";
            }
            if (request.getParameter("company").length() == 0) {
                errorMessage = errorMessage + "Please enter the company name.<br/>";
            }
            if (sdf.parse("01 " + request.getParameter("startmonth") + " " + request.getParameter("startyear")).after(sdf.parse("01 " + request.getParameter("endmonth") + " " + request.getParameter("endyear")))) {
                errorMessage = errorMessage + "Your start date is later than your end date.<br/>";
            }
            if (errorMessage.length() > 0) {
                setMessage(request, errorMessage);
                return false;
            }
        } catch (ParseException ex) {
            Logger.getLogger(workActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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
