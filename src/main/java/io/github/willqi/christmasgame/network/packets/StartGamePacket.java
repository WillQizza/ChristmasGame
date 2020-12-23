package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class StartGamePacket implements GamePacket {

    @Override
    public int getId () {
        return PacketTypes.START_GAME_PACKET;
    }

    public StartGamePacket () {}

}
