package vibenexor.rngremover.mixin;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(ThreadSafeLegacyRandomSource.class)
public class ThreadSafeLegacyRandomSourceMixin {

  @Shadow @Final private AtomicLong seed;

  @Inject(method = "setSeed", at = @At("HEAD"))
  private void rngremover$setSeed(long seed, CallbackInfo ci) {
    this.seed.set(RNGData.legacySeed);
  }

  @Inject(at = @At("HEAD"), method = "next", cancellable = true)
  private void rngremover$next(int bits, CallbackInfoReturnable<Integer> cir) {

    if (RNGData.disableThreadSafeLegacy) {

      long oldSeed;
      long nextSeed;
      do {
        oldSeed = this.seed.get();
        nextSeed = oldSeed * 25214903917L + 11L & 281474976710655L;
      } while (!this.seed.compareAndSet(oldSeed, nextSeed));

      cir.setReturnValue((int)(nextSeed >>> 48 - bits));
    }
  }
}
