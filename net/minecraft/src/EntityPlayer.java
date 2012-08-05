package net.minecraft.src;

import java.util.*;

public abstract class EntityPlayer extends EntityLiving implements ICommandSender
{
    /** Inventory of the player */
    public InventoryPlayer inventory;
    private InventoryEnderChest field_71078_a;

    /** the crafting inventory in you get when opening your inventory */
    public Container inventorySlots;

    /** the crafting inventory you are currently using */
    public Container craftingInventory;

    /** The player's food stats. (See class FoodStats) */
    protected FoodStats foodStats;

    /**
     * Used to tell if the player pressed jump twice. If this is at 0 and it's pressed (And they are allowed to fly, as
     * defined in the player's movementInput) it sets this to 7. If it's pressed and it's greater than 0 enable fly.
     */
    protected int flyToggleTimer;
    public byte field_71098_bD;
    public int score;
    public float prevCameraYaw;
    public float cameraYaw;

    /** Whether the player is swinging the current item in their hand. */
    public boolean isSwinging;
    public int swingProgressInt;
    public String username;

    /** Which dimension the player is in (-1 = the Nether, 0 = normal world) */
    public int dimension;
    public String playerCloakUrl;

    /**
     * Used by EntityPlayer to prevent too many xp orbs from getting absorbed at once.
     */
    public int xpCooldown;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;

    /** Boolean value indicating weather a player is sleeping or not */
    protected boolean sleeping;

    /**
     * The chunk coordinates of the bed the player is in (null if player isn't in a bed).
     */
    public ChunkCoordinates playerLocation;
    private int sleepTimer;
    public float field_71079_bU;
    public float field_71082_cx;
    public float field_71089_bV;

    /**
     * Holds the last coordinate to spawn based on last bed that the player sleep.
     */
    private ChunkCoordinates spawnChunk;

    /** Holds the coordinate of the player when enter a minecraft to ride. */
    private ChunkCoordinates startMinecartRidingCoordinate;
    public int timeUntilPortal;

    /** Whether the entity is inside a Portal */
    protected boolean inPortal;

    /** The amount of time an entity has been in a Portal */
    public float timeInPortal;

    /** The amount of time an entity has been in a Portal the previous tick */
    public float prevTimeInPortal;

    /** The player's capabilities. (See class PlayerCapabilities) */
    public PlayerCapabilities capabilities;

    /** The current experience level the player is on. */
    public int experienceLevel;

    /**
     * The total amount of experience the player has. This also includes the amount of experience within their
     * Experience Bar.
     */
    public int experienceTotal;

    /**
     * The current amount of experience the player has within their Experience Bar.
     */
    public float experience;

    /**
     * This is the item that is in use when the player is holding down the useItemButton (e.g., bow, food, sword)
     */
    private ItemStack itemInUse;

    /**
     * This field starts off equal to getMaxItemUseDuration and is decremented on each tick
     */
    private int itemInUseCount;
    protected float speedOnGround;
    protected float speedInAir;

    /**
     * An instance of a fishing rod's hook. If this isn't null, the icon image of the fishing rod is slightly different
     */
    public EntityFishHook fishEntity;
	
    /** The player's water stats. (See class WaterStats) */
    //[ERKIN]
    protected WaterStats waterStats;
    protected EnergyStats energyStats;

    /** The player's temperature (See class PlayerTemperature) */
    //[ERKIN]
    protected PlayerTemperature playerTemperature;
	
    public EntityPlayer(World par1World)
    {
        super(par1World);
        inventory = new InventoryPlayer(this);
        field_71078_a = new InventoryEnderChest();
        foodStats = new FoodStats();
        flyToggleTimer = 0;
        field_71098_bD = 0;
        score = 0;
        isSwinging = false;
        swingProgressInt = 0;
        xpCooldown = 0;
        timeUntilPortal = 20;
        inPortal = false;
        capabilities = new PlayerCapabilities();
        speedOnGround = 0.1F;
        speedInAir = 0.02F;
        fishEntity = null;
        inventorySlots = new ContainerPlayer(inventory, !par1World.isRemote);
        craftingInventory = inventorySlots;
        yOffset = 1.62F;
        ChunkCoordinates chunkcoordinates = par1World.getSpawnPoint();
        setLocationAndAngles((double)chunkcoordinates.posX + 0.5D, chunkcoordinates.posY + 1, (double)chunkcoordinates.posZ + 0.5D, 0.0F, 0.0F);
        entityType = "humanoid";
        field_70741_aB = 180F;
        fireResistance = 20;
        texture = "/mob/char.png";
        waterStats = new WaterStats(); //[ERKIN]
        playerTemperature = new PlayerTemperature(); //[ERKIN]
        energyStats = new EnergyStats(); // [ERKIN]
        //DEBUG [ERKIN]
        //inventory.addItemStackToInventory(new ItemStack(Item.bed, 1));
        //inventory.addItemStackToInventory(new ItemStack(Item.gourdFull, 1));
        //inventory.addItemStackToInventory(new ItemStack(Item.leather, 10));
    }

