package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOff extends CommandBase
{
    public CommandServerSaveOff()
    {
    }

    public String func_71517_b()
    {
        return "save-off";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        MinecraftServer minecraftserver = MinecraftServer.func_71276_C();

        for (int i = 0; i < minecraftserver.field_71305_c.length; i++)
        {
            if (minecraftserver.field_71305_c[i] != null)
            {
                WorldServer worldserver = minecraftserver.field_71305_c[i];
                worldserver.field_73058_d = true;
            }
        }

        func_71522_a(par1ICommandSender, "commands.save.disabled", new Object[0]);
    }
}
