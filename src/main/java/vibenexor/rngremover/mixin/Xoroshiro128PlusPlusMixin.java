package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.Xoroshiro128PlusPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.rngData;

@Mixin(Xoroshiro128PlusPlus.class)
public abstract class Xoroshiro128PlusPlusMixin {

    @Shadow private long seedLo;
    @Shadow private long seedHi;

    @Inject(method = "nextLong", at = @At("HEAD"), cancellable = true)
    private void quellNextLong(CallbackInfoReturnable<Long> cir) {
        if (!rngData.rngActive_Xoroshiro) {
            this.seedLo = rngData.xoroshiroSeedLo;
            this.seedHi = rngData.xoroshiroSeedHi;
            cir.setReturnValue(Long.rotateLeft(this.seedLo + this.seedHi, 17) + rngData.xoroshiroSeedLo);
        }
    }
}