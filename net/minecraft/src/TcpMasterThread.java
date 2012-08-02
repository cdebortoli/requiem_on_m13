package net.minecraft.src;

class TcpMasterThread extends Thread
{
    final TcpConnection field_74504_a;

    TcpMasterThread(TcpConnection par1TcpConnection)
    {
        field_74504_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (TcpConnection.func_74457_g(field_74504_a).isAlive())
            {
                try
                {
                    TcpConnection.func_74457_g(field_74504_a).stop();
                }
                catch (Throwable throwable) { }
            }

            if (TcpConnection.func_74461_h(field_74504_a).isAlive())
            {
                try
                {
                    TcpConnection.func_74461_h(field_74504_a).stop();
                }
                catch (Throwable throwable1) { }
            }
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }
}
