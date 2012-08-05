package net.minecraft.src;

import java.io.*;

public class Packet240UpdateRequiem extends Packet
{
	public int water;
	public int energy;
	public boolean isCold;
	public boolean isHot;
	public boolean isInHypothermia;
	public boolean isInHyperthermia;

    public Packet240UpdateRequiem()
    {
    }

    public Packet240UpdateRequiem(int waterParam, int energyParam, boolean isColdParam, boolean isHotParam, boolean isInHypothermiaParam, boolean isInHyperthermiaParam)
    {
        energy = energyParam;
		water = waterParam;
		isCold = isColdParam;
		isHot = isHotParam;
		isInHypothermia = isInHypothermiaParam;
		isInHyperthermia = isInHyperthermiaParam;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        energy = par1DataInputStream.readShort();
        water = par1DataInputStream.readShort();
		isCold = par1DataInputStream.readBoolean();
		isHot = par1DataInputStream.readBoolean();
		isInHypothermia = par1DataInputStream.readBoolean();
		isInHyperthermia = par1DataInputStream.readBoolean();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(energy);
        par1DataOutputStream.writeShort(water);
        par1DataOutputStream.writeBoolean(isCold);
        par1DataOutputStream.writeBoolean(isHot);
        par1DataOutputStream.writeBoolean(isInHypothermia);
        par1DataOutputStream.writeBoolean(isInHyperthermia);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleUpdateRequiem(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 20;
    }

    public boolean func_73278_e()
    {
        return true;
    }

    public boolean func_73268_a(Packet par1Packet)
    {
        return true;
    }
}
