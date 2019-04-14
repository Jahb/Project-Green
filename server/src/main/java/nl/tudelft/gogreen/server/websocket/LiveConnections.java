package nl.tudelft.gogreen.server.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.shared.PingPacket;
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
    public static Map<String, WebSocketSession> sessionMap = new HashMap<>();


    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session,
                                  TextMessage message) throws IOException {
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
            default:
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) {
        while (sessionMap.values().remove(session)) {
            System.out.println();
        }

    }
}
