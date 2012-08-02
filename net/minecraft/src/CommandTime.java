package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandTime extends CommandBase
{
    public CommandTime()
    {
    }

    public String func_71517_b()
    {
        return "time";
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.time.usage", new Object[0]);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 1)
        {
            if (par2ArrayOfStr[0].equals("set"))
            {
                int i;

                if (par2ArrayOfStr[1].equals("day"))
                {
                    i = 0;
                }
                else if (par2ArrayOfStr[1].equals("night"))
                {
                    i = 12500;
                }
                else
                {
                    i = func_71528_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                }

                func_71552_a(par1ICommandSender, i);
                func_71522_a(par1ICommandSender, "commands.time.set", new Object[]
                        {
                            Integer.valueOf(i)
                        });
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                int j = func_71528_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                func_71553_b(par1ICommandSender, j);
                func_71522_a(par1ICommandSender, "commands.time.added", new Object[]
                        {
                            Integer.valueOf(j)
                        });
                return;
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "set", "add"
                    });
        }

        if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set"))
        {
            return func_71530_a(par2ArrayOfStr, new String[]
                    {
                        "day", "night"
                    });
        }
        else
        {
            return null;
        }
    }

    protected void func_71552_a(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_71276_C().field_71305_c.length; i++)
        {
            MinecraftServer.func_71276_C().field_71305_c[i].func_73048_a(par2);
        }
    }

    protected void func_71553_b(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_71276_C().field_71305_c.length; i++)
        {
            WorldServer worldserver = MinecraftServer.func_71276_C().field_71305_c[i];
            worldserver.func_73048_a(worldserver.getWorldTime() + (long)par2);
        }
    }
}
