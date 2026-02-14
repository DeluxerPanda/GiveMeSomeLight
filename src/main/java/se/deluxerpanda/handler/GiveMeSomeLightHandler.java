package se.deluxerpanda.handler;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import se.deluxerpanda.GiveMeSomeLightClient;
import se.deluxerpanda.util.GiveMeSomeLightUtil;

@ApiStatus.Internal
public interface GiveMeSomeLightHandler {
    // Stores the current light position being used by ArrayLightDataCache#get
    // We use ThreadLocal because Sodium's chunk builder is multithreaded, otherwise it will break
    // catastrophically.
    ThreadLocal<BlockPos.MutableBlockPos> POS = ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

    static int getLightmap(BlockPos pos, int word, int lightmap) {

        if ((word >>> 30 & 1) != 0)
            return lightmap;

        int block = GiveMeSomeLightUtil.getBlock(lightmap);
        int sky = GiveMeSomeLightUtil.getSky(lightmap);

        int dynamic = GiveMeSomeLightClient.getDynamicLightLevel(pos,block);

        int newBlock = Math.max(block, dynamic);

        return GiveMeSomeLightUtil.pack(newBlock, sky);
}
}
