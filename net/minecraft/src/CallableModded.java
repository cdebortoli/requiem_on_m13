package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

public class CallableModded implements Callable
{
    final Minecraft field_74416_a;

    public CallableModded(Minecraft par1Minecraft)
    {
        field_74416_a = par1Minecraft;
    }

    public String func_74415_a()
    {
        String s = ClientBrandRetriever.getClientModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }

        if ((net.minecraft.client.Minecraft.class).getClassLoader().getResource("META-INF/MOJANG_C.DSA") == null)
        {
            return "Very likely";
        }
        else
        {
            return "Probably not";
        }
    }

    public Object call()
    {
        return func_74415_a();
    }
}
