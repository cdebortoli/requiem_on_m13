package net.minecraft.src;

public class MerchantRecipe
{
    private ItemStack field_77403_a;
    private ItemStack field_77401_b;
    private ItemStack field_77402_c;
    private int field_77400_d;

    public MerchantRecipe(NBTTagCompound par1NBTTagCompound)
    {
        func_77390_a(par1NBTTagCompound);
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack, ItemStack par3ItemStack)
    {
        field_77403_a = par1ItemStack;
        field_77401_b = par2ItemStack;
        field_77402_c = par3ItemStack;
    }

    public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        this(par1ItemStack, null, par2ItemStack);
    }

    public MerchantRecipe(ItemStack par1ItemStack, Item par2Item)
    {
        this(par1ItemStack, new ItemStack(par2Item));
    }

    public ItemStack func_77394_a()
    {
        return field_77403_a;
    }

    public ItemStack func_77396_b()
    {
        return field_77401_b;
    }

    public boolean func_77398_c()
    {
        return field_77401_b != null;
    }

    public ItemStack func_77397_d()
    {
        return field_77402_c;
    }

    public boolean func_77393_a(MerchantRecipe par1MerchantRecipe)
    {
        if (field_77403_a.itemID != par1MerchantRecipe.field_77403_a.itemID || field_77402_c.itemID != par1MerchantRecipe.field_77402_c.itemID)
        {
            return false;
        }
        else
        {
            return field_77401_b == null && par1MerchantRecipe.field_77401_b == null || field_77401_b != null && par1MerchantRecipe.field_77401_b != null && field_77401_b.itemID == par1MerchantRecipe.field_77401_b.itemID;
        }
    }

    public boolean func_77391_b(MerchantRecipe par1MerchantRecipe)
    {
        return func_77393_a(par1MerchantRecipe) && (field_77403_a.stackSize < par1MerchantRecipe.field_77403_a.stackSize || field_77401_b != null && field_77401_b.stackSize < par1MerchantRecipe.field_77401_b.stackSize);
    }

    public int func_77392_e()
    {
        return field_77400_d;
    }

    public void func_77399_f()
    {
        field_77400_d++;
    }

    public void func_77390_a(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound nbttagcompound = par1NBTTagCompound.getCompoundTag("buy");
        field_77403_a = ItemStack.loadItemStackFromNBT(nbttagcompound);
        NBTTagCompound nbttagcompound1 = par1NBTTagCompound.getCompoundTag("sell");
        field_77402_c = ItemStack.loadItemStackFromNBT(nbttagcompound1);

        if (par1NBTTagCompound.hasKey("buyB"))
        {
            field_77401_b = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("buyB"));
        }

        if (par1NBTTagCompound.hasKey("uses"))
        {
            field_77400_d = par1NBTTagCompound.getInteger("uses");
        }
    }

    public NBTTagCompound func_77395_g()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setCompoundTag("buy", field_77403_a.writeToNBT(new NBTTagCompound("buy")));
        nbttagcompound.setCompoundTag("sell", field_77402_c.writeToNBT(new NBTTagCompound("sell")));

        if (field_77401_b != null)
        {
            nbttagcompound.setCompoundTag("buyB", field_77401_b.writeToNBT(new NBTTagCompound("buyB")));
        }

        nbttagcompound.setInteger("uses", field_77400_d);
        return nbttagcompound;
    }
}
