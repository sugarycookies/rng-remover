package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import vibenexor.rngremover.OctaveInfoGetter;

@Mixin(targets = "net/minecraft/world/level/levelgen/synth/NormalNoise$OctaveInfo")
public class OctaveInfoMixin implements OctaveInfoGetter {
    public void doSomething() {
        int x = 0;
    }
}
