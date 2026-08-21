package vibenexor.rngremover.client;

import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import vibenexor.rngremover.RNGData;

public interface CreateWorldScreenRNGHaver {
    void RNGof(RNGData RNG);

    void uiStateOf(WorldCreationUiState state);
}
