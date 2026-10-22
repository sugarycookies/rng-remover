package vibenexor.rngremover;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.jspecify.annotations.Nullable;

import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.UUID;

public interface WorldDataRNGDataAccessor {
    RNGData RNGState();

    void setRNGState(RNGData RNG);

    boolean getXoroshiroState();

    boolean getLegacyState();

    boolean getSingleThreadedState();

    boolean getThreadSafeLegacyState();

    boolean getGaussianState();

    long getXoroshiroSeed();

    long getLegacySeed();

    long getSingleThreadedSeed();

    long getThreadSafeLegacySeed();

    double getGaussianValue();

    boolean getUUIDState();

    boolean getUnfixFarlands();

    boolean getForceStructuresSpawn();

    boolean getForceNoiseRepeat();

    String getXoroshiroSeedS();

    String getLegacySeedS();

    String getSingleThreadedSeedS();

    String getThreadSafeLegacySeedS();

    String getGaussianValueS();

    void setXoroshiroState(boolean x);

    void setLegacyState(boolean x);

    void setSingleThreadedState(boolean x);

    void setThreadSafeLegacyState(boolean x);

    void setGaussianState(boolean x);

    void setXoroshiroSeed(String x);

    void setLegacySeed(String x);

    void setSingleThreadedSeed(String x);

    void setThreadSafeLegacySeed(String x);

    void setGaussianValue(String x) throws NoSuchAlgorithmException;

    void setUUIDState(boolean x);

    void setUnfixFarlands(boolean x);

    void setForceStructureSpawn(boolean x);

    void setForceNoiseRepeat(boolean x);

    void PrimaryLevelDataAgain(final @Nullable UUID singlePlayerUUID, final boolean wasModded, final LevelData.RespawnData respawnData, final long gameTime, final int version, final boolean initialized, final Set<String> knownServerBrands, final Set<String> removedFeatureFlags, final LevelSettings settings, final PrimaryLevelData.SpecialWorldProperty specialWorldProperty, final Lifecycle worldGenSettingsLifecycle);

    <T> void parseRNG(final Dynamic<T> input);
}
