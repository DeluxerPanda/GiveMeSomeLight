package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.mojang.text2speech.Narrator.LOGGER;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPos;
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
                        | client.player.getMainHandStack().getItem() == Items.PEARLESCENT_FROGLIGHT) {

                    BlockPos playerPos = new BlockPos((int) client.player.getX(), (int) client.player.getY() + 1, (int) client.player.getZ());
                    if (!playerPos.equals(lastPlayerPos) && !isPlayerInRestrictedArea(client.player)) {
                        placeBlock(playerPos);
                        lastPlayerPos = playerPos;
                    }
                }else {
                    if (client.player.getOffHandStack().getItem() == Items.TORCH
                            | client.player.getOffHandStack().getItem() == Items.LANTERN
                            | client.player.getOffHandStack().getItem() == Items.OCHRE_FROGLIGHT
                            | client.player.getOffHandStack().getItem() == Items.VERDANT_FROGLIGHT
                            | client.player.getOffHandStack().getItem() == Items.PEARLESCENT_FROGLIGHT) {

                        BlockPos playerPos = new BlockPos((int) client.player.getX(), (int) client.player.getY() + 1, (int) client.player.getZ());

                        if (!playerPos.equals(lastPlayerPos) && !isPlayerInRestrictedArea(client.player)) {
                            placeBlock(playerPos);
                            lastPlayerPos = playerPos;
                        }
                    }else {
                        ClientWorld world = MinecraftClient.getInstance().world;
                        if (lastPlayerPos != null && world.getBlockState(lastPlayerPos).getBlock() == Blocks.LIGHT) {
                            world.setBlockState(lastPlayerPos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        });
    }
    private void placeBlock(BlockPos pos) {
        // Get the client world
        ClientWorld world = MinecraftClient.getInstance().world;

        // Place a block at the player's position
        assert world != null;
        world.setBlockState(pos, Blocks.LIGHT.getDefaultState());

        if (lastPlayerPos != null && world.getBlockState(lastPlayerPos).getBlock() == Blocks.LIGHT) {
            world.setBlockState(lastPlayerPos, Blocks.AIR.getDefaultState());
        }
    }
    private boolean isPlayerInRestrictedArea(Entity player) {
        // Check if the player is in water or lava
        if (player.isInFluid() || player.isInLava() || player.isTouchingWater()) {
            return true;
        }
        return false;
    }
    }


