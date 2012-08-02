package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable
{
    final World field_77440_a;

    CallableLvl3(World par1World)
    {
        field_77440_a = par1World;
    }

    public String func_77439_a()
    {
        return field_77440_a.chunkProvider.makeString();
    }

    public Object call()
    {
        return func_77439_a();
    }
}
