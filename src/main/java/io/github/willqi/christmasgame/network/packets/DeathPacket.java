package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class DeathPacket implements GamePacket {

    private int playerId;

    public int getPlayerId () {
        return playerId;
    }

    public void setPlayerId (int id) {
        playerId = id;
    }

    @Override
    public int getId () {
        return PacketTypes.DEATH_PACKET;
    }

    public DeathPacket () {}

}
