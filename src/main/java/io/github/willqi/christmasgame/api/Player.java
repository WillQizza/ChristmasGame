package io.github.willqi.christmasgame.api;

import io.javalin.websocket.WsContext;

public interface Player {

    int getColorType ();

    int getId ();

    GameServer getServer ();

    WsContext getWebSocketContext ();

    String getName ();

    float getX ();

    float getY ();

    void setX (float x);

    void setY (float y);

    void sendPacket (GamePacket packet);

}
