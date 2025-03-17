package websockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.PathParam;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{chatId}")
public class ChatWebSocket {

    private static final Map<String, Set<Session>> chatSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        String query = session.getRequestURI().toString(); // Obtiene la URL completa
        String chatId = query.substring(query.lastIndexOf("/") + 1); // Extrae el chatId

        chatSessions.computeIfAbsent(chatId, k -> new CopyOnWriteArraySet<>()).add(session);
        System.out.println("Nueva conexión: " + session.getId() + " para el chat: " + chatId);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String query = session.getRequestURI().toString(); // Obtiene la URL completa
        String chatId = query.substring(query.lastIndexOf("/") + 1); // Extrae el chatId
        if (chatId != null) {
            System.out.println("Mensaje recibido en el chat " + chatId + ": " + message);
            broadcast(message, chatId);
        } else {
            System.out.println("Error: No se pudo obtener el chatId.");
        }
    }

    @OnClose
    public void onClose(Session session) {
        String query = session.getRequestURI().toString(); // Obtiene la URL completa
        String chatId = query.substring(query.lastIndexOf("/") + 1); // Extrae el chatId
        if (chatId != null) {
            Set<Session> sessions = chatSessions.get(chatId);
            if (sessions != null) {
                sessions.remove(session);
                System.out.println("Conexión cerrada: " + session.getId() + " para el chat: " + chatId);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String chatId = (String) session.getUserProperties().get("chatId"); // Obtener chatId
        if (chatId != null) {
            System.err.println("Error en la sesión " + session.getId() + " para el chat " + chatId + ": " + throwable.getMessage());
        } else {
            System.err.println("Error en la sesión " + session.getId() + ": " + throwable.getMessage());
        }
    }

    private void broadcast(String message, String chatId) {
        Set<Session> sessions = chatSessions.get(chatId);
        if (sessions != null) {
            for (Session s : sessions) {
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
}
