package com.sjkz1;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class HappinessFoodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(HappinessFood.HAPPINESS,(client, handler, buf, responseSender) -> {
			HappinessFood.happiness = buf.readInt();
		});
	}
}