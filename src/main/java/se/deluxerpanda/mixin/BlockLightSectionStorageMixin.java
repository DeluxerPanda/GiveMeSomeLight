
package se.deluxerpanda.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.BlockLightSectionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.deluxerpanda.GiveMeSomeLightClient;

@Mixin(BlockLightSectionStorage.class)
public class BlockLightSectionStorageMixin {
    @Inject(
            method = "getLightValue",
            at = @At("RETURN"),
            cancellable = true
    )
    private void addPlayerTorchLight(long l, CallbackInfoReturnable<Integer> cir) {
        BlockPos pos = BlockPos.of(l);
        int vanilla = cir.getReturnValueI();
        int dynamic = GiveMeSomeLightClient.getDynamicLightLevel(pos, vanilla);
        cir.setReturnValue(Math.max(vanilla, dynamic));
    }
}