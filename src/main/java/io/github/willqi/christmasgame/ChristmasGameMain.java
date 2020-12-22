package io.github.willqi.christmasgame;

import io.github.willqi.christmasgame.game.ChristmasGameServer;
import io.github.willqi.christmasgame.web.HTTPServer;
import io.github.willqi.christmasgame.websocket.WebSocketServer;

public class ChristmasGameMain {

    public static void main (String[] args) {
        ChristmasGameServer gameServer = new ChristmasGameServer();
        WebSocketServer webSocketServer = new WebSocketServer();
        HTTPServer httpServer = new HTTPServer();
    }

}
