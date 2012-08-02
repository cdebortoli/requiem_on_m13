package net.minecraft.src;

import java.util.List;

public class BlockWood extends Block
{
    public static final String field_72152_a[] =
    {
        "oak", "spruce", "birch", "jungle"
    };

    public BlockWood(int par1)
    {
        super(par1, 4, Material.wood);
        func_71849_a(CreativeTabs.field_78030_b);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        switch (par2)
        {
            default:
                return 4;

            case 1:
                return 198;

            case 2:
                return 214;

            case 3:
                return 199;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    protected int damageDropped(int par1)
    {
        return par1;
    }

    public void func_71879_a(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
}
