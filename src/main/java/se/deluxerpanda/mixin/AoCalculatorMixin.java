package se.deluxerpanda.mixin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import se.deluxerpanda.GiveMeSomeLightClient;

@Pseudo
@Mixin(targets = "AoCalculator", remap = false)
public abstract class AoCalculatorMixin {
    @Dynamic
    @Inject(method = "getLightmapCoordinates", at = @At(value = "RETURN", ordinal = 0), require = 0, cancellable = true, remap = false)
    private static void onGetLightmapCoordinates(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!level.getBlockState(pos).isSolidRender())
        cir.setReturnValue(GiveMeSomeLightClient.getDynamicLightLevel(pos,cir.getReturnValue()));
    }
}
