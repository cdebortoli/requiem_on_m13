package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import org.lwjgl.Sys;

public class CallableLWJGLVersion implements Callable
{
    final Minecraft field_74421_a;

    public CallableLWJGLVersion(Minecraft par1Minecraft)
    {
        field_74421_a = par1Minecraft;
    }

    public String func_74420_a()
    {
        return Sys.getVersion();
    }

    public Object call()
    {
        return func_74420_a();
    }
}
