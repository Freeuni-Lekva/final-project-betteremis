package Endpoints;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@ServerEndpoint(value="/chat")
public class ChatEndpoint {
    private static final String USERNAME_KEY = "username";
    private static Map<String, Session> chatRooms = Collections.synchronizedMap(new LinkedHashMap<String, Session>());
    @OnOpen
    public void onOpen(Session session) throws Exception {

        Map<String, List<String>> parameter = session.getRequestParameterMap();
        List<String> list = parameter.get(USERNAME_KEY);
        String newUsername = list.get(0);

        chatRooms.put(newUsername, session);

        session.getUserProperties().put(USERNAME_KEY, newUsername);

        String response = "newUser|" + String.join("|", chatRooms.keySet());
        session.getBasicRemote().sendText(response);

        for (Session client : chatRooms.values()) {
            if(client == session) continue;
            client.getBasicRemote().sendText("newUser|" + newUsername);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        String[] data = message.split("\\|");
        String destination = data[0];
        String messageContent = data[1];

        String sender = (String)session.getUserProperties().get(USERNAME_KEY);
        if(destination.equals("all")) {
            for (Session client : chatRooms.values()) {
                if(client.equals(session)) continue;
                client.getBasicRemote().sendText("message|" + sender + "|" + messageContent + "|all" );
            }
        } else {
            Session client = chatRooms.get(destination);
            String response = "message|" + sender + "|" + messageContent;
            client.getBasicRemote().sendText(response);
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String username = (String)session.getUserProperties().get(USERNAME_KEY);
        chatRooms.remove(username);

        for (Session client : chatRooms.values()) {
            client.getBasicRemote().sendText("removeUser|" + username);
        }
    }
}
