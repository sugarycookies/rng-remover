package vibenexor.rngremover.mixin;

import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(RandomSource.class)
public interface RandomSourceMixin {

  @Inject(at = @At("HEAD"), method = "create()Lnet/minecraft/util/RandomSource;", cancellable = true)
  private static void rngremover$create(CallbackInfoReturnable<RandomSource> cir) {
    if (RNGData.disableLegacy) {
      cir.setReturnValue(RandomSource.create(RNGData.legacySeed));
    }
  }
}
