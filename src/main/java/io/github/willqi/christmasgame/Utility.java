package io.github.willqi.christmasgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.willqi.christmasgame.network.PacketDeserializer;
import io.github.willqi.christmasgame.network.PacketWrapper;

public class Utility {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(PacketWrapper.class, new PacketDeserializer())
            .create();

}
