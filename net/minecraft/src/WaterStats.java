package net.minecraft.src;

public class WaterStats
{
    /** The player's water level. */
    private int waterLevel;

    /** The player's water exhaustion. */
    private float waterExhaustionLevel;

    /** The player's water timer value. */
    private int waterTimer;
    private int prevWaterLevel;

    private boolean dehydrationIncreased;
    private float dehydrationIncreasedExhaustionValue;

	private float baseWaterExhaustionValue;
	
    public WaterStats()
    {
        waterTimer = 0;
        waterLevel = 20;
        prevWaterLevel = 20;
        dehydrationIncreased = false;
        dehydrationIncreasedExhaustionValue = 0.015F;
		baseWaterExhaustionValue = 0.9F;
    }

    /**
     * Args: int waterLevel
     */
    public void addStats(int par1)
    {
        waterLevel = Math.min(par1 + waterLevel, 20);
    }

    /**
     * Drink some water.
     */
    public void addStats(ItemGourd par1ItemGourd)
    {
        addStats(par1ItemGourd.getWaterAmount());
    }

    /**
     * Handles the water game logic.
     */
    public void onUpdate(EntityPlayer par1EntityPlayer)
    {
        int i = par1EntityPlayer.worldObj.difficultySetting;
        prevWaterLevel = waterLevel;

        if (waterExhaustionLevel > 4F)
        {
            waterExhaustionLevel -= 4F;

            if (i > 0)
            {
                waterLevel = Math.max(waterLevel - 1, 0);
            }
        }

        if (waterLevel <= 0)
        {
            waterTimer++;

            if (waterTimer >= 80)
            {
                if (par1EntityPlayer.getHealth() > 10 || i >= 3 || par1EntityPlayer.getHealth() > 1 && i >= 2)
                {
                    par1EntityPlayer.attackEntityFrom(DamageSource.dehydrated, 1);
                }

                waterTimer = 0;
            }
        }
        else
        {
            waterTimer = 0;
        }
    }

    /**
     * Reads water stats from an NBT object.
     */
    public void readNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("waterLevel"))
        {
            waterLevel = par1NBTTagCompound.getInteger("waterLevel");
            waterTimer = par1NBTTagCompound.getInteger("waterTickTimer");
            waterExhaustionLevel = par1NBTTagCompound.getFloat("waterExhaustionLevel");
        }
    }

    /**
     * Writes water stats to an NBT object.
     */
    public void writeNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("waterLevel", waterLevel);
        par1NBTTagCompound.setInteger("waterTickTimer", waterTimer);
        par1NBTTagCompound.setFloat("waterExhaustionLevel", waterExhaustionLevel);
    }

    /**
     * adds input to waterExhaustionLevel to a max of 40
     */
    public void addWaterExhaustion(float par1)
    {
        float newExhaustionLevel = par1;

        if (dehydrationIncreased)
        {
            newExhaustionLevel = par1 + dehydrationIncreasedExhaustionValue;
        }

        waterExhaustionLevel = Math.min(waterExhaustionLevel + newExhaustionLevel, 40F);
    }

    /**
    * Drink water with hands
    **/
    /*public void drinkWaterWithHand(MovingObjectPosition waterMovingobjectposition, EntityPlayer par1EntityPlayer)
    {
        int i = waterMovingobjectposition.blockX;
        int j = waterMovingobjectposition.blockY;
        int k = waterMovingobjectposition.blockZ;

        if (par1EntityPlayer.worldObj.getBlockMaterial(i, j, k) == Material.water && par1EntityPlayer.worldObj.getBlockMetadata(i, j, k) == 0)
        {
            System.out.println("drink");
            par1EntityPlayer.worldObj.playSoundAtEntity(par1EntityPlayer, "random.drink", 0.5F, par1EntityPlayer.worldObj.rand.nextFloat() * 0.1F + 0.9F); //TODO : Not working anymore ?
            addStats(20);
            par1EntityPlayer.playerTemperature.playerDrinkSomeWater(20);
        }
    }*/

    /**
    * Setter and getter
    **/
	
    public void setWaterLevel(int par1)
    {
        waterLevel = par1;
    }
    public void setDehydrationIncreased(boolean par1)
    {
        dehydrationIncreased = par1;
    }
    public boolean getDehydrationIncreased()
    {
        return dehydrationIncreased;
    }
	public float getBaseWaterExhaustionValue()
	{
		return baseWaterExhaustionValue;
	}
	 /**
     * Get the player's water level.
     */
    public int getWaterLevel()
    {
        return waterLevel;
    }

    public int getPrevWaterLevel()
    {
        return prevWaterLevel;
    }
    /**
     * If waterLevel is not max.
     */
    public boolean needWater()
    {
        return waterLevel < 20;
    }
}
