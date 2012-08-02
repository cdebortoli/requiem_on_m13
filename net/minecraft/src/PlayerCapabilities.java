package net.minecraft.src;

public class PlayerCapabilities
{
    /** Disables player damage. */
    public boolean disableDamage;

    /** Sets/indicates whether the player is flying. */
    public boolean isFlying;

    /** whether or not to allow the player to fly when they double jump. */
    public boolean allowFlying;

    /**
     * Used to determine if creative mode is enabled, and therefore if items should be depleted on usage
     */
    public boolean isCreativeMode;
    public boolean field_75099_e;
    private float field_75096_f;
    private float field_75097_g;

    public PlayerCapabilities()
    {
        disableDamage = false;
        isFlying = false;
        allowFlying = false;
        isCreativeMode = false;
        field_75099_e = true;
        field_75096_f = 0.05F;
        field_75097_g = 0.1F;
    }

    public void writeCapabilitiesToNBT(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setBoolean("invulnerable", disableDamage);
        nbttagcompound.setBoolean("flying", isFlying);
        nbttagcompound.setBoolean("mayfly", allowFlying);
        nbttagcompound.setBoolean("instabuild", isCreativeMode);
        nbttagcompound.setBoolean("mayBuild", field_75099_e);
        nbttagcompound.setFloat("flySpeed", field_75096_f);
        nbttagcompound.setFloat("walkSpeed", field_75097_g);
        par1NBTTagCompound.setTag("abilities", nbttagcompound);
    }

    public void readCapabilitiesFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("abilities"))
        {
            NBTTagCompound nbttagcompound = par1NBTTagCompound.getCompoundTag("abilities");
            disableDamage = nbttagcompound.getBoolean("invulnerable");
            isFlying = nbttagcompound.getBoolean("flying");
            allowFlying = nbttagcompound.getBoolean("mayfly");
            isCreativeMode = nbttagcompound.getBoolean("instabuild");

            if (nbttagcompound.hasKey("flySpeed"))
            {
                field_75096_f = nbttagcompound.getFloat("flySpeed");
                field_75097_g = nbttagcompound.getFloat("walkSpeed");
            }

            if (nbttagcompound.hasKey("mayBuild"))
            {
                field_75099_e = nbttagcompound.getBoolean("mayBuild");
            }
        }
    }

    public float func_75093_a()
    {
        return field_75096_f;
    }

    public void func_75092_a(float par1)
    {
        field_75096_f = par1;
    }

    public float func_75094_b()
    {
        return field_75097_g;
    }
}
