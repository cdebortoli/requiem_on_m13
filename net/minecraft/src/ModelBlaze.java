package net.minecraft.src;

public class ModelBlaze extends ModelBase
{
    private ModelRenderer field_78106_a[];
    private ModelRenderer field_78105_b;

    public ModelBlaze()
    {
        field_78106_a = new ModelRenderer[12];

        for (int i = 0; i < field_78106_a.length; i++)
        {
            field_78106_a[i] = new ModelRenderer(this, 0, 16);
            field_78106_a[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
        }

        field_78105_b = new ModelRenderer(this, 0, 0);
        field_78105_b.addBox(-4F, -4F, -4F, 8, 8, 8);
    }

    public int func_78104_a()
    {
        return 8;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        field_78105_b.render(par7);
        ModelRenderer amodelrenderer[] = field_78106_a;
        int i = amodelrenderer.length;

        for (int j = 0; j < i; j++)
        {
            ModelRenderer modelrenderer = amodelrenderer[j];
            modelrenderer.render(par7);
        }
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        float f = par3 * (float)Math.PI * -0.1F;

        for (int i = 0; i < 4; i++)
        {
            field_78106_a[i].rotationPointY = -2F + MathHelper.cos(((float)(i * 2) + par3) * 0.25F);
            field_78106_a[i].rotationPointX = MathHelper.cos(f) * 9F;
            field_78106_a[i].rotationPointZ = MathHelper.sin(f) * 9F;
            f += ((float)Math.PI / 2F);
        }

        f = ((float)Math.PI / 4F) + par3 * (float)Math.PI * 0.03F;

        for (int j = 4; j < 8; j++)
        {
            field_78106_a[j].rotationPointY = 2.0F + MathHelper.cos(((float)(j * 2) + par3) * 0.25F);
            field_78106_a[j].rotationPointX = MathHelper.cos(f) * 7F;
            field_78106_a[j].rotationPointZ = MathHelper.sin(f) * 7F;
            f += ((float)Math.PI / 2F);
        }

        f = 0.4712389F + par3 * (float)Math.PI * -0.05F;

        for (int k = 8; k < 12; k++)
        {
            field_78106_a[k].rotationPointY = 11F + MathHelper.cos(((float)k * 1.5F + par3) * 0.5F);
            field_78106_a[k].rotationPointX = MathHelper.cos(f) * 5F;
            field_78106_a[k].rotationPointZ = MathHelper.sin(f) * 5F;
            f += ((float)Math.PI / 2F);
        }

        field_78105_b.rotateAngleY = par4 / (180F / (float)Math.PI);
        field_78105_b.rotateAngleX = par5 / (180F / (float)Math.PI);
    }
}