    public int getMaxHealth()
    {
        return 20;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, Byte.valueOf((byte)0));
    }

    /**
     * returns the ItemStack containing the itemInUse
     */
    public ItemStack getItemInUse()
    {
        return itemInUse;
    }

    /**
     * Returns the item in use count
     */
    public int getItemInUseCount()
    {
        return itemInUseCount;
    }

    /**
     * Checks if the entity is currently using an item (e.g., bow, food, sword) by holding down the useItemButton
     */
    public boolean isUsingItem()
    {
        return itemInUse != null;
    }

    /**
     * gets the duration for how long the current itemInUse has been in use
     */
    public int getItemInUseDuration()
    {
        if (isUsingItem())
        {
            return itemInUse.getMaxItemUseDuration() - itemInUseCount;
        }
        else
        {
            return 0;
        }
    }

    public void stopUsingItem()
    {
        if (itemInUse != null)
        {
            itemInUse.onPlayerStoppedUsing(worldObj, this, itemInUseCount);
        }

        clearItemInUse();
    }

    public void clearItemInUse()
    {
        itemInUse = null;
        itemInUseCount = 0;

        if (!worldObj.isRemote)
        {
            setEating(false);
        }
    }

    public boolean isBlocking()
    {
        return isUsingItem() && Item.itemsList[itemInUse.itemID].getItemUseAction(itemInUse) == EnumAction.block;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (itemInUse != null)
        {
            ItemStack itemstack = inventory.getCurrentItem();

            if (itemstack == itemInUse)
            {
                if (itemInUseCount <= 25 && itemInUseCount % 4 == 0)
                {
                    updateItemUse(itemstack, 5);
                }

                if (--itemInUseCount == 0 && !worldObj.isRemote)
                {
                    onItemUseFinish();
                }
            }
            else
            {
                clearItemInUse();
            }
        }

        if (xpCooldown > 0)
        {
            xpCooldown--;
        }

        if (isPlayerSleeping())
        {
            energyStats.addStats(20); // [ERKIN]
            sleepTimer++;

            if (sleepTimer > 100)
            {
                sleepTimer = 100;
            }

            if (!worldObj.isRemote)
            {
                if (!isInBed())
                {
                    wakeUpPlayer(true, true, false);
                }
                else if (worldObj.isDaytime())
                {
                    wakeUpPlayer(false, true, true);
                }
            }
        }
        else if (sleepTimer > 0)
        {
            sleepTimer++;

            if (sleepTimer >= 110)
            {
                sleepTimer = 0;
            }
        }

        super.onUpdate();

        if (!worldObj.isRemote && craftingInventory != null && !craftingInventory.canInteractWith(this))
        {
            closeScreen();
            craftingInventory = inventorySlots;
        }

        if (isBurning() && capabilities.disableDamage)
        {
            extinguish();
        }

        field_71091_bM = field_71094_bP;
        field_71096_bN = field_71095_bQ;
        field_71097_bO = field_71085_bR;
        double d = posX - field_71094_bP;
        double d1 = posY - field_71095_bQ;
        double d2 = posZ - field_71085_bR;
        double d3 = 10D;

        if (d > d3)
        {
            field_71091_bM = field_71094_bP = posX;
        }

        if (d2 > d3)
        {
            field_71097_bO = field_71085_bR = posZ;
        }

        if (d1 > d3)
        {
            field_71096_bN = field_71095_bQ = posY;
        }

        if (d < -d3)
        {
            field_71091_bM = field_71094_bP = posX;
        }

        if (d2 < -d3)
        {
            field_71097_bO = field_71085_bR = posZ;
        }

        if (d1 < -d3)
        {
            field_71096_bN = field_71095_bQ = posY;
        }

        field_71094_bP += d * 0.25D;
        field_71085_bR += d2 * 0.25D;
        field_71095_bQ += d1 * 0.25D;
        addStat(StatList.minutesPlayedStat, 1);

        if (ridingEntity == null)
        {
            startMinecartRidingCoordinate = null;
        }

		//System.out.println("entity server or client update ---- : " + waterStats.getWaterLevel());
        if (!worldObj.isRemote)
        {
            foodStats.onUpdate(this);
            //[ERKIN]
            waterStats.onUpdate(this);
            energyStats.onUpdate(this);
            playerTemperature.onUpdate(this);
			//System.out.println("server only ---- : " + waterStats.getWaterLevel());
        }
		// [ERKIN]
		// Effects on player (client and server)
		energyStats.checkIfPlayerIsFatigued();
        if (playerTemperature.getIsInHypothermia() || energyStats.getIsFatigued())
        {
            setPlayerAsExhausted();
        }
    }

    /**
     * Plays sounds and makes particles for item in use state
     */
    protected void updateItemUse(ItemStack par1ItemStack, int par2)
    {
        if (par1ItemStack.getItemUseAction() == EnumAction.drink)
        {
            worldObj.playSoundAtEntity(this, "random.drink", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (par1ItemStack.getItemUseAction() == EnumAction.eat)
        {
            for (int i = 0; i < par2; i++)
            {
                Vec3 vec3 = Vec3.func_72437_a().func_72345_a(((double)rand.nextFloat() - 0.5D) * 0.10000000000000001D, Math.random() * 0.10000000000000001D + 0.10000000000000001D, 0.0D);
                vec3.rotateAroundX((-rotationPitch * (float)Math.PI) / 180F);
                vec3.rotateAroundY((-rotationYaw * (float)Math.PI) / 180F);
                Vec3 vec3_1 = Vec3.func_72437_a().func_72345_a(((double)rand.nextFloat() - 0.5D) * 0.29999999999999999D, (double)(-rand.nextFloat()) * 0.59999999999999998D - 0.29999999999999999D, 0.59999999999999998D);
                vec3_1.rotateAroundX((-rotationPitch * (float)Math.PI) / 180F);
                vec3_1.rotateAroundY((-rotationYaw * (float)Math.PI) / 180F);
                vec3_1 = vec3_1.addVector(posX, posY + (double)getEyeHeight(), posZ);
                worldObj.spawnParticle((new StringBuilder()).append("iconcrack_").append(par1ItemStack.getItem().shiftedIndex).toString(), vec3_1.xCoord, vec3_1.yCoord, vec3_1.zCoord, vec3.xCoord, vec3.yCoord + 0.050000000000000003D, vec3.zCoord);
            }

            worldObj.playSoundAtEntity(this, "random.eat", 0.5F + 0.5F * (float)rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }
    }

    /**
     * Used for when item use count runs out, ie: eating completed
     */
    protected void onItemUseFinish()
    {
        if (itemInUse != null)
        {
            updateItemUse(itemInUse, 16);
            int i = itemInUse.stackSize;
			
            // [ERKIN]
            ItemStack itemstack;
			if (itemInUse.getItem().getItemName().equals("item.gourdFull"))
            {
                itemstack = itemInUse.onWaterDrunk(worldObj, this);
            }
            else
            {
				itemstack = itemInUse.onFoodEaten(worldObj, this);
			}
			
            if (itemstack != itemInUse || itemstack != null && itemstack.stackSize != i)
            {
                inventory.mainInventory[inventory.currentItem] = itemstack;

                if (itemstack.stackSize == 0)
                {
                    inventory.mainInventory[inventory.currentItem] = null;
                }
            }

            clearItemInUse();
        }
    }

    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 9)
        {
            onItemUseFinish();
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return getHealth() <= 0 || isPlayerSleeping();
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    protected void closeScreen()
    {
        craftingInventory = inventorySlots;
    }

    /**
     * Handles updating while being ridden by an entity
     */
    public void updateRidden()
    {
        double d = posX;
        double d1 = posY;
        double d2 = posZ;
        super.updateRidden();
        prevCameraYaw = cameraYaw;
        cameraYaw = 0.0F;
        addMountedMovementStat(posX - d, posY - d1, posZ - d2);
    }

    /**
     * Keeps moving the entity up so it isn't colliding with blocks and other requirements for this entity to be spawned
     * (only actually used on players though its also on Entity)
     */
    public void preparePlayerToSpawn()
    {
        yOffset = 1.62F;
        setSize(0.6F, 1.8F);
        super.preparePlayerToSpawn();
        setEntityHealth(getMaxHealth());
        deathTime = 0;
    }

    /**
     * Returns the swing speed modifier
     */
    private int getSwingSpeedModifier()
    {
	    // [ERKIN]
        int digSpeedHypothermia = 0;
        if (playerTemperature.getIsInHypothermia() || energyStats.getIsFatigued())
        {
            digSpeedHypothermia += 6;
        }
		
        if (isPotionActive(Potion.digSpeed))
        {
            return 6 + digSpeedHypothermia - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1;
        }

        if (isPotionActive(Potion.digSlowdown))
        {
            return 6 + digSpeedHypothermia - (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2;
        }
        else
        {
            return 6 + digSpeedHypothermia;
        }
    }

    protected void updateEntityActionState()
    {
        int i = getSwingSpeedModifier();

        if (isSwinging)
        {
            swingProgressInt++;

            if (swingProgressInt >= i)
            {
                swingProgressInt = 0;
                isSwinging = false;
            }
        }
        else
        {
            swingProgressInt = 0;
        }

        swingProgress = (float)swingProgressInt / (float)i;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (flyToggleTimer > 0)
        {
            flyToggleTimer--;
        }

        if (worldObj.difficultySetting == 0 && getHealth() < getMaxHealth() && (ticksExisted % 20) * 12 == 0)
        {
            heal(1);
        }

        inventory.decrementAnimations();
        prevCameraYaw = cameraYaw;
        super.onLivingUpdate();
        landMovementFactor = capabilities.func_75094_b();
        jumpMovementFactor = speedInAir;

        if (isSprinting())
        {
            landMovementFactor += (double)capabilities.func_75094_b() * 0.29999999999999999D;
            jumpMovementFactor += (double)speedInAir * 0.29999999999999999D;
        }

        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        float f1 = (float)Math.atan(-motionY * 0.20000000298023224D) * 15F;

        if (f > 0.1F)
        {
            f = 0.1F;
        }

        if (!onGround || getHealth() <= 0)
        {
            f = 0.0F;
        }

        if (onGround || getHealth() <= 0)
        {
            f1 = 0.0F;
        }

        cameraYaw += (f - cameraYaw) * 0.4F;
        cameraPitch += (f1 - cameraPitch) * 0.8F;

        if (getHealth() > 0)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));

            if (list != null)
            {
                Iterator iterator = list.iterator();

                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }

                    Entity entity = (Entity)iterator.next();

                    if (!entity.isDead)
                    {
                        collideWithPlayer(entity);
                    }
                }
                while (true);
            }
        }
    }

    private void collideWithPlayer(Entity par1Entity)
    {
        par1Entity.onCollideWithPlayer(this);
    }

    public int getScore()
    {
        return score;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);
        setSize(0.2F, 0.2F);
        setPosition(posX, posY, posZ);
        motionY = 0.10000000149011612D;

        if (username.equals("Notch"))
        {
            dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }

        inventory.dropAllItems();

        if (par1DamageSource != null)
        {
            motionX = -MathHelper.cos(((attackedAtYaw + rotationYaw) * (float)Math.PI) / 180F) * 0.1F;
            motionZ = -MathHelper.sin(((attackedAtYaw + rotationYaw) * (float)Math.PI) / 180F) * 0.1F;
        }
        else
        {
            motionX = motionZ = 0.0D;
        }

        yOffset = 0.1F;
        addStat(StatList.deathsStat, 1);
    }

    /**
     * Adds a value to the player score. Currently not actually used and the entity passed in does nothing. Args:
     * entity, scoreToAdd
     */
    public void addToPlayerScore(Entity par1Entity, int par2)
    {
        score += par2;

        if (par1Entity instanceof EntityPlayer)
        {
            addStat(StatList.playerKillsStat, 1);
        }
        else
        {
            addStat(StatList.mobKillsStat, 1);
        }
    }

    /**
     * Decrements the entity's air supply when underwater
     */
    protected int decreaseAirSupply(int par1)
    {
        int i = EnchantmentHelper.getRespiration(inventory);

        if (i > 0 && rand.nextInt(i + 1) > 0)
        {
            return par1;
        }
        else
        {
            return super.decreaseAirSupply(par1);
        }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem()
    {
        return dropPlayerItemWithRandomChoice(inventory.decrStackSize(inventory.currentItem, 1), false);
    }

    /**
     * Args: itemstack - called when player drops an item stack that's not in his inventory (like items still placed in
     * a workbench while the workbench'es GUI gets closed)
     */
    public EntityItem dropPlayerItem(ItemStack par1ItemStack)
    {
        return dropPlayerItemWithRandomChoice(par1ItemStack, false);
    }

    /**
     * Args: itemstack, flag
     */
    public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack, boolean par2)
    {
        if (par1ItemStack == null)
        {
            return null;
        }

        EntityItem entityitem = new EntityItem(worldObj, posX, (posY - 0.30000001192092896D) + (double)getEyeHeight(), posZ, par1ItemStack);
        entityitem.delayBeforeCanPickup = 40;
        float f = 0.1F;

        if (par2)
        {
            float f2 = rand.nextFloat() * 0.5F;
            float f4 = rand.nextFloat() * (float)Math.PI * 2.0F;
            entityitem.motionX = -MathHelper.sin(f4) * f2;
            entityitem.motionZ = MathHelper.cos(f4) * f2;
            entityitem.motionY = 0.20000000298023224D;
        }
        else
        {
            float f1 = 0.3F;
            entityitem.motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI) * f1;
            entityitem.motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI) * f1;
            entityitem.motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI) * f1 + 0.1F;
            f1 = 0.02F;
            float f3 = rand.nextFloat() * (float)Math.PI * 2.0F;
            f1 *= rand.nextFloat();
            entityitem.motionX += Math.cos(f3) * (double)f1;
            entityitem.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            entityitem.motionZ += Math.sin(f3) * (double)f1;
        }

        joinEntityItemWithWorld(entityitem);
        addStat(StatList.dropStat, 1);
        return entityitem;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem par1EntityItem)
    {
        worldObj.spawnEntityInWorld(par1EntityItem);
    }

    /**
     * Returns how strong the player is against the specified block at this moment
     */
    public float getCurrentPlayerStrVsBlock(Block par1Block)
    {
        float f = inventory.getStrVsBlock(par1Block);
        int i = EnchantmentHelper.getEfficiencyModifier(inventory);

        if (i > 0 && inventory.canHarvestBlock(par1Block))
        {
            f += i * i + 1;
        }

        if (isPotionActive(Potion.digSpeed))
        {
            f *= 1.0F + (float)(getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
        }

        if (isPotionActive(Potion.digSlowdown))
        {
            f *= 1.0F - (float)(getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F;
        }

        if (isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(inventory))
        {
            f /= 5F;
        }

        if (!onGround)
        {
            f /= 5F;
        }

        return f;
    }

    /**
     * Checks if the player has the ability to harvest a block (checks current inventory item for a tool if necessary)
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return inventory.canHarvestBlock(par1Block);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory");
        inventory.readFromNBT(nbttaglist);
        dimension = par1NBTTagCompound.getInteger("Dimension");
        sleeping = par1NBTTagCompound.getBoolean("Sleeping");
        sleepTimer = par1NBTTagCompound.getShort("SleepTimer");
        experience = par1NBTTagCompound.getFloat("XpP");
        experienceLevel = par1NBTTagCompound.getInteger("XpLevel");
        experienceTotal = par1NBTTagCompound.getInteger("XpTotal");

        if (sleeping)
        {
            playerLocation = new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
            wakeUpPlayer(true, true, false);
        }

        if (par1NBTTagCompound.hasKey("SpawnX") && par1NBTTagCompound.hasKey("SpawnY") && par1NBTTagCompound.hasKey("SpawnZ"))
        {
            spawnChunk = new ChunkCoordinates(par1NBTTagCompound.getInteger("SpawnX"), par1NBTTagCompound.getInteger("SpawnY"), par1NBTTagCompound.getInteger("SpawnZ"));
        }

        foodStats.readNBT(par1NBTTagCompound);
        waterStats.readNBT(par1NBTTagCompound); //[ERKIN]
        energyStats.readNBT(par1NBTTagCompound); // [ERKIN]
        playerTemperature.readNBT(par1NBTTagCompound); // [ERKIN]
        capabilities.readCapabilitiesFromNBT(par1NBTTagCompound);

        if (par1NBTTagCompound.hasKey("EnderItems"))
        {
            NBTTagList nbttaglist1 = par1NBTTagCompound.getTagList("EnderItems");
            field_71078_a.func_70486_a(nbttaglist1);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("Dimension", dimension);
        par1NBTTagCompound.setBoolean("Sleeping", sleeping);
        par1NBTTagCompound.setShort("SleepTimer", (short)sleepTimer);
        par1NBTTagCompound.setFloat("XpP", experience);
        par1NBTTagCompound.setInteger("XpLevel", experienceLevel);
        par1NBTTagCompound.setInteger("XpTotal", experienceTotal);

        if (spawnChunk != null)
        {
            par1NBTTagCompound.setInteger("SpawnX", spawnChunk.posX);
            par1NBTTagCompound.setInteger("SpawnY", spawnChunk.posY);
            par1NBTTagCompound.setInteger("SpawnZ", spawnChunk.posZ);
        }

        foodStats.writeNBT(par1NBTTagCompound);
        waterStats.writeNBT(par1NBTTagCompound); //[ERKIN]
        energyStats.writeNBT(par1NBTTagCompound); // [ERKIN]
        playerTemperature.writeNBT(par1NBTTagCompound); // [ERKIN]
        capabilities.writeCapabilitiesToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("EnderItems", field_71078_a.func_70487_g());
    }

    /**
     * Displays the GUI for interacting with a chest inventory. Args: chestInventory
     */
    public void displayGUIChest(IInventory iinventory)
    {
    }

    public void displayGUIEnchantment(int i, int j, int k)
    {
    }

    /**
     * Displays the crafting GUI for a workbench.
     */
    public void displayWorkbenchGUI(int i, int j, int k)
    {
    }

    /**
     * Called whenever an item is picked up from walking over it. Args: pickedUpEntity, stackSize
     */
    public void onItemPickup(Entity entity, int i)
    {
    }

    public float getEyeHeight()
    {
        return 0.12F;
    }

    /**
     * sets the players height back to normal after doing things like sleeping and dieing
     */
    protected void resetHeight()
    {
        yOffset = 1.62F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        if (capabilities.disableDamage && !par1DamageSource.canHarmInCreative())
        {
            return false;
        }

        entityAge = 0;

        if (getHealth() <= 0)
        {
            return false;
        }

        if (isPlayerSleeping() && !worldObj.isRemote)
        {
            wakeUpPlayer(true, true, false);
        }

        Entity entity = par1DamageSource.getEntity();

        if (par1DamageSource.func_76350_n())
        {
            if (worldObj.difficultySetting == 0)
            {
                par2 = 0;
            }

            if (worldObj.difficultySetting == 1)
            {
                par2 = par2 / 2 + 1;
            }

            if (worldObj.difficultySetting == 3)
            {
                par2 = (par2 * 3) / 2;
            }
        }

        if (par2 == 0)
        {
            return false;
        }

        Entity entity1 = par1DamageSource.getEntity();

        if ((entity1 instanceof EntityArrow) && ((EntityArrow)entity1).shootingEntity != null)
        {
            entity1 = ((EntityArrow)entity1).shootingEntity;
        }

        if (entity1 instanceof EntityLiving)
        {
            alertWolves((EntityLiving)entity1, false);
        }

        addStat(StatList.damageTakenStat, par2);
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * Reduces damage, depending on potions
     */
    protected int applyPotionDamageCalculations(DamageSource par1DamageSource, int par2)
    {
        int i = super.applyPotionDamageCalculations(par1DamageSource, par2);

        if (i <= 0)
        {
            return 0;
        }

        int j = EnchantmentHelper.getEnchantmentModifierDamage(inventory, par1DamageSource);

        if (j > 20)
        {
            j = 20;
        }

        if (j > 0 && j <= 20)
        {
            int k = 25 - j;
            int l = i * k + carryoverDamage;
            i = l / 25;
            carryoverDamage = l % 25;
        }

        return i;
    }

    /**
     * returns if pvp is enabled or not
     */
    protected boolean isPVPEnabled()
    {
        return false;
    }

    /**
     * Called when the player attack or gets attacked, it's alert all wolves in the area that are owned by the player to
     * join the attack or defend the player.
     */
    protected void alertWolves(EntityLiving par1EntityLiving, boolean par2)
    {
        if ((par1EntityLiving instanceof EntityCreeper) || (par1EntityLiving instanceof EntityGhast))
        {
            return;
        }

        if (par1EntityLiving instanceof EntityWolf)
        {
            EntityWolf entitywolf = (EntityWolf)par1EntityLiving;

            if (entitywolf.isTamed() && username.equals(entitywolf.getOwnerName()))
            {
                return;
            }
        }

        if ((par1EntityLiving instanceof EntityPlayer) && !isPVPEnabled())
        {
            return;
        }

        List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityWolf.class, AxisAlignedBB.func_72332_a().func_72299_a(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityWolf entitywolf1 = (EntityWolf)iterator.next();

            if (entitywolf1.isTamed() && entitywolf1.getEntityToAttack() == null && username.equals(entitywolf1.getOwnerName()) && (!par2 || !entitywolf1.isSitting()))
            {
                entitywolf1.setSitting(false);
                entitywolf1.setTarget(par1EntityLiving);
            }
        }
        while (true);
    }

    protected void damageArmor(int par1)
    {
        inventory.damageArmor(par1);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        return inventory.getTotalArmorValue();
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource par1DamageSource, int par2)
    {
        if (!par1DamageSource.isUnblockable() && isBlocking())
        {
            par2 = 1 + par2 >> 1;
        }

        par2 = applyArmorCalculations(par1DamageSource, par2);
        par2 = applyPotionDamageCalculations(par1DamageSource, par2);
        addExhaustion(par1DamageSource.getHungerDamage());
		addWaterExhaustion(par1DamageSource.getThirstDamage()); // [ERKIN]
		addEnergyExhaustion(par1DamageSource.getFatigueDamage()); // [ERKIN]
        health -= par2;
    }

    /**
     * Displays the furnace GUI for the passed in furnace entity. Args: tileEntityFurnace
     */
    public void displayGUIFurnace(TileEntityFurnace tileentityfurnace)
    {
    }

    /**
     * Displays the dipsenser GUI for the passed in dispenser entity. Args: TileEntityDispenser
     */
    public void displayGUIDispenser(TileEntityDispenser tileentitydispenser)
    {
    }

    /**
     * Displays the GUI for editing a sign. Args: tileEntitySign
     */
    public void displayGUIEditSign(TileEntitySign tileentitysign)
    {
    }

    /**
     * Displays the GUI for interacting with a brewing stand.
     */
    public void displayGUIBrewingStand(TileEntityBrewingStand tileentitybrewingstand)
    {
    }

    public void func_71030_a(IMerchant imerchant)
    {
    }

    public void func_71048_c(ItemStack itemstack)
    {
    }

    public boolean func_70998_m(Entity par1Entity)
    {
        if (par1Entity.interact(this))
        {
            return true;
        }

        ItemStack itemstack = getCurrentEquippedItem();

        if (itemstack != null && (par1Entity instanceof EntityLiving))
        {
            if (capabilities.isCreativeMode)
            {
                itemstack = itemstack.copy();
            }

            if (itemstack.func_77947_a((EntityLiving)par1Entity))
            {
                if (itemstack.stackSize <= 0 && !capabilities.isCreativeMode)
                {
                    destroyCurrentEquippedItem();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Returns the currently being used item by the player.
     */
    public ItemStack getCurrentEquippedItem()
    {
        return inventory.getCurrentItem();
    }

    /**
     * Destroys the currently equipped item from the player's inventory.
     */
    public void destroyCurrentEquippedItem()
    {
        inventory.setInventorySlotContents(inventory.currentItem, null);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return (double)(yOffset - 0.5F);
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        if (!isSwinging || swingProgressInt >= getSwingSpeedModifier() / 2 || swingProgressInt < 0)
        {
            swingProgressInt = -1;
            isSwinging = true;
        }
    }

    /**
     * Attacks for the player the targeted entity with the currently equipped item.  The equipped item has hitEntity
     * called on it. Args: targetEntity
     */
    public void attackTargetEntityWithCurrentItem(Entity par1Entity)
    {
        if (!par1Entity.canAttackWithItem())
        {
            return;
        }

        int i = inventory.getDamageVsEntity(par1Entity);

        if (isPotionActive(Potion.damageBoost))
        {
            i += 3 << getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (isPotionActive(Potion.weakness))
        {
            i -= 2 << getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        int j = 0;
        int k = 0;

        if (par1Entity instanceof EntityLiving)
        {
            k = EnchantmentHelper.getEnchantmentModifierLiving(inventory, (EntityLiving)par1Entity);
            j += EnchantmentHelper.getKnockbackModifier(inventory, (EntityLiving)par1Entity);
        }

        if (isSprinting())
        {
            j++;
        }

        if (i > 0 || k > 0)
        {
            boolean flag = fallDistance > 0.0F && !onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && ridingEntity == null && (par1Entity instanceof EntityLiving);

            if (flag)
            {
                i += rand.nextInt(i / 2 + 2);
            }

            i += k;
            boolean flag1 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(this), i);

            if (flag1)
            {
                if (j > 0)
                {
                    par1Entity.addVelocity(-MathHelper.sin((rotationYaw * (float)Math.PI) / 180F) * (float)j * 0.5F, 0.10000000000000001D, MathHelper.cos((rotationYaw * (float)Math.PI) / 180F) * (float)j * 0.5F);
                    motionX *= 0.59999999999999998D;
                    motionZ *= 0.59999999999999998D;
                    setSprinting(false);
                }

                if (flag)
                {
                    onCriticalHit(par1Entity);
                }

                if (k > 0)
                {
                    onEnchantmentCritical(par1Entity);
                }

                if (i >= 18)
                {
                    triggerAchievement(AchievementList.overkill);
                }

                setLastAttackingEntity(par1Entity);
            }

            ItemStack itemstack = getCurrentEquippedItem();

            if (itemstack != null && (par1Entity instanceof EntityLiving))
            {
                itemstack.hitEntity((EntityLiving)par1Entity, this);

                if (itemstack.stackSize <= 0)
                {
                    destroyCurrentEquippedItem();
                }
            }

            if (par1Entity instanceof EntityLiving)
            {
                if (par1Entity.isEntityAlive())
                {
                    alertWolves((EntityLiving)par1Entity, true);
                }

                addStat(StatList.damageDealtStat, i);
                int l = EnchantmentHelper.getFireAspectModifier(inventory, (EntityLiving)par1Entity);

                if (l > 0)
                {
                    par1Entity.setFire(l * 4);
                }
            }

			addGeneralExhaustion(2.6F,0.0F,false); // [ERKIN]
            //addExhaustion(0.3F);
        }
    }

    /**
     * Called when the player performs a critical hit on the Entity. Args: entity that was hit critically
     */
    public void onCriticalHit(Entity entity)
    {
    }

    public void onEnchantmentCritical(Entity entity)
    {
    }

    public void respawnPlayer()
    {
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();
        inventorySlots.onCraftGuiClosed(this);

        if (craftingInventory != null)
        {
            craftingInventory.onCraftGuiClosed(this);
        }
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        return !sleeping && super.isEntityInsideOpaqueBlock();
    }

    public boolean func_71066_bF()
    {
        return false;
    }

    /**
     * Attempts to have the player sleep in a bed at the specified location.
     */
    public EnumStatus sleepInBedAt(int par1, int par2, int par3)
    {
        if (!worldObj.isRemote)
        {
            if (isPlayerSleeping() || !isEntityAlive())
            {
                return EnumStatus.OTHER_PROBLEM;
            }

            if (!worldObj.worldProvider.isSurfaceWorld())
            {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }

            if (worldObj.isDaytime())
            {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(posX - (double)par1) > 3D || Math.abs(posY - (double)par2) > 2D || Math.abs(posZ - (double)par3) > 3D)
            {
                return EnumStatus.TOO_FAR_AWAY;
            }

            double d = 8D;
            double d1 = 5D;
            List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityMob.class, AxisAlignedBB.func_72332_a().func_72299_a((double)par1 - d, (double)par2 - d1, (double)par3 - d, (double)par1 + d, (double)par2 + d1, (double)par3 + d));

            if (!list.isEmpty())
            {
                return EnumStatus.NOT_SAFE;
            }
        }

        setSize(0.2F, 0.2F);
        yOffset = 0.2F;

        if (worldObj.blockExists(par1, par2, par3))
        {
            int i = worldObj.getBlockMetadata(par1, par2, par3);
            int j = BlockBed.getDirection(i);
            float f = 0.5F;
            float f1 = 0.5F;

            switch (j)
            {
                case 0:
                    f1 = 0.9F;
                    break;

                case 2:
                    f1 = 0.1F;
                    break;

                case 1:
                    f = 0.1F;
                    break;

                case 3:
                    f = 0.9F;
                    break;
            }

            func_71013_b(j);
            setPosition((float)par1 + f, (float)par2 + 0.9375F, (float)par3 + f1);
        }
        else
        {
            setPosition((float)par1 + 0.5F, (float)par2 + 0.9375F, (float)par3 + 0.5F);
        }

        sleeping = true;
        sleepTimer = 0;
        playerLocation = new ChunkCoordinates(par1, par2, par3);
        motionX = motionZ = motionY = 0.0D;

        if (!worldObj.isRemote)
        {
            worldObj.updateAllPlayersSleepingFlag();
        }

        return EnumStatus.OK;
    }

    private void func_71013_b(int par1)
    {
        field_71079_bU = 0.0F;
        field_71089_bV = 0.0F;

        switch (par1)
        {
            case 0:
                field_71089_bV = -1.8F;
                break;

            case 2:
                field_71089_bV = 1.8F;
                break;

            case 1:
                field_71079_bU = 1.8F;
                break;

            case 3:
                field_71079_bU = -1.8F;
                break;
        }
    }

    /**
     * Wake up the player if they're sleeping.
     */
    public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
    {
        setSize(0.6F, 1.8F);
        resetHeight();
        ChunkCoordinates chunkcoordinates = playerLocation;
        ChunkCoordinates chunkcoordinates1 = playerLocation;

        if (chunkcoordinates != null && worldObj.getBlockId(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ) == Block.bed.blockID)
        {
            BlockBed.setBedOccupied(worldObj, chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, false);
            ChunkCoordinates chunkcoordinates2 = BlockBed.getNearestEmptyChunkCoordinates(worldObj, chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, 0);

            if (chunkcoordinates2 == null)
            {
                chunkcoordinates2 = new ChunkCoordinates(chunkcoordinates.posX, chunkcoordinates.posY + 1, chunkcoordinates.posZ);
            }

            setPosition((float)chunkcoordinates2.posX + 0.5F, (float)chunkcoordinates2.posY + yOffset + 0.1F, (float)chunkcoordinates2.posZ + 0.5F);
        }

        sleeping = false;

        if (!worldObj.isRemote && par2)
        {
            worldObj.updateAllPlayersSleepingFlag();
        }

        if (par1)
        {
            sleepTimer = 0;
        }
        else
        {
            sleepTimer = 100;
        }

        if (par3)
        {
            setSpawnChunk(playerLocation);
        }
    }

    /**
     * Checks if the player is currently in a bed
     */
    private boolean isInBed()
    {
        return worldObj.getBlockId(playerLocation.posX, playerLocation.posY, playerLocation.posZ) == Block.bed.blockID;
    }

    /**
     * Ensure that a block enabling respawning exists at the specified coordinates and find an empty space nearby to
     * spawn.
     */
    public static ChunkCoordinates verifyRespawnCoordinates(World par0World, ChunkCoordinates par1ChunkCoordinates)
    {
        IChunkProvider ichunkprovider = par0World.getChunkProvider();
        ichunkprovider.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        ichunkprovider.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        ichunkprovider.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
        ichunkprovider.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);

        if (par0World.getBlockId(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ) != Block.bed.blockID)
        {
            return null;
        }
        else
        {
            ChunkCoordinates chunkcoordinates = BlockBed.getNearestEmptyChunkCoordinates(par0World, par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ, 0);
            return chunkcoordinates;
        }
    }

    /**
     * Returns the orientation of the bed in degrees.
     */
    public float getBedOrientationInDegrees()
    {
        if (playerLocation != null)
        {
            int i = worldObj.getBlockMetadata(playerLocation.posX, playerLocation.posY, playerLocation.posZ);
            int j = BlockBed.getDirection(i);

            switch (j)
            {
                case 0:
                    return 90F;

                case 1:
                    return 0.0F;

                case 2:
                    return 270F;

                case 3:
                    return 180F;
            }
        }

        return 0.0F;
    }

    /**
     * Returns whether player is sleeping or not
     */
    public boolean isPlayerSleeping()
    {
        return sleeping;
    }

    /**
     * Returns whether or not the player is asleep and the screen has fully faded.
     */
    public boolean isPlayerFullyAsleep()
    {
        return sleeping && sleepTimer >= 100;
    }

    public int getSleepTimer()
    {
        return sleepTimer;
    }

    /**
     * Add a chat message to the player
     */
    public void addChatMessage(String s)
    {
    }

    /**
     * Returns the coordinates to respawn the player based on last bed that the player sleep.
     */
    public ChunkCoordinates getSpawnChunk()
    {
        return spawnChunk;
    }

    /**
     * Defines a spawn coordinate to player spawn. Used by bed after the player sleep on it.
     */
    public void setSpawnChunk(ChunkCoordinates par1ChunkCoordinates)
    {
        if (par1ChunkCoordinates != null)
        {
            spawnChunk = new ChunkCoordinates(par1ChunkCoordinates);
        }
        else
        {
            spawnChunk = null;
        }
    }

    /**
     * Will trigger the specified trigger.
     */
    public void triggerAchievement(StatBase par1StatBase)
    {
        addStat(par1StatBase, 1);
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase statbase, int i)
    {
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump()
    {
		// [ERKIN]
        if (playerTemperature.getIsInHypothermia() ||  energyStats.getIsFatigued())
        {
            super.jumpWithFatigue();
        }
        else
        {
			super.jump();
		}
        addStat(StatList.jumpStat, 1);

        if (isSprinting())
        {
			addGeneralExhaustion(2.6F,0.0F,false); // [ERKIN]
            //addExhaustion(0.8F);
        }
        else
        {
			addGeneralExhaustion(4F,0.0F,false); // [ERKIN]
            //addExhaustion(0.2F);
        }
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        double d = posX;
        double d1 = posY;
        double d2 = posZ;

        if (capabilities.isFlying && ridingEntity == null)
        {
            double d3 = motionY;
            float f = jumpMovementFactor;
            jumpMovementFactor = capabilities.func_75093_a();
            super.moveEntityWithHeading(par1, par2);
            motionY = d3 * 0.59999999999999998D;
            jumpMovementFactor = f;
        }
        else
        {
            super.moveEntityWithHeading(par1, par2);
        }

        addMovementStat(posX - d, posY - d1, posZ - d2);
    }

    /**
     * Adds a value to a movement statistic field - like run, walk, swin or climb.
     */
    public void addMovementStat(double par1, double par3, double par5)
    {
        if (ridingEntity != null)
        {
            return;
        }

        if (isInsideOfMaterial(Material.water))
        {
            int i = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100F);

            if (i > 0)
            {
                addStat(StatList.distanceDoveStat, i);
				addGeneralExhaustion(53F,i,true); // [ERKIN]
				//addExhaustion(0.015F * (float)i * 0.01F);
            }
        }
        else if (isInWater())
        {
            int j = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100F);

            if (j > 0)
            {
                addStat(StatList.distanceSwumStat, j);
				addGeneralExhaustion(53F,j,true); // [ERKIN]
                //addExhaustion(0.015F * (float)j * 0.01F);
            }
        }
        else if (isOnLadder())
        {
            if (par3 > 0.0D)
            {
                addStat(StatList.distanceClimbedStat, (int)Math.round(par3 * 100D));
            }
        }
        else if (onGround)
        {
            int k = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100F);

            if (k > 0)
            {
                addStat(StatList.distanceWalkedStat, k);

                if (isSprinting())
                {
					addGeneralExhaustion(8.00000080000008F,k,true); // [ERKIN]
                    //addExhaustion(0.09999999F * (float)k * 0.01F);
                }
                else
                {
					addGeneralExhaustion(80F,k,true); // [ERKIN]
                    //addExhaustion(0.01F * (float)k * 0.01F);
                }
            }
        }
        else
        {
            int l = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100F);

            if (l > 25)
            {
                addStat(StatList.distanceFlownStat, l);
            }
        }
    }

    /**
     * Adds a value to a mounted movement statistic field - by minecart, boat, or pig.
     */
    private void addMountedMovementStat(double par1, double par3, double par5)
    {
        if (ridingEntity != null)
        {
            int i = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100F);

            if (i > 0)
            {
                if (ridingEntity instanceof EntityMinecart)
                {
                    addStat(StatList.distanceByMinecartStat, i);

                    if (startMinecartRidingCoordinate == null)
                    {
                        startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
                    }
                    else if ((double)startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) >= 1000000D)
                    {
                        addStat(AchievementList.onARail, 1);
                    }
                }
                else if (ridingEntity instanceof EntityBoat)
                {
                    addStat(StatList.distanceByBoatStat, i);
                }
                else if (ridingEntity instanceof EntityPig)
                {
                    addStat(StatList.distanceByPigStat, i);
                }
            }
        }
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        if (capabilities.allowFlying)
        {
            return;
        }

        if (par1 >= 2.0F)
        {
            addStat(StatList.distanceFallenStat, (int)Math.round((double)par1 * 100D));
        }

        super.fall(par1);
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving par1EntityLiving)
    {
        if (par1EntityLiving instanceof EntityMob)
        {
            triggerAchievement(AchievementList.killEnemy);
        }
    }

    /**
     * Gets the Icon Index of the item currently held
     */
    public int getItemIcon(ItemStack par1ItemStack, int par2)
    {
        int i = super.getItemIcon(par1ItemStack, par2);

        if (par1ItemStack.itemID == Item.fishingRod.shiftedIndex && fishEntity != null)
        {
            i = par1ItemStack.getIconIndex() + 16;
        }
        else
        {
            if (par1ItemStack.getItem().requiresMultipleRenderPasses())
            {
                return par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2);
            }

            if (itemInUse != null && par1ItemStack.itemID == Item.bow.shiftedIndex)
            {
                int j = par1ItemStack.getMaxItemUseDuration() - itemInUseCount;

                if (j >= 18)
                {
                    return 133;
                }

                if (j > 13)
                {
                    return 117;
                }

                if (j > 0)
                {
                    return 101;
                }
            }
        }

        return i;
    }

    /**
     * Called by portal blocks when an entity is within it.
     */
    public void setInPortal()
    {
        if (timeUntilPortal > 0)
        {
            timeUntilPortal = 10;
            return;
        }
        else
        {
            inPortal = true;
            return;
        }
    }

    /**
     * This method increases the player's current amount of experience.
     */
    public void addExperience(int par1)
    {
        score += par1;
        int i = 0x7fffffff - experienceTotal;

        if (par1 > i)
        {
            par1 = i;
        }

        experience += (float)par1 / (float)xpBarCap();
        experienceTotal += par1;

        for (; experience >= 1.0F; experience /= xpBarCap())
        {
            experience = (experience - 1.0F) * (float)xpBarCap();
            increaseLevel();
        }
    }

    /**
     * Decrease the player level, used to pay levels for enchantments on items at enchanted table.
     */
    public void removeExperience(int par1)
    {
        experienceLevel -= par1;

        if (experienceLevel < 0)
        {
            experienceLevel = 0;
        }
    }

    /**
     * This method returns the cap amount of experience that the experience bar can hold. With each level, the
     * experience cap on the player's experience bar is raised by 10.
     */
    public int xpBarCap()
    {
        if (experienceLevel >= 30)
        {
            return 62 + (experienceLevel - 30) * 7;
        }

        if (experienceLevel >= 15)
        {
            return 17 + (experienceLevel - 15) * 3;
        }
        else
        {
            return 17;
        }
    }

    /**
     * This method increases the player's experience level by one.
     */
    private void increaseLevel()
    {
        experienceLevel++;
    }

    /**
     * increases exhaustion level by supplied amount
     */
    public void addExhaustion(float par1)
    {
        if (capabilities.disableDamage)
        {
            return;
        }

        if (!worldObj.isRemote)
        {
            foodStats.addExhaustion(par1);
        }
    }

    /**
     * Returns the player's FoodStats object.
     */
    public FoodStats getFoodStats()
    {
        return foodStats;
    }

    public boolean canEat(boolean par1)
    {
        return (par1 || foodStats.needFood()) && !capabilities.disableDamage;
    }

    /**
     * Checks if the player's health is not full and not zero.
     */
    public boolean shouldHeal()
    {
        return getHealth() > 0 && getHealth() < getMaxHealth();
    }

    /**
     * sets the itemInUse when the use item button is clicked. Args: itemstack, int maxItemUseDuration
     */
    public void setItemInUse(ItemStack par1ItemStack, int par2)
    {
        if (par1ItemStack == itemInUse)
        {
            return;
        }

        itemInUse = par1ItemStack;
        itemInUseCount = par2;

        if (!worldObj.isRemote)
        {
            // [ERKIN]
            if (par1ItemStack.getItem().getItemName().equals("item.gourdFull"))
            {
                setDrinking(true);
            }
            else
            {
                setEating(true);
            }
        }
    }

    public boolean canPlayerEdit(int par1, int par2, int par3)
    {
        return capabilities.field_75099_e;
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
    {
        int i = experienceLevel * 7;

        if (i > 100)
        {
            return 100;
        }
        else
        {
            return i;
        }
    }

    /**
     * Only use is to identify if class is an instance of player for experience dropping
     */
    protected boolean isPlayer()
    {
        return true;
    }

    public String func_70023_ak()
    {
        return username;
    }

    public void travelToTheEnd(int i)
    {
    }

    public void func_71049_a(EntityPlayer par1EntityPlayer, boolean par2)
    {
        if (par2)
        {
            inventory.copyInventory(par1EntityPlayer.inventory);
            health = par1EntityPlayer.health;
            foodStats = par1EntityPlayer.foodStats;
			waterStats = par1EntityPlayer.waterStats; //[ERKIN]
			energyStats = par1EntityPlayer.energyStats; //[ERKIN]
			playerTemperature = par1EntityPlayer.playerTemperature; //[ERKIN]
            experienceLevel = par1EntityPlayer.experienceLevel;
            experienceTotal = par1EntityPlayer.experienceTotal;
            experience = par1EntityPlayer.experience;
            score = par1EntityPlayer.score;
        }

        field_71078_a = par1EntityPlayer.field_71078_a;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return !capabilities.isFlying;
    }

    public void func_71016_p()
    {
    }

    public void func_71033_a(EnumGameType enumgametype)
    {
    }

    public String func_70005_c_()
    {
        return username;
    }

    public StringTranslate func_71025_t()
    {
        return StringTranslate.getInstance();
    }

    public String func_70004_a(String par1Str, Object par2ArrayOfObj[])
    {
        return func_71025_t().translateKeyFormat(par1Str, par2ArrayOfObj);
    }

    public InventoryEnderChest func_71005_bN()
    {
        return field_71078_a;
    }
	
	// [ERKIN] Set player exhausted (no energy or hypothermia)
    private void setPlayerAsExhausted()
    {
        landMovementFactor = landMovementFactor / 2f;
    }
	
	/**
    * increases exhaustion water level by supplied amount
    */
    //[ERKIN]
    public void addWaterExhaustion(float par1)
    {
        if (capabilities.disableDamage)
        {
            return;
        }

        if (!worldObj.isRemote)
        {
            waterStats.addWaterExhaustion(par1);
        }
    }
	
	/**
    * increases exhaustion energy level by supplied amount
    */
    //[ERKIN]
    public void addEnergyExhaustion(float par1)
    {
        if (capabilities.disableDamage)
        {
            return;
        }

        if (!worldObj.isRemote)
        {
            energyStats.addEnergyExhaustion(par1);
        }
    }
	
	/**
	* [ERKIN]
	* Calculate the global exhaust values
	*/
	public void addGeneralExhaustion(float divisor, float movementDistance, boolean isVariable)
	{
		float foodExhaustionValue = foodStats.getBaseFoodExhaustionValue() / divisor;
		float waterExhaustionValue = waterStats.getBaseWaterExhaustionValue() / divisor;
		float energyExhaustionValue = energyStats.getBaseEnergyExhaustionValue() / divisor;
        /*if (!worldObj.isRemote)
        {
			System.out.println("server only energy consumption---- : " + energyStats.getBaseEnergyExhaustionValue());
		}*/
		if(!isVariable)
		{
			addExhaustion(foodExhaustionValue);
			addWaterExhaustion(waterExhaustionValue);
			addEnergyExhaustion(energyExhaustionValue);
		}
		else
		{
			addExhaustion(foodExhaustionValue * movementDistance * 0.01F);
			addWaterExhaustion(waterExhaustionValue * movementDistance * 0.01F );
			addEnergyExhaustion(energyExhaustionValue * movementDistance * 0.01F);
		}
	}
	
	/**
     * Returns the player's WaterStats object.
     */
    //[ERKIN]
    public WaterStats getWaterStats()
    {
        return waterStats;
    }

    /**
     * Returns the player's EnergyStats object.
     */
    //[ERKIN]
    public EnergyStats getEnergyStats()
    {
        return energyStats;
    }
	
	//[ERKIN]
    public boolean canDrink(boolean par1)
    {
        return (par1 || waterStats.needWater()) && !capabilities.disableDamage;
    }
}
