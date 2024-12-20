package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.List;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPos;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {

                PlayerEntity player = MinecraftClient.getInstance().player;
                ClientWorld world = MinecraftClient.getInstance().world;

                Item mainHandItem = client.player.getMainHandStack().getItem();
                Item offHandItem = client.player.getOffHandStack().getItem();

                BlockPos currentPlayerPos = BlockPos.ofFloored(player.getPos());

                if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                    placeLightBlocks(world, currentPlayerPos);
                // Check if player has moved
                if (!currentPlayerPos.equals(lastPlayerPos)) {
                    // Remove light blocks from previous position
                    if (lastPlayerPos != null) {
                        removeLightBlocks(world, lastPlayerPos);
                    }

                    // Place light blocks around current player position
                    placeLightBlocks(world, currentPlayerPos);

                    // Update lastPlayerPos to current position
                    lastPlayerPos = currentPlayerPos;
                }
            } else {
                    removeLightBlocks(world, currentPlayerPos);
                }
            }
        });
    }

    private void placeLightBlocks(ClientWorld world, BlockPos centerPos) {
        // Place light blocks in a specific pattern around the centerPos
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos blockPos = centerPos.add(x, y, z);
                    if (world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(blockPos, Blocks.LIGHT.getDefaultState());
                    }
                }
            }
        }
    }

    private void removeLightBlocks(ClientWorld world, BlockPos centerPos) {
        // Remove light blocks in the same pattern around the centerPos
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos blockPos = centerPos.add(x, y, z);
                    if (world.getBlockState(blockPos).getBlock() == Blocks.LIGHT) {
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }



    private boolean isPlayerInRestrictedArea(Entity player) {
        return player.isInFluid() || player.isInLava() || player.isTouchingWater();
    }
}
