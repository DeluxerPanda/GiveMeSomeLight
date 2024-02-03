package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

import static com.mojang.text2speech.Narrator.LOGGER;

public class GiveMeSomeLightClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric Client!");
        ClientTickEvents.END_CLIENT_TICK.register(server -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                if (client.player.getMainHandStack().getItem() == Items.TORCH
                        | client.player.getMainHandStack().getItem() == Items.LANTERN
                        | client.player.getMainHandStack().getItem() == Items.OCHRE_FROGLIGHT
                        | client.player.getMainHandStack().getItem() == Items.VERDANT_FROGLIGHT
                        | client.player.getMainHandStack().getItem() == Items.PEARLESCENT_FROGLIGHT

                        | client.player.getOffHandStack().getItem() == Items.TORCH
                        | client.player.getOffHandStack().getItem() == Items.LANTERN
                        | client.player.getOffHandStack().getItem() == Items.OCHRE_FROGLIGHT
                        | client.player.getOffHandStack().getItem() == Items.VERDANT_FROGLIGHT
                        | client.player.getOffHandStack().getItem() == Items.PEARLESCENT_FROGLIGHT	) {

                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1, 0, false, false, false));
                }
            }
        });
    }
}
