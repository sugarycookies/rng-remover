package vibenexor.rngremover.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vibenexor.rngremover.RNGData;

@Mixin(WorldgenRandom.class)
public abstract class WorldgenRandomMixin extends LegacyRandomSource {
    @Final
    @Shadow
    private RandomSource randomSource;

    public WorldgenRandomMixin(long seed) {
        super(seed);
    }

    @Inject(method = "setSeed", at = @At("HEAD"), cancellable = true)
    private synchronized void rngremover$setSeed(long seed, CallbackInfo ci) {
        if (RNGData.disableLegacy && randomSource != null) {
            randomSource.setSeed(RNGData.legacySeed);
            ci.cancel();
        }
    }
}
