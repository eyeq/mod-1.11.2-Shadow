package eyeq.shadow.client.renderer.entity;

import eyeq.shadow.client.model.ModelShadow;
import eyeq.shadow.client.renderer.entity.layers.LayerShadowEyes;
import eyeq.shadow.entity.monster.EntityShadow;
import eyeq.util.client.renderer.EntityRenderResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import static eyeq.shadow.Shadow.MOD_ID;

public class RenderShadow extends RenderLiving<EntityShadow> {
    protected static final ResourceLocation textures = new EntityRenderResourceLocation(MOD_ID, "shadow");

    private static float scale = 2.0F;

    public RenderShadow(RenderManager renderManager) {
        super(renderManager, new ModelShadow(), 0.5F);
        this.addLayer(new LayerShadowEyes(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShadow entity) {
        return textures;
    }

    @Override
    public void doRender(EntityShadow entity, double x, double y, double z, float rotationYaw, float partialTicks) {
        ModelShadow model = (ModelShadow) this.mainModel;
        boolean isHide = entity.isHide();
        model.head.isHidden = isHide;
        model.body.isHidden = isHide;
        model.armRight.isHidden = isHide;
        model.armLeft.isHidden = isHide;
        model.legRight.isHidden = isHide;
        model.legLeft.isHidden = isHide;
        model.shadow.isHidden = !isHide;
        super.doRender(entity, x, y, z, rotationYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityShadow entity, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
}
