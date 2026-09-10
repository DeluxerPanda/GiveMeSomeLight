package se.deluxerpanda;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class GiveMeSomeLightClient implements ClientModInitializer {

    public static final Minecraft client = Minecraft.getInstance();
    public static boolean isLightItem(Item item) {
        if (!(item instanceof BlockItem blockItem)) return false;
        return blockItem.getBlock().defaultBlockState().getLightEmission() > 0;
    }
    private final boolean GenDocCheck = FabricLoader.getInstance().isDevelopmentEnvironment();
    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            var world = client.level;
            if (client.player != null) {
                if (GenDocCheck) {
                    getLightItemsToDoc();
                }
            }

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


    private void getLightItemsToDoc() {
        StringBuilder md = new StringBuilder();
        md.append("# Light list  - Minecraft: ").append(FabricLoader.getInstance().getRawGameVersion())
                .append("\n\n --- \n\n");

        Set<Item> seenItems = new HashSet<>();

        for (Block block : BuiltInRegistries.BLOCK) {
            if (block.defaultBlockState().getLightEmission() > 0) {

                Item item = block.asItem();

                if (item.getDefaultInstance().getItem() instanceof BlockItem && !seenItems.contains(item)) {

                    ItemStack stack = new ItemStack(item);

                    md.append("* ## ").append(stack.getHoverName().getString()).append("\n\n");
                    seenItems.add(item);
                }
            }
        }
        md.append("---");
        try {
            Path path = Paths.get("Light-list.md");
            Files.writeString(path, md.toString());
            GiveMeSomeLight.LOGGER.info("Markdown exported with English names!");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}