package vibenexor.rngremover.mixin;

import net.fabricmc.fabric.api.attachment.v1.GlobalAttachmentsProvider;
import net.fabricmc.fabric.api.resource.v1.DataResourceStore;
import net.minecraft.commands.CommandSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInfo;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.level.chunk.storage.ChunkIOErrorReporter;
import net.minecraft.world.level.storage.WorldData;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;

import java.util.UUID;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantBlockableEventLoop<TickTask> implements CommandSource, ServerInfo, ChunkIOErrorReporter, GlobalAttachmentsProvider, DataResourceStore {
    public MinecraftServerMixin(String name, boolean propagatesCrashes) {
        super(name, propagatesCrashes);
    }

    @Final
    @Shadow
    protected WorldData worldData;

    @Inject(method = "loadLevel", at = @At("HEAD"))
    private void rngremover$loadLevel(CallbackInfo ci) {
        this.setWorldRNGState(worldData);
    }

    @Unique
    private void setWorldRNGState(WorldData worldData) {
        if (worldData instanceof WorldDataRNGDataAccessor) {
            RNGData.of(((WorldDataRNGDataAccessor)worldData).RNGState());
        }
    }
}
