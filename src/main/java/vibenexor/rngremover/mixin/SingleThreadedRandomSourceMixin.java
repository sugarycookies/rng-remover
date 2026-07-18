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
    this.seed = RNGData.singleThreadedSeed;
  }

  @Inject(method = "next", at = @At("HEAD"), cancellable = true)
  public void rngremover$next(final int bits, CallbackInfoReturnable<Integer> cir) {

    if (RNGData.disableSingleThreaded) {

      this.seed = RNGData.singleThreadedSeed * 25214903917L + 11L & 281474976710655L;
      long newSeed = this.seed * 25214903917L + 11L & 281474976710655L;
      this.seed = newSeed;
      cir.setReturnValue((int)(newSeed >> 48 - bits));
    }
  }
}
