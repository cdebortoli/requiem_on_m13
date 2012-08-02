package net.minecraft.src;

public class CommandShowSeed extends CommandBase
{
    public CommandShowSeed()
    {
    }

    public String func_71517_b()
    {
        return "seed";
    }

    public void func_71515_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_71521_c(par1ICommandSender);
        par1ICommandSender.func_70006_a((new StringBuilder()).append("Seed: ").append(entityplayer.worldObj.getSeed()).toString());
    }
}
