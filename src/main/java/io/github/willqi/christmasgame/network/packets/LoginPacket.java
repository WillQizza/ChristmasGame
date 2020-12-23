package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class LoginPacket implements GamePacket {

    private String name;
    private int colorType;

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

    @Override
    public int getId() {
        return PacketTypes.LOGIN_PACKET;
    }

    public LoginPacket () {}

}
