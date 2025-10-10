package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class GiveMeSomeLightClient implements ClientModInitializer {
    private BlockPos lastPlayerPos;
    boolean isLight = false;
    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {

                PlayerEntity player = MinecraftClient.getInstance().player;
                ClientWorld world = MinecraftClient.getInstance().world;

                Item mainHandItem = client.player.getMainHandStack().getItem();
                Item offHandItem = client.player.getOffHandStack().getItem();

                    BlockPos currentPlayerPos = BlockPos.ofFloored(Objects.requireNonNull(player).getEntityPos());

                if (Items.isLightItem(mainHandItem) || Items.isLightItem(offHandItem)) {
                    if (!currentPlayerPos.equals(lastPlayerPos) && lastPlayerPos != null) {
                            removeLightBlocks(world, lastPlayerPos);
                    }
                    placeLightBlocks(world, currentPlayerPos);
                    lastPlayerPos = currentPlayerPos;
                    isLight = true;
                }else{
                    if (isLight){
                    if (lastPlayerPos != null) {
                        removeLightBlocks(world, lastPlayerPos);
                    }
                    removeLightBlocks(world, currentPlayerPos);
                        isLight = false;
                }
                }
            }
        });
    }
    private void placeLightBlocks(ClientWorld world, BlockPos centerPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos blockPos = centerPos.add(x, y, z);
                    if (world.getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        world.setBlockState(blockPos, Blocks.LIGHT.getDefaultState().with(Properties.LEVEL_15, 12));
                    }
                }
            }
        }
    }
    private void removeLightBlocks(ClientWorld world, BlockPos centerPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos blockPos = centerPos.add(x, y, z);
                    if (world.getBlockState(blockPos).getBlock() == Blocks.LIGHT) {
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

}
