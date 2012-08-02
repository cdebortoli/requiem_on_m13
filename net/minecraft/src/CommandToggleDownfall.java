package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandToggleDownfall extends CommandBase
{
    public CommandToggleDownfall()
    {
    }

    public String func_71517_b()
    {
        return "toggledownfall";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_71554_c();
        func_71522_a(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }

    protected void func_71554_c()
    {
        MinecraftServer.func_71276_C().field_71305_c[0].func_72913_w();
        MinecraftServer.func_71276_C().field_71305_c[0].getWorldInfo().setThundering(true);
    }
}
