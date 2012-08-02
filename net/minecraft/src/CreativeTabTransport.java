package net.minecraft.src;

final class CreativeTabTransport extends CreativeTabs
{
    CreativeTabTransport(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    public int func_78012_e()
    {
        return Block.railPowered.blockID;
    }
}
