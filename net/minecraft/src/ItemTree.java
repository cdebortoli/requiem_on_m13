package net.minecraft.src;

public class ItemTree extends ItemBlock
{
    private Block field_77892_a;

    public ItemTree(int par1, Block par2Block)
    {
        super(par1);
        field_77892_a = par2Block;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return field_77892_a.getBlockTextureFromSideAndMetadata(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.getItemDamage();

        if (i < 0 || i >= BlockLog.field_72142_a.length)
        {
            i = 0;
        }

        return (new StringBuilder()).append(super.getItemName()).append(".").append(BlockLog.field_72142_a[i]).toString();
    }
}
