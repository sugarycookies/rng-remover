package vibenexor.rngremover.client.gui.screens;

import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import vibenexor.rngremover.RNGData;

import java.security.NoSuchAlgorithmException;

// currently modeling off of WorldOptionsScreen & CreateWorldScreen, right now it doesn't connect to anything because
// idrk how to actually do that lol. also button states for Gaussian and UUIDs need to be updated in relation to others

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
    private static final Tooltip MOB_FIX_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.mobFix.tooltip"));

    private static final Component XOROSHIRO_SEED = Component.translatable("options.RNGOptions.xoroshiroSeed");
    private static final Component LEGACY_SEED = Component.translatable("options.RNGOptions.legacySeed");
    private static final Component SINGLE_THREADED_SEED = Component.translatable("options.RNGOptions.singleThreadedSeed");
    private static final Component THREAD_SAFE_LEGACY_SEED = Component.translatable("options.RNGOptions.threadSafeLegacySeed");
    private static final Component GAUSSIAN_VALUE = Component.translatable("options.RNGOptions.gaussianValue");

    private static final Tooltip XOROSHIRO_SEED_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.xoroshiroSeed.tooltip"));
    private static final Tooltip LEGACY_SEED_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.legacySeed.tooltip"));
    private static final Tooltip SINGLE_THREADED_SEED_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.singleThreadedSeed.tooltip"));
    private static final Tooltip THREAD_SAFE_LEGACY_SEED_TOOLIP = Tooltip.create(Component.translatable("options.RNGOptions.threadSafeLegacySeed.tooltip"));
    private static final Tooltip GAUSSIAN_VALUE_TOOLTIP = Tooltip.create(Component.translatable("options.RNGOptions.gaussianValue.tooltip"));

    private final Screen lastScreen;

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    private final RNGData uiRNGData;

    public RNGOptionsScreen(final Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
        this.uiRNGData = new RNGData();
    }

    protected void init() {
        this.layout.addToHeader(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);

        GridLayout content = (GridLayout)this.layout.addToContents(new GridLayout(0, 0));
        GridLayout.RowHelper gridHelper = content.columnSpacing(8).rowSpacing(4).createRowHelper(2);
        gridHelper.addChild(this.createXoroshiroButton());
        gridHelper.addChild(this.createXoroshiroSeedButton());
        gridHelper.addChild(this.createLegacyButton());
        gridHelper.addChild(this.createLegacySeedButton());
        gridHelper.addChild(this.createSingleThreadedButton());
        gridHelper.addChild(this.createSingleThreadedSeedButton());
        gridHelper.addChild(this.createThreadSafeLegacyButton());
        gridHelper.addChild(this.createThreadSafeLegacySeedButton());
        gridHelper.addChild(this.createGaussianButton());
        gridHelper.addChild(this.createGaussianValueButton());
        gridHelper.addChild(this.createUUIDsButton());
        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (var1) -> this.onClose()).width(200).build());
        this.layout.visitWidgets((x$0) -> this.addRenderableWidget(x$0));
        this.repositionElements();
    }

    private CycleButton<Boolean> createXoroshiroButton() {
        CycleButton<Boolean> xorButton = CycleButton.onOffBuilder(false).create(XOROSHIRO, (var1, value) -> uiRNGData.setDisableXoroshiro(value));
        xorButton.active = true;
        xorButton.setTooltip(XOROSHIRO_TOOLTIP);
        return xorButton;
    }

    private EditBox createXoroshiroSeedButton() {
        EditBox xorSeeder = new EditBox(this.font, 150, 20, XOROSHIRO_SEED) {
            protected MutableComponent createNarrationMessage() {
                return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(XOROSHIRO_SEED);
            }
        }; //not sure what width it should be

        xorSeeder.setHint(XOROSHIRO_SEED);
        xorSeeder.setTooltip(XOROSHIRO_SEED_TOOLTIP);
        xorSeeder.setValue(String.valueOf(uiRNGData.xoroshiroSeed()));
        xorSeeder.setResponder((value) -> uiRNGData.setXoroshiroSeed(value));

        return xorSeeder;
    }

    private CycleButton<Boolean> createLegacyButton() {
        CycleButton<Boolean> legButton = CycleButton.onOffBuilder(false).create(LEGACY, (var1, value) -> uiRNGData.setDisableLegacy(value));
        legButton.active = true;
        legButton.setTooltip(LEGACY_TOOLTIP);
        return legButton;
    }

    private EditBox createLegacySeedButton() {
        EditBox legSeeder = new EditBox(this.font, 150, 20, LEGACY_SEED) {
            protected MutableComponent createNarrationMessage() {
                return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(LEGACY_SEED);
            }
        };

        legSeeder.setHint(LEGACY_SEED);
        legSeeder.setTooltip(LEGACY_SEED_TOOLTIP);
        legSeeder.setValue(String.valueOf(uiRNGData.legacySeed()));
        legSeeder.setResponder((value) -> uiRNGData.setLegacySeed(value));

        return legSeeder;
    }

    private CycleButton<Boolean> createSingleThreadedButton() {
        CycleButton<Boolean> sinButton = CycleButton.onOffBuilder(false).create(SINGLE_THREADED, (var1, value) -> uiRNGData.setDisableSingleThreaded(value));
        sinButton.active = true;
        sinButton.setTooltip(SINGLE_THREADED_TOOLTIP);
        return sinButton;
    }

    private EditBox createSingleThreadedSeedButton() {
        EditBox sinSeeder = new EditBox(this.font, 150, 20, SINGLE_THREADED_SEED) {
            protected MutableComponent createNarrationMessage() {
                return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(SINGLE_THREADED_SEED);
            }
        };

        sinSeeder.setHint(SINGLE_THREADED_SEED);
        sinSeeder.setTooltip(SINGLE_THREADED_SEED_TOOLTIP);
        sinSeeder.setValue(String.valueOf(uiRNGData.singleThreadedSeed()));
        sinSeeder.setResponder((value) -> uiRNGData.setSingleThreadedSeed(value));

        return sinSeeder;
    }

    private CycleButton<Boolean> createThreadSafeLegacyButton() {
        CycleButton<Boolean> thrButton = CycleButton.onOffBuilder(false).create(THREAD_SAFE, (var1, value) -> uiRNGData.setDisableSingleThreaded(value));
        thrButton.active = true;
        thrButton.setTooltip(THREAD_SAFE_TOOLTIP);
        return thrButton;
    }

    private EditBox createThreadSafeLegacySeedButton() {
        EditBox thrSeeder = new EditBox(this.font, 150, 20, THREAD_SAFE_LEGACY_SEED) {
            protected MutableComponent createNarrationMessage() {
                return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(THREAD_SAFE_LEGACY_SEED);
            }
        };

        thrSeeder.setHint(THREAD_SAFE_LEGACY_SEED);
        thrSeeder.setTooltip(THREAD_SAFE_LEGACY_SEED_TOOLIP);
        thrSeeder.setValue(String.valueOf(uiRNGData.threadSafeLegacySeed()));
        thrSeeder.setResponder((value) -> uiRNGData.setThreadSafeLegacySeed(value));

        return thrSeeder;
    }

    private CycleButton<Boolean> createGaussianButton() {
        CycleButton<Boolean> gauButton = CycleButton.onOffBuilder(false).create(GAUSSIAN, (var1, value) -> uiRNGData.setDisableGaussian(value));
        if (uiRNGData.disableXoroshiro() && uiRNGData.disableLegacy() && uiRNGData.disableSingleThreaded()) {
            gauButton.active = false;
            gauButton.setValue(true);
        } else {
            gauButton.active = true;
        }
        gauButton.setTooltip(GAUSSIAN_TOOLTIP);
        return gauButton;
    }

    private EditBox createGaussianValueButton() {
        EditBox gauSeeder = new EditBox(this.font, 150, 20, GAUSSIAN_VALUE) {
            protected MutableComponent createNarrationMessage() {
                return super.createNarrationMessage().append(CommonComponents.NARRATION_SEPARATOR).append(GAUSSIAN_VALUE);
            }
        };

        gauSeeder.setHint(GAUSSIAN_VALUE);
        gauSeeder.setTooltip(GAUSSIAN_VALUE_TOOLTIP);
        gauSeeder.setValue(String.valueOf(uiRNGData.gaussianValue()));
        gauSeeder.setResponder((value) -> {
            try {
                uiRNGData.setGaussianValue(value);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });

        return gauSeeder;
    }

    private CycleButton<Boolean> createUUIDsButton() {
        CycleButton<Boolean> UUIDButton = CycleButton.onOffBuilder(false).create(MOB_FIX, (var1, value) -> uiRNGData.setDisableGaussian(value));
        if (uiRNGData.disableLegacy()) {
            UUIDButton.active = true;
        } else {
            UUIDButton.active = false;
        }
        UUIDButton.setTooltip(MOB_FIX_TOOLTIP);
        return UUIDButton;
    }

    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    } //should also set WorldCreationUiState RNGData (when that's implemented) to equal uiRNGData
}
