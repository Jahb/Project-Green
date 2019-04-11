package nl.tudelft.gogreen.server.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.gogreen.shared.PingPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LiveConnections extends TextWebSocketHandler {
    public static ObservableList<PingPacket> packets = FXCollections.observableArrayList();
    public static Map<String, WebSocketSession> sessionMap = new HashMap<>();

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        PingPacket packet = mapper.readValue(message.getPayload(), PingPacket.class);
        System.out.println(packet.getDataType());
        System.out.println("New connection from " + packet.getData());
        System.out.println(sessionMap);

        switch (packet.getDataType()) {

            case FOLLOWER:
                break;
            case NOTIFICATION:
                break;
            case OPEN:
                sessionMap.put(packet.getData(), session);
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.entrySet().stream().filter(it -> it.getValue().equals(session)).forEach(it -> sessionMap.remove(it.getKey()));
    }
}
