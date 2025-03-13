/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package websockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatWebSocket {
    private static final Set<Session> chatSessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        chatSessions.add(session);
        System.out.println("Nueva conexión: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Mensaje recibido: " + message);
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        chatSessions.remove(session);
        System.out.println("Conexión cerrada: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error en la sesión " + session.getId() + ": " + throwable.getMessage());
    }

    private void broadcast(String message) {
        for (Session s : chatSessions) {
            if (s.isOpen()) {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
