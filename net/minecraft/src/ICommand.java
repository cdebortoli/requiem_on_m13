package net.minecraft.src;

import java.util.List;

public interface ICommand extends Comparable
{
    public abstract String func_71517_b();

    public abstract String func_71518_a(ICommandSender icommandsender);

    public abstract List func_71514_a();

    public abstract void func_71515_b(ICommandSender icommandsender, String as[]);

    public abstract boolean func_71519_b(ICommandSender icommandsender);

    public abstract List func_71516_a(ICommandSender icommandsender, String as[]);
}
