package io.github.willqi.christmasgame.network;

public class PacketTypes {

    // Server bound
    public static final int LOGIN_PACKET = 0;
    public static final int USE_PACKET = 8;

    // Client bound
    public static final int MAP_PACKET = 2;
    public static final int ADD_PLAYER_PACKET = 4;
    public static final int REMOVE_PLAYER_PACKET = 5;
    public static final int DEATH_PACKET = 6;
    public static final int SET_TILE_PACKET = 7;
    public static final int GIVE_POWERUP_PACKET = 9;
    public static final int END_GAME_PACKET = 10;
    public static final int GAMEMODE_PACKET = 11;


    // Both
    public static final int PLAYER_MOVE_PACKET = 1;
    public static final int START_GAME_PACKET = 3;

}
