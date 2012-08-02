package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    final CrashReport field_71494_a;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        field_71494_a = par1CrashReport;
    }

    public String func_71493_a()
    {
        return "1.3.1";
    }

    public Object call()
    {
        return func_71493_a();
    }
}
