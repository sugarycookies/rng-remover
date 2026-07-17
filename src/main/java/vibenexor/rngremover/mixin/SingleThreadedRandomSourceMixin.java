package vibenexor.rngremover.mixin;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import org.spongepowered.asm.mixin.Final;
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
  private void setSeed(long seed, CallbackInfo ci) {
    this.seed = RNGData.legacySeed;
  }

  @Inject(method = "next", at = @At("HEAD"), cancellable = true)
  public void quellNext(final int bits, CallbackInfoReturnable<Integer> cir) {

    if (RNGData.disableSingleThreaded) {

      this.seed = RNGData.legacySeed * 25214903917L + 11L & 281474976710655L;
      long newSeed = this.seed * 25214903917L + 11L & 281474976710655L;
      cir.setReturnValue((int)(newSeed >> 48 - bits));

//      long oldSeed = this.seed.get();
//      long newSeed = oldSeed * 25214903917L + 11L & 281474976710655L;
//      if (!this.seed.compareAndSet(oldSeed, newSeed - 1)) {
//        throw ThreadingDetector.makeThreadingException("LegacyRandomSource", (Thread)null);
//      } else {
//        cir.setReturnValue((int)(newSeed >> 48 - bits));
//      }
    }
  }
}
