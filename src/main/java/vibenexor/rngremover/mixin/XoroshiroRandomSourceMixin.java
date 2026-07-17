package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import vibenexor.rngremover.RNGData;

@Mixin(XoroshiroRandomSource.class)
public abstract class XoroshiroRandomSourceMixin {

  @ModifyVariable(method = "setSeed", at = @At("HEAD"), name = "seed")
  private long setSeed(long seed) {

    return RNGData.legacySeed;
  }
}