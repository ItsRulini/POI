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
        //String uri = session.getRequestURI().toString();  // Obtener la URL completa
        String path = session.getRequestURI().getPath();  // Obtener solo el path de la URL

        // El chatId está inmediatamente después de "/chat/"
        String chatId = path.substring(path.lastIndexOf("/") + 1);

        String query = session.getRequestURI().getQuery(); // Obtener parámetros de la URL
        String userId = null;

        if (query != null && query.contains("userId=")) {
            userId = query.split("userId=")[1]; // Extraer userId del query string
        }

        if (chatId != null) {
            session.getUserProperties().put("chatId", chatId);
        }
        if (userId != null) {
            session.getUserProperties().put("userId", userId);
        }

        chatSessions.computeIfAbsent(chatId, k -> new CopyOnWriteArraySet<>()).add(session);
        System.out.println("Usuario " + userId + " conectado al chat " + chatId);
    }


    /*@OnMessage
    public void onMessage(String message, Session session) {
        String query = session.getRequestURI().toString(); // Obtiene la URL completa
        String chatId = query.substring(query.lastIndexOf("/") + 1); // Extrae el chatId
        if (chatId != null) {
            System.out.println("Mensaje recibido en el chat " + chatId + ": " + message);
            broadcast(message, chatId);
        } else {
            System.out.println("Error: No se pudo obtener el chatId.");
        }
    }*/
    
    @OnMessage
    public void onMessage(String message, Session session) {
        String chatId = (String) session.getUserProperties().get("chatId");
        String senderId = (String) session.getUserProperties().get("userId");

        if (chatId != null && senderId != null) {
            // Crear un objeto JSON
            String fullMessage = "{\"senderId\": \"" + senderId + "\", \"message\": \"" + message + "\"}";
            System.out.println("Mensaje recibido en el chat " + chatId + ": " + message);
            broadcast(fullMessage, chatId);
        } else {
            System.out.println("Error: No se pudo obtener chatId o userId.");
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
