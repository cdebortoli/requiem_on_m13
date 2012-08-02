package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerList extends CommandBase
{
    public CommandServerList()
    {
    }

    public String func_71517_b()
    {
        return "list";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        par1ICommandSender.func_70006_a(par1ICommandSender.func_70004_a("commands.players.list", new Object[]
                {
                    Integer.valueOf(MinecraftServer.func_71276_C().func_71233_x()), Integer.valueOf(MinecraftServer.func_71276_C().func_71275_y())
                }));
        par1ICommandSender.func_70006_a(MinecraftServer.func_71276_C().func_71203_ab().func_72398_c());
    }
}
