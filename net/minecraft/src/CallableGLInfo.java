package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class CallableGLInfo implements Callable
{
    final Minecraft field_74419_a;

    public CallableGLInfo(Minecraft par1Minecraft)
    {
        field_74419_a = par1Minecraft;
    }

    public String func_74418_a()
    {
        return (new StringBuilder()).append(GL11.glGetString(GL11.GL_RENDERER)).append(" GL version ").append(GL11.glGetString(GL11.GL_VERSION)).append(", ").append(GL11.glGetString(GL11.GL_VENDOR)).toString();
    }

    public Object call()
    {
        return func_74418_a();
    }
}
