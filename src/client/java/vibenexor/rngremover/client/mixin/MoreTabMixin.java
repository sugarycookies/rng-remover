package vibenexor.rngremover.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.GridLayoutTab;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vibenexor.rngremover.client.gui.screens.RNGOptionsScreen;

@Mixin(targets = "net.minecraft.client.gui.screens.worldselection.CreateWorldScreen$MoreTab")
public class MoreTabMixin extends GridLayoutTab {
    public MoreTabMixin(Component title) {
        super(title);
    }

    @Final
    @Shadow
    CreateWorldScreen this$0;

    @Unique
    private static final Component RNG_BUTTON = Component.translatable("options.RNGButton");

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void rngremover$MoreTab(CallbackInfo ci, @Local(name = "helper") GridLayout.RowHelper helper) {
        helper.addChild(this.createRNGButton());
    }

    @Unique
    private Button createRNGButton() {
        return Button.builder(RNG_BUTTON, (b) -> this.openRNGOptionsScreen()).width(210).build();
    }

    @Unique
    private void openRNGOptionsScreen() {
        Minecraft.getInstance().gui.setScreen(new RNGOptionsScreen(this$0, this$0.getUiState()));
    }
}
