package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class MapPacket implements GamePacket {

    private int[][] data;
    private float spawnX;
    private float spawnY;

    public int[][] getData () {
        return data;
    }

    public void setData (int[][] newData) {
        data = newData;
    }

    public float getSpawnX () {
        return spawnX;
    }

    public void setSpawnX (float x) {
        spawnX = x;
    }

    public float getSpawnY () {
        return spawnY;
    }

    public void setSpawnY (float y) {
        spawnY = y;
    }

    @Override
    public int getId () {
        return PacketTypes.MAP_PACKET;
    }

    public MapPacket () {}

}
