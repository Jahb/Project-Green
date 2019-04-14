package nl.tudelft.gogreen.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.gogreen.server.websocket.LiveConnections;
import nl.tudelft.gogreen.shared.PingPacket;
import nl.tudelft.gogreen.shared.PingPacketData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WSTest {

    private LiveConnections connections = new LiveConnections();

    @Autowired
    public ObjectMapper mapper;

    @Test
    public void yeet() throws IOException {
        for (PingPacketData dt : PingPacketData.values()) {
            connections.handleTextMessage(null, new TextMessage(mapper.writeValueAsString(new PingPacket(dt, "TestKees"))));
        }
        connections.afterConnectionClosed(null, CloseStatus.NORMAL);
    }
}
