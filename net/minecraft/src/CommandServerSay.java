package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerSay extends CommandBase
{
    public CommandServerSay()
    {
    }

    public String func_71517_b()
    {
        return "say";
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.say.usage", new Object[0]);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 0)
        {
            String s = func_71520_a(par2ArrayOfStr, 0);
            MinecraftServer.func_71276_C().func_71203_ab().func_72384_a(new Packet3Chat(String.format("[%s] %s", new Object[]
                    {
                        par1ICommandSender.func_70005_c_(), s
                    })));
            return;
        }
        else
        {
            throw new WrongUsageException("commands.say.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            return func_71530_a(par2ArrayOfStr, MinecraftServer.func_71276_C().func_71213_z());
        }
        else
        {
            return null;
        }
    }
}
