package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityClientPlayerMP extends EntityPlayerSP
{
    public NetClientHandler sendQueue;
    private double oldPosX;

    /** Old Minimum Y of the bounding box */
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;

    /** Check if was on ground last update */
    private boolean wasOnGround;

    /** should the player stop sneaking? */
    private boolean shouldStopSneaking;
    private boolean wasSneaking;
    private int field_71168_co;

    /** has the client player's health been set? */
    private boolean hasSetHealth;

    public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler)
    {
        super(par1Minecraft, par2World, par3Session, 0);
        wasOnGround = false;
        shouldStopSneaking = false;
        wasSneaking = false;
        field_71168_co = 0;
        hasSetHealth = false;
        sendQueue = par4NetClientHandler;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(int i)
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!worldObj.blockExists(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)))
        {
            return;
        }
        else
        {
            super.onUpdate();
            sendMotionUpdates();
            return;
        }
    }

    /**
     * Send updated motion and position information to the server
     */
    public void sendMotionUpdates()
    {
        boolean flag = isSprinting();

        if (flag != wasSneaking)
        {
            if (flag)
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
            }

            wasSneaking = flag;
        }

        boolean flag1 = isSneaking();

        if (flag1 != shouldStopSneaking)
        {
            if (flag1)
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
            }

            shouldStopSneaking = flag1;
        }

        double d = posX - oldPosX;
        double d1 = boundingBox.minY - oldMinY;
        double d2 = posZ - oldPosZ;
        double d3 = rotationYaw - oldRotationYaw;
        double d4 = rotationPitch - oldRotationPitch;
        boolean flag2 = d * d + d1 * d1 + d2 * d2 > 0.00089999999999999998D || field_71168_co >= 20;
        boolean flag3 = d3 != 0.0D || d4 != 0.0D;

        if (ridingEntity != null)
        {
            if (flag3)
            {
                sendQueue.addToSendQueue(new Packet11PlayerPosition(motionX, -999D, -999D, motionZ, onGround));
            }
            else
            {
                sendQueue.addToSendQueue(new Packet13PlayerLookMove(motionX, -999D, -999D, motionZ, rotationYaw, rotationPitch, onGround));
            }

            flag2 = false;
        }
        else if (flag2 && flag3)
        {
            sendQueue.addToSendQueue(new Packet13PlayerLookMove(posX, boundingBox.minY, posY, posZ, rotationYaw, rotationPitch, onGround));
        }
        else if (flag2)
        {
            sendQueue.addToSendQueue(new Packet11PlayerPosition(posX, boundingBox.minY, posY, posZ, onGround));
        }
        else if (flag3)
        {
            sendQueue.addToSendQueue(new Packet12PlayerLook(rotationYaw, rotationPitch, onGround));
        }
        else if (wasOnGround != onGround)
        {
            sendQueue.addToSendQueue(new Packet10Flying(onGround));
        }

        field_71168_co++;
        wasOnGround = onGround;

        if (flag2)
        {
            oldPosX = posX;
            oldMinY = boundingBox.minY;
            oldPosY = posY;
            oldPosZ = posZ;
            field_71168_co = 0;
        }

        if (flag3)
        {
            oldRotationYaw = rotationYaw;
            oldRotationPitch = rotationPitch;
        }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem()
    {
        sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem entityitem)
    {
    }

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String par1Str)
    {
        sendQueue.addToSendQueue(new Packet3Chat(par1Str));
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        sendQueue.addToSendQueue(new Packet18Animation(this, 1));
    }

    public void respawnPlayer()
    {
        sendQueue.addToSendQueue(new Packet205ClientCommand(1));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource par1DamageSource, int par2)
    {
        setEntityHealth(getHealth() - par2);
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        sendQueue.addToSendQueue(new Packet101CloseWindow(craftingInventory.windowId));
        inventory.setItemStack(null);
        super.closeScreen();
    }

    /**
     * Updates health locally.
     */
    public void setHealth(int par1)
    {
        if (hasSetHealth)
        {
            super.setHealth(par1);
        }
        else
        {
            setEntityHealth(par1);
            hasSetHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase == null)
        {
            return;
        }

        if (par1StatBase.isIndependent)
        {
            super.addStat(par1StatBase, par2);
        }
    }

    /**
     * Used by NetClientHandler.handleStatistic
     */
    public void incrementStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase == null)
        {
            return;
        }

        if (!par1StatBase.isIndependent)
        {
            super.addStat(par1StatBase, par2);
        }
    }

    public void func_71016_p()
    {
        sendQueue.addToSendQueue(new Packet202PlayerAbilities(capabilities));
    }

    public boolean func_71066_bF()
    {
        return true;
    }
	
	// [ERKIN]
	public void checkIfDrinkWithHand(MovingObjectPosition waterMovingobjectposition)
	{
	    int i = waterMovingobjectposition.blockX;
        int j = waterMovingobjectposition.blockY;
        int k = waterMovingobjectposition.blockZ;

        if (this.worldObj.getBlockMaterial(i, j, k) == Material.water && this.worldObj.getBlockMetadata(i, j, k) == 0)
        {
            waterStats.addStats(20);
            sendQueue.addToSendQueue(new Packet240UpdateRequiem(waterStats.getWaterLevel(), energyStats.getEnergyLevel(), playerTemperature.getIsCold(), playerTemperature.getIsHot(),playerTemperature.getIsInHypothermia(),playerTemperature.getIsInHyperthermia()));
        }
	}
}
