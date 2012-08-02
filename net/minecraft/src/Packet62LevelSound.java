package net.minecraft.src;

import java.io.*;

public class Packet62LevelSound extends Packet
{
    private String field_73579_a;
    private int field_73577_b;
    private int field_73578_c;
    private int field_73575_d;
    private float field_73576_e;
    private int field_73574_f;

    public Packet62LevelSound()
    {
        field_73578_c = 0x7fffffff;
    }

    public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        field_73578_c = 0x7fffffff;
        field_73579_a = par1Str;
        field_73577_b = (int)(par2 * 8D);
        field_73578_c = (int)(par4 * 8D);
        field_73575_d = (int)(par6 * 8D);
        field_73576_e = par8;
        field_73574_f = (int)(par9 * 63F);

        if (field_73574_f < 0)
        {
            field_73574_f = 0;
        }

        if (field_73574_f > 255)
        {
            field_73574_f = 255;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_73579_a = readString(par1DataInputStream, 32);
        field_73577_b = par1DataInputStream.readInt();
        field_73578_c = par1DataInputStream.readInt();
        field_73575_d = par1DataInputStream.readInt();
        field_73576_e = par1DataInputStream.readFloat();
        field_73574_f = par1DataInputStream.readUnsignedByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_73579_a, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_73577_b);
        par1DataOutputStream.writeInt(field_73578_c);
        par1DataOutputStream.writeInt(field_73575_d);
        par1DataOutputStream.writeFloat(field_73576_e);
        par1DataOutputStream.writeByte(field_73574_f);
    }

    public String func_73570_d()
    {
        return field_73579_a;
    }

    public double func_73572_f()
    {
        return (double)((float)field_73577_b / 8F);
    }

    public double func_73568_g()
    {
        return (double)((float)field_73578_c / 8F);
    }

    public double func_73569_h()
    {
        return (double)((float)field_73575_d / 8F);
    }

    public float func_73571_i()
    {
        return field_73576_e;
    }

    public float func_73573_j()
    {
        return (float)field_73574_f / 63F;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_72457_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 24;
    }
}
