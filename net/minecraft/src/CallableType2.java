package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableType2 implements Callable
{
    final Minecraft field_74503_a;

    public CallableType2(Minecraft par1Minecraft)
    {
        field_74503_a = par1Minecraft;
    }

    public String func_74502_a()
    {
        return "Client";
    }

    public Object call()
    {
        return func_74502_a();
    }
}
