package net.minecraft.src;

public class PlayerTemperature
{
    // Distance to check for heat source
    private final int xDistanceToCheck = 5;
    private final int yDistanceToCheck = 5;
    private final int zDistanceToCheck = 5;
    // Item value for heat source
    private final float furnaceHeatProduction = 10f;
    private final float lavaHeatProduction = 30f;
    private final float torchHeatProduction = 5f;
    private final float redstoneHeatProduction = 10f;
    private final float redstoneLampHeatProduction = 20f;
    private final float blockFireHeatProduction = 25f;
    private final float coefMultipleHeatSources = 2.5f; //If many heat sources, we take the max heat production + coefMultipleHeatSources
    // Temperature of environment (not only biome) before hot/cold
    private final float maxTemperatureBeforeHot = 50f;
    private final float minTemperatureBeforeCold = 1f;
    // Temperature of biome which are extreme
    private final float highBiomeTemperature = 15f;
    private final float lowBiomeTemperature = 0.5f;
    // Timers for damage
    private final int maxTimerBeforeInjury = 2000; // Max timer before the player is damaged by weather
    private final int maxTimer = 2500; // Timer can pass this value
    // Temperatures data for calculs
    private final float highTemperatureAndHumidityValue = 5.0f; // Temperature to add if biome has high temperature et high humidity
    private final float highTemperatureAndSunExpositionValue = 10.0f; // Temperature to add if biome has an high temperature and the player is under the sun
    private final float highAltitudeValue = 20.0f; // Temperature to subtract when the player is at high altitude
    private final float coldAndInWaterValue = 20.0f; // Temperature to subtract when the player is in water while the biome has low temperature
    private final float coldAndUnderSnowValue = 25.0f; // Temperature to subtract when the player is under snow while the biome has low temperature
    private final float nightValue = 10.0f; // Temperature to subtract when it's night
    // Altitude max
    private final int altitudeMax = 128;

    //Data
    private float biomeTemperature;
    private boolean biomeIsRaining;
    private boolean biomeSnowIsEnabled;
    private int altitude;
    private boolean isUnderWater;
    private boolean canSeeSky;
    private boolean isUnderCloud;
    private float biomeRainfall;
    private boolean multipleHeatSources;
    private boolean isDayTime;

    //Calculs
    private boolean isInHypothermia;
    private boolean isInHyperthermia;
    private boolean isHot;
    private boolean isCold;
    private float playerTemperatureFromHeatSource;
    private float playerTemperatureValue;

    //Timers
    private int hotCount;
    private int coldCount;
    private int damageHyperthermiaCount;
    private int damageHypothermiaCount;

    public PlayerTemperature()
    {
        biomeTemperature = 0;
        biomeIsRaining = false;
        biomeSnowIsEnabled = false;
        altitude = 0;
        isUnderWater = false;
        canSeeSky = false;
        isUnderCloud = false;
        biomeRainfall = 0;
        isInHypothermia = false;
        isInHyperthermia = false;
        isHot = false;
        isCold = false;
        playerTemperatureFromHeatSource = 0;
        multipleHeatSources = false;
        hotCount = 0;
        coldCount = 0;
        damageHyperthermiaCount = 0;
        damageHypothermiaCount = 0;
        isDayTime = false;
        playerTemperatureValue = 0;
    }

