package vibenexor.rngremover.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData.RespawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

  @Redirect(method = "setInitialSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ChunkPos;containing(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/ChunkPos;"))
  private static ChunkPos injected(BlockPos pos) {

    return ChunkPos.ZERO;
  }

  @Redirect(method = "setInitialSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelData$RespawnData;of(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FF)Lnet/minecraft/world/level/storage/LevelData$RespawnData;"))
  private static RespawnData injected2(ResourceKey<Level> dimension, BlockPos pos, float yaw, float pitch) {

    System.out.println("respawn data of " + pos.toShortString());

    return  RespawnData.of(dimension, pos, 0.0F, 0.0F);
  }
}
