package dev.zopad.web.server;

import dev.zopad.durak.state.Card;

import javax.websocket.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@javax.websocket.server.ServerEndpoint(value = "/hello")
public class ServerEndpoint {

    //The thread safe Set of the concurrent package is used to store the MyWebSocket object corresponding to each client. To realize the communication between the server and a single client, Map can be used for storage, where Key can be the user ID

    protected static CopyOnWriteArraySet<ServerEndpoint> webSocketSet = new CopyOnWriteArraySet<>();

    //A connection session with a client through which data is sent to the client

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        // Get session and WebSocket connection
        this.session = session;
        webSocketSet.add(this);
        session.addMessageHandler(stringHandler);
        Map<String, String> pathParameters = session.getPathParameters();
    }

    private final MessageHandler.Whole<String> stringHandler =
            message -> {
                // this comes from the client(s)
                String msg = message;
            };

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText("te buzika");
    }

    @OnClose
    public void onClose(Session session) {
        webSocketSet.remove(this);
    }
}
