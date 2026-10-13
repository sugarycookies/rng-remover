package vibenexor.rngremover.mixin;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import vibenexor.rngremover.StructurePieceDataGetter;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces$StrongholdPiece")
public abstract class StrongholdPieceMixin extends StructurePiece implements StructurePieceDataGetter {
    protected StrongholdPieceMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @Override
    public StructurePiece getPiece() {
        return this;
    }
}
