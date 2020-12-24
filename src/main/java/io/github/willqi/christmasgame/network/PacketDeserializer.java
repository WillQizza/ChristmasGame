package io.github.willqi.christmasgame.network;

import com.google.gson.*;
import io.github.willqi.christmasgame.api.GamePacket;
import io.github.willqi.christmasgame.network.packets.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PacketDeserializer implements JsonDeserializer<PacketWrapper> {

    private Map<Integer, Class<? extends GamePacket>> packetTypes = new HashMap<>();

    public PacketDeserializer () {
        packetTypes.put(PacketTypes.LOGIN_PACKET, LoginPacket.class);
        packetTypes.put(PacketTypes.PLAYER_MOVE_PACKET, PlayerMovePacket.class);
        packetTypes.put(PacketTypes.MAP_PACKET, MapPacket.class);
        packetTypes.put(PacketTypes.START_GAME_PACKET, StartGamePacket.class);
        packetTypes.put(PacketTypes.ADD_PLAYER_PACKET, AddPlayerPacket.class);
        packetTypes.put(PacketTypes.DEATH_PACKET, DeathPacket.class);
        packetTypes.put(PacketTypes.SET_TILE_PACKET, SetTilePacket.class);
        packetTypes.put(PacketTypes.USE_PACKET, UsePacket.class);
        packetTypes.put(PacketTypes.GIVE_POWERUP_PACKET, GivePowerupPacket.class);
        packetTypes.put(PacketTypes.END_GAME_PACKET, EndGamePacket.class);
        packetTypes.put(PacketTypes.GAMEMODE_PACKET, GamemodePacket.class);
    }


    @Override
    public PacketWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        int packetId = obj.get("id").getAsInt();

        if (!packetTypes.containsKey(packetId)) {
            return null;
        }

        Class<? extends GamePacket> packetClass = packetTypes.get(packetId);
        PacketWrapper wrapper = new PacketWrapper(
                jsonDeserializationContext.deserialize(obj.getAsJsonObject("packet"), packetClass)
        );

        return wrapper;

    }
}
