package vibenexor.rngremover.client.gui.screens;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.level.Level;
import vibenexor.rngremover.RNGData;

// currently modelling off of WorldOptionsScreen, right now it's just empty and doesn't connect to anything because 1
// making GUIs is apparently really annoying and 2 to prevent errors. the reason it isn't modelled off of like something
// like VideoSettingsScreen is because coding-wise they're very different due to the nature of the options, since the
// options in WorldOptionsScreen affect the level data, which is more involved with where i'm implementing the RNGData
// per world (PrimaryLevelData)

public class RNGOptionsScreen extends Screen {
    private static final Component TITLE = Component.translatable("options.RNGOptions.title");
    private static final Component GAUSSIAN = Component.translatable("options.RNGOptions.gaussian");
    private static final Component LEGACY = Component.translatable("options.RNGOptions.legacy");
    private static final Component SINGLE_THREADED = Component.translatable("options.RNGOptions.singleThreaded");
    private static final Component THREAD_SAFE = Component.translatable("options.RNGOptions.threadSafeLegacy");
    private static final Component XOROSHIRO = Component.translatable("options.RNGOptions.xoroshiro");

    private static final Tooltip GAUSSIAN_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.gaussian.tooltip"));
    private static final Tooltip LEGACY_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.legacy.tooltip"));
    private static final Tooltip SINGLE_THREADED_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.singleThreaded.tooltip"));
    private static final Tooltip THREAD_SAFE_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.threadSafeLegacy.tooltip"));
    private static final Tooltip XOROSHIRO_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.xoroshiro.tooltip"));

    private static final Component MOB_FIX = Component.translatable("options.RNGOptions.mobFix");
    private static final Component MOB_FIX_TOOLTIP = Component.translatable("options.RNGOptions.mobFix.tooltip");

    private final Screen lastScreen;

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public RNGOptionsScreen(final Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
    }

    protected void init() {
        this.layout.addToHeader(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);
        IntegratedServer singleplayerServer = this.minecraft.getSingleplayerServer();

        GridLayout content = (GridLayout)this.layout.addToContents(new GridLayout(0, 0));
        GridLayout.RowHelper gridHelper = content.columnSpacing(8).rowSpacing(4).createRowHelper(2);
        //add buttons here
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (var1) -> this.onClose()).width(200).build());
        this.layout.visitWidgets((x$0) -> this.addRenderableWidget(x$0));
        this.repositionElements();
    }


    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }
}
