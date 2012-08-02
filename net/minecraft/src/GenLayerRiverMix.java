package net.minecraft.src;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer field_75910_b;
    private GenLayer field_75911_c;

    public GenLayerRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer)
    {
        super(par1);
        field_75910_b = par3GenLayer;
        field_75911_c = par4GenLayer;
    }

    /**
     * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
     * argument).
     */
    public void initWorldGenSeed(long par1)
    {
        field_75910_b.initWorldGenSeed(par1);
        field_75911_c.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int ai[] = field_75910_b.getInts(par1, par2, par3, par4);
        int ai1[] = field_75911_c.getInts(par1, par2, par3, par4);
        int ai2[] = IntCache.getIntCache(par3 * par4);

        for (int i = 0; i < par3 * par4; i++)
        {
            if (ai[i] == BiomeGenBase.ocean.biomeID)
            {
                ai2[i] = ai[i];
                continue;
            }

            if (ai1[i] >= 0)
            {
                if (ai[i] == BiomeGenBase.icePlains.biomeID)
                {
                    ai2[i] = BiomeGenBase.frozenRiver.biomeID;
                    continue;
                }

                if (ai[i] == BiomeGenBase.mushroomIsland.biomeID || ai[i] == BiomeGenBase.mushroomIslandShore.biomeID)
                {
                    ai2[i] = BiomeGenBase.mushroomIslandShore.biomeID;
                }
                else
                {
                    ai2[i] = ai1[i];
                }
            }
            else
            {
                ai2[i] = ai[i];
            }
        }

        return ai2;
    }
}
