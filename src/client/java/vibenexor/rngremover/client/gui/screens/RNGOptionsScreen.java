package vibenexor.rngremover.client.gui.screens;

import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// currently modelling off of WorldOptionsScreen

public class RNGOptionsScreen extends Screen {
    private static final Component TITLE = Component.translatable("options.RNGOptions.title");
    private static final Component GAUSSIAN = Component.translatable("options.RNGOptions.gaussian");
    private static final Component LEGACY = Component.translatable("options.RNGOptions.legacy");
    private static final Component SINGLE_THREADED = Component.translatable("options.RNGOptions.singleThreaded");
    private static final Component THREAD_SAFE = Component.translatable("options.RNGOptions.threadSafeLegacy");
    private static final Component XOROSHIRO = Component.translatable("options.RNGOptions.xoroshiro");

    private static final Component GAUSSIAN_TOOLTIP = Component.translatable("options.RNGOptions.gaussian.tooltip");
    private static final Component LEGACY_TOOLTIP = Component.translatable("options.RNGOptions.legacy.tooltip");
    private static final Component SINGLE_THREADED_TOOLTIP = Component.translatable("options.RNGOptions.singleThreaded.tooltip");
    private static final Component THREAD_SAFE_TOOLTIP = Component.translatable("options.RNGOptions.threadSafeLegacy.tooltip");
    private static final Component XOROSHIRO_TOOLTIP = Component.translatable("options.RNGOptions.xoroshiro.tooltip");

    private static final Component MOB_FIX = Component.translatable("options.RNGOptions.mobFix");
    private static final Component MOB_FIX_TOOLTIP = Component.translatable("options.RNGOptions.mobFix.tooltip");

    private final Screen lastScreen;

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public RNGOptionsScreen(final Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
    }

    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }
}
