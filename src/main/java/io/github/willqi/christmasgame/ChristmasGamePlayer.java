package io.github.willqi.christmasgame;

import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.api.GameServer;
import io.github.willqi.christmasgame.api.Player;
import io.github.willqi.christmasgame.network.PacketWrapper;
import io.github.willqi.christmasgame.network.packets.DeathPacket;
import io.javalin.websocket.WsContext;

public class ChristmasGamePlayer implements Player {

    private static int PLAYER_ID = 0;

    private final WsContext context;
    private final GameServer server;
    private final int id;
    private final String name;
    private final int colorType;

    private float x;
    private float y;
    private boolean alive = true;
    private boolean spectator = false;

    private boolean positionUpdated = true;

    public ChristmasGamePlayer (GameServer playerServer, WsContext playerContext, String playerName, int playerColorType) {
        context = playerContext;
        server = playerServer;
        name = playerName;
        colorType = playerColorType;

        id = PLAYER_ID++;
    }

    @Override
    public WsContext getWebSocketContext () {
        return context;
    }

    @Override
    public int getColorType() {
        return colorType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public GameServer getServer() {
        return server;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public boolean isPositionUpdated () {
        return positionUpdated;
    }

    @Override
    public boolean isSpectator() {
        return spectator;
    }

    @Override
    public void setAlive(boolean status) {
        if (!alive && !status) {
            // kill
            DeathPacket packet = new DeathPacket();
            sendPacket(packet);

            DeathPacket notifierDeathPacket = new DeathPacket();
            notifierDeathPacket.setPlayerId(getId());

            for (Player p : getServer().getPlayers()) {
                p.sendPacket(notifierDeathPacket);
            }

        }
        alive = status;
    }

    public void setPositionUpdated (boolean status) {
        positionUpdated = status;
    }

    @Override
    public void setSpectator(boolean status) {
        spectator = status;
    }

    @Override
    public void setX(float newX) {
        x = newX;
        setPositionUpdated(true);
    }

    @Override
    public void setY(float newY) {
        y = newY;
        setPositionUpdated(true);
    }

    @Override
    public void sendPacket(GamePacket packet) {
        context.send(Utility.GSON.toJson(new PacketWrapper(packet)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player)obj;
            return player.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode () {
        return id;
    }

}
