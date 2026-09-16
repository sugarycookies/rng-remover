package vibenexor.rngremover.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.BitRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(BitRandomSource.class)
public interface BitRandomSourceMixin extends RandomSource {
    @Inject(method = "nextInt(I)I", at = @At("HEAD"), cancellable = true)
    default void rngremover$nextInt(int bound, CallbackInfoReturnable<Integer> cir) {
        if (bound <= 0) {
            cir.setReturnValue(0);
        } else if (RNGData.disableLegacy) {
            int sample = (int) RNGData.legacySeed;
            int modulo = sample % bound;
            cir.setReturnValue(Math.max(modulo, 0));
        }
    }
}