    public void onUpdate(EntityPlayer par1EntityPlayer)
    {
        //Player position
        int playerPosX = MathHelper.floor_double(par1EntityPlayer.posX);
        int playerPosY = MathHelper.floor_double(par1EntityPlayer.posY);
        int playerPosZ = MathHelper.floor_double(par1EntityPlayer.posZ);
        //Check closest heat sources
        resetPlayerHeatSourceInformations();
        //Get min and max positions around the player
        int minX = playerPosX - xDistanceToCheck;
        int maxX = playerPosX + xDistanceToCheck;
        int minZ = playerPosZ - yDistanceToCheck;
        int maxZ = playerPosZ + yDistanceToCheck;
        int minY = playerPosY - zDistanceToCheck;
        int maxY = playerPosY + zDistanceToCheck;

        //Check
        for (int iX = minX; iX <= maxX; iX++)
        {
            for (int iZ = minZ; iZ <= maxZ; iZ++)
            {
                for (int iY = minY; iY <= maxY; iY++)
                {
                    int blockIdSelected = par1EntityPlayer.worldObj.getBlockId(iX, iY, iZ);

                    /*if(blockIdSelected>0)
                    {
                    	Block blockSelected = Block.blocksList[blockIdSelected];
                    	System.out.println("Block name = "+ blockSelected.getBlockName());
                    }*/
                    //Remove 61
                    if ((blockIdSelected == 61) || (blockIdSelected == 62) || (blockIdSelected == 50) || (blockIdSelected == 51) || (blockIdSelected == 10) || (blockIdSelected == 11) || (blockIdSelected == 124) || (blockIdSelected == 76))
                    {
                        setHeatSource(iX, iY, iZ, playerPosX, playerPosY, playerPosZ, blockIdSelected);
                    }
                }
            }
        }

        //Conditions
        setBiomeTemperature(par1EntityPlayer.worldObj.getBiomeGenForCoords(playerPosX, playerPosZ).temperature);
        setBiomeSnowIsEnabled(par1EntityPlayer.worldObj.getBiomeGenForCoords(playerPosX, playerPosZ).getEnableSnow());
        setCanSeeSky(par1EntityPlayer.worldObj.canBlockSeeTheSky(playerPosX, playerPosY, playerPosZ));
        setIsUnderCloud(par1EntityPlayer.worldObj.getPrecipitationHeight(playerPosX, playerPosZ) < playerPosY);
        setBiomeRainfall(par1EntityPlayer.worldObj.getBiomeGenForCoords(playerPosX, playerPosZ).getFloatRainfall());
        setBiomeIsRaining(par1EntityPlayer.worldObj.isRaining());
        setAltitude(playerPosY);
        setIsUnderWater(par1EntityPlayer.isInWater());
        setIsDayTime(par1EntityPlayer.worldObj.isDaytime());
        //System.out.println("bTemp = " + biomeTemperature + " bRain = " + biomeIsRaining + " bSnow = " + biomeSnowIsEnabled + " altitude = " + altitude + " uWater = " + isUnderWater + " seeSky = " + canSeeSky + " uCloud = " + isUnderCloud + " bRainfall = " + biomeRainfall + " isDayTime = " + isDayTime);
        //Update the temperature of the player
        updatePlayerTemperature(par1EntityPlayer);
    }

    public void updatePlayerTemperature(EntityPlayer par1EntityPlayer)
    {
        //Heat source result
        float calculedPlayerTemperatureFromHeatSource = playerTemperatureFromHeatSource;

        if (multipleHeatSources)
        {
            calculedPlayerTemperatureFromHeatSource += coefMultipleHeatSources;
        }

        //System.out.println("calculedPlayerTemperatureFromHeatSource = " + calculedPlayerTemperatureFromHeatSource);
        caculPlayerTemperature(calculedPlayerTemperatureFromHeatSource);
        calculHyperthermia(par1EntityPlayer);
        calculHypothermia(par1EntityPlayer);
    }

