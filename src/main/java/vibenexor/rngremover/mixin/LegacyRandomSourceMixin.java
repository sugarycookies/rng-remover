package vibenexor.rngremover.mixin;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(LegacyRandomSource.class)
public abstract class LegacyRandomSourceMixin implements BitRandomSource {

  @Shadow @Final private AtomicLong seed;

  @Inject(method = "setSeed", at = @At("HEAD"))
  private void rngremover$setSeed(long seed, CallbackInfo ci) {
    this.seed.set(RNGData.legacySeed);
  }

  @Inject(method = "next", at = @At("HEAD"), cancellable = true)
  public void rngremover$next(final int bits, CallbackInfoReturnable<Integer> cir) {

    if (RNGData.disableLegacy) {

      long oldSeed = this.seed.get();
      long newSeed = oldSeed * 25214903917L + 11L & 281474976710655L;
      if (!this.seed.compareAndSet(oldSeed, newSeed)) {
        throw ThreadingDetector.makeThreadingException("LegacyRandomSource", (Thread)null);
      } else {
        cir.setReturnValue((int)(newSeed >> 48 - bits));
      }
    }
  }
}
