package io.github.willqi.christmasgame.network;

import io.github.willqi.christmasgame.api.GamePacket;

public class PacketWrapper {
    private int id;
    private GamePacket packet;

    public PacketWrapper (GamePacket packet) {
        this.packet = packet;
        id = packet.getId();
    }

    public GamePacket getPacket () {
        return packet;
    }

    public int getId () {
        return id;
    }

}
