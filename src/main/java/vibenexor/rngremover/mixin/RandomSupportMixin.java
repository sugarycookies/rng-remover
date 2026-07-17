package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.RandomSupport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(RandomSupport.class)
public class RandomSupportMixin {

//  @Inject(at = @At("HEAD"), method = "generateUniqueSeed", cancellable = true)
//  private static void rngremover$generateUniqueSeed(CallbackInfoReturnable<Long> cir) {
//
//    // cir.setReturnValue(RNGData.legacySeed);
//  }
}
