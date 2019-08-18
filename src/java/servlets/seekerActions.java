/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Salary;
import entities.Seekers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import sessions.Utils;
import utils.Db;
import utils.Encode;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "seekerActions", urlPatterns = {"/seekerActions"})
public class seekerActions extends HttpServlet {

    private static final long serialVersionUID = 297849619576959638L;
    private UserTransaction utx;
    private EntityManager em;
    private Seekers seeker;
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
        try {
            Class cls = Class.forName(seekerActions.class.getName());
            Class partypes[] = new Class[1];
            partypes[0] = HttpServletRequest.class;
            Method meth = cls.getMethod(request.getParameter("seekerAction"), partypes);
            seekerActions methobj = new seekerActions();
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

    public String updateProfile(HttpServletRequest request) {
        if (request.getParameter("firstname").length() == 0) {
            errorMessage = errorMessage + "Please enter your first name.<br/>";
        }
        if (request.getParameter("lastname").length() == 0) {
            errorMessage = errorMessage + "Please enter your last name.<br/>";
        }

        if (errorMessage.length() > 0) {
            setMessage(request, errorMessage);
            return "/m/profile/index.jsp";
        }
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
            seeker.setMobile(request.getParameter("mobile"));
            seeker.setOthernumber(request.getParameter("othernumber"));
            seeker.setFirstname(request.getParameter("firstname"));
            seeker.setLastname(request.getParameter("lastname"));
            seeker.setMiddlename(request.getParameter("middlename"));
            seeker.setAddress(request.getParameter("address"));
            seeker.setDateofbirth(sdf.parse("01 " + request.getParameter("birthmonth") + " " + request.getParameter("birthyear")));
            seeker.setGender(Boolean.valueOf(request.getParameter("gender")));
            seeker.setWebsite(request.getParameter("website"));
            seeker.setMaritalstatus(request.getParameter("maritalstatus"));
            seeker.setIdnumber(request.getParameter("idnumber"));
            seeker.setPinnumber(request.getParameter("pinnumber"));
            seeker.setPassport(request.getParameter("passport"));
            seeker.setDriverslicence(request.getParameter("driverslicence"));
            Salary salary = (Salary) em.createNamedQuery("Salary.findByID").setParameter("id", Integer.valueOf(request.getParameter("salaryid"))).getSingleResult();
            seeker.setSalaryid(salary);
            seeker.setDatemodified(new Date());
            em.persist(seeker);
            em.flush();
            utx.commit();
            errorMessage = "Profile updated successfully";
            setMessage(request, errorMessage);
            return "/m/profile/index.jsp";
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | ParseException ex) {
            Logger.getLogger(seekerActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request.getParameter("id");
    }

    public String updatePassword(HttpServletRequest request) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPassword2 = request.getParameter("newPassword2");

        if (oldPassword.length() < 6 || newPassword.length() < 6 || newPassword2.length() < 6) {
            errorMessage = errorMessage + "Passwords should be at least six characters<br/>";
        }
        if (newPassword.compareTo(newPassword2) != 0) {
            errorMessage = errorMessage + "The passwords you have entered are not the same.<br/>";
        }

        if (errorMessage.length() > 0) {
            setMessage(request, errorMessage);
            return "/m/profile/password.jsp";
        }
        if (newPassword.compareTo(newPassword2) == 0) {
            Encode encode = new Encode();
            try {
                Db db = new Db(utx, em);
                utx = db.getUtx();
                em = db.getEm();
                utx.begin();
                List<Seekers> seekers = (List<Seekers>) em.createNamedQuery("Seekers.findByPassword").setParameter("password", encode.endodesha1(oldPassword)).setParameter("id", Integer.valueOf(request.getParameter("id"))).getResultList();
                if (seekers.size() == 1) {
                    seeker = seekers.get(0);
                    seeker.setPassword(encode.endodesha1(newPassword));
                    seeker.setDatemodified(new Date());
                    em.persist(seeker);
                    utx.commit();
                } else {
                    errorMessage = "Could not find your record";
                    setMessage(request, errorMessage);
                    return "/m/profile/password.jsp";
                }
                errorMessage = "Password updated successfully";
                setMessage(request, errorMessage);
                return "/m/home.jsp";
            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(seekerActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "/m/home.jsp";
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
