package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPosHead;
    private BlockPos lastPlayerPosFoot;
    private BlockPos lastPlayerPosOverHead;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                ClientWorld world = MinecraftClient.getInstance().world;

                BlockPos playerPos = BlockPos.ofFloored(player.getPos());

                    Item mainHandItem = client.player.getMainHandStack().getItem();
                    Item offHandItem = client.player.getOffHandStack().getItem();

                //Player over Head
                BlockPos playerPosOverHead = playerPos.up(2);
                if (!playerPosOverHead.equals(lastPlayerPosOverHead)) {
                    removeBlock(lastPlayerPosOverHead);
                }
                if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                       placeBlock(playerPosOverHead);
                     lastPlayerPosOverHead = playerPosOverHead;
                } else {
                    if (client.world.getBlockState(playerPosOverHead).getBlock() == Blocks.LIGHT) {
                        removeBlock(playerPosOverHead);
                    }
                }

                    //Player Head
                     BlockPos playerPosHead = playerPos.up();
                    if (!playerPosHead.equals(lastPlayerPosHead)) {
                        removeBlock(lastPlayerPosHead);
                    }
                    if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                        placeBlock(playerPosHead);
                        lastPlayerPosHead = playerPosHead;
                    } else {
                        if (client.world.getBlockState(playerPosHead).getBlock() == Blocks.LIGHT) {
                            removeBlock(playerPosHead);
                        }
                }
                    //Player foot
                BlockPos playerPosFoot = playerPos.down(0);
                if (!playerPosFoot.equals(lastPlayerPosFoot)) {
                    removeBlock(lastPlayerPosFoot);
                }
                if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                    placeBlock(playerPosFoot);
                    lastPlayerPosFoot = playerPosFoot;
                } else {
                    if (client.world.getBlockState(playerPosFoot).getBlock() == Blocks.LIGHT) {
                        removeBlock(playerPosFoot);
                    }
                }
            }
        });
    }
    private void placeBlock(BlockPos pos) {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
            world.setBlockState(pos, Blocks.LIGHT.getDefaultState());

        }
    }
    private void removeBlock(BlockPos pos) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (pos != null && world.getBlockState(pos).getBlock() == Blocks.LIGHT) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    private boolean isPlayerInRestrictedArea(Entity player) {
        return player.isInFluid() || player.isInLava() || player.isTouchingWater();
    }
}
