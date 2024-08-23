package com.breelock.hamster_business;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.minecraft.client.MinecraftClient;

public class ModConfig {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configFilePath = Paths.get(MinecraftClient.getInstance().runDirectory.getAbsolutePath(), "config", "com.breelock.hamster_business.config.json");

    public static int hamsterCoins = 0;

    public static void save() {
        JsonObject config = new JsonObject();
        config.addProperty("hamsterCoins", hamsterCoins);

        try (FileWriter writer = new FileWriter(configFilePath.toFile())) {
            gson.toJson(config, writer);
        } catch (IOException ignored) {  }
    }

    public static void load() {
        if (configFilePath.toFile().exists()) {
            try (FileReader reader = new FileReader(configFilePath.toFile())) {
                JsonObject config = gson.fromJson(reader, JsonObject.class);
                hamsterCoins = config.has("hamsterCoins") ? config.get("hamsterCoins").getAsInt() : hamsterCoins;
            } catch (IOException ignored) {  }
        }
        else {
            loadDefault();
        }
    }

    public static void loadDefault() {
        hamsterCoins = 0;
    }
}
