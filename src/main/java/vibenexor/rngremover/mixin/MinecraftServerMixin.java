package vibenexor.rngremover.mixin;

import net.fabricmc.fabric.api.attachment.v1.GlobalAttachmentsProvider;
import net.fabricmc.fabric.api.resource.v1.DataResourceStore;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInfo;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.level.chunk.storage.ChunkIOErrorReporter;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import vibenexor.rngremover.RNGData;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantBlockableEventLoop<TickTask> implements CommandSource, ServerInfo, ChunkIOErrorReporter, GlobalAttachmentsProvider, DataResourceStore {
    public MinecraftServerMixin(String name, boolean propagatesCrashes) {
        super(name, propagatesCrashes);
    }

    //ok so since the RNGData is being turned into a codec as part of PrimaryLevelData,
    //it might be necessary to change MinecraftServer so that the worldData variable it
    //uses is what actually controls whether the RNG is active or not? since it's a per
    //world thing ideally right. based on that i think the best move might be to add to
    //the world initialization function (not sure which one tho) that sets stuff in the
    //RNGData class to the values in the codec and then they hopefully don't change for
    //the rest of the time in a single level instance. otherwise wouldn't it make it so
    //we have to manually edit every instance of random function usage to initially see
    //what's in the worldData variable in MinecraftServer? would be very annoying

    @Unique
    private void setRNGState(WorldData worldData) {
        RNGData.of(worldData.RNGState());
    }
    //not sure how to fix this error; what's supposed to happen is that we're taking an input
    //of some WorldData (the one from MinecraftServer) and extracting the RNGState of that data,
    //which is then used to set all the variables in RNGData. ideally a call for this function
    //would be added in like the MinecraftServer initialization function. anyway the issue
    //is that the variable RNGState is added to PrimaryLevelData (an implementation of WorldData
    //since it's an interface for some reason) through a mixin and therefore the program doesn't
    //know what it is since it's not normally part of the class. not sure how to make it understand
}
