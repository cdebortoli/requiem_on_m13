package net.minecraft.src;

import java.util.*;
import net.minecraft.server.MinecraftServer;

public class CommandServerBanlist extends CommandBase
{
    public CommandServerBanlist()
    {
    }

    public String func_71517_b()
    {
        return "banlist";
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return (MinecraftServer.func_71276_C().func_71203_ab().func_72363_f().func_73710_b() || MinecraftServer.func_71276_C().func_71203_ab().func_72390_e().func_73710_b()) && super.func_71519_b(par1ICommandSender);
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.banlist.usage", new Object[0]);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips"))
        {
            par1ICommandSender.func_70006_a(par1ICommandSender.func_70004_a("commands.banlist.ips", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().func_72363_f().func_73712_c().size())
                    }));
            par1ICommandSender.func_70006_a(func_71527_a(MinecraftServer.func_71276_C().func_71203_ab().func_72363_f().func_73712_c().keySet().toArray()));
        }
        else
        {
            par1ICommandSender.func_70006_a(par1ICommandSender.func_70004_a("commands.banlist.players", new Object[]
                    {
                        Integer.valueOf(MinecraftServer.func_71276_C().func_71203_ab().func_72390_e().func_73712_c().size())
                    }));
            par1ICommandSender.func_70006_a(func_71527_a(MinecraftServer.func_71276_C().func_71203_ab().func_72390_e().func_73712_c().keySet().toArray()));
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "players", "ips"
                    });
        }
        else
        {
            return null;
        }
    }
}
