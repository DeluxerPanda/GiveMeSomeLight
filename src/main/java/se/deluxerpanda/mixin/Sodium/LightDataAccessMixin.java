package se.deluxerpanda.mixin.Sodium;

import net.caffeinemc.mods.sodium.client.model.light.data.ArrayLightDataCache;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.deluxerpanda.handler.GiveMeSomeLightHandler;

@Pseudo
@Mixin(ArrayLightDataCache.class)
public abstract class LightDataAccessMixin {

    @Dynamic
    @Inject(
            method = "get(III)I",
            at = @At("HEAD"),
            require = 0
    )
    private void GiveMeSomeLight$storeLightPos(int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        // Store the current light position.
        // This is possible under smooth lighting scenarios, because AoFaceData in Sodium runs a get() call
        // before getting the lightmap.
        GiveMeSomeLightHandler.POS.get().set(x, y, z);
    }
}
