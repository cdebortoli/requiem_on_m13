package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandDefaultGameMode extends CommandGameMode
{
    public CommandDefaultGameMode()
    {
    }

    public String func_71517_b()
    {
        return "defaultgamemode";
    }

    public String func_71518_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_70004_a("commands.defaultgamemode.usage", new Object[0]);
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 0)
        {
            EnumGameType enumgametype = func_71539_b(par1ICommandSender, par2ArrayOfStr[0]);
            func_71541_a(enumgametype);
            String s = StatCollector.translateToLocal((new StringBuilder()).append("gameMode.").append(enumgametype.func_77149_b()).toString());
            func_71522_a(par1ICommandSender, "commands.defaultgamemode.success", new Object[]
                    {
                        s
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
    }

    protected void func_71541_a(EnumGameType par1EnumGameType)
    {
        MinecraftServer.func_71276_C().func_71235_a(par1EnumGameType);
    }
}
