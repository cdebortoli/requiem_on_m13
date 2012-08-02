package net.minecraft.src;

public class EnergyStats
{
    /** The player's energy level. */
    private int energyLevel;
    /** The player's energy exhaustion. */
    private float energyExhaustionLevel;
    private int prevEnergyLevel;

    private boolean fatigueIncreased;
    private float fatigueIncreasedExhaustionValue;
    private boolean isFatigued;
	
	private float baseEnergyExhaustionValue;
	private float baseEnergyExhaustionValueNormal;
	private float baseEnergyExhaustionValueWhenHungry;
	private float baseEnergyExhaustionValueWhenThirsty;
	private float baseEnergyExhaustionValueWhenHungryAndThirsty;
	
	private int baseEnergyRegenerationTimer;
	private int energyRegenerationTimer;


    public EnergyStats()
    {
        energyLevel = 20;
        prevEnergyLevel = 20;
        fatigueIncreased = false;
        fatigueIncreasedExhaustionValue = 0.015F;
        isFatigued = false;
		baseEnergyExhaustionValueNormal = 0.25F;
		baseEnergyExhaustionValueWhenThirsty = 0.35F;
		baseEnergyExhaustionValueWhenHungry = 0.45F;
		baseEnergyExhaustionValueWhenHungryAndThirsty = 0.55F;
		baseEnergyExhaustionValue = baseEnergyExhaustionValueNormal;
		
		baseEnergyRegenerationTimer = 6000;
		energyRegenerationTimer = 0;
    }

    /**
     * Args: int energyLevel
     */
    public void addStats(int par1)
    {
        energyLevel = Math.min(par1 + energyLevel, 20);
    }

    /**
     * Handles the energy game logic.
     */
    public void onUpdate(EntityPlayer par1EntityPlayer)
    {
		// Check if player is hungry or thirsty
		if((par1EntityPlayer.getFoodStats().getFoodLevel()<=2)&&(par1EntityPlayer.getWaterStats().getWaterLevel()<=2))
		{
			baseEnergyExhaustionValue = baseEnergyExhaustionValueWhenHungryAndThirsty;
		}
		else if(par1EntityPlayer.getFoodStats().getFoodLevel()<=2)
		{
			baseEnergyExhaustionValue = baseEnergyExhaustionValueWhenHungry;
		}
		else if(par1EntityPlayer.getWaterStats().getWaterLevel()<=2)
		{
			baseEnergyExhaustionValue = baseEnergyExhaustionValueWhenThirsty;
		}
		else
		{
			baseEnergyExhaustionValue = baseEnergyExhaustionValueNormal;
		}
		
		// Calcul energy loss
        int i = par1EntityPlayer.worldObj.difficultySetting;
        prevEnergyLevel = energyLevel;

        if (energyExhaustionLevel > 4F)
        {
            energyExhaustionLevel -= 4F;

            if (i > 0)
            {
                energyLevel = Math.max(energyLevel - 1, 0);
            }
        }
		
		// Calcul energy regeneration
		energyRegenerationTimer++;
		if(energyRegenerationTimer >= baseEnergyRegenerationTimer)
		{
			energyRegenerationTimer = 0;
			energyLevel++;
			if(energyLevel>20)
			{
				energyLevel = 20;
			}
		}
		
		// Effect of low energy
        if (energyLevel <= 0)
        {
            isFatigued = true;
        }
        else
        {
            isFatigued = false;
        }

    }
	
    /**
     * Reads energy stats from an NBT object.
     */
    public void readNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("energyLevel"))
        {
            energyLevel = par1NBTTagCompound.getInteger("energyLevel");
            energyExhaustionLevel = par1NBTTagCompound.getFloat("energyExhaustionLevel");
            isFatigued = par1NBTTagCompound.getBoolean("isFatigued");
        }
    }

    /**
     * Writes energy stats to an NBT object.
     */
    public void writeNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("energyLevel", energyLevel);
        par1NBTTagCompound.setFloat("energyExhaustionLevel", energyExhaustionLevel);
        par1NBTTagCompound.setBoolean("isFatigued", isFatigued);
    }

    /**
     * Get the player's energy level.
     */
    public int getEnergyLevel()
    {
        return energyLevel;
    }

    public int getPrevEnergyLevel()
    {
        return prevEnergyLevel;
    }

    public boolean getIsFatigued()
    {
        return isFatigued;
    }

    /**
     * If energyLevel is not max.
     */
    public boolean needEnergy()
    {
        return energyLevel < 20;
    }

    /**
     * adds input to energyExhaustionLevel to a max of 40
     */
    public void addEnergyExhaustion(float par1)
    {
        float newExhaustionLevel = par1;

        if (fatigueIncreased)
        {
            newExhaustionLevel = par1 + fatigueIncreasedExhaustionValue;
        }

        energyExhaustionLevel = Math.min(energyExhaustionLevel + newExhaustionLevel, 40F);
    }

    public void setEnergyLevel(int par1)
    {
        energyLevel = par1;
    }

    public void setFatigueIncreased(boolean par1)
    {
        fatigueIncreased = par1;
    }
    public boolean getFatigueIncreased()
    {
        return fatigueIncreased;
    }
	public float getBaseEnergyExhaustionValue()
	{
		return baseEnergyExhaustionValue;
	}
}
