package io.github.willqi.christmasgame.api;

public interface GameMap {

    int[][] getFullMap ();

    GameServer getServer ();

    void setTile (int x, int y, int newTileId);

}
