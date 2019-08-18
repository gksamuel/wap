/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entities.Filecategory;
import entities.Files;
import entities.Seekers;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.Db;

/**
 *
 * @author gachanja
 */
@WebServlet(name = "upload", urlPatterns = {"/upload"})
public class upload extends HttpServlet {

    private boolean isMultipart;
    private final String filePath = "/opt/files/";
    private final String quarantinePath = "/opt/quarantine/";
    private int maxFileSize = 3072000;
    private int maxMemSize = 2560000;
    private File file;
    private UserTransaction utx;
    private EntityManager em;
    private File newUploadFile;
    private String name;
    private Boolean virus = false;
    private Boolean isImage = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //TODO: Delete files from folder after deleting record
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Check that we have a file upload request
            isMultipart = ServletFileUpload.isMultipartContent(request);

            response.setContentType("text/html");
//            java.io.PrintWriter out = response.getWriter();
            if (!isMultipart) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet upload</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>No file uploaded</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(maxMemSize);
            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File(filePath));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            upload.setSizeMax(maxFileSize);

            try {
                // Parse the request to get file items.
                try {
                    Db db = new Db(utx, em);
                    utx = db.getUtx();
                    em = db.getEm();
                    utx.begin();
                    Files fileData = new Files();
                    List fileItems = upload.parseRequest(request);

                    // Process the uploaded file items
                    Iterator i = fileItems.iterator();

                    while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();
                        if (!fi.isFormField()) {
                            // Get the uploaded file parameters

                            // Write the file
                            if (fi.getName().lastIndexOf("\\") >= 0) {
                                file = new File(filePath + fi.getName().substring(fi.getName().lastIndexOf("\\")));
                            } else {
                                file = new File(filePath + fi.getName().substring(fi.getName().lastIndexOf("\\") + 1));
                            }

                            newUploadFile = new File(filePath + fi.getName());
                            name = fi.getName();
                            if (newUploadFile.exists()) {
                                Boolean fileExists = true;
                                String baseName = fi.getName().substring(0, fi.getName().lastIndexOf("."));
                                String extension = fi.getName().substring(fi.getName().lastIndexOf(".") + 1, fi.getName().length());
                                int fileCount = 0;
                                while (fileExists.compareTo(true) == 0) {
                                    newUploadFile = new File(filePath + name);
                                    if (newUploadFile.exists()) {
                                        fileCount++;
                                        name = baseName + "_" + fileCount + "." + extension;
                                    } else {
                                        fileExists = false;
                                    }
                                }
                                if (fileCount >= 1) {
                                    name = baseName + "_" + fileCount + "." + extension;
                                }
                            }
                            file = new File(quarantinePath + name);
                            fi.write(file);

                            try {
                                Process p = Runtime.getRuntime().exec(new String[]{"clamscan", quarantinePath + name});
                                p.waitFor();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line = reader.readLine();
                                if (line.substring((line.length() - 2), line.length()).compareTo("OK") == 0) {
                                    p = Runtime.getRuntime().exec(new String[]{"convert", "-resize", "x150", quarantinePath + name, filePath + name});
                                    p.waitFor();
                                    p = Runtime.getRuntime().exec(new String[]{"rm", quarantinePath + name});
                                    p.waitFor();
                                    isImage = true;
                                } else {
                                    p = Runtime.getRuntime().exec(new String[]{"rm", quarantinePath + name});
                                    p.waitFor();
                                    virus = true;

                                }
                            } catch (IOException | InterruptedException e1) {

                            }

                            fileData.setOriginalfilename(fi.getName());
                            fileData.setFilename(name);
                            fileData.setFilesize((int) fi.getSize());
                            fileData.setFiletype(fi.getContentType());
                        }
                        if (fi.getFieldName().compareTo("id") == 0) {
                            Seekers seeker = (Seekers) em.createNamedQuery("Seekers.findById").setParameter("id", Integer.valueOf(fi.getString())).getSingleResult();
                            fileData.setSeekerid(seeker);
                        }
                        if (fi.getFieldName().compareTo("filecategoryid") == 0) {
                            Filecategory fileCategory = (Filecategory) em.createNamedQuery("Filecategory.findOne").setParameter("id", Integer.valueOf(fi.getString())).getSingleResult();
                            fileData.setFilecategoryid(fileCategory);
                        }
                        if (fi.getFieldName().compareTo("description") == 0) {
                            fileData.setDescription(fi.getString());
                        }
                        if (fi.getFieldName().compareTo("viewable") == 0) {
                            if (fi.getString().compareTo("yes") == 0) {
                                fileData.setViewable(true);
                            }
                            if (fi.getString().compareTo("no") == 0) {
                                fileData.setViewable(false);
                            }
                        }
                    }
                    fileData.setDatemodified(new Date());
                    if (virus == false && isImage == true) {
                        em.persist(fileData);
                    }
                    utx.commit();
                    response.sendRedirect("/m/files/index.jsp");

                } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                    response.sendRedirect("/m/files/index.jsp");
                }

            } catch (Exception ex) {
                response.sendRedirect("/m/files/index.jsp");
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

}
