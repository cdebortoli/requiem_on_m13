package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    private final int field_72306_a;
    private final int field_72304_b;
    private final List field_72305_c = new ArrayList();
    private int field_72302_d;
    private int field_72303_e;
    private int field_72301_f;

    public AABBPool(int par1, int par2)
    {
        field_72302_d = 0;
        field_72303_e = 0;
        field_72301_f = 0;
        field_72306_a = par1;
        field_72304_b = par2;
    }

    public AxisAlignedBB func_72299_a(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        AxisAlignedBB axisalignedbb;

        if (field_72302_d >= field_72305_c.size())
        {
            axisalignedbb = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
            field_72305_c.add(axisalignedbb);
        }
        else
        {
            axisalignedbb = (AxisAlignedBB)field_72305_c.get(field_72302_d);
            axisalignedbb.setBounds(par1, par3, par5, par7, par9, par11);
        }

        field_72302_d++;
        return axisalignedbb;
    }

    public void func_72298_a()
    {
        if (field_72302_d > field_72303_e)
        {
            field_72303_e = field_72302_d;
        }

        if (field_72301_f++ == field_72306_a)
        {
            for (int i = Math.max(field_72303_e, field_72305_c.size() - field_72304_b); field_72305_c.size() > i; field_72305_c.remove(i)) { }

            field_72303_e = 0;
            field_72301_f = 0;
        }

        field_72302_d = 0;
    }

    public void func_72300_b()
    {
        field_72302_d = 0;
        field_72305_c.clear();
    }
}
