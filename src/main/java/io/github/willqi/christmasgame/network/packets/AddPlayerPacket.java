package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class AddPlayerPacket implements GamePacket {

    private int playerId;
    private String name;
    private int colorType;
    private float x;
    private float y;

    public int getPlayerId () {
        return playerId;
    }

    public void setPlayerId (int id) {
        playerId = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String newName) {
        name = newName;
    }

    public int getColorType () {
        return colorType;
    }

    public void setColorType (int type) {
        colorType = type;
    }

    public float getX () {
        return x;
    }

    public void setX (float xCoord) {
        x = xCoord;
    }

    public float getY () {
        return y;
    }

    public void setY (float yCoord) {
        y = yCoord;
    }

    @Override
    public int getId() {
        return PacketTypes.ADD_PLAYER_PACKET;
    }

    public AddPlayerPacket () {}
}
