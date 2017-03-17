package eyeq.shadow.entity.monster;

import eyeq.shadow.Shadow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGiantShadow extends EntityShadow {
    public EntityGiantShadow(World world) {
        super(world);
        this.setSize(1.2F, 1.6F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(14.0);
    }

    @Override
    protected void spawnGiant() {}

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        dropItem(Shadow.lucidShard, 1);
    }

    @Override
    public void onEntityUpdate() {
        if(this.attackMeleeShadow != null) {
            this.attackMeleeShadow.tick = 240;
        }
        super.onEntityUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            this.setDead();
            return true;
        }
        return false;
    }
}
