package vibenexor.rngremover.client.mixin;

import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;

@Mixin(WorldCreationUiState.class)
public abstract class WorldCreationUIStateMixin implements WorldDataRNGDataAccessor {
    @Unique
    private RNGData rngState;

    @Override
    public RNGData RNGState() {
        return this.rngState;
    }

    @Override
    public void setRNGState(RNGData RNG) {
        this.rngState = RNG;
    }
}
