package vibenexor.rngremover.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;

import java.util.Set;
import java.util.UUID;

@Mixin(PrimaryLevelData.class)
public abstract class PrimaryLevelDataMixin implements ServerLevelData, WorldData, WorldDataRNGDataAccessor {
    @Unique
    public RNGData RNGState; //this should end up being the RNGData that is used in whatever world being played in

    //initial values for the RNGData, which is just normal stuff
    @Inject(method = "<init>(Ljava/util/UUID;ZLnet/minecraft/world/level/storage/LevelData$RespawnData;JIZLjava/util/Set;Ljava/util/Set;Lnet/minecraft/world/level/LevelSettings;Lnet/minecraft/world/level/storage/PrimaryLevelData$SpecialWorldProperty;Lcom/mojang/serialization/Lifecycle;)V", at = @At("TAIL"))
    private void rngremover$addsettings(final @Nullable UUID singlePlayerUUID, final boolean wasModded, final LevelData.RespawnData respawnData, final long gameTime, final int version, final boolean initialized, final Set<String> knownServerBrands, final Set<String> removedFeatureFlags, final LevelSettings settings, final PrimaryLevelData.SpecialWorldProperty specialWorldProperty, final Lifecycle worldGenSettingsLifecycle, CallbackInfo ci) {
        RNGState = RNGData.of(false, false, false, false, false, 0, 0, 0, 0, 0.0, false);
    }

    //see the codec in RNGData
    @Inject(method = "setTagData", at = @At("TAIL"))
    private void rngremover$settagdata(final CompoundTag tag, final @Nullable UUID singlePlayerUUID, CallbackInfo ci) {
        tag.store("rng_data", RNGData.CODEC, this.RNGState);
    }

    @Override
    public RNGData RNGState() {
        return this.RNGState;
    }

    @Unique
    public void setRNGState(RNGData RNG) {
        RNGState = RNGData.of(RNG);
    }
}