    private void caculPlayerTemperature(float playerTemperatureFromHSParam)
    {
        playerTemperatureValue = biomeTemperature;
        playerTemperatureValue += playerTemperatureFromHSParam;

        //Check if high temperature + humidity
        if ((biomeTemperature >= highBiomeTemperature) && (biomeIsRaining))
        {
            playerTemperatureValue += highTemperatureAndHumidityValue;
        }

        //Check if user is under sun //Todo : Hat
        if ((biomeTemperature >= highBiomeTemperature) && (!biomeIsRaining) && (canSeeSky) && (isDayTime))
        {
            playerTemperatureValue += highTemperatureAndSunExpositionValue;
        }

        // Check if player is in high altitude
        if (altitude >= altitudeMax)
        {
            playerTemperatureValue -= highAltitudeValue;
        }

        // Check if biome is cold and user in water/under rain
        if ((biomeTemperature <= lowBiomeTemperature) && (isUnderWater || (biomeIsRaining && canSeeSky && !biomeSnowIsEnabled)))
        {
            playerTemperatureValue -= coldAndInWaterValue;
        }
        else if ((biomeTemperature <= lowBiomeTemperature) && (biomeIsRaining && canSeeSky && biomeSnowIsEnabled))
        {
            playerTemperatureValue -= coldAndUnderSnowValue;
        }

        if (!isDayTime)
        {
            playerTemperatureValue -= nightValue;
        }

        //System.out.println("Player Temperature = "+ playerTemperatureValue);
    }
    private void calculHyperthermia(EntityPlayer par1EntityPlayer)
    {
        //Calcul if the player is hot and adjust the counter of hyperthemia
        if (playerTemperatureValue >= maxTemperatureBeforeHot)
        {
            isHot = true;
            hotCount++;
        }
        else
        {
            isHot = false;
			if (hotCount>0)
			{
				hotCount--;
            }
			damageHyperthermiaCount = 0;
        }

        //Check if the player is in hyperthermia
        if (hotCount >= maxTimerBeforeInjury)
        {
            damageHyperthermiaCount++;
            isInHyperthermia = true;

            if (hotCount >= maxTimer)
            {
                hotCount = maxTimer;
            }

            if (damageHyperthermiaCount >= 200)
            {
                par1EntityPlayer.attackEntityFrom(DamageSource.hyperthermia, 1);
                damageHyperthermiaCount = 0;
            }
        }
        else
        {
            isInHyperthermia = false;
            damageHyperthermiaCount = 0;
        }
    }
    private void calculHypothermia(EntityPlayer par1EntityPlayer)
    {
        //Calcul if the player is cold and adjust the counter of hypothermia
        if (playerTemperatureValue <= minTemperatureBeforeCold)
        {
            isCold = true;
            coldCount++;
        }
        else
        {
            isCold = false;
			if(coldCount>0)
			{
				coldCount--;
            }
			damageHypothermiaCount = 0;
        }

        //Check if the player is in hyopthermia
        if (coldCount >= maxTimerBeforeInjury)
        {
            damageHypothermiaCount++;
            isInHypothermia = true;

            if (coldCount >= maxTimer)
            {
                coldCount = maxTimer;
            }

            if (damageHypothermiaCount >= 300)
            {
                par1EntityPlayer.attackEntityFrom(DamageSource.hypothermia, 1);
                damageHypothermiaCount = 0;
            }
        }
        else
        {
            isInHypothermia = false;
            damageHypothermiaCount = 0;
        }
    }

