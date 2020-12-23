package io.github.willqi.christmasgame.api;

import java.util.Collection;

public interface GameServer {

    void addPlayer (Player player);

    void removePlayer (Player player);

    Collection<Player> getPlayers ();

    GameMap getGameMap ();

    void update ();

    void startGame ();

}
