package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerBan extends CommandBase
{
    public CommandServerBan()
    {
    }

    public String func_71517_b()
    {
        return "ban";
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.ban.usage", new Object[0]);
    }

    public boolean func_71519_b(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_71276_C().func_71203_ab().func_72390_e().func_73710_b() && super.func_71519_b(par1ICommandSender);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0)
        {
            EntityPlayerMP entityplayermp = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(par2ArrayOfStr[0]);
            BanEntry banentry = new BanEntry(par2ArrayOfStr[0]);
            banentry.func_73687_a(par1ICommandSender.func_70005_c_());

            if (par2ArrayOfStr.length >= 2)
            {
                banentry.func_73689_b(func_71520_a(par2ArrayOfStr, 1));
            }

            MinecraftServer.func_71276_C().func_71203_ab().func_72390_e().func_73706_a(banentry);

            if (entityplayermp != null)
            {
                entityplayermp.field_71135_a.func_72565_c("You are banned from this server.");
            }

            func_71522_a(par1ICommandSender, "commands.ban.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
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
