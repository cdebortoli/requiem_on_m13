package net.minecraft.src;

public class CommandKill extends CommandBase
{
    public CommandKill()
    {
    }

    public String func_71517_b()
    {
        return "kill";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_71521_c(par1ICommandSender);
        entityplayer.attackEntityFrom(DamageSource.outOfWorld, 1000);
        par1ICommandSender.func_70006_a("Ouch. That look like it hurt.");
    }
}
