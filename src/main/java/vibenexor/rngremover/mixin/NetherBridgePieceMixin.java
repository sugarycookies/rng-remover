package vibenexor.rngremover.mixin;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

import java.util.List;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces$NetherBridgePiece")
public abstract class NetherBridgePieceMixin extends StructurePiece {
    protected NetherBridgePieceMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @Inject(method = "generatePiece", at = @At("HEAD"), cancellable = true)
    public void rngremover$generatePiece(NetherFortressPieces.StartPiece startPiece, List<Object> currentPieces, StructurePiecesBuilder builder, RandomSource random, int footX, int footY, int footZ, Direction direction, int depth, CallbackInfoReturnable<Object> cir) {
        if (RNGData.disableLegacy && RNGData.forceStructureSpawn) {
            Object netherBridgePiece = NetherFortressPieces.MonsterThrone.createPiece(builder, footX, footY, footZ, depth, direction);
            cir.setReturnValue(netherBridgePiece);
        }
    }
}
