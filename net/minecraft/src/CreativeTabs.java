package net.minecraft.src;

import java.util.List;

public class CreativeTabs
{
    public static final CreativeTabs field_78032_a[] = new CreativeTabs[12];
    public static final CreativeTabs field_78030_b = new CreativeTabBlock(0, "buildingBlocks");
    public static final CreativeTabs field_78031_c = new CreativeTabDeco(1, "decorations");
    public static final CreativeTabs field_78028_d = new CreativeTabRedstone(2, "redstone");
    public static final CreativeTabs field_78029_e = new CreativeTabTransport(3, "transportation");
    public static final CreativeTabs field_78026_f = new CreativeTabMisc(4, "misc");
    public static final CreativeTabs field_78027_g = (new CreativeTabSearch(5, "search")).func_78025_a("search.png");
    public static final CreativeTabs field_78039_h = new CreativeTabFood(6, "food");
    public static final CreativeTabs field_78040_i = new CreativeTabTools(7, "tools");
    public static final CreativeTabs field_78037_j = new CreativeTabCombat(8, "combat");
    public static final CreativeTabs field_78038_k = new CreativeTabBrewing(9, "brewing");
    public static final CreativeTabs field_78035_l = new CreativeTabMaterial(10, "materials");
    public static final CreativeTabs field_78036_m = (new CreativeTabInventory(11, "inventory")).func_78025_a("survival_inv.png").func_78022_j().func_78014_h();
    private final int field_78033_n;
    private final String field_78034_o;
    private String field_78043_p;
    private boolean field_78042_q;
    private boolean field_78041_r;

    public CreativeTabs(int par1, String par2Str)
    {
        field_78043_p = "list_items.png";
        field_78042_q = true;
        field_78041_r = true;
        field_78033_n = par1;
        field_78034_o = par2Str;
        field_78032_a[par1] = this;
    }

    public int func_78021_a()
    {
        return field_78033_n;
    }

    public String func_78013_b()
    {
        return field_78034_o;
    }

    public String func_78024_c()
    {
        return StringTranslate.getInstance().translateKey((new StringBuilder()).append("itemGroup.").append(func_78013_b()).toString());
    }

    public Item func_78016_d()
    {
        return Item.itemsList[func_78012_e()];
    }

    public int func_78012_e()
    {
        return 1;
    }

    public String func_78015_f()
    {
        return field_78043_p;
    }

    public CreativeTabs func_78025_a(String par1Str)
    {
        field_78043_p = par1Str;
        return this;
    }

    public boolean func_78019_g()
    {
        return field_78041_r;
    }

    public CreativeTabs func_78014_h()
    {
        field_78041_r = false;
        return this;
    }

    public boolean func_78017_i()
    {
        return field_78042_q;
    }

    public CreativeTabs func_78022_j()
    {
        field_78042_q = false;
        return this;
    }

    public int func_78020_k()
    {
        return field_78033_n % 6;
    }

    public boolean func_78023_l()
    {
        return field_78033_n < 6;
    }

    public void func_78018_a(List par1List)
    {
        Item aitem[] = Item.itemsList;
        int i = aitem.length;

        for (int j = 0; j < i; j++)
        {
            Item item = aitem[j];

            if (item != null && item.func_77640_w() == this)
            {
                item.func_77633_a(item.shiftedIndex, this, par1List);
            }
        }
    }
}
