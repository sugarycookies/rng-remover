package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.synth.Noise;
import net.minecraft.world.level.levelgen.synth.NoiseStack;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vibenexor.rngremover.RNGData;

@Mixin(NormalNoise.class)
public abstract class NormalNoiseMixin {
    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/synth/NoiseStack$Builder;add(Lnet/minecraft/world/level/levelgen/synth/Noise;DF)Lnet/minecraft/world/level/levelgen/synth/NoiseStack$Builder;", ordinal = 1))
    private NoiseStack.Builder rngremover$create(NoiseStack.Builder instance, Noise noise, double frequency, float amplitude) {
        if (RNGData.forceNoiseRepeat) {
            instance.add(noise, frequency * 0.98219584569, amplitude);
        } else {
            instance.add(noise, frequency, amplitude);
        }
        return instance;
    }
}
