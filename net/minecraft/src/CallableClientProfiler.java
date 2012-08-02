package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableClientProfiler implements Callable
{
    final Minecraft field_74500_a;

    public CallableClientProfiler(Minecraft par1Minecraft)
    {
        field_74500_a = par1Minecraft;
    }

    public String func_74499_a()
    {
        return field_74500_a.field_71424_I.profilingEnabled ? field_74500_a.field_71424_I.func_76322_c() : "N/A (disabled)";
    }

    public Object call()
    {
        return func_74499_a();
    }
}
