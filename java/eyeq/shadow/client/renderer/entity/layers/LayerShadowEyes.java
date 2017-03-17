package eyeq.shadow.client.renderer.entity.layers;

import eyeq.shadow.entity.monster.EntityShadow;
import eyeq.util.client.renderer.EntityRenderResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static eyeq.shadow.Shadow.MOD_ID;

@SideOnly(Side.CLIENT)
public class LayerShadowEyes implements LayerRenderer<EntityShadow> {
    protected static final ResourceLocation textures = new EntityRenderResourceLocation(MOD_ID, "shadow_eyes");

    private final RenderLiving render;

    public LayerShadowEyes(RenderLiving render) {
        this.render = render;
    }

    @Override
    public void doRenderLayer(EntityShadow entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.render.bindTexture(textures);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(!(entity.isInvisible() || entity.isHide()));
        int i = 61680;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i % 65536, i / 65536);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getMinecraft().entityRenderer.func_191514_d(true);
        this.render.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.func_191514_d(false);

        i = entity.getBrightnessForRender(partialTicks);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i % 65536, i / 65536);
        this.render.setLightmap(entity, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
