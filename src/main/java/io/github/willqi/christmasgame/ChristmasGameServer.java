package io.github.willqi.christmasgame;

import com.google.gson.JsonSyntaxException;
import io.github.willqi.christmasgame.api.GameMap;
import io.github.willqi.christmasgame.api.GameServer;
import io.github.willqi.christmasgame.api.Player;
import io.github.willqi.christmasgame.network.PacketTypes;
import io.github.willqi.christmasgame.network.PacketWrapper;
import io.github.willqi.christmasgame.network.packets.*;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ChristmasGameServer implements GameServer {

    private final Collection<Player> players = new HashSet<>();

    private Timer timer = new Timer();

    private boolean serverStarted;
    private boolean gameStarted;

    @Override
    public void addPlayer(Player player) {
        AddPlayerPacket packet = new AddPlayerPacket();
        packet.setPlayerId(player.getId());
        packet.setColorType(player.getColorType());
        packet.setName(player.getName());
        packet.setX(0); // TODO
        packet.setY(0); // TODO
        for (Player p : getPlayers()) {
            p.sendPacket(packet);
        }
        players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
        RemovePlayerPacket packet = new RemovePlayerPacket();
        packet.setPlayerId(player.getId());
        for (Player p : getPlayers()) {
            p.sendPacket(packet);
        }
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

        for (Player player : getPlayers()) {

            if (!player.isSpectator() && ((ChristmasGamePlayer)player).isPositionUpdated()) {

                PlayerMovePacket playerMovePacket = new PlayerMovePacket();
                playerMovePacket.setPlayerId(player.getId());
                playerMovePacket.setX(player.getX());
                playerMovePacket.setY(player.getY());
                ((ChristmasGamePlayer)player).setPositionUpdated(false);

                for (Player otherPlayer : getPlayers()) {
                    if (otherPlayer != player) {
                        otherPlayer.sendPacket(playerMovePacket);
                    }
                }

            }

        }

    }

    @Override
    public void startGame() {
        // TODO: Should use a new thread for the game instead of combining the game and webserver. We need a better game loop.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 2);
    }

    public void startServer (int port) {
        if (serverStarted) {
            throw new IllegalStateException("The server has already been started.");
        }
        startGame();
        serverStarted = true;

        Javalin server = Javalin.create(conf -> conf.addStaticFiles("/public"));

        Logger logger = LoggerFactory.getLogger(ChristmasGameServer.class);

        server.ws("/game", client -> {

            client.onConnect(connection -> logger.info(String.format("Connection opened: %s", connection.getSessionId())));

            client.onClose(connection -> {
                logger.info(String.format("Connection closed: %s", connection.getSessionId()));
                for (Player player : getPlayers()) {
                    if (player.getWebSocketContext().getSessionId() == connection.getSessionId()) {
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
                    LoginPacket loginPacket = (LoginPacket)response.getPacket();
                    Player player = new ChristmasGamePlayer(this, messageCtx, loginPacket.getName(), loginPacket.getColorType());

                    // TODO: This should probably be sent after the start game packet when that gets implemented
                    for (Player otherPlayer : getPlayers()) {
                        AddPlayerPacket addPlayerPacket = new AddPlayerPacket();
                        addPlayerPacket.setPlayerId(otherPlayer.getId());
                        addPlayerPacket.setX(otherPlayer.getX());
                        addPlayerPacket.setY(otherPlayer.getY());
                        addPlayerPacket.setName(otherPlayer.getName());
                        addPlayerPacket.setColorType(otherPlayer.getColorType());
                        player.sendPacket(addPlayerPacket);
                    }

                    addPlayer(player);

                    GamemodePacket gamemodePacket = new GamemodePacket();
                    gamemodePacket.setGamemode(Utility.GAMEMODE_SPECTATOR);
                    //gamemodePacket.setGamemode(getPlayers().size() == 0 ? Utility.GAMEMODE_SPECTATOR : Utility.GAMEMODE_PLAYER);
                    player.sendPacket(gamemodePacket);

                    MapPacket mapPacket = new MapPacket(); // TODO
                    player.sendPacket(mapPacket);

                    return;
                }
                Player foundPlayer = null;
                for (Player p : getPlayers()) {
                    if (p.getWebSocketContext().getSessionId() == messageCtx.getSessionId()) {
                        foundPlayer = p;
                        break;
                    }
                }
                final Player player = foundPlayer;
                if (player == null) {
                    logger.debug("Client sent packet before logging in.");
                    return;
                }

                switch (response.getId()) {
                    case PacketTypes.START_GAME_PACKET:
                        if (!gameStarted) {
                            // TODO: use this packet instead of automatically starting the game.
                            gameStarted = true;
                            startGame();
                            StartGamePacket startGamePacket = new StartGamePacket();
                            for (Player p : getPlayers()) {
                                p.sendPacket(startGamePacket);
                            }
                        }
                        break;
                    case PacketTypes.PLAYER_MOVE_PACKET:
                        PlayerMovePacket playerMovePacket = (PlayerMovePacket)response.getPacket();
                        player.setX(playerMovePacket.getX());
                        player.setY(playerMovePacket.getY());
                        ((ChristmasGamePlayer)player).setPositionUpdated(true);

                        if (player.getY() < 0 && player.isAlive()) {    // TODO: get actual y
                            player.setAlive(false);
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    player.setAlive(true);
                                }
                            }, 5000);
                            return;
                        }

                        PlayerMovePacket respondingPlayerMovePacket = new PlayerMovePacket();
                        respondingPlayerMovePacket.setPlayerId(player.getId());
                        respondingPlayerMovePacket.setX(player.getX());
                        respondingPlayerMovePacket.setY(player.getY());
                        for (Player p : getPlayers()) {
                            if (player != p) {
                                p.sendPacket(respondingPlayerMovePacket);
                            }
                        }
                        break;
                }

            });

        });
        server.start(port);
    }

}
