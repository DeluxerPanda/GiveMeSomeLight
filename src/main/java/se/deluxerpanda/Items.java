package se.deluxerpanda;

import net.minecraft.item.Item;

public class Items {
    public static boolean isLightItem(Item item) {
        return
         item == net.minecraft.item.Items.TORCH ||
         item == net.minecraft.item.Items.SOUL_TORCH ||
         item == net.minecraft.item.Items.REDSTONE_TORCH ||
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
         item == net.minecraft.item.Items.BEACON ||
         item == net.minecraft.item.Items.GLOW_ITEM_FRAME ||
         item == net.minecraft.item.Items.GLOW_LICHEN ||
         item == net.minecraft.item.Items.GLOW_INK_SAC ||
         item == net.minecraft.item.Items.COPPER_TORCH ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.exposed() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.oxidized() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.unaffected() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.waxed() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.waxedExposed() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.waxedOxidized() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.weathered() ||
         item == net.minecraft.item.Items.COPPER_LANTERNS.waxedWeathered() ||
         item == net.minecraft.item.Items.PEARLESCENT_FROGLIGHT;
    }
}
