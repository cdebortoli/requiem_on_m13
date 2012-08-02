package net.minecraft.src;

import java.util.Set;
import java.util.concurrent.Callable;

class CallableMPL2 implements Callable
{
    final WorldClient field_78835_a;

    CallableMPL2(WorldClient par1WorldClient)
    {
        field_78835_a = par1WorldClient;
    }

    public String func_78834_a()
    {
        return (new StringBuilder()).append(WorldClient.func_73030_b(field_78835_a).size()).append(" total; ").append(WorldClient.func_73030_b(field_78835_a).toString()).toString();
    }

    public Object call()
    {
        return func_78834_a();
    }
}
