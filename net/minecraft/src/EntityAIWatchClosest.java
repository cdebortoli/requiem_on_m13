package net.minecraft.src;

import java.util.Random;

public class EntityAIWatchClosest extends EntityAIBase
{
    private EntityLiving field_75332_b;

    /** The closest entity which is being watched by this one. */
    protected Entity closestEntity;
    private float field_75333_c;
    private int field_75330_d;
    private float field_75331_e;
    private Class field_75329_f;

    public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3)
    {
        field_75332_b = par1EntityLiving;
        field_75329_f = par2Class;
        field_75333_c = par3;
        field_75331_e = 0.02F;
        setMutexBits(2);
    }

    public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4)
    {
        field_75332_b = par1EntityLiving;
        field_75329_f = par2Class;
        field_75333_c = par3;
        field_75331_e = par4;
        setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (field_75332_b.getRNG().nextFloat() >= field_75331_e)
        {
            return false;
        }

        if (field_75329_f == (net.minecraft.src.EntityPlayer.class))
        {
            closestEntity = field_75332_b.worldObj.getClosestPlayerToEntity(field_75332_b, field_75333_c);
        }
        else
        {
            closestEntity = field_75332_b.worldObj.findNearestEntityWithinAABB(field_75329_f, field_75332_b.boundingBox.expand(field_75333_c, 3D, field_75333_c), field_75332_b);
        }

        return closestEntity != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!closestEntity.isEntityAlive())
        {
            return false;
        }

        if (field_75332_b.getDistanceSqToEntity(closestEntity) > (double)(field_75333_c * field_75333_c))
        {
            return false;
        }
        else
        {
            return field_75330_d > 0;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_75330_d = 40 + field_75332_b.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        closestEntity = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_75332_b.getLookHelper().setLookPosition(closestEntity.posX, closestEntity.posY + (double)closestEntity.getEyeHeight(), closestEntity.posZ, 10F, field_75332_b.getVerticalFaceSpeed());
        field_75330_d--;
    }
}
