package io.github.willqi.christmasgame.api;

public interface Player {

    int getColorType ();

    int getId ();

    GameServer getServer ();

    String getName ();

    float getX ();

    float getY ();

    void sendPacket ();

}
