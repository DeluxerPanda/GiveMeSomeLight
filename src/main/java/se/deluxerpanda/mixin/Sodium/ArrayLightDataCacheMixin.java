package se.deluxerpanda.mixin.Sodium;

import net.caffeinemc.mods.sodium.client.model.light.data.LightDataAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.deluxerpanda.handler.GiveMeSomeLightHandler;

@Mixin(LightDataAccess.class)
public abstract class ArrayLightDataCacheMixin {
	@Inject(
			method = "getLightmap",
			at = @At("RETURN"),
			remap = false,
			require = 0,
			cancellable = true
	)
	private static void GiveMeSomeLight$getLightmap(int word, CallbackInfoReturnable<Integer> cir) {
		int lightmap = GiveMeSomeLightHandler.getLightmap(GiveMeSomeLightHandler.POS.get(),word, cir.getReturnValueI());
		cir.setReturnValue(lightmap);
	}
}