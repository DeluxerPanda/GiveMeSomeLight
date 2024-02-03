package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPos;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                ClientWorld world = MinecraftClient.getInstance().world;
                BlockPos playerPos = new BlockPos((int) player.getX(), (int) (player.getY() + player.getEyeHeight(player.getPose())), (int) player.getZ());


                Item mainHandItem = client.player.getMainHandStack().getItem();
                Item offHandItem = client.player.getOffHandStack().getItem();

                if (!playerPos.equals(lastPlayerPos)) {
                    removeBlock(lastPlayerPos);
                }
                if (isLightItem(mainHandItem) || isLightItem(offHandItem)) {
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

    private boolean isLightItem(Item item) {
        return item == Items.TORCH ||
                item == Items.SOUL_TORCH ||
                item == Items.REDSTONE_TORCH ||
                item == Items.REDSTONE_LAMP ||
                item == Items.SEA_LANTERN ||
                item == Items.SOUL_LANTERN ||
                item == Items.GLOWSTONE ||
                item == Items.SHROOMLIGHT ||
                item == Items.MAGMA_BLOCK ||
                item == Items.LAVA_BUCKET ||
                item == Items.SOUL_CAMPFIRE ||
                item == Items.CAMPFIRE ||
                item == Items.LANTERN ||
                item == Items.OCHRE_FROGLIGHT ||
                item == Items.VERDANT_FROGLIGHT ||
                item == Items.END_CRYSTAL ||
                item == Items.END_ROD ||
                item == Items.JACK_O_LANTERN ||
                item == Items.PEARLESCENT_FROGLIGHT;
    }
}
