package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class LoginPacket implements GamePacket {

    @Override
    public int getId() {
        return PacketTypes.LOGIN_PACKET;
    }

    public LoginPacket () {}

}
