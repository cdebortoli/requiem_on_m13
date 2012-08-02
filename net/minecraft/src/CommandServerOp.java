package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerOp extends CommandBase
{
    public CommandServerOp()
    {
    }

    public String func_71517_b()
    {
        return "op";
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.op.usage", new Object[0]);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_71276_C().func_71203_ab().func_72386_b(par2ArrayOfStr[0]);
            func_71522_a(par1ICommandSender, "commands.op.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
    }

    public List func_71516_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList arraylist = new ArrayList();
            String as[] = MinecraftServer.func_71276_C().func_71213_z();
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s1 = as[j];

                if (!MinecraftServer.func_71276_C().func_71203_ab().func_72353_e(s1) && func_71523_a(s, s1))
                {
                    arraylist.add(s1);
                }
            }

            return arraylist;
        }
        else
        {
            return null;
        }
    }
}
