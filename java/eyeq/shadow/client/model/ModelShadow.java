package eyeq.shadow.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShadow extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer earRight0;
    public ModelRenderer earRight1;
    public ModelRenderer earRight2;
    public ModelRenderer earLeft0;
    public ModelRenderer earLeft1;
    public ModelRenderer earLeft2;
    public ModelRenderer body;
    public ModelRenderer armRight;
    public ModelRenderer armLeft;
    public ModelRenderer legRight;
    public ModelRenderer legLeft;
    public ModelRenderer footRight;
    public ModelRenderer footLeft;
    public ModelRenderer shadow;

    public ModelShadow() {
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-2F, -2F, -2F, 4, 4, 4);
        head.setRotationPoint(0F, 18F, -4F);

        earRight0 = new ModelRenderer(this, 0, 0);
        earRight0.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        earRight0.setRotationPoint(1F, -2.5F, 0F);
        head.addChild(earRight0);
        earLeft0 = new ModelRenderer(this, 0, 0);
        earLeft0.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        earLeft0.setRotationPoint(-1F, -2.5F, 0F);
        head.addChild(earLeft0);

        earRight1 = new ModelRenderer(this, 0, 0);
        earRight1.addBox(-0.5F, -0.5F, -1F, 1, 1, 2);
        earRight1.setRotationPoint(0F, -1.5F, -0.6F);
        earRight1.rotateAngleX = -0.5F;
        earRight0.addChild(earRight1);
        earLeft1 = new ModelRenderer(this, 0, 0);
        earLeft1.addBox(-0.5F, -0.5F, -1F, 1, 1, 2);
        earLeft1.setRotationPoint(0F, -1.5F, -0.6F);
        earLeft1.rotateAngleX = -0.5F;
        earLeft0.addChild(earLeft1);

        earRight2 = new ModelRenderer(this, 0, 0);
        earRight2.addBox(-0.5F, -0.5F, -1F, 1, 1, 2);
        earRight2.setRotationPoint(0F, 0.3F, -1.2F);
        earRight2.rotateAngleX = 0.6F;
        earRight1.addChild(earRight2);
        earLeft2 = new ModelRenderer(this, 0, 0);
        earLeft2.addBox(-0.5F, -0.5F, -1F, 1, 1, 2);
        earLeft2.setRotationPoint(0F, 0.3F, -1.2F);
        earLeft2.rotateAngleX = 0.6F;
        earLeft1.addChild(earLeft2);

        body = new ModelRenderer(this, 0, 8);
        body.addBox(-2F, -1.5F, 0F, 4, 3, 6);
        body.setRotationPoint(0F, 20F, -3F);
        body.rotateAngleX = 0.2F;

        armRight = new ModelRenderer(this, 0, 0);
        armRight.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        armRight.setRotationPoint(-1.5F, 20F, -2F);
        armLeft = new ModelRenderer(this, 0, 0);
        armLeft.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        armLeft.setRotationPoint(1.5F, 20F, -2F);

        legRight = new ModelRenderer(this, 0, 0);
        legRight.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        legRight.setRotationPoint(-1.5F, 20F, 2F);
        legLeft = new ModelRenderer(this, 0, 0);
        legLeft.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        legLeft.setRotationPoint(1.5F, 20F, 2F);

        footRight = new ModelRenderer(this, 0, 0);
        footRight.addBox(-0.5F, -0.5F, 0F, 1, 1, 3);
        footRight.setRotationPoint(0F, 3F, -2F);
        footRight.rotateAngleX = 0.2F;
        legRight.addChild(footRight);
        footLeft = new ModelRenderer(this, 0, 0);
        footLeft.addBox(-0.5F, -0.5F, 0F, 1, 1, 3);
        footLeft.setRotationPoint(0F, 3F, -2F);
        footLeft.rotateAngleX = 0.2F;
        legLeft.addChild(footLeft);

        shadow = new ModelRenderer(this, 16, 0);
        shadow.addBox(-4F, 0F, -4F, 8, 1, 8);
        shadow.setRotationPoint(0F, 23.8F, 0F);
        shadow.isHidden = true;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        head.render(scale);
        body.render(scale);
        armRight.render(scale);
        armLeft.render(scale);
        legRight.render(scale);
        legLeft.render(scale);
        shadow.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        head.rotateAngleX = headPitch * 0.017453292F + 0.2F;
        head.rotateAngleY = netHeadYaw * 0.017453292F;

        limbSwing *= 0.6662F;
        float rotate;
        rotate = MathHelper.cos(limbSwing) * 1.4F * limbSwingAmount;
        armRight.rotateAngleX = rotate;
        legLeft.rotateAngleX = rotate - 0.2F;

        rotate = MathHelper.cos(limbSwing + (float) Math.PI) * 1.4F * limbSwingAmount;
        armLeft.rotateAngleX = rotate;
        legRight.rotateAngleX = rotate - 0.2F;

        limbSwing *= 0.5F;
        rotate = MathHelper.sin(limbSwing + (float) Math.PI) * 0.62F * limbSwingAmount;
        armRight.rotateAngleZ = rotate + 0.2F;
        legRight.rotateAngleZ = -rotate;

        rotate = MathHelper.sin(limbSwing) * 0.62F * limbSwingAmount;
        armLeft.rotateAngleZ = -rotate - 0.2F;
        legLeft.rotateAngleZ = rotate;
    }
}
