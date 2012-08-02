package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean field_73105_c;
    private boolean field_73103_d;
    private int field_73104_e;
    private int field_73102_f;

    public DemoWorldManager(World par1World)
    {
        super(par1World);
        field_73105_c = false;
        field_73103_d = false;
        field_73104_e = 0;
        field_73102_f = 0;
    }

    public void func_73075_a()
    {
        super.func_73075_a();
        field_73102_f++;
        long l = field_73092_a.getWorldTime();
        long l1 = l / 24000L + 1L;

        if (!field_73105_c && field_73102_f > 20)
        {
            field_73105_c = true;
            field_73090_b.field_71135_a.func_72567_b(new Packet70GameEvent(5, 0));
        }

        field_73103_d = l > 0x1d6b4L;

        if (field_73103_d)
        {
            field_73104_e++;
        }

        if (l % 24000L == 500L)
        {
            if (l1 <= 6L)
            {
                field_73090_b.func_70006_a(field_73090_b.func_70004_a((new StringBuilder()).append("demo.day.").append(l1).toString(), new Object[0]));
            }
        }
        else if (l1 == 1L)
        {
            if (l == 100L)
            {
                field_73090_b.field_71135_a.func_72567_b(new Packet70GameEvent(5, 101));
            }
            else if (l == 175L)
            {
                field_73090_b.field_71135_a.func_72567_b(new Packet70GameEvent(5, 102));
            }
            else if (l == 250L)
            {
                field_73090_b.field_71135_a.func_72567_b(new Packet70GameEvent(5, 103));
            }
        }
        else if (l1 == 5L && l % 24000L == 22000L)
        {
            field_73090_b.func_70006_a(field_73090_b.func_70004_a("demo.day.warning", new Object[0]));
        }
    }

    private void func_73101_e()
    {
        if (field_73104_e > 100)
        {
            field_73090_b.func_70006_a(field_73090_b.func_70004_a("demo.reminder", new Object[0]));
            field_73104_e = 0;
        }
    }

    public void func_73074_a(int par1, int par2, int par3, int par4)
    {
        if (field_73103_d)
        {
            func_73101_e();
            return;
        }
        else
        {
            super.func_73074_a(par1, par2, par3, par4);
            return;
        }
    }

    public void func_73082_a(int par1, int par2, int par3)
    {
        if (field_73103_d)
        {
            return;
        }
        else
        {
            super.func_73082_a(par1, par2, par3);
            return;
        }
    }

    public boolean func_73084_b(int par1, int par2, int par3)
    {
        if (field_73103_d)
        {
            return false;
        }
        else
        {
            return super.func_73084_b(par1, par2, par3);
        }
    }

    public boolean func_73085_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        if (field_73103_d)
        {
            func_73101_e();
            return false;
        }
        else
        {
            return super.func_73085_a(par1EntityPlayer, par2World, par3ItemStack);
        }
    }

    public boolean func_73078_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (field_73103_d)
        {
            func_73101_e();
            return false;
        }
        else
        {
            return super.func_73078_a(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
        }
    }
}
