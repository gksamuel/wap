/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Groups;
import entities.Salary;
import entities.Seekers;
import entities.Status;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
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
import utils.Db;
import utils.Encode;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "GoogleProcessor", urlPatterns = {"/GoogleProcessor"})
public class GoogleProcessor extends HttpServlet {

    private Seekers seeker;
    private static String salt = "3d274hSDF#";
    private Encode encode = new Encode();
    private EntityManager em;
    private UserTransaction utx;

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
        String redirect = null;
        response.setContentType("text/html;charset=UTF-8");
        Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, request.getHeader("referer"));
        Enumeration e = request.getParameterNames();
        Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, request.getParameter("token"));
        try (PrintWriter out = response.getWriter()) {
            String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";
            String token = request.getParameter("token");
            url = url + token;
            URL u = new URL(url);
            HttpsURLConnection httpclient = (HttpsURLConnection) u.openConnection();
            httpclient.setRequestMethod("GET");
            httpclient.setDoOutput(true);
            httpclient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if (httpclient.getResponseCode() == 200) {
                request.logout();
                Db db = new Db(utx, em);
                utx = db.getUtx();
                em = db.getEm();
                utx.begin();
                BufferedReader in = new BufferedReader(new InputStreamReader(httpclient.getInputStream()));
                String inputLine;
                StringBuffer googleresponse = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    googleresponse.append(inputLine);
                }
                in.close();
                String json = googleresponse.toString();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = (HashMap<String, String>) mapper.readValue(json, new TypeReference<Map<String, String>>() {
                });

                String googleEmail = map.get("email");
                Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, googleEmail);
                List<Seekers> currentSeekers = (List<Seekers>) em.createNamedQuery("Seekers.findByEmail").setParameter("email", map.get("email")).setMaxResults(1).getResultList();
                if (currentSeekers.size() == 1) {
                    seeker = currentSeekers.get(0);

                    String password = map.get("sub") + salt + seeker.getEmail();
                    password = encode.endodesha1(password);
                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, encode.endodesha1("seeker"));
                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, password);
                    if ((seeker.getUsername().compareTo(map.get("email")) == 0) && (seeker.getPassword().compareTo(password) == 0)) {
                        utx.commit();
                    } else {
                        seeker.setUsername(seeker.getEmail());
                        seeker.setPassword(password);
                        em.persist(seeker);
                        utx.commit();

                    }
                    redirect = userLogin(seeker.getUsername(), map.get("sub") + salt + map.get("email"), request, response);
                    out.print(redirect);

                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "REDIRECTING TO  = {0}", redirect);
                } else {
                    seeker = new Seekers();
                    seeker.setUsername(map.get("email"));
                    seeker.setEmail(map.get("email"));
                    seeker.setFirstname(map.get("given_name"));
                    seeker.setLastname(map.get("family_name"));
                    seeker.setPassword(encode.endodesha1(map.get("sub") + salt + map.get("email")));
                    seeker.setDatemodified(new Date());
                    Calendar day = Calendar.getInstance();
                    day.add(Calendar.MONTH, 1);
                    seeker.setExpiry(day.getTime());
                    Salary salary = (Salary) em.createNamedQuery("Salary.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                    salary.setId(1);
                    seeker.setSalaryid(salary);
                    Status status = (Status) em.createNamedQuery("Status.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                    seeker.setStatusid(status);
                    seeker.setDatemodified(new Date());
                    seeker.setDatecreated(new Date());
                    Groups group = (Groups) em.createNamedQuery("Groups.findByID").setParameter("id", 1).setMaxResults(1).getSingleResult();
                    seeker.setGroupid(group);
                    em.persist(seeker);
                    utx.commit();
                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "Loggin in new user = {0}", seeker.getUsername());
                    redirect = userLogin(map.get("email"), map.get("sub") + salt + map.get("email"), request, response);
                    out.print(redirect);
                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "ELSE REDIRECTING TO  = {0}", redirect);
                    Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "new user");
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String userLogin(String username, String password, HttpServletRequest r, HttpServletResponse p) {
        Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin for user = {0}", seeker.getUsername());
        Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin try for user = {0}", seeker.getUsername());
        HttpSession session = r.getSession();
        Seeker seekerSession = (Seeker) session.getAttribute("userSession");
        if (seekerSession == null) {
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin null user = {0}", seeker.getUsername());
            seekerSession = new Seeker();
            session.setAttribute("userSession", seekerSession);
        }
        seekerSession = seekerSession.login(username, password);
        if (seekerSession.getId() != null) {
            session.setAttribute("userSession", seekerSession);
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin NOT null user = {0}", seeker.getUsername());
            if (seekerSession.getExpiry().after(new Date())) {
                Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin AFTER null user = {0}", seeker.getUsername());
                return "/m/home.jsp";
            }
            if (seekerSession.getExpiry().before(new Date())) {
                Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin BEFORE null user = {0}", seeker.getUsername());
//                session.removeAttribute("userSession");
                return "/m/expired.jsp";
            }

        } else {
            Logger.getLogger(GoogleProcessor.class.getName()).log(Level.INFO, "userLogin ELSE null user = {0}", seeker.getUsername());
            return "/m/index.jsp";
        }
        return null;
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
