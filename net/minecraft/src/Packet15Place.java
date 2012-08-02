package net.minecraft.src;

import java.io.*;

public class Packet15Place extends Packet
{
    private int xPosition;
    private int yPosition;
    private int zPosition;

    /** The offset to use for block/item placement. */
    private int direction;
    private ItemStack itemStack;
    private float field_73409_f;
    private float field_73410_g;
    private float field_73416_h;

    public Packet15Place()
    {
    }

    public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, float par6, float par7, float par8)
    {
        xPosition = par1;
        yPosition = par2;
        zPosition = par3;
        direction = par4;
        itemStack = par5ItemStack;
        field_73409_f = par6;
        field_73410_g = par7;
        field_73416_h = par8;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.read();
        zPosition = par1DataInputStream.readInt();
        direction = par1DataInputStream.read();
        itemStack = readItemStack(par1DataInputStream);
        field_73409_f = (float)par1DataInputStream.read() / 16F;
        field_73410_g = (float)par1DataInputStream.read() / 16F;
        field_73416_h = (float)par1DataInputStream.read() / 16F;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.write(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.write(direction);
        writeItemStack(itemStack, par1DataOutputStream);
        par1DataOutputStream.write((int)(field_73409_f * 16F));
        par1DataOutputStream.write((int)(field_73410_g * 16F));
        par1DataOutputStream.write((int)(field_73416_h * 16F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlace(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 19;
    }

    public int func_73403_d()
    {
        return xPosition;
    }

    public int func_73402_f()
    {
        return yPosition;
    }

    public int func_73407_g()
    {
        return zPosition;
    }

    public int func_73401_h()
    {
        return direction;
    }

    public ItemStack func_73405_i()
    {
        return itemStack;
    }

    public float func_73406_j()
    {
        return field_73409_f;
    }

    public float func_73404_l()
    {
        return field_73410_g;
    }

    public float func_73408_m()
    {
        return field_73416_h;
    }
}
