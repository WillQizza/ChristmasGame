package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class PlayerMovePacket implements GamePacket {

    private float x;
    private float y;
    private int playerId;

    public float getX () {
        return x;
    }

    public void setX (float newX) {
        x = newX;
    }

    public float getY () {
        return y;
    }

    public void setY (float newY) {
        y = newY;
    }

    public int getPlayerId () {
        return playerId;
    }

    public void setPlayerId (int id) {
        playerId = id;
    }

    @Override
    public int getId() {
        return PacketTypes.PLAYER_MOVE_PACKET;
    }

    public PlayerMovePacket() {}
}
