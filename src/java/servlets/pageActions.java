/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Pages;
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
@WebServlet(name = "pageActions", urlPatterns = {"/pageActions"})
public class pageActions extends HttpServlet {

    private static final long serialVersionUID = 5695248992508774119L;
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
            Class cls = Class.forName(pageActions.class.getName());
            Class partypes[] = new Class[1];
            partypes[0] = HttpServletRequest.class;
            Method meth = cls.getMethod(request.getParameter("pageAction"), partypes);
            pageActions methobj = new pageActions();
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

    public String updatePage(HttpServletRequest request) {
        if (validatePage(request) == false) {
            return "/m/pages/edit.jsp?id=" + request.getParameter("id");
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Pages page = (Pages) em.createNamedQuery("Pages.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            page.setTitle(request.getParameter("title"));
            page.setContent(request.getParameter("content"));
            page.setDatemodified(new Date());
            em.persist(page);
            utx.commit();
            errorMessage = "Record updated successfully";
            setMessage(request, errorMessage);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(pageActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/m/pages/index.jsp";

    }

    public String createPage(HttpServletRequest request) {
        if (validatePage(request) == false) {
            return "/m/pages/create.jsp";
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            Pages page = new Pages();
            page.setSeekerid(seeker);
            page.setTitle(request.getParameter("title"));
            page.setContent(request.getParameter("content"));
            page.setDatemodified(new Date());
            em.persist(page);
            utx.commit();
            errorMessage = "Record created successfully";
            setMessage(request, errorMessage);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(pageActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/m/pages/index.jsp";
    }

    public boolean validatePage(HttpServletRequest request) {
        if (request.getParameter("title").length() == 0) {
            errorMessage = errorMessage + "Please enter the page title.<br/>";
        }
        if (request.getParameter("content").length() == 0) {
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
