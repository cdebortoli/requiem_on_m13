package net.minecraft.src;

import java.io.*;

public class Packet2ClientProtocol extends Packet
{
    private int field_73458_a;
    private String field_73456_b;
    private String field_73457_c;
    private int field_73455_d;

    public Packet2ClientProtocol()
    {
    }

    public Packet2ClientProtocol(int par1, String par2Str, String par3Str, int par4)
    {
        field_73458_a = par1;
        field_73456_b = par2Str;
        field_73457_c = par3Str;
        field_73455_d = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_73458_a = par1DataInputStream.readByte();
        field_73456_b = readString(par1DataInputStream, 16);
        field_73457_c = readString(par1DataInputStream, 255);
        field_73455_d = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(field_73458_a);
        writeString(field_73456_b, par1DataOutputStream);
        writeString(field_73457_c, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_73455_d);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_72500_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + 2 * field_73456_b.length();
    }

    public int func_73453_d()
    {
        return field_73458_a;
    }

    public String func_73454_f()
    {
        return field_73456_b;
    }
}
