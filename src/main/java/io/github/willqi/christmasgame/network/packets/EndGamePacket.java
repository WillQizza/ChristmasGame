package io.github.willqi.christmasgame.network.packets;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.PacketTypes;

public class EndGamePacket implements GamePacket {

    private LeaderboardEntryData[] scores;

    @Override
    public int getId() {
        return PacketTypes.END_GAME_PACKET;
    }

    public LeaderboardEntryData[] getScores () {
        return scores;
    }

    public void setScores (LeaderboardEntryData[] newScores) {
        scores = newScores;
    }

    public EndGamePacket () {}

    public static class LeaderboardEntryData {
        private int score;
        private int playerId;
    }

}
