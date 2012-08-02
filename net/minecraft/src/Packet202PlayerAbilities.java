package net.minecraft.src;

import java.io.*;

public class Packet202PlayerAbilities extends Packet
{
    /** Disables player damage. */
    private boolean disableDamage;

    /** Indicates whether the player is flying or not. */
    private boolean isFlying;

    /** Whether or not to allow the player to fly when they double jump. */
    private boolean allowFlying;

    /**
     * Used to determine if creative mode is enabled, and therefore if items should be depleted on usage
     */
    private boolean isCreativeMode;
    private float field_73359_e;
    private float field_73357_f;

    public Packet202PlayerAbilities()
    {
        disableDamage = false;
        isFlying = false;
        allowFlying = false;
        isCreativeMode = false;
    }

    public Packet202PlayerAbilities(PlayerCapabilities par1PlayerCapabilities)
    {
        disableDamage = false;
        isFlying = false;
        allowFlying = false;
        isCreativeMode = false;
        func_73353_a(par1PlayerCapabilities.disableDamage);
        func_73349_b(par1PlayerCapabilities.isFlying);
        func_73354_c(par1PlayerCapabilities.allowFlying);
        func_73356_d(par1PlayerCapabilities.isCreativeMode);
        func_73351_a(par1PlayerCapabilities.func_75093_a());
        func_73355_b(par1PlayerCapabilities.func_75094_b());
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        byte byte0 = par1DataInputStream.readByte();
        func_73353_a((byte0 & 1) > 0);
        func_73349_b((byte0 & 2) > 0);
        func_73354_c((byte0 & 4) > 0);
        func_73356_d((byte0 & 8) > 0);
        func_73351_a((float)par1DataInputStream.readByte() / 255F);
        func_73355_b((float)par1DataInputStream.readByte() / 255F);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        byte byte0 = 0;

        if (func_73352_d())
        {
            byte0 |= 1;
        }

        if (func_73350_f())
        {
            byte0 |= 2;
        }

        if (func_73348_g())
        {
            byte0 |= 4;
        }

        if (func_73346_h())
        {
            byte0 |= 8;
        }

        par1DataOutputStream.writeByte(byte0);
        par1DataOutputStream.writeByte((int)(field_73359_e * 255F));
        par1DataOutputStream.writeByte((int)(field_73357_f * 255F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlayerAbilities(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2;
    }

    public boolean func_73352_d()
    {
        return disableDamage;
    }

    public void func_73353_a(boolean par1)
    {
        disableDamage = par1;
    }

    public boolean func_73350_f()
    {
        return isFlying;
    }

    public void func_73349_b(boolean par1)
    {
        isFlying = par1;
    }

    public boolean func_73348_g()
    {
        return allowFlying;
    }

    public void func_73354_c(boolean par1)
    {
        allowFlying = par1;
    }

    public boolean func_73346_h()
    {
        return isCreativeMode;
    }

    public void func_73356_d(boolean par1)
    {
        isCreativeMode = par1;
    }

    public float func_73347_i()
    {
        return field_73359_e;
    }

    public void func_73351_a(float par1)
    {
        field_73359_e = par1;
    }

    public void func_73355_b(float par1)
    {
        field_73357_f = par1;
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
