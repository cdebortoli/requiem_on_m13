package net.minecraft.src;

public class EntityAIOwnerHurtTarget extends EntityAITarget
{
    EntityTameable theEntityTameable;
    EntityLiving field_75313_b;

    public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
    {
        super(par1EntityTameable, 32F, false);
        theEntityTameable = par1EntityTameable;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theEntityTameable.isTamed())
        {
            return false;
        }

        EntityLiving entityliving = theEntityTameable.getOwner();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            field_75313_b = entityliving.getLastAttackingEntity();
            return isSuitableTarget(field_75313_b, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        taskOwner.setAttackTarget(field_75313_b);
        super.startExecuting();
    }
}
