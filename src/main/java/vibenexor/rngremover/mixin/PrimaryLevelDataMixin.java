package vibenexor.rngremover.mixin;

import com.google.common.collect.Sets;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.storage.*;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mixin(PrimaryLevelData.class)
public abstract class PrimaryLevelDataMixin implements ServerLevelData, WorldData, WorldDataRNGDataAccessor {
    @Shadow
    public static <T> PrimaryLevelData parse(Dynamic<T> input, LevelSettings settings, PrimaryLevelData.SpecialWorldProperty specialWorldProperty, Lifecycle worldGenSettingsLifecycle) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    private LevelSettings settings;

    @Shadow
    @Final
    @Mutable
    private PrimaryLevelData.SpecialWorldProperty specialWorldProperty;

    @Shadow
    @Final
    @Mutable
    private Lifecycle worldGenSettingsLifecycle;

    @Shadow
    private LevelData.RespawnData respawnData;

    @Shadow
    private long gameTime;

    @Shadow
    @Final
    @Mutable
    private @Nullable UUID singlePlayerUUID;

    @Shadow
    @Final
    @Mutable
    private int version;

    @Shadow
    private boolean initialized;

    @Shadow
    @Final
    @Mutable
    private Set<String> knownServerBrands;

    @Shadow
    private boolean wasModded;

    @Shadow
    @Final
    @Mutable
    private Set<String> removedFeatureFlags;

    @Unique
    public RNGData RNGState;

    //initial values for the RNGData, which is just normal stuff
    @Inject(method = "<init>(Ljava/util/UUID;ZLnet/minecraft/world/level/storage/LevelData$RespawnData;JIZLjava/util/Set;Ljava/util/Set;Lnet/minecraft/world/level/LevelSettings;Lnet/minecraft/world/level/storage/PrimaryLevelData$SpecialWorldProperty;Lcom/mojang/serialization/Lifecycle;)V", at = @At("TAIL"))
    private void rngremover$addsettings(final @Nullable UUID singlePlayerUUID, final boolean wasModded, final LevelData.RespawnData respawnData, final long gameTime, final int version, final boolean initialized, final Set<String> knownServerBrands, final Set<String> removedFeatureFlags, final LevelSettings settings, final PrimaryLevelData.SpecialWorldProperty specialWorldProperty, final Lifecycle worldGenSettingsLifecycle, CallbackInfo ci) {
        RNGState = new RNGData();
    }

    //see the codec in RNGData
    @Inject(method = "setTagData", at = @At("TAIL"))
    private void rngremover$settagdata(final CompoundTag tag, final @Nullable UUID singlePlayerUUID, CallbackInfo ci) {
        tag.store("rng_data", RNGData.CODEC, this.RNGState);
    }

    @Inject(method = "parse", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static <T> void rngremover$parse(Dynamic<T> input, LevelSettings settings, PrimaryLevelData.SpecialWorldProperty specialWorldProperty, Lifecycle worldGenSettingsLifecycle, CallbackInfoReturnable<PrimaryLevelData> cir, @Local(name = "gameTime") long gameTime, @Local(name = "levelVersion") LevelVersion levelVersion) {
        OptionalDynamic var10002 = input.get("singleplayer_uuid");
        Codec var10003 = UUIDUtil.CODEC;
        Objects.requireNonNull(var10003);
        PrimaryLevelData data = new PrimaryLevelData(new LevelSettings("a", GameType.SURVIVAL, LevelSettings.DifficultySettings.DEFAULT, false, WorldDataConfiguration.DEFAULT), PrimaryLevelData.SpecialWorldProperty.NONE, Lifecycle.stable());
        ((WorldDataRNGDataAccessor)data).PrimaryLevelDataAgain((UUID)var10002.flatMap((input2) -> var10003.parse((Dynamic<T>) input2)).result().orElse((Object)null), input.get("WasModded").asBoolean(false), (RespawnData)input.get("spawn").read(RespawnData.CODEC).result().orElse(RespawnData.DEFAULT), gameTime, levelVersion.levelDataVersion(), input.get("initialized").asBoolean(true), (Set)input.get("ServerBrands").asStream().flatMap((b) -> b.asString().result().stream()).collect(Collectors.toCollection(Sets::newLinkedHashSet)), (Set)input.get("removed_features").asStream().flatMap((b) -> b.asString().result().stream()).collect(Collectors.toSet()), settings, specialWorldProperty, worldGenSettingsLifecycle);
        ((WorldDataRNGDataAccessor)data).parseRNG(input);
        cir.setReturnValue(data);
    }

    @Override
    public void PrimaryLevelDataAgain(final @Nullable UUID singlePlayerUUID, final boolean wasModded, final LevelData.RespawnData respawnData, final long gameTime, final int version, final boolean initialized, final Set<String> knownServerBrands, final Set<String> removedFeatureFlags, final LevelSettings settings, final PrimaryLevelData.SpecialWorldProperty specialWorldProperty, final Lifecycle worldGenSettingsLifecycle) {
        this.wasModded = wasModded;
        this.respawnData = respawnData;
        this.gameTime = gameTime;
        this.version = version;
        this.initialized = initialized;
        this.knownServerBrands = knownServerBrands;
        this.removedFeatureFlags = removedFeatureFlags;
        this.singlePlayerUUID = singlePlayerUUID;
        this.settings = settings;
        this.specialWorldProperty = specialWorldProperty;
        this.worldGenSettingsLifecycle = worldGenSettingsLifecycle;
    }

    @Override
    public RNGData RNGState() {
        return this.RNGState;
    }

    @Override
    public void setRNGState(RNGData RNG) {
        RNGState = RNGData.of(RNG);
    }

    @Override
    public <T> void parseRNG(final Dynamic<T> input) {
        RNGState = input.get("rng_data").read(RNGData.CODEC).result().orElse(new RNGData());
    }
}
