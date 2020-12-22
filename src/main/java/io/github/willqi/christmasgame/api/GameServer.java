package io.github.willqi.christmasgame.api;

import java.util.List;

public interface GameServer {

    void addPlayer (Player player);

    void removePlayer (Player player);

    List<Player> getPlayers ();

    GameMap getGameMap ();

    void update ();

    void startGame ();

}
