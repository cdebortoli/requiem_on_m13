package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerStop extends CommandBase
{
    public CommandServerStop()
    {
    }

    public String func_71517_b()
    {
        return "stop";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_71522_a(par1ICommandSender, "commands.stop.start", new Object[0]);
        MinecraftServer.func_71276_C().func_71263_m();
    }
}
