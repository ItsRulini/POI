package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import clases.Conexion;
import dao.usuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Set;
import org.json.JSONObject;
import jakarta.servlet.http.Part;
import java.io.File;

import models.Usuario;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raula
 */
@WebServlet(urlPatterns = {"/RegistroServlet"})
@MultipartConfig // Añadir esta anotación
public class RegistroServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registro at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            // Obtener datos del FormData
            String jsonData = request.getParameter("datos");
            JSONObject json = new JSONObject(jsonData);

            // Obtener datos
            String nombres = json.getString("nombres");
            String apellidoPaterno = json.getString("apellidoPaterno");
            String apellidoMaterno = json.getString("apellidoMaterno");
            String nacimiento = json.getString("nacimiento");
            String usuario = json.getString("usuario");
            String password = json.getString("password");
            String correo = json.getString("correo");

            // Manejar la imagen
            String avatarPath = null;
            Part filePart = request.getPart("imagen");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = usuario + ".jpg";
                String uploadPath = getServletContext().getRealPath("") + "Frontend\\Avatar perfil\\";

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                filePart.write(uploadPath + fileName);
                avatarPath = "Frontend\\Avatar perfil\\" + fileName;
            }

            // Convertir String a java.sql.Date
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(nacimiento);

            Usuario user = new Usuario();
            user.setNombres(nombres);
            user.setPaterno(apellidoPaterno);
            user.setMaterno(apellidoMaterno);
            user.setFechaNacimiento(fechaNacimiento);
            user.setAvatar(avatarPath);
            user.setUsuario(usuario);
            user.setCorreo(correo);
            user.setContraseña(password);

            Conexion conn = new Conexion();
            usuarioDAO uDao = new usuarioDAO(conn.Conectar());

            boolean insert = uDao.insertUsuario(user);

            response.setContentType("application/json");
            if (insert) {
                System.out.println("Usuario registrado: " + usuario);
                response.getWriter().write("{\"status\": \"success\"}");
            } else {
                response.getWriter().write("{\"status\": \"error\", \"message\": \"No se pudo insertar el usuario\"}");
            }

            conn.Desconectar();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
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
