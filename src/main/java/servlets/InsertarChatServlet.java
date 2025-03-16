/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import clases.Conexion;
import dao.chatDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author raula
 */
@WebServlet(name = "InsertarChatServlet", urlPatterns = {"/InsertarChatServlet"})
public class InsertarChatServlet extends HttpServlet {

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
            out.println("<title>Servlet InsertarChatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertarChatServlet at " + request.getContextPath() + "</h1>");
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

        String nombreChat = request.getParameter("nombreChat");
        String[] usuariosSeleccionados = request.getParameterValues("usuariosSeleccionados");
        int idUsuario = (int) request.getSession().getAttribute("idUsuario");
        Boolean chatIndividual = null;

        if (usuariosSeleccionados != null && usuariosSeleccionados.length > 0) {

            if (usuariosSeleccionados.length == 1) { // Solamente se seleccionó a un usuario
                chatIndividual = true;

            } else {
                chatIndividual = false;
            }

            for (String userId : usuariosSeleccionados) {
                System.out.println("Usuario seleccionado: " + userId);
            }
        }

        try {
            Conexion conn = new Conexion();
            chatDAO chdao = new chatDAO(conn.Conectar());

            if (chatIndividual == null) {
                System.out.println("error, no se seleccionó ningún usuario");
                return; // Hay error
            }

            if (chatIndividual == true) {
                int idUsuario2 = Integer.parseInt(usuariosSeleccionados[0]); // id del segundo usuario

                boolean insert = chdao.insertChatPrivado(idUsuario, idUsuario2, nombreChat);

                if (insert == false) {
                    System.out.println("error, no se creo el chat");
                }

                //Metodo para redireccionar a otra vista/Servlet
                response.sendRedirect("Frontend/MAIN.jsp");

            } else { // chat grupal

            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }

        //seguirle mañana, ya tengo hueva;
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
