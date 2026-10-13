package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(SingleThreadedRandomSource.class)
public abstract class SingleThreadedRandomSourceMixin implements BitRandomSource {

  @Shadow private long seed;

  @Inject(method = "setSeed", at = @At("HEAD"))
  private void rngremover$setSeed(long seed, CallbackInfo ci) {
    if (RNGData.disableSingleThreaded) {
      this.seed = RNGData.singleThreadedSeed;
    }
  }

  @Inject(method = "next", at = @At("HEAD"), cancellable = true)
  public void rngremover$next(final int bits, CallbackInfoReturnable<Integer> cir) {
    if (RNGData.disableSingleThreaded) {
      long constrained = (RNGData.singleThreadedSeed >> (64 - bits));
      if (constrained < 0) {
        cir.setReturnValue((int) (RNGData.singleThreadedSeed >> 63));
      } else {
        cir.setReturnValue((int)constrained);
      }
    }
  }
}
