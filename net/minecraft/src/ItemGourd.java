package net.minecraft.src;

import java.util.Random;

public class ItemGourd extends Item
{
    private final int waterAmount;
    private boolean isFull;

    public ItemGourd(int par1, boolean par2)
    {
        super(par1);
        waterAmount = 20;
        maxStackSize = 1;
        isFull = par2;
    }

    public ItemStack onWaterDrunk(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.getWaterStats().addStats(this);
        par3EntityPlayer.playerTemperature.playerDrinkSomeWater(this.waterAmount);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        return new ItemStack(Item.gourdEmpty);
    }

    /**
    * How long it takes to use or consume an item
    */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
    * returns the action that specifies what animation to play when the items is being used
    */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float f = 1.0F;
        double d = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
        double d1 = (par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)par3EntityPlayer.yOffset;
        double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
        boolean flag = isFull == false;
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);

        if ((isFull) && (par3EntityPlayer.canDrink(true)))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
        }
        else if (movingobjectposition == null)
        {
            return par1ItemStack;
        }
        else if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
        {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (!isFull)
            {
                if (!par3EntityPlayer.canPlayerEdit(i, j, k))
                {
                    return par1ItemStack;
                }

                if (par2World.getBlockMaterial(i, j, k) == Material.water && par2World.getBlockMetadata(i, j, k) == 0)
                {
                    if (par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        return par1ItemStack;
                    }
                    else
                    {
                        return new ItemStack(Item.gourdFull);
                    }
                }
            }
        }

        return par1ItemStack;
    }

    public int getWaterAmount()
    {
        return waterAmount;
    }

    /**
     * set name of item from language file
     */
    public Item setItemName(String par1Str)
    {
        return super.setItemName(par1Str);
    }
}