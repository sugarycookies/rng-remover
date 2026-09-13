package vibenexor.rngremover;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RNGRemover implements ModInitializer {

  public static final String MOD_ID = "rng-remover";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("getrngstate").executes(context -> {
				String xor;
				String xors = Long.toString(RNGData.xoroshiroSeed);
				String leg;
				String legs = Long.toString(RNGData.legacySeed);
				String sin;
				String sins = Long.toString(RNGData.singleThreadedSeed);
				String thr;
				String thrs = Long.toString(RNGData.threadSafeLegacySeed);
				String gau;
				String gaus = Double.toString(RNGData.gaussianValue);
				String uuid;
				if (RNGData.disableXoroshiro) {
					xor = "Disabled";
				} else {
                    xor = "Enabled";
                }
                if (RNGData.disableLegacy) {
					leg = "Disabled";
				} else {
                    leg = "Enabled";
                }
                if (RNGData.disableSingleThreaded) {
					sin = "Disabled";
				} else {
                    sin = "Enabled";
                }
                if (RNGData.disableThreadSafeLegacy) {
					thr = "Disabled";
				} else {
                    thr = "Enabled";
                }
                if (RNGData.disableGaussian) {
					gau = "Disabled";
				} else {
                    gau = "Enabled";
                }
                if (RNGData.forceUUIDsRandom) {
					uuid = "On";
				} else {
                    uuid = "Off";
                }
                context.getSource().sendSuccess(() -> Component.translatable("commands.getrngstate", xor, xors, leg, legs, sin, sins, thr, thrs, gau, gaus, uuid), false);
				return 1;
			}));
		});
	}

	public static Identifier id(String path) {

		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
