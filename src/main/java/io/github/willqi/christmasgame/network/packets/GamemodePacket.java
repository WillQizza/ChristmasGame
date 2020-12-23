package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class GamemodePacket implements GamePacket {

    private int gamemode;

    public int getGamemode () {
        return gamemode;
    }

    public void setGamemode (int newGamemode) {
        gamemode = newGamemode;
    }

    @Override
    public int getId() {
        return PacketTypes.GAMEMODE_PACKET;
    }
}
