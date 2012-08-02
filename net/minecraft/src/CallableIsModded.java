package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

class CallableIsModded implements Callable
{
    final IntegratedServer field_76972_a;

    CallableIsModded(IntegratedServer par1IntegratedServer)
    {
        field_76972_a = par1IntegratedServer;
    }

    public String func_76971_a()
    {
        String s = ClientBrandRetriever.getClientModName();

        if (!s.equals("vanilla"))
        {
            return (new StringBuilder()).append("Definitely; '").append(s).append("'").toString();
        }

        s = field_76972_a.getServerModName();

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
        return func_76971_a();
    }
}
