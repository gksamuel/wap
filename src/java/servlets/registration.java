/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Content;
import entities.Groups;
import entities.Lostpassword;
import entities.Registration;
import entities.Salary;
import entities.Seekers;
import entities.Status;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
import utils.Email;
import utils.Encode;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "registration", urlPatterns = {"/registration"})
public class registration extends HttpServlet {

    private static final long serialVersionUID = 6939602432869025647L;
    private UserTransaction utx;
    private EntityManager em;

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
                Class cls = Class.forName(registration.class.getName());
                Class partypes[] = new Class[1];
                partypes[0] = HttpServletRequest.class;
                Method meth = cls.getMethod(request.getParameter("registerActions"), partypes);
                registration methobj = new registration();
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

    public String register(HttpServletRequest request) {
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            HttpSession session = request.getSession();
            Utils utilSession = (Utils) session.getAttribute("utilSession");
            if (utilSession == null) {
                utilSession = new Utils();
            }
            if(username.contains(" ")){
                utilSession.setMessage("Please remove spaces from your username");
            }
            if (email.length() == 0) {
                utilSession.setMessage("Please enter your email address");
            }
            if (isValidEmailAddress(email) == false) {
                utilSession.setMessage("Email is not valid");
            }
            if (password.compareTo(password2) != 0) {
                utilSession.setMessage("Passwords are not the same.");
            }
            if (password.length() < 6) {
                utilSession.setMessage("Passwords should be at least 6 characters.");

            }
            if (em.createNamedQuery("Seekers.findByUsername").setParameter("username", username).setMaxResults(1).getResultList().iterator().hasNext()
                    || em.createNamedQuery("Registration.findByUsername").setParameter("username", username).setMaxResults(1).getResultList().iterator().hasNext()) {
                utilSession.setMessage("Username is already in use.");
            }

            if (em.createNamedQuery("Seekers.findByEmail").setParameter("email", email).setMaxResults(1).getResultList().iterator().hasNext()
                    || em.createNamedQuery("Registration.findByEmail").setParameter("email", email).setMaxResults(1).getResultList().iterator().hasNext()) {
                utilSession.setMessage("Email is already in use.");
            }
            if (utilSession.getMessage().length() > 0) {
                session.setAttribute("utilSession", utilSession);
                return "/m/registration/index.jsp";
            }
            Registration registration = new Registration();
            registration.setEmail(email);
            registration.setUsername(username);
            registration.setCreatedate(new Date());
            registration.setPassphrase(password);
            registration.setPassword(new Encode().endodesha1(password));
            registration.setCode(UUID.randomUUID().toString().substring(0, 6));
            registration.setGroupid((Groups) em.createNamedQuery("Groups.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult());
            em.persist(registration);
            Content content = (Content) em.createNamedQuery("Content.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
            Email regEmail = new Email();
            regEmail.sendRegistrationEmail(registration, content);
            utilSession.setMessage("Please check your email to proceed (check your spam too!)");
            session.setAttribute("utilSession", utilSession);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/m/registration/complete.jsp";
    }

    public String completeRegistration(HttpServletRequest request) {
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            String code = request.getParameter("code");
            String username = request.getParameter("username");
                Logger.getLogger(registration.class.getName()).log(Level.SEVERE, "Username = {0}", new Object[]{username});
//            String password = new Encode().endodesha1(request.getParameter("password"));
            List<Registration> registrationList = (List<Registration>) em.createNamedQuery("Registration.findByRecord").setParameter("code", code).setParameter("username", username).setMaxResults(1).getResultList();
            if (registrationList.size() == 1) {
                Logger.getLogger(registration.class.getName()).log(Level.SEVERE, "Status = {0}", new Object[]{"we found one?"});
                Registration registration = registrationList.get(0);
                String password = registration.getPassphrase();
                password = new Encode().endodesha1(password);
                Calendar newDate = Calendar.getInstance();
                newDate.add(Calendar.DAY_OF_MONTH, 30);
                Seekers seeker = new Seekers();
                seeker.setExpiry(newDate.getTime());
                seeker.setEmail(registration.getEmail());
                seeker.setUsername(registration.getUsername());
                seeker.setPassword(password);
                Salary salary = (Salary) em.createNamedQuery("Salary.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                salary.setId(1);
                seeker.setSalaryid(salary);
                Status status = (Status) em.createNamedQuery("Status.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                seeker.setStatusid(status);
                seeker.setDatemodified(new Date());
                seeker.setDatecreated(new Date());
                Groups group = (Groups) em.createNamedQuery("Groups.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                seeker.setGroupid(group);
                Logger.getLogger(registration.class.getName()).log(Level.SEVERE, "Expiry = {0}", new Object[]{seeker.getExpiry()});
                Logger.getLogger(registration.class.getName()).log(Level.SEVERE, "Modified = {0}", new Object[]{seeker.getDatemodified()});

                em.persist(seeker);
                em.remove(registration);
                em.flush();
                utx.commit();

                HttpSession session = request.getSession();
                Seeker seekerSession = new Seeker();
                seekerSession.setUsername(seeker.getUsername());
                seekerSession.setId(seeker.getId());
                seekerSession.setExpiry(seeker.getExpiry());
                session.setAttribute("userSession", seekerSession);
                return "/m/home.jsp";
            }
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        Utils utilSession = new Utils();
        utilSession.setMessage("Failed to verify your registration. Please try again later");
        HttpSession session = request.getSession();
        session.setAttribute("utilSession", utilSession);
        return "/m/registration/complete.jsp";
    }

    public String forgotPassword(HttpServletRequest request) {
        Utils utilSession = new Utils();
        utilSession.setMessage("Please check your email to proceed (check your spam too!)");
        String page = "/m/registration/complete.jsp";
        try {
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            List<Seekers> seekers = (List<Seekers>) em.createNamedQuery("Seekers.findByEmail").setParameter("email", request.getParameter("email")).setMaxResults(1).getResultList();
            if (seekers.size() == 1) {
                Seekers seeker = (Seekers) seekers.get(0);
                Lostpassword lostPassword = new Lostpassword();
                lostPassword.setCode(UUID.randomUUID().toString().substring(0, 6));
                lostPassword.setDatemodified(new Date());
                lostPassword.setUsername(seeker.getUsername());
                em.persist(lostPassword);
                Email email = new Email();
                email.sendPasswordEmail(lostPassword, seeker.getEmail());
            } else {
                page = "/m/registration/forgot.jsp";
                utilSession.setMessage("Email address not found");
            }
            utx.commit();
            HttpSession session = request.getSession();
            session.setAttribute("utilSession", utilSession);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return page;
    }

    public String completeReset(HttpServletRequest request) {
        String page = "/m/registration/resetPassword.jsp?asdewedhyfse=" + request.getParameter("code");
        Logger.getLogger(registration.class.getName()).log(Level.OFF, page);
        try {
            String code = request.getParameter("code");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            HttpSession session = request.getSession();
            Utils utilSession = (Utils) session.getAttribute("utilSession");
            if (password.compareTo(password2) != 0) {
                utilSession.setMessage("Passwords are not the same.");
                return page;
            }
            if (password.length() < 6) {
                utilSession.setMessage("Passwords should be at least 6 characters.");
                return page;

            }
            Db db = new Db(utx, em);
            utx = db.getUtx();
            em = db.getEm();
            utx.begin();
            List<Lostpassword> lostPasswords = (List<Lostpassword>) em.createNamedQuery("Lostpassword.findRecord").setParameter("code", code).setParameter("username", username).setMaxResults(1).getResultList();
            if (lostPasswords.isEmpty()) {
                utilSession.setMessage("Could not find record");
                return page;
            }

            if (lostPasswords.size() == 1) {
                Lostpassword lostPassword = lostPasswords.get(0);
                Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findByUsername").setParameter("username", username).setMaxResults(1).getSingleResult();
                seeker.setPassword(new Encode().endodesha1(password));
                em.persist(seeker);
                em.remove(lostPassword);
                em.flush();
                page = "/m/home.jsp";
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return page;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
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
}
