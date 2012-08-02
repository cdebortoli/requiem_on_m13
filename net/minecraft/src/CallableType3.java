package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType3 implements Callable
{
    final IntegratedServer field_76974_a;

    CallableType3(IntegratedServer par1IntegratedServer)
    {
        field_76974_a = par1IntegratedServer;
    }

    public String func_76973_a()
    {
        return "Integrated Server";
    }

    public Object call()
    {
        return func_76973_a();
    }
}
