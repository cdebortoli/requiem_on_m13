package net.minecraft.src;

import java.util.*;

class GuiSlotStatsItem extends GuiSlotStats
{
    final GuiStats field_77269_a;

    public GuiSlotStatsItem(GuiStats par1GuiStats)
    {
        super(par1GuiStats);
        field_77269_a = par1GuiStats;
        field_77266_h = new ArrayList();
        Iterator iterator = StatList.itemStats.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            StatCrafting statcrafting = (StatCrafting)iterator.next();
            boolean flag = false;
            int i = statcrafting.getItemID();

            if (GuiStats.getStatsFileWriter(par1GuiStats).writeStat(statcrafting) > 0)
            {
                flag = true;
            }
            else if (StatList.objectBreakStats[i] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectBreakStats[i]) > 0)
            {
                flag = true;
            }
            else if (StatList.objectCraftStats[i] != null && GuiStats.getStatsFileWriter(par1GuiStats).writeStat(StatList.objectCraftStats[i]) > 0)
            {
                flag = true;
            }

            if (flag)
            {
                field_77266_h.add(statcrafting);
            }
        }
        while (true);

        field_77267_i = new SorterStatsItem(this, par1GuiStats);
    }

    protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator)
    {
        super.func_77222_a(par1, par2, par3Tessellator);

        if (field_77262_g == 0)
        {
            GuiStats.drawSprite(field_77269_a, ((par1 + 115) - 18) + 1, par2 + 1 + 1, 72, 18);
        }
        else
        {
            GuiStats.drawSprite(field_77269_a, (par1 + 115) - 18, par2 + 1, 72, 18);
        }

        if (field_77262_g == 1)
        {
            GuiStats.drawSprite(field_77269_a, ((par1 + 165) - 18) + 1, par2 + 1 + 1, 18, 18);
        }
        else
        {
            GuiStats.drawSprite(field_77269_a, (par1 + 165) - 18, par2 + 1, 18, 18);
        }

        if (field_77262_g == 2)
        {
            GuiStats.drawSprite(field_77269_a, ((par1 + 215) - 18) + 1, par2 + 1 + 1, 36, 18);
        }
        else
        {
            GuiStats.drawSprite(field_77269_a, (par1 + 215) - 18, par2 + 1, 36, 18);
        }
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        StatCrafting statcrafting = func_77257_d(par1);
        int i = statcrafting.getItemID();
        GuiStats.drawItemSprite(field_77269_a, par2 + 40, par3, i);
        func_77260_a((StatCrafting)StatList.objectBreakStats[i], par2 + 115, par3, par1 % 2 == 0);
        func_77260_a((StatCrafting)StatList.objectCraftStats[i], par2 + 165, par3, par1 % 2 == 0);
        func_77260_a(statcrafting, par2 + 215, par3, par1 % 2 == 0);
    }

    protected String func_77258_c(int par1)
    {
        if (par1 == 1)
        {
            return "stat.crafted";
        }

        if (par1 == 2)
        {
            return "stat.used";
        }
        else
        {
            return "stat.depleted";
        }
    }
}
