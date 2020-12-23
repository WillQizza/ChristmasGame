package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class UsePacket implements GamePacket {

    @Override
    public int getId() {
        return PacketTypes.USE_PACKET;
    }

    public UsePacket () {}
}
