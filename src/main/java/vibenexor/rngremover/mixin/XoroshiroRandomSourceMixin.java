package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.Xoroshiro128PlusPlus;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(XoroshiroRandomSource.class)
public abstract class XoroshiroRandomSourceMixin {

  @Shadow
  private Xoroshiro128PlusPlus randomNumberGenerator;

  @Inject(method = "setSeed", at = @At("HEAD"), cancellable = true)
  private void rngremover$setSeed(long seed, CallbackInfo ci) {
    if (RNGData.disableXoroshiro) {
      this.randomNumberGenerator = new Xoroshiro128PlusPlus(new RandomSupport.Seed128bit(RNGData.xoroshiroSeed, RNGData.xoroshiroSeed));
      ci.cancel();
    }
  }

  @Inject(method = "nextInt(I)I", at = @At("HEAD"), cancellable = true)
  private void rngremover$nextInt(int bound, CallbackInfoReturnable<Integer> cir) {
    if (RNGData.disableXoroshiro) {
      if (bound <= 0) {
        throw new IllegalArgumentException("Bound must be positive");
      } else {
        long randomBits = RNGData.xoroshiroSeed;
        long multipliedRandomBits = randomBits * (long)bound;
        long fractionalPart = multipliedRandomBits & 4294967295L;
        long integerPart = fractionalPart;
        if (fractionalPart > bound) {
          integerPart = fractionalPart % bound;
        }

        cir.setReturnValue((int)integerPart);
      }
    }
  }
}