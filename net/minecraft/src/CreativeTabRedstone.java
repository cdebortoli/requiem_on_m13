package net.minecraft.src;

final class CreativeTabRedstone extends CreativeTabs
{
    CreativeTabRedstone(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    public int func_78012_e()
    {
        return Item.redstone.shiftedIndex;
    }
}
