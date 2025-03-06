/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



import models.Tarea_Usuario;
import models.Tarea_Usuario.Estatus;
import models.Tarea;

import java.sql.CallableStatement;
import java.sql.Date;
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
public class tarea_usuarioDAO {
    Connection conn;

    public tarea_usuarioDAO(Connection conn) {
        this.conn = conn;
    }
    
    public HashSet<Tarea_Usuario> getTareas(int idUsuario) {
        CallableStatement cs = null;
        HashSet<Tarea_Usuario> listaTareas = new HashSet();
        Tarea_Usuario tareaUsuario = null;
        Tarea tarea = null;
        
        try{
            String query = "{CALL spGetTareasDeUsuario(?)}";
            cs = conn.prepareCall(query);
            
            cs.setInt(1, idUsuario);
            ResultSet rs = cs.executeQuery();
            
            while(rs.next()){
                tareaUsuario = new Tarea_Usuario();
                tarea = new Tarea();
                
                tarea.setDescripcion(rs.getString("descripcion"));
                tarea.setIdTarea(rs.getInt("idTarea"));
                tareaUsuario.setTarea(tarea); // agregando la tarea a tarea_usuarios
                tareaUsuario.setEstatus(Estatus.valueOf(rs.getString("estatus").toUpperCase()));
                tareaUsuario.setFechaCompletada(rs.getDate("fechaCompletada"));

                listaTareas.add(tareaUsuario); // Agregando las tareas
            }
            
            
        }
        catch (SQLException ex) {
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
        
        return listaTareas;
    }
    
    
}
