package vibenexor.rngremover.mixin;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.SecondaryLegacyRandomSource;

import java.util.UUID;

@Mixin(Mth.class)
public abstract class MthMixin {

    @Inject(method = "createInsecureUUID", at = @At(value = "HEAD"), cancellable = true)
    private static void rngremover$createInsecureUUIDStart(RandomSource random, CallbackInfoReturnable<UUID> cir) {
        if (RNGData.forceUUIDsRandom) {
            SecondaryLegacyRandomSource random2 = new SecondaryLegacyRandomSource(SecondaryLegacyRandomSource.generateUniqueSeed());
            long most = random2.nextLong() & -61441L | 16384L;
            long least = random2.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
            UUID newUUID = new UUID(most, least);
            cir.setReturnValue(newUUID);
        }
    }
}
