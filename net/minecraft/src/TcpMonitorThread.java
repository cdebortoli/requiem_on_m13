package net.minecraft.src;

class TcpMonitorThread extends Thread
{
    final TcpConnection field_74417_a;

    TcpMonitorThread(TcpConnection par1TcpConnection)
    {
        field_74417_a = par1TcpConnection;
    }

    public void run()
    {
        try
        {
            Thread.sleep(2000L);

            if (TcpConnection.func_74462_a(field_74417_a))
            {
                TcpConnection.func_74461_h(field_74417_a).interrupt();
                field_74417_a.networkShutdown("disconnect.closed", new Object[0]);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
