package io.github.willqi.christmasgame.network;

import com.google.gson.*;
import io.github.willqi.christmasgame.api.GamePacket;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PacketDeserializer implements JsonDeserializer<PacketWrapper> {

    private Map<Integer, Class<? extends GamePacket>> packetTypes = new HashMap<>();

    private class Test implements GamePacket {

        public int asd;

        @Override
        public int getId() {
            return 2;
        }

    }

    public PacketDeserializer () {
        // TODO: register packets here
        packetTypes.put(2, Test.class);
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
