package net.minecraft.src;

public enum EnumGameType
{
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");

    int field_77154_e;
    String field_77151_f;

    private EnumGameType(int par3, String par4Str)
    {
        field_77154_e = par3;
        field_77151_f = par4Str;
    }

    public int func_77148_a()
    {
        return field_77154_e;
    }

    public String func_77149_b()
    {
        return field_77151_f;
    }

    public void func_77147_a(PlayerCapabilities par1PlayerCapabilities)
    {
        if (this == CREATIVE)
        {
            par1PlayerCapabilities.allowFlying = true;
            par1PlayerCapabilities.isCreativeMode = true;
            par1PlayerCapabilities.disableDamage = true;
        }
        else
        {
            par1PlayerCapabilities.allowFlying = false;
            par1PlayerCapabilities.isCreativeMode = false;
            par1PlayerCapabilities.disableDamage = false;
            par1PlayerCapabilities.isFlying = false;
        }

        par1PlayerCapabilities.field_75099_e = !func_77150_c();
    }

    public boolean func_77150_c()
    {
        return this == ADVENTURE;
    }

    public boolean func_77145_d()
    {
        return this == CREATIVE;
    }

    public boolean func_77144_e()
    {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public static EnumGameType func_77146_a(int par0)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.field_77154_e == par0)
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }

    public static EnumGameType func_77142_a(String par0Str)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.field_77151_f.equals(par0Str))
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }
}
