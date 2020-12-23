package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class GivePowerupPacket implements GamePacket {

    private int powerUpId;

    public int getPowerUpId () {
        return powerUpId;
    }

    public void setPowerUpId (int id) {
        powerUpId = id;
    }

    @Override
    public int getId() {
        return PacketTypes.GIVE_POWERUP_PACKET;
    }

    public GivePowerupPacket () {}

}
