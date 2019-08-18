/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Coverletters;
import entities.Seekers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
@WebServlet(name = "letterActions", urlPatterns = {"/letterActions"})
public class letterActions extends HttpServlet {

    private static final long serialVersionUID = -7463230283261312103L;
    private UserTransaction utx;
    private EntityManager em;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Class cls = Class.forName(letterActions.class.getName());
            Class partypes[] = new Class[1];
            partypes[0] = HttpServletRequest.class;
            Method meth = cls.getMethod(request.getParameter("letterAction"), partypes);
            letterActions methobj = new letterActions();
            Object arglist[] = new Object[1];
            arglist[0] = request;
            Object retobj = meth.invoke(methobj, arglist);
            String retval = (String) retobj;
            response.sendRedirect(retval);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.err.println(e);
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

    public String updateLetter(HttpServletRequest request) {
        if (validateLetter(request) == false) {
            return "/m/letters/edit.jsp?id=" + request.getParameter("id");
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Coverletters letter = (Coverletters) em.createNamedQuery("Coverletters.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            letter.setName(request.getParameter("name"));
            letter.setLetter(request.getParameter("letter"));
            letter.setDatemodified(new Date());
            em.persist(letter);
            utx.commit();
            errorMessage = "Record updated successfully";
            setMessage(request, errorMessage);
            return "/m/letters/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(letterActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public String createLetter(HttpServletRequest request) {
        if (validateLetter(request) == false) {
            return "/m/letters/create.jsp";
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Coverletters coverLetter = new Coverletters();
            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            coverLetter.setSeekerid(seeker);
            coverLetter.setName(request.getParameter("name"));
            coverLetter.setLetter(request.getParameter("letter"));
            coverLetter.setDatemodified(new Date());
            em.persist(coverLetter);
            utx.commit();
            errorMessage = "Record created successfully";
            setMessage(request, errorMessage);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(educationActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/m/letters/index.jsp";
    }

    public boolean validateLetter(HttpServletRequest request) {
        if (request.getParameter("name").length() == 0) {
            errorMessage = errorMessage + "Please enter the name.<br/>";
        }
        if (request.getParameter("letter").length() == 0) {
            errorMessage = errorMessage + "Please enter the content.<br/>";
        }

        if (errorMessage.length() > 0) {
            setMessage(request, errorMessage);
            return false;
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
