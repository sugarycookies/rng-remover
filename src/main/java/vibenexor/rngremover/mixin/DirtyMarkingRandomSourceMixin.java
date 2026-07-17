package vibenexor.rngremover.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.RandomSequences;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RandomSequences.DirtyMarkingRandomSource.class)
public class DirtyMarkingRandomSourceMixin {

  @ModifyVariable(method = "<init>", at = @At("HEAD"))
  private static RandomSource rngremover$init(RandomSource random) {

    return RandomSource.create();
  }
}
