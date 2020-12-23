package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class SetTilePacket implements GamePacket {

    private int x;
    private int y;
    private int type;

    public int getX () {
        return x;
    }

    public void setX (int newX) {
        x = newX;
    }

    public int getY () {
        return y;
    }

    public void setY (int newY) {
        y = newY;
    }

    public int getType () {
        return type;
    }

    public void setType (int newType) {
        type = newType;
    }

    @Override
    public int getId () {
        return PacketTypes.SET_TILE_PACKET;
    }

    public SetTilePacket () {}

}
