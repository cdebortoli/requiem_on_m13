package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL1 implements Callable
{
    final WorldClient field_78833_a;

    CallableMPL1(WorldClient par1WorldClient)
    {
        field_78833_a = par1WorldClient;
    }

    public String func_78832_a()
    {
        return (new StringBuilder()).append(WorldClient.func_73026_a(field_78833_a).size()).append(" total; ").append(WorldClient.func_73026_a(field_78833_a).toString()).toString();
    }

    public Object call()
    {
        return func_78832_a();
    }
}
