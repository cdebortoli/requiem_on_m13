package net.minecraft.src;

public class EntityMoveHelper
{
    /** The EntityLiving that is being moved */
    private EntityLiving entity;
    private double posX;
    private double posY;
    private double posZ;

    /** The speed at which the entity should move */
    private float speed;
    private boolean field_75643_f;

    public EntityMoveHelper(EntityLiving par1EntityLiving)
    {
        field_75643_f = false;
        entity = par1EntityLiving;
        posX = par1EntityLiving.posX;
        posY = par1EntityLiving.posY;
        posZ = par1EntityLiving.posZ;
    }

    public boolean func_75640_a()
    {
        return field_75643_f;
    }

    public float getSpeed()
    {
        return speed;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double par1, double par3, double par5, float par7)
    {
        posX = par1;
        posY = par3;
        posZ = par5;
        speed = par7;
        field_75643_f = true;
    }

    public void onUpdateMoveHelper()
    {
        entity.setMoveForward(0.0F);

        if (!field_75643_f)
        {
            return;
        }

        field_75643_f = false;
        int i = MathHelper.floor_double(entity.boundingBox.minY + 0.5D);
        double d = posX - entity.posX;
        double d1 = posZ - entity.posZ;
        double d2 = posY - (double)i;
        double d3 = d * d + d2 * d2 + d1 * d1;

        if (d3 < 2.5000002779052011E-007D)
        {
            return;
        }

        float f = (float)((Math.atan2(d1, d) * 180D) / Math.PI) - 90F;
        entity.rotationYaw = func_75639_a(entity.rotationYaw, f, 30F);
        entity.setAIMoveSpeed(speed);

        if (d2 > 0.0D && d * d + d1 * d1 < 1.0D)
        {
            entity.getJumpHelper().setJumping();
        }
    }

    private float func_75639_a(float par1, float par2, float par3)
    {
        float f = MathHelper.func_76142_g(par2 - par1);

        if (f > par3)
        {
            f = par3;
        }

        if (f < -par3)
        {
            f = -par3;
        }

        return par1 + f;
    }
}
