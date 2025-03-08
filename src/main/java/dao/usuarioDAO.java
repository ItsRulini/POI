/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Date;
import models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author raula
 */
public class usuarioDAO {

    Connection conn;

    public usuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public Usuario usuarioPorId(int id) {
        PreparedStatement ps = null;
        Usuario user = null;
        try {
            String query = "SELECT * FROM Usuarios WHERE idUsuario = ?";
            ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("idUsuario"));
                user.setUsuario(rs.getString("usuario"));
                user.setCorreo(rs.getString("correo"));
                user.setNombres(rs.getString("nombres"));
                user.setPaterno(rs.getString("paterno"));
                user.setMaterno(rs.getString("materno"));
                user.setAvatar(rs.getString("avatar"));
                user.setDescripcion(rs.getString("descripcion"));
                user.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                user.setFechaRegistro(rs.getDate("fechaRegistro"));
                user.setContraseña(rs.getString("contraseña"));
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            // Asegurarse de cerrar el CallableStatement para liberar recursos
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public boolean updateUsuario(Usuario usuario) {
        String query = "{CALL spUpdateUsuario(?,?,?,?,?,?,?,?,?)}";

        try (CallableStatement cs = conn.prepareCall(query)) {
            cs.setInt(1, usuario.getIdUsuario());
            cs.setString(2, usuario.getUsuario());
            cs.setString(3, usuario.getCorreo());
            cs.setString(4, usuario.getNombres());
            cs.setString(5, usuario.getPaterno());
            cs.setString(6, usuario.getMaterno());
            cs.setString(7, usuario.getAvatar());
            cs.setString(8, usuario.getDescripcion());
            cs.setString(9, usuario.getContraseña());

            int filasAfectadas = cs.executeUpdate();
            return filasAfectadas > 0; // Devuelve true si al menos una fila fue actualizada

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23000")) { // Código de error SQL para restricción UNIQUE
                System.err.println("Error: Usuario o correo ya existente.");
            } else {
                System.err.println("Error SQL: " + ex.getMessage());
            }
            return false;
        }
    }

    public Usuario buscarUsuario(String usuario, String password) {
        CallableStatement cs = null;
        Usuario user = null;
        try {
            String query = "{CALL GetUsuario_sp(?,?)}";
            cs = conn.prepareCall(query);

            cs.setString(1, usuario);
            cs.setString(2, password);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setIdUsuario(rs.getInt("idUsuario"));
                user.setUsuario(rs.getString("usuario"));
                user.setCorreo(rs.getString("correo"));
                user.setNombres(rs.getString("nombres"));
                user.setPaterno(rs.getString("paterno"));
                user.setMaterno(rs.getString("materno"));
                user.setAvatar(rs.getString("avatar"));
                user.setDescripcion(rs.getString("descripcion"));
                user.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                user.setFechaRegistro(rs.getDate("fechaRegistro"));
                user.setContraseña(rs.getString("contraseña"));

            }

            rs.close();
            cs.close();

        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            // Asegurarse de cerrar el CallableStatement para liberar recursos
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public boolean insertUsuario(Usuario usuario) {

        CallableStatement cs = null;
        try {
            // Llamar al procedimiento almacenado (no olvides el nombre correcto de tu procedimiento)
            String query = "{CALL AltaUsuarios_sp(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = conn.prepareCall(query);

            // Establecer los valores de los parámetros
            cs.setString(1, usuario.getUsuario());     // usuUsuario
            cs.setString(2, usuario.getCorreo());      // usuEmail
            cs.setString(3, usuario.getContraseña());  // usuPass
            cs.setString(4, usuario.getNombres());    // usuNombre
            cs.setString(5, usuario.getPaterno());    // usuPaterno
            cs.setString(6, usuario.getMaterno());    // usuMaterno
            cs.setString(7, usuario.getAvatar());     // usuAvatar
            cs.setString(8, usuario.getDescripcion()); // usuDescripcion
            cs.setDate(9, usuario.getFechaNacimiento());    // usuNacimiento (java.sql.Date)

            // Ejecutar la actualización
            int insert = cs.executeUpdate();
            System.out.println("Resultado de executeUpdate: " + insert);

            // Si el procedimiento afecta filas, lo consideramos un éxito
            if (insert != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        } finally {
            // Asegurarse de cerrar el CallableStatement para liberar recursos
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Usuario> getAllUsuarios() {

        List<Usuario> usuarios = new ArrayList<>();
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Llamar al procedimiento almacenado (no olvides el nombre correcto de tu procedimiento)
            String query = "{CALL spGetAllUsuarios()}";
            cs = conn.prepareCall(query);

            rs = cs.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsuario(rs.getString("usuario"));

                usuarios.add(usuario); // Ingresamos al usuario que nos trajimos a la lista
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // Asegurarse de cerrar el CallableStatement para liberar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuarios;
    }

}
