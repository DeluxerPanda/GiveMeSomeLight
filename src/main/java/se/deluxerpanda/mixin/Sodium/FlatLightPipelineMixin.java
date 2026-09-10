
package se.deluxerpanda.mixin.Sodium;
import com.llamalad7.mixinextras.sugar.Local;
import se.deluxerpanda.handler.GiveMeSomeLightHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = {
        "me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline",
        "net.caffeinemc.mods.sodium.client.model.light.flat.FlatLightPipeline"
}, remap = false)
public abstract class FlatLightPipelineMixin {
    @Dynamic
    @Inject(
            method = "getOffsetLightmap",
            at = @At(value = "RETURN", ordinal = 1),
            require = 0,
            remap = false,
            cancellable = true
    )
    private void GiveMeSomeLightClient$getLightmap(
            BlockPos pos, Direction face, CallbackInfoReturnable<Integer> cir,
            @Local(name = "adjWord") int adjWord
    ) {
        int lightmap = GiveMeSomeLightHandler.getLightmap(pos, adjWord, cir.getReturnValueI());
        cir.setReturnValue(lightmap);
    }
}
