package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.BitRandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.rngData;

import java.util.concurrent.atomic.AtomicLong;

@Mixin(LegacyRandomSource.class)
public abstract class LegacyRandomSourceMixin implements BitRandomSource {

    @Inject(method = "next", at = @At("HEAD"), cancellable = true)
    public void quellNext(final int bits, CallbackInfoReturnable<Integer> cir) {
        rngData data = new rngData();
        if (!data.rngActive_Legacy) {
            long newSeed = data.legacySeed * 25214903917L + 11L & 281474976710655L;
            cir.setReturnValue((int)(newSeed >> 48 - bits));
        }
    }

}
