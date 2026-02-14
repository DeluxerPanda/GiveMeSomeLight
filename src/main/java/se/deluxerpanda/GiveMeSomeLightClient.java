package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class GiveMeSomeLightClient implements ClientModInitializer {

    public static final Minecraft client = Minecraft.getInstance();
    public static boolean isLightItem(Item item) {
        if (!(item instanceof BlockItem blockItem)) return false;
        return blockItem.getBlock().defaultBlockState().getLightEmission() > 0;
    }
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            var world = client.level;
            if (player == null || world == null) return;

            BlockPos playerPos = player.blockPosition();

            for (int x = -14; x <= 14; x++) {
                for (int y = -14; y <= 14; y++) {
                    for (int z = -14; z <= 14; z++) {
                        BlockPos pos = playerPos.offset(x, y, z);
                        world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                    }
                }
            }
        });
    }

    public static int getDynamicLightLevel(BlockPos pos, int lightmap) {
        LocalPlayer player = client.player;
        if (player == null) return lightmap;

        boolean holdingLightItem = isLightItem(player.getMainHandItem().getItem()) ||
                isLightItem(player.getOffhandItem().getItem());
        if (!holdingLightItem) return lightmap;

        double dx = player.getX() - (pos.getX());
        double dy = player.getY() - (pos.getY());
        double dz = player.getZ() - (pos.getZ());
        double distSq = dx * dx + dy * dy + dz * dz;

        if (distSq <= 196) {
            int dynamic;
            dynamic = (int) Math.max(0, (14) * (1 - Math.sqrt(distSq) / 14));
            return Math.max(lightmap, dynamic);
        }

        return lightmap;
    }
}