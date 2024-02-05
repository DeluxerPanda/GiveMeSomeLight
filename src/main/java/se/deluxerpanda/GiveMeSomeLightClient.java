package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.function.ToIntFunction;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPos;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                ClientWorld world = MinecraftClient.getInstance().world;

                BlockPos playerPos1 = BlockPos.ofFloored(player.getPos());
                BlockPos playerPos = playerPos1.up();
                    Item mainHandItem = client.player.getMainHandStack().getItem();
                    Item offHandItem = client.player.getOffHandStack().getItem();

                    if (!playerPos.equals(lastPlayerPos)) {
                        removeBlock(lastPlayerPos);
                    }
                    if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                        placeBlock(playerPos);
                        lastPlayerPos = playerPos;
                    } else {
                        if (client.world.getBlockState(playerPos).getBlock() == Blocks.LIGHT) {
                            removeBlock(playerPos);
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
