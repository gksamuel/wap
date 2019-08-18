/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Education;
import entities.Seekers;
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
@WebServlet(name = "educationActions", urlPatterns = {"/educationActions"})
public class educationActions extends HttpServlet {

    private static final long serialVersionUID = 3125824195538124298L;
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
            try {
                Class cls = Class.forName(educationActions.class.getName());
                Class partypes[] = new Class[1];
                partypes[0] = HttpServletRequest.class;
                Method meth = cls.getMethod(request.getParameter("educationAction"), partypes);
                educationActions methobj = new educationActions();
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

    public String updateInstitution(HttpServletRequest request) {
        if (validateEducation(request) == false) {
            return "/m/education/edit.jsp?id=" + request.getParameter("id");
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Education education = (Education) em.createNamedQuery("Education.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            education.setStartdate(sdf.parse("01 " + request.getParameter("startmonth") + " " + request.getParameter("startyear")));
            education.setEnddate(sdf.parse("01 " + request.getParameter("endmonth") + " " + request.getParameter("endyear")));
            education.setInstitution(request.getParameter("institution"));
            education.setWebsite(request.getParameter("website"));
            education.setLocation(request.getParameter("location"));
            education.setCertification(request.getParameter("certification"));
            education.setRefereename(request.getParameter("refereename"));
            education.setAddress(request.getParameter("address"));
            education.setEmail(request.getParameter("email"));
            education.setMobileno(request.getParameter("mobileno"));
            education.setDatemodified(new Date());
            em.persist(education);
            utx.commit();
            errorMessage = "Record updated successfully";
            setMessage(request, errorMessage);
            return "/m/education/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | ParseException ex) {
            Logger.getLogger(educationActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public String createInstitution(HttpServletRequest request) {
        if (validateEducation(request) == false) {
            return "/m/education/create.jsp";
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Education education = new Education();
            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            Logger.getLogger(educationActions.class.getName()).log(Level.SEVERE, request.getParameter("startmonth"));
            Logger.getLogger(educationActions.class.getName()).log(Level.SEVERE, request.getParameter("startyear"));
            education.setSeekerid(seeker);
            education.setStartdate(sdf.parse("01 " + request.getParameter("startmonth") + " " + request.getParameter("startyear")));
            education.setEnddate(sdf.parse("01 " + request.getParameter("endmonth") + " " + request.getParameter("endyear")));
            education.setInstitution(request.getParameter("institution"));
            education.setWebsite(request.getParameter("website"));
            education.setLocation(request.getParameter("location"));
            education.setCertification(request.getParameter("certification"));
            education.setRefereename(request.getParameter("refereename"));
            education.setAddress(request.getParameter("address"));
            education.setEmail(request.getParameter("email"));
            education.setMobileno(request.getParameter("mobileno"));
            education.setDatemodified(new Date());
            em.persist(education);
            utx.commit();
            errorMessage = "Record created successfully";
            setMessage(request, errorMessage);
            return "/m/education/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | ParseException ex) {
            Logger.getLogger(educationActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public boolean validateEducation(HttpServletRequest request) {
        try {

            if (request.getParameter("institution").length() == 0) {
                errorMessage = errorMessage + "Please enter the institution.<br/>";
            }
            if (request.getParameter("certification").length() == 0) {
                errorMessage = errorMessage + "Please enter the certification.<br/>";
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
