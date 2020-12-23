package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class RemovePlayerPacket implements GamePacket {

    private int playerId;

    public int getPlayerId () {
        return playerId;
    }

    public void setPlayerId (int id) {
        playerId = id;
    }

    @Override
    public int getId() {
        return PacketTypes.REMOVE_PLAYER_PACKET;
    }

    public RemovePlayerPacket () {}
}
