package io.github.willqi.christmasgame;

import com.google.gson.JsonSyntaxException;
import io.github.willqi.christmasgame.api.GameMap;
import io.github.willqi.christmasgame.api.GameServer;
import io.github.willqi.christmasgame.api.Player;
import io.github.willqi.christmasgame.network.PacketTypes;
import io.github.willqi.christmasgame.network.PacketWrapper;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ChristmasGameServer implements GameServer {

    private final Collection<Player> players = new HashSet<>();

    private boolean serverStarted;

    public ChristmasGameServer () {

    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public void removePlayer(Player player) {

    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public GameMap getGameMap() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void startGame() {

    }

    public void startServer (int port) {
        if (serverStarted) {
            throw new IllegalStateException("The server has already been started.");
        }
        serverStarted = true;

        Javalin server = Javalin.create(conf -> conf.addStaticFiles("/public"));

        Logger logger = LoggerFactory.getLogger(ChristmasGameServer.class);

        server.ws("/game", client -> {

            client.onConnect(connection -> logger.info(String.format("Connection opened: %s", connection.getSessionId())));

            client.onClose(connection -> {
                logger.info(String.format("Connection closed: %s", connection.getSessionId()));
                for (Player player : getPlayers()) {
                    if (player.getWebSocketContext() == connection) {
                        removePlayer(player);
                        break;
                    }
                }
            });

            client.onMessage(messageCtx -> {
                PacketWrapper response;
                try {
                    response = Utility.GSON.fromJson(messageCtx.message(), PacketWrapper.class);
                } catch (JsonSyntaxException exception) {
                    logger.debug("Invalid packet retrieved: " + messageCtx.message());
                    logger.debug(exception.toString());
                    return; // invalid packet retrieved.
                }
                if (response == null) {
                    logger.debug("Missing packet handler: " + messageCtx.message());
                    return; // missing packet handler
                }

                if (response.getId() == PacketTypes.LOGIN_PACKET) {
                    // construct player object
                    return;
                }
                // a player object should exist.

            });

        });
        server.start(port);
    }

}
