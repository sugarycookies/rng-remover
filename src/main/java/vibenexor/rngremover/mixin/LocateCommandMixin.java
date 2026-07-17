package vibenexor.rngremover.mixin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument.Result;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocateCommand.class)
public class LocateCommandMixin {

  @Inject(at = @At(value = "INVOKE", target = "Lcom/google/common/base/Stopwatch;createStarted(Lcom/google/common/base/Ticker;)Lcom/google/common/base/Stopwatch;"), method = "locateStructure")
  private static void rngremover$locateStructure(CommandSourceStack source, Result<Structure> resourceOrTag, CallbackInfoReturnable<Integer> cir) {

    System.out.println("stopwatch started???");
  }
}
