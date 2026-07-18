package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.MarsagliaPolarGaussian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(MarsagliaPolarGaussian.class)
public abstract class MarsagliaPolarGaussianMixin {

    @Inject(method = "nextGaussian", at = @At("HEAD"), cancellable = true)
    private void rngremover$nextGaussian(CallbackInfoReturnable<Double> cir) {
        if (RNGData.disableGaussian) {
            cir.setReturnValue(RNGData.gaussianValue);
        }
    }
}