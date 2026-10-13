package vibenexor.rngremover.mixin;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;
import vibenexor.rngremover.StructurePieceDataGetter;

@Mixin(StrongholdPieces.class)
public abstract class StrongholdPiecesMixin {
    @Shadow
    private static boolean updatePieceWeight() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "generatePieceFromSmallDoor", at = @At("HEAD"), cancellable = true)
    private static void generatePieceFromSmallDoor$rngremover(StrongholdPieces.StartPiece startPiece, StructurePiecesBuilder builder, RandomSource random, int footX, int footY, int footZ, Direction direction, int depth, CallbackInfoReturnable<Object> cir) {
        if (RNGData.disableLegacy && RNGData.forceStructureSpawn) {
            Object strongholdPiece = StrongholdPieces.PortalRoom.createPiece(builder, footX, footY, footZ, direction, depth);
            cir.setReturnValue(strongholdPiece);
        }
    }
}
