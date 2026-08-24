package vibenexor.rngremover.client.mixin;

import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;

import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalLong;

@Mixin(WorldCreationUiState.class)
public abstract class WorldCreationUIStateMixin implements WorldDataRNGDataAccessor {
    @Unique
    private RNGData rngState;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void rngremover$WorldCreationUiState(final Path savesFolder, final WorldCreationContext settings, final Optional<ResourceKey<WorldPreset>> preset, final OptionalLong seed, CallbackInfo ci) {
        rngState = new RNGData();
    }

    @Override
    public RNGData RNGState() {
        return this.rngState;
    }

    @Override
    public void setRNGState(RNGData RNG) {
        this.rngState = RNG;
    }

    @Override
    public boolean getXoroshiroState() {
        return this.rngState.disableXoroshiro();
    }

    @Override
    public boolean getLegacyState() {
        return this.rngState.disableLegacy();
    }

    @Override
    public boolean getSingleThreadedState() {
        return this.rngState.disableSingleThreaded();
    }

    @Override
    public boolean getThreadSafeLegacyState() {
        return this.rngState.disableThreadSafeLegacy();
    }

    @Override
    public boolean getGaussianState() {
        return this.rngState.disableGaussian();
    }

    @Override
    public long getXoroshiroSeed() {
        return this.rngState.xoroshiroSeed();
    }

    @Override
    public long getLegacySeed() {
        return this.rngState.legacySeed();
    }

    @Override
    public long getSingleThreadedSeed() {
        return this.rngState.singleThreadedSeed();
    }

    @Override
    public long getThreadSafeLegacySeed() {
        return this.rngState.threadSafeLegacySeed();
    }

    @Override
    public double getGaussianValue() {
        return this.rngState.gaussianValue();
    }

    @Override
    public boolean getUUIDState() {
        return this.rngState.forceUUIDsRandom();
    }

    @Override
    public String getXoroshiroSeedS() {
        long x = this.rngState.xoroshiroSeed();
        return Long.toString(x);
    }

    @Override
    public String getLegacySeedS() {
        long x = this.rngState.legacySeed();
        return Long.toString(x);
    }

    @Override
    public String getSingleThreadedSeedS() {
        long x = this.rngState.singleThreadedSeed();
        return Long.toString(x);
    }

    @Override
    public String getThreadSafeLegacySeedS() {
        long x = this.rngState.threadSafeLegacySeed();
        return Long.toString(x);
    }

    @Override
    public String getGaussianValueS() {
        double x = this.rngState.gaussianValue();
        return Double.toString(x);
    }

    //ADD ALL THIS FOR SETTER FUNCTIONS. USE THOSE IN RNGOPTIONSSCREEN INSTEAD OF UIRNGSTATE (possibly)
}
