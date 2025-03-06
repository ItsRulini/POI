/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.sql.Date;

/**
 *
 * @author raula
 */

public class Chat {
    public enum Tipo {
        PRIVADO, GRUPO
    }
    
    int idChat;
    int idCreador;
    Tipo tipoChat;
    Date fechaCreacion;
    String nombre;

    public Chat() {}
    
    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    public Tipo getTipoChat() {
        return tipoChat;
    }

    public void setTipoChat(Tipo tipoChat) {
        this.tipoChat = tipoChat;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
