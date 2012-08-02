package net.minecraft.src;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{
    protected BlockMobSpawner(int par1, int par2)
    {
        super(par1, par2, Material.rock);
    }

    public TileEntity func_72274_a(World par1World)
    {
        return new TileEntityMobSpawner();
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        int i = 15 + par1World.rand.nextInt(15) + par1World.rand.nextInt(15);
        func_71923_g(par1World, par2, par3, par4, i);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public int func_71922_a(World par1World, int par2, int par3, int i)
    {
        return 0;
    }
}
