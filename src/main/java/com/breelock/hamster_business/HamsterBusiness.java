package com.breelock.hamster_business;

import com.breelock.hamster_business.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HamsterBusiness implements ModInitializer {
	public static final String MOD_ID = "hamster_business";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int tickCounter = 0;

    @Override
	public void onInitialize() {
		ModItems.register();
		ModConfig.load();

		ServerTickEvents.START_SERVER_TICK.register(this::onServerTick);
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> ModConfig.save());
	}

	private void onServerTick(MinecraftServer server) {
		tickCounter++;

		// 1 min = 1200 ticks
        if (tickCounter >= 1200) {
			ModConfig.save();
			tickCounter = 0;
		}
	}
}