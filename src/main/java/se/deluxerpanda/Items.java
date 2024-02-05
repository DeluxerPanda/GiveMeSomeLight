package se.deluxerpanda;

import net.minecraft.item.Item;

public class Items {
    public static boolean isLightItem(Item item) {
        return item == net.minecraft.item.Items.TORCH ||
//item == net.minecraft.item.Item.byRawId(item.getName().contains(Text.of("torch"))) ||
        item == net.minecraft.item.Items.SOUL_TORCH ||
                item == net.minecraft.item.Items.REDSTONE_TORCH ||
                item == net.minecraft.item.Items.REDSTONE_LAMP ||
                item == net.minecraft.item.Items.SEA_LANTERN ||
                item == net.minecraft.item.Items.SOUL_LANTERN ||
                item == net.minecraft.item.Items.GLOWSTONE ||
                item == net.minecraft.item.Items.SHROOMLIGHT ||
                item == net.minecraft.item.Items.MAGMA_BLOCK ||
                item == net.minecraft.item.Items.LAVA_BUCKET ||
                item == net.minecraft.item.Items.SOUL_CAMPFIRE ||
                item == net.minecraft.item.Items.CAMPFIRE ||
                item == net.minecraft.item.Items.LANTERN ||
                item == net.minecraft.item.Items.OCHRE_FROGLIGHT ||
                item == net.minecraft.item.Items.VERDANT_FROGLIGHT ||
                item == net.minecraft.item.Items.END_CRYSTAL ||
                item == net.minecraft.item.Items.END_ROD ||
                item == net.minecraft.item.Items.JACK_O_LANTERN ||
                item == net.minecraft.item.Items.PEARLESCENT_FROGLIGHT;
    }
}
