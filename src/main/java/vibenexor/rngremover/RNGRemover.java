package vibenexor.rngremover;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.resources.Identifier;

import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RNGRemover implements ModInitializer {

  public static final String MOD_ID = "rng-remover";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

    ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {

      if (entity instanceof ServerPlayer player) {

        System.out.println("player joined with pos " + player.blockPosition().toShortString());
      }
    });
	}

	public static Identifier id(String path) {

		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
