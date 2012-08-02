package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

public class CallablePlayers implements Callable
{
    final MinecraftServer field_74270_a;

    public CallablePlayers(MinecraftServer par1MinecraftServer)
    {
        field_74270_a = par1MinecraftServer;
    }

    public String func_74269_a()
    {
        return (new StringBuilder()).append(MinecraftServer.func_71196_a(field_74270_a).func_72394_k()).append(" / ").append(MinecraftServer.func_71196_a(field_74270_a).func_72352_l()).append("; ").append(MinecraftServer.func_71196_a(field_74270_a).field_72404_b).toString();
    }

    public Object call()
    {
        return func_74269_a();
    }
}
