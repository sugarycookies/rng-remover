package vibenexor.rngremover.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldCallback;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationGameRulesScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.WorldDataRNGDataAccessor;
import vibenexor.rngremover.client.CreateWorldScreenRNGHaver;

import java.util.Objects;

import static java.lang.IO.print;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen implements CreateWorldScreenRNGHaver {
    @Mutable
    @Final
    @Shadow
    private WorldCreationUiState uiState;

    protected CreateWorldScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "openFresh(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldCallback;)V", at = @At("HEAD"))
    private static void rngremover$openfresh(Minecraft minecraft, Runnable onClose, CreateWorldCallback createWorld, CallbackInfo ci) {
        RNGData.resetData();
    }

    @Inject(method = "onCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldCreationContext;options()Lnet/minecraft/world/level/levelgen/WorldOptions;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void rngremover$oncreate(CallbackInfo ci, @Local PrimaryLevelData worldData) {
        if (worldData instanceof WorldDataRNGDataAccessor) {
            if (uiState instanceof WorldDataRNGDataAccessor) {
                ((WorldDataRNGDataAccessor)worldData).setRNGState(((WorldDataRNGDataAccessor)uiState).RNGState());
            }
        }
    }

    @Override
    public void RNGof(RNGData RNG) {
        if (uiState instanceof WorldDataRNGDataAccessor) {
            ((WorldDataRNGDataAccessor)uiState).setRNGState(RNG);
        }
    }

    @Override
    public void uiStateOf(WorldCreationUiState state) {
        this.uiState = state;
    }
}
