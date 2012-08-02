package net.minecraft.src;

import java.io.*;

public class Packet1Login extends Packet
{
    public int field_73561_a;
    public WorldType terrainType;
    public boolean field_73560_c;
    public EnumGameType field_73557_d;
    public int field_73558_e;

    /** The difficulty setting byte. */
    public byte difficultySetting;

    /** Defaults to 128 */
    public byte worldHeight;

    /** The maximum players. */
    public byte maxPlayers;

    public Packet1Login()
    {
        field_73561_a = 0;
    }

    public Packet1Login(int par1, WorldType par2WorldType, EnumGameType par3EnumGameType, boolean par4, int par5, int par6, int par7, int par8)
    {
        field_73561_a = 0;
        field_73561_a = par1;
        terrainType = par2WorldType;
        field_73558_e = par5;
        difficultySetting = (byte)par6;
        field_73557_d = par3EnumGameType;
        worldHeight = (byte)par7;
        maxPlayers = (byte)par8;
        field_73560_c = par4;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_73561_a = par1DataInputStream.readInt();
        String s = readString(par1DataInputStream, 16);
        terrainType = WorldType.parseWorldType(s);

        if (terrainType == null)
        {
            terrainType = WorldType.DEFAULT;
        }

        int i = par1DataInputStream.readByte();
        field_73560_c = (i & 8) == 8;
        i &= -9;
        field_73557_d = EnumGameType.func_77146_a(i);
        field_73558_e = par1DataInputStream.readByte();
        difficultySetting = par1DataInputStream.readByte();
        worldHeight = par1DataInputStream.readByte();
        maxPlayers = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(field_73561_a);
        writeString(terrainType != null ? terrainType.getWorldTypeName() : "", par1DataOutputStream);
        int i = field_73557_d.func_77148_a();

        if (field_73560_c)
        {
            i |= 8;
        }

        par1DataOutputStream.writeByte(i);
        par1DataOutputStream.writeByte(field_73558_e);
        par1DataOutputStream.writeByte(difficultySetting);
        par1DataOutputStream.writeByte(worldHeight);
        par1DataOutputStream.writeByte(maxPlayers);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleLogin(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        int i = 0;

        if (terrainType != null)
        {
            i = terrainType.getWorldTypeName().length();
        }

        return 6 + 2 * i + 4 + 4 + 1 + 1 + 1;
    }
}
