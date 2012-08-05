package net.minecraft.src;

public class EnergyStats
{
    /** The player's energy level. */
    private int energyLevel;
    /** The player's energy exhaustion. */
    private float energyExhaustionLevel;
    private int prevEnergyLevel;

    private boolean isFatigued;
	
	private float baseEnergyExhaustionValue;
	private float baseEnergyExhaustionValueNormal;
	private float baseEnergyExhaustionValueWhenHungry;
	private float baseEnergyExhaustionValueWhenThirsty;
	private float baseEnergyExhaustionValueWhenHyperthermiaOrHypothermia;
	
	private int baseEnergyRegenerationTimer;
	private int energyRegenerationTimer;


    public EnergyStats()
    {
        energyLevel = 20;
        prevEnergyLevel = 20;
        isFatigued = false;
		baseEnergyExhaustionValueNormal = 0.25F;
		baseEnergyExhaustionValueWhenThirsty = 0.10F;
		baseEnergyExhaustionValueWhenHungry = 0.15F;
		baseEnergyExhaustionValueWhenHyperthermiaOrHypothermia = 0.25F;
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
		baseEnergyExhaustionValue = baseEnergyExhaustionValueNormal;
		if(par1EntityPlayer.getFoodStats().getFoodLevel()<=2)
		{
			baseEnergyExhaustionValue += baseEnergyExhaustionValueWhenHungry;
		}
		if(par1EntityPlayer.getWaterStats().getWaterLevel()<=2)
		{
			baseEnergyExhaustionValue += baseEnergyExhaustionValueWhenThirsty;
		}
		if((par1EntityPlayer.playerTemperature.getIsInHypothermia()) || (par1EntityPlayer.playerTemperature.getIsInHyperthermia()))
		{
			baseEnergyExhaustionValue += baseEnergyExhaustionValueWhenHyperthermiaOrHypothermia;
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
        checkIfPlayerIsFatigued();
    }
	public void checkIfPlayerIsFatigued()
	{
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
        energyExhaustionLevel = Math.min(energyExhaustionLevel + par1, 40F);
    }

    public void setEnergyLevel(int par1)
    {
        energyLevel = par1;
    }

	public float getBaseEnergyExhaustionValue()
	{
		return baseEnergyExhaustionValue;
	}
}
