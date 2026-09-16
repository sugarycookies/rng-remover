package vibenexor.rngremover.client.mixin;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vibenexor.rngremover.RNGData;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
    @Shadow
    private int starIndexCount;

    @Inject(method = "buildStars", at = @At(value = "HEAD"), cancellable = true)
    private void rngremover$buildStars(CallbackInfoReturnable<GpuBuffer> cir) {
        if (RNGData.disableSingleThreaded) {
            RandomSource random = RandomSource.createThreadLocalInstance(10842L);

            GpuBuffer var19;
            try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(DefaultVertexFormat.POSITION.getVertexSize() * 1500 * 4)) {
                BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, PrimitiveTopology.QUADS, DefaultVertexFormat.POSITION);

                for(int i = 0; i < 1500; ++i) {
                    float x = random.nextFloat() * 2.0F - 1.0F;
                    float y = random.nextFloat() * 2.0F - 1.0F;
                    float z = random.nextFloat() * 2.0F - 1.0F;
                    float starSize = 0.15F + random.nextFloat() * 0.1F;
                    Vector3f starCenter = (new Vector3f(x, y, z)).normalize(100.0F);
                    float zRot = (float)(random.nextDouble() * (double)(float)Math.PI * (double)2.0F);
                    Matrix3f rotation = (new Matrix3f()).rotateTowards((new Vector3f(starCenter)).negate(), new Vector3f(0.0F, 1.0F, 0.0F)).rotateZ(-zRot);
                    bufferBuilder.addVertex((new Vector3f(starSize, -starSize, 0.0F)).mul(rotation).add(starCenter));
                    bufferBuilder.addVertex((new Vector3f(starSize, starSize, 0.0F)).mul(rotation).add(starCenter));
                    bufferBuilder.addVertex((new Vector3f(-starSize, starSize, 0.0F)).mul(rotation).add(starCenter));
                    bufferBuilder.addVertex((new Vector3f(-starSize, -starSize, 0.0F)).mul(rotation).add(starCenter));
                }

                try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                    this.starIndexCount = mesh.drawState().indexCount();
                    var19 = RenderSystem.getDevice().createBuffer(() -> "Stars vertex buffer", 40, mesh.vertexBuffer());
                }
            }

            cir.setReturnValue(var19);
        }
    }
}
