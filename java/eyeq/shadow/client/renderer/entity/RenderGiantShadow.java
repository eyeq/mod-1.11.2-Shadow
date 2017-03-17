package eyeq.shadow.client.renderer.entity;

import eyeq.shadow.entity.monster.EntityShadow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderGiantShadow extends RenderShadow {
    private static float scale = 4.0F;

    public RenderGiantShadow(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void preRenderCallback(EntityShadow entity, float partialTickTime) {
        GlStateManager.scale(this.scale, this.scale, this.scale);
    }
}
