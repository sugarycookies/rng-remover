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
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.OptionalLong;

@Mixin(WorldCreationUiState.class)
public abstract class WorldCreationUIStateMixin implements WorldDataRNGDataAccessor {
    @Unique
    private RNGData rngState;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void rngremover$WorldCreationUiState(final Path savesFolder, final WorldCreationContext settings, final Optional<ResourceKey<WorldPreset>> preset, final OptionalLong seed, CallbackInfo ci) {
        rngState = new RNGData();
        System.out.println("New WorldCreationUiState created");
        Thread.dumpStack();
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
    public boolean getUnfixFarlands() {
        return this.rngState.unfixFarlands();
    }

    @Override
    public boolean getForceStructuresSpawn() {
        return this.rngState.forceStructuresSpawn();
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

    @Override
    public void setXoroshiroState(boolean x) {
        this.rngState.setDisableXoroshiro(x);
    }

    @Override
    public void setLegacyState(boolean x) {
        this.rngState.setDisableLegacy(x);
    }

    @Override
    public void setSingleThreadedState(boolean x) {
        this.rngState.setDisableSingleThreaded(x);
    }

    @Override
    public void setThreadSafeLegacyState(boolean x) {
        this.rngState.setDisableThreadSafeLegacy(x);
    }

    @Override
    public void setGaussianState(boolean x) {
        this.rngState.setDisableGaussian(x);
    }

    @Override
    public void setXoroshiroSeed(String x) {
        this.rngState.setXoroshiroSeed(x);
    }

    @Override
    public void setLegacySeed(String x) {
        this.rngState.setLegacySeed(x);
    }

    @Override
    public void setSingleThreadedSeed(String x) {
        this.rngState.setSingleThreadedSeed(x);
    }

    @Override
    public void setThreadSafeLegacySeed(String x) {
        this.rngState.setThreadSafeLegacySeed(x);
    }

    @Override
    public void setGaussianValue(String x) throws NoSuchAlgorithmException {
        this.rngState.setGaussianValue(x);
    }

    @Override
    public void setUUIDState(boolean x) {
        this.rngState.setUUIDs(x);
    }

    @Override
    public void setUnfixFarlands(boolean x) {
        this.rngState.setUnfixFarlands(x);
    }

    @Override
    public void setForceStructureSpawn(boolean x) {
        this.rngState.setForceStructureSpawn(x);
    }
}
