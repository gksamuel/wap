/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Mpesa;
import entities.Mpesatarrifs;
import entities.Seekers;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import utils.Db;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "MpesaActions", urlPatterns = {"/MpesaActions"})
public class MpesaActions extends HttpServlet {

    private static final long serialVersionUID = -1673843957186817794L;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                Class cls = Class.forName(MpesaActions.class.getName());
                Class partypes[] = new Class[1];
                partypes[0] = HttpServletRequest.class;
                Method meth = cls.getMethod(request.getParameter("mpesaAction"), partypes);
                MpesaActions methobj = new MpesaActions();
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

    public String getPayment(HttpServletRequest request) {
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            String mobileno = request.getParameter("mobileno");
            mobileno = mobileno.trim();
            if (validate(mobileno) == true || mobileno.length() == 0) {
                errorMessage = "The number you entered is not valid";
                setMessage(request, errorMessage);
                return "/m/payments/create.jsp";
            }

            if (mobileno.length() == 10) {
                mobileno = "254" + mobileno.substring(1, mobileno.length());
            }
            if (mobileno.length() == 9) {
                mobileno = "254" + mobileno;
            }
            List<Mpesa> payments = (List<Mpesa>) em.createNamedQuery("Mpesa.findOne").setParameter("source", Long.valueOf(mobileno)).setMaxResults(1).getResultList();
            System.out.println("payments found = " + payments.size());
            if (payments.size() > 0) {
                Mpesa payment = payments.get(0);
                List<Mpesatarrifs> tarrifs = (List<Mpesatarrifs>) em.createNamedQuery("Mpesatarrifs.findAll").getResultList();
                Iterator i = tarrifs.iterator();
                BigDecimal days = BigDecimal.valueOf(Long.valueOf("0"));
                BigDecimal amount = payment.getAmount();
                Mpesatarrifs tarrif = new Mpesatarrifs();
                while (i.hasNext()) {
                    tarrif = (Mpesatarrifs) i.next();
                    BigDecimal[] result = amount.divideAndRemainder(BigDecimal.valueOf((long) tarrif.getMembership()));
                    if (result[0].scale() > 0) {
                        amount = result[1];
                        days = days.add(result[0].multiply(BigDecimal.valueOf((long) tarrif.getDays())));
                    }
                }
                days = days.add(BigDecimal.valueOf((double) tarrif.getDays()).multiply(amount.divide(BigDecimal.valueOf((double) tarrif.getMembership()))));
                Calendar newDate = Calendar.getInstance();
                Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(request.getParameter("id"))).getSingleResult();
                payment.setFromtime(seeker.getExpiry());
                if (seeker.getExpiry().before(newDate.getTime())) {
                    newDate.setTime(new Date());
                    newDate.add(Calendar.DAY_OF_MONTH, days.intValue());
                    seeker.setExpiry(newDate.getTime());
                }
                if (seeker.getExpiry().after(newDate.getTime())) {
                    newDate.setTime(seeker.getExpiry());
                    newDate.add(Calendar.DAY_OF_MONTH, days.intValue());
                    seeker.setExpiry(newDate.getTime());
                }
                payment.setProcessed(true);
                payment.setTotime(seeker.getExpiry());
                payment.setSeekerid(seeker);
                payment.setProcesstime(new Date());
                em.persist(seeker);
                em.persist(payment);
                utx.commit();
                Seeker seekerSession = new Seeker();
                seekerSession.setUsername(seeker.getUsername());
                seekerSession.setId(seeker.getId());
                seekerSession.setMobile(seeker.getMobile());
                seekerSession.setExpiry(seeker.getExpiry());
                HttpSession session = request.getSession();
                session.setAttribute("userSession", seekerSession);
                errorMessage = "Payment processed successfully";
                setMessage(request, errorMessage);
                return "/m/payments/index.jsp";
            }
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | NumberFormatException ex) {
            errorMessage = "Could not process payment, please try again laterz!";
            setMessage(request, errorMessage);
            Logger.getLogger(MpesaActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        errorMessage = "Could not process payment, please try again laterz!";
        setMessage(request, errorMessage);
        return "/m/payments/create.jsp";
    }

    public Boolean validate(String input) {
        Pattern p = Pattern.compile("((.*)\\D(.*))");
        Matcher m = p.matcher(input);
        return m.matches();
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

    public void setMessage(HttpServletRequest request, String message) {
        HttpSession session = request.getSession();
        Utils utilSession = (Utils) session.getAttribute("utilSession");
        if (utilSession == null) {
            utilSession = new Utils();
        }
        utilSession.setMessage(message);
    }
}
