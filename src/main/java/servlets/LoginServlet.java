/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import clases.Conexion;
import dao.usuarioDAO;
import dao.tarea_usuarioDAO;
import dao.chatDAO;

import models.Usuario;
import models.Tarea_Usuario;
import models.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.HashSet;

/**
 *
 * @author raula
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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

        String usuario = request.getParameter("user");
        String contraseña = request.getParameter("pwrd");

        try {
            Conexion conn = new Conexion();
            usuarioDAO uDao = new usuarioDAO(conn.Conectar());
            tarea_usuarioDAO tareaUsuario = new tarea_usuarioDAO(conn.Conectar());
            chatDAO cDao = new chatDAO(conn.Conectar());
            
            Usuario user = uDao.buscarUsuario(usuario, contraseña);
            
            if (user != null) {
                int idUsuario = user.getIdUsuario();
                
                HashSet<Tarea_Usuario> tareas = tareaUsuario.getTareas(idUsuario);
                List<Usuario> usuarios = uDao.getAllUsuarios(idUsuario);
                HashSet<Chat> chats = cDao.getChatsUsuario(idUsuario);
                
                HttpSession session = request.getSession();
                session.setAttribute("idUsuario", user.getIdUsuario());
                session.setAttribute("Usuario", user.getUsuario());
                session.setAttribute("Nombres", user.getNombres());
                session.setAttribute("Paterno", user.getPaterno());
                session.setAttribute("Materno", user.getMaterno());
                session.setAttribute("Email", user.getCorreo());
                session.setAttribute("Nacimiento", user.getFechaNacimiento());
                session.setAttribute("Registro", user.getFechaRegistro());
                session.setAttribute("Descripcion", user.getDescripcion());
                session.setAttribute("Contraseña", user.getContraseña());
                session.setAttribute("Avatar", user.getAvatar());
                
                session.setAttribute("ListaUsuarios", usuarios); // Lista de usuarios disponibles
                session.setAttribute("ListaTareas", tareas); // Lista de tareas del usuario que se logeó
                session.setAttribute("ListaChats", chats); // Lista de chats a los que pertenece el usuario actual

                //Metodo para redireccionar a otra vista/Servlet
                response.sendRedirect("Frontend/MAIN.jsp");
            } else {
                // Redirigir a LOGIN.jsp con un parámetro de error
                response.sendRedirect("Frontend/LOGIN.jsp?error=1");
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
