package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(PerlinNoise.class)
public class PerlinNoiseMixin {
    @Inject(method = "wrap", at = @At("HEAD"), cancellable = true)
    private static void rngremover$wrap(double x, CallbackInfoReturnable<Double> cir) {
        if (RNGData.unfixFarlands) {
            cir.setReturnValue(x);
        }
    }
}
