/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import clases.Conexion;
import dao.usuarioDAO;
import jakarta.servlet.http.HttpSession;
import models.Usuario;

/**
 *
 * @author raula
 */
@WebServlet(name = "CambiosPerfilServlet", urlPatterns = {"/CambiosPerfilServlet"})
public class CambiosPerfilServlet extends HttpServlet {

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
            out.println("<title>Servlet CambiosPerfilServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CambiosPerfilServlet at " + request.getContextPath() + "</h1>");
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
        int id = (int) request.getSession().getAttribute("idUsuario");
        
        String usuario = request.getParameter("usuario");
        String biografia = request.getParameter("biografia");
        String nombres = request.getParameter("nombre");
        String paterno = request.getParameter("paterno");
        String materno = request.getParameter("materno");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        
        try{
            Conexion conn = new Conexion();
            Usuario user = new Usuario();
            usuarioDAO udao = new usuarioDAO(conn.Conectar());
            user = udao.usuarioPorId(id);
            
            if(user != null){
                user.setUsuario(usuario);
                user.setDescripcion(biografia);
                user.setNombres(nombres);
                user.setPaterno(paterno);
                user.setMaterno(materno);
                user.setCorreo(email);
                user.setContraseña(pass);
                if(udao.updateUsuario(user)){
                    user = udao.usuarioPorId(id); // Actualiza al usuario
                    
                    HttpSession session = request.getSession(false); // sin crar una nueva sesión
                    //session.invalidate();
                    //session = request.getSession(true);
                    
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
                    
                    //Metodo para redireccionar a otra vista/Servlet
                response.sendRedirect("Frontend/PROFILE.jsp");
                }
                    
            }
            
        }catch (Exception e) {
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
