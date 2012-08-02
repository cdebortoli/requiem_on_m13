package net.minecraft.src;

import java.util.concurrent.atomic.AtomicInteger;

class TcpReaderThread extends Thread
{
    final TcpConnection field_74498_a;

    TcpReaderThread(TcpConnection par1TcpConnection, String par2Str)
    {
        super(par2Str);
        field_74498_a = par1TcpConnection;
    }

    public void run()
    {
        TcpConnection.field_74471_a.getAndIncrement();

        try
        {
            while (TcpConnection.func_74462_a(field_74498_a) && !TcpConnection.func_74449_b(field_74498_a))
            {
                while (TcpConnection.func_74450_c(field_74498_a)) ;

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            TcpConnection.field_74471_a.getAndDecrement();
        }
    }
}
