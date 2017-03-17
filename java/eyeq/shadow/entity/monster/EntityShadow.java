package eyeq.shadow.entity.monster;

import eyeq.shadow.Shadow;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityShadow extends EntityMob {
    private static final DataParameter<Boolean> IS_HIDE = EntityDataManager.<Boolean>createKey(EntityShadow.class, DataSerializers.BOOLEAN);

    protected EntityAIAttackMeleeShadow attackMeleeShadow;

    public EntityShadow(World world) {
        super(world);
        this.setSize(0.6F, 0.8F);
        this.jumpMovementFactor = 0.04F;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.attackMeleeShadow = new EntityAIAttackMeleeShadow(this, 1.0, false);
        this.tasks.addTask(2, this.attackMeleeShadow);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_HIDE, Boolean.FALSE);
    }

    @Override
    protected float getJumpUpwardsMotion() {
        return this.isHide() ? 0.0F : 0.84F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isHide() || source == DamageSource.FALL) {
            return false;
        }
        if(super.attackEntityFrom(source, amount)) {
            this.attackMeleeShadow.tick = 240;
            return true;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource cause) {
        spawnGiant();
        super.onDeath(cause);
    }

    protected void spawnGiant() {
        if(world.isRemote) {
            return;
        }
        if(rand.nextInt(100) != 0) {
            return;
        }
        List<EntityShadow> entityList = world.getEntitiesWithinAABB(EntityShadow.class, this.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
        if(entityList.isEmpty() || (entityList.size() == 1 && entityList.get(0) == this)) {
            for(int i = 0; i < 7; i++) {
                EntityGiantShadow entity = new EntityGiantShadow(world);
                float r = (float) (2 * Math.PI * i / 7);
                BlockPos pos = this.getPosition().add(MathHelper.cos(r) * 8, 0, MathHelper.sin(r) * 8);
                entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntity(entity);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        if(rand.nextFloat() < (0.08 + lootingModifier * 0.01)) {
            dropItem(Shadow.lucidShard, 1);
        }
    }

    public boolean isHide() {
        return this.getDataManager().get(IS_HIDE);
    }

    public void setHide(boolean isHide) {
        this.getDataManager().set(IS_HIDE, isHide);
    }

    public class EntityAIAttackMeleeShadow extends EntityAIAttackMelee {
        private final EntityShadow entity;
        protected int tick;

        public EntityAIAttackMeleeShadow(EntityShadow entity, double speed, boolean useLongMemory) {
            super(entity, speed, useLongMemory);
            this.entity = entity;
        }

        @Override
        public void updateTask() {
            if(!entity.onGround) {
                entity.setHide(false);
                tick = 240;
                super.updateTask();
                return;
            }
            boolean isHide = entity.isHide();
            tick--;
            if(tick < 0) {
                tick = isHide ? 240 : 60;
                isHide = !isHide;
                entity.setHide(isHide);
            }
            if(isHide) {
                attackTick = 200;
            } else if(rand.nextInt(200) == 0) {
                entity.getJumpHelper().setJumping();
            }
            super.updateTask();
        }
    }
}
