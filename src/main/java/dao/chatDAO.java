/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import models.Chat;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Chat.Tipo;
import static models.Chat.Tipo.GRUPO;
import static models.Chat.Tipo.PRIVADO;

/**
 *
 * @author raula
 */
public class chatDAO {

    Connection conn;

    public chatDAO(Connection conn) {
        this.conn = conn;
    }

    public HashSet<Chat> getChatsUsuario(int id) {
        HashSet<Chat> chats = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Llamar al procedimiento almacenado (no olvides el nombre correcto de tu procedimiento)
            String query = "{CALL spGetChatsUsuario(?)}";
            cs = conn.prepareCall(query);
            cs.setInt(1, id);

            rs = cs.executeQuery();
            chats = new HashSet();
            while (rs.next()) {
                Chat chat = new Chat();
                chat.setIdChat(rs.getInt("idChat"));
                chat.setNombre(rs.getString("nombre"));

                chats.add(chat); // Ingresamos al chat que nos trajimos a la lista
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

        return chats;
    }

    public boolean insertChatPrivado(int usuario1, int usuario2, String nombre) {
        CallableStatement cs = null;
        Chat chat = null;

        try {

            String query = "{CALL spCrearChatPrivado(?,?,?)}";
            cs = conn.prepareCall(query);

            cs.setInt(1, usuario1);
            cs.setInt(2, usuario2);
            cs.setString(3, nombre);

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
            System.out.println("Código de error SQL: " + ex.getErrorCode());
            System.out.println("Estado SQL: " + ex.getSQLState());
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
}