    // Data functions and setter
    public void setBiomeTemperature(float temperature)
    {
        biomeTemperature = temperature * 20;
    }
    public void setBiomeIsRaining(boolean isRaining)
    {
        biomeIsRaining = isRaining;
    }
    public void setBiomeSnowIsEnabled(boolean snowIsEnabled)
    {
        biomeSnowIsEnabled = snowIsEnabled;
    }
    public void setAltitude(int altitudeParam)
    {
        altitude = altitudeParam;
    }
    public void setIsUnderWater(boolean isUnderWaterParam)
    {
        isUnderWater = isUnderWaterParam;
    }
    public void setCanSeeSky(boolean canSeeSkyParam)
    {
        canSeeSky = canSeeSkyParam;
    }
    public void setIsUnderCloud(boolean isUnderCloudParam)
    {
        isUnderCloud = isUnderCloudParam;
    }
    public void setBiomeRainfall(float rainfall)
    {
        biomeRainfall = rainfall;
    }
    public void setIsDayTime(boolean isDayTimeParam)
    {
        isDayTime = isDayTimeParam;
    }
    public void resetPlayerHeatSourceInformations()
    {
        playerTemperatureFromHeatSource = 0;
        multipleHeatSources = false;
    }
    public void setHeatSource(int heatSourceX, int heatSourceY, int heatSourceZ, int playerPosX, int playerPosY, int playerPosZ, int heatSourceType)
    {
        //Calcul distance from heat source
        int xDistance = heatSourceX - playerPosX;

        if (xDistance < 0)
        {
            xDistance = Math.abs(xDistance);
        }

        int yDistance = heatSourceY - playerPosY;

        if (yDistance < 0)
        {
            yDistance = Math.abs(yDistance);
        }

        int zDistance = heatSourceZ - playerPosZ;

        if (zDistance < 0)
        {
            zDistance = Math.abs(zDistance);
        }

        //Apply coef in function of distance
        float divisorDistance = 1;

        if ((xDistance > xDistanceToCheck / 2) || (yDistance > yDistanceToCheck / 2) || (zDistance > zDistanceToCheck / 2))
        {
            divisorDistance = 2;
        }

        //System.out.println("HeatSource = " + heatSourceType + " to x = " + xDistance + " to y = " + yDistance + " to z = " + zDistance);

        switch (heatSourceType)
        {
                //Furnace inactive TO REMOVE
            case 61 :
                addNewHeatSourceValue(furnaceHeatProduction / divisorDistance);
                break;

                //Furnace active
            case 62 :
                addNewHeatSourceValue(furnaceHeatProduction / divisorDistance);
                break;

                //Torch
            case 50 :
                addNewHeatSourceValue(torchHeatProduction / divisorDistance);
                break;

                //Block fire
            case 51 :
                addNewHeatSourceValue(blockFireHeatProduction / divisorDistance);
                break;

                //Lava
            case 10 :
                addNewHeatSourceValue(lavaHeatProduction / divisorDistance);
                break;

                //Lava
            case 11 :
                addNewHeatSourceValue(lavaHeatProduction / divisorDistance);
                break;

                //Redstone lamp active
            case 124 :
                addNewHeatSourceValue(redstoneLampHeatProduction / divisorDistance);
                break;

                //Redstone torch active
            case 76 :
                addNewHeatSourceValue(redstoneHeatProduction / divisorDistance);
                break;
        }
    }
    private void addNewHeatSourceValue(float heatSourceValue)
    {
        if (playerTemperatureFromHeatSource != 0)
        {
            multipleHeatSources = true;
        }

        if (playerTemperatureFromHeatSource < heatSourceValue)
        {
            playerTemperatureFromHeatSource = heatSourceValue;
        }
    }
    public void playerDrinkSomeWater(int waterValue)
    {
		if(hotCount >waterValue * 100)
		{
			hotCount -= waterValue * 100;
		}
		else
		{
			hotCount = 0;
		}
    }

    // Get calculated parameters
    public boolean getIsInHypothermia()
    {
        return isInHypothermia;
    }
    public boolean getIsInHyperthermia()
    {
        return isInHyperthermia;
    }
    public boolean getIsHot()
    {
        return isHot;
    }
    public boolean getIsCold()
    {
        return isCold;
    }

	// Set calculated parameters
	public void setIsInHypothermia(boolean isInHypothermiaParam)
	{
		isInHypothermia = isInHypothermiaParam;
	}
    public void setIsInHyperthermia(boolean isInHyperthermiaParam)
    {
        isInHyperthermia = isInHyperthermiaParam;
    }
    public void setIsHot(boolean isHotParam)
    {
        isHot = isHotParam;
    }
    public void setIsCold(boolean isColdParam)
    {
        isCold = isColdParam;
    }
	
    // Save and load functions
    /**
    * Reads player temperatures from an NBT object.
    */
    public void readNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("isInHypothermia"))
        {
            isInHypothermia = par1NBTTagCompound.getBoolean("isInHypothermia");
            isInHyperthermia = par1NBTTagCompound.getBoolean("isInHyperthermia");
            hotCount = par1NBTTagCompound.getInteger("hotCount");
            coldCount = par1NBTTagCompound.getInteger("coldCount");
        }
    }

    /**
     * Writes player temperatures stats to an NBT object.
     */
    public void writeNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setBoolean("isInHypothermia", isInHypothermia);
        par1NBTTagCompound.setBoolean("isInHyperthermia", isInHyperthermia);
        par1NBTTagCompound.setInteger("hotCount", hotCount);
        par1NBTTagCompound.setInteger("coldCount", coldCount);
    }
}