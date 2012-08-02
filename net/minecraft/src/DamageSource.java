package net.minecraft.src;

public class DamageSource
{
    public static DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
    public static DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
    public static DamageSource lava = (new DamageSource("lava")).setFireDamage();
    public static DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
    public static DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
    public static DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor();
    public static DamageSource cactus = new DamageSource("cactus");
    public static DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
    public static DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
    public static DamageSource explosion = (new DamageSource("explosion")).func_76351_m();
    public static DamageSource field_76375_l = new DamageSource("explosion");
    public static DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor();
    public static DamageSource dehydrated = (new DamageSource("dehydrated")).setDamageBypassesArmor(); //[ERKIN]
    public static DamageSource hyperthermia = (new DamageSource("hyperthermia")).setDamageBypassesArmor(); //[ERKIN]
    public static DamageSource hypothermia = (new DamageSource("hypothermia")).setDamageBypassesArmor(); //[ERKIN]

    /** This kind of damage can be blocked or not. */
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private float hungerDamage;
    private float thirstDamage; //[ERKIN]
	private float fatigueDamage; //[ERKIN]

    /** This kind of damage is based on fire or not. */
    private boolean fireDamage;

    /** This kind of damage is based on a projectile or not. */
    private boolean projectile;
    private boolean field_76381_t;
    public String damageType;

    public static DamageSource causeMobDamage(EntityLiving par0EntityLiving)
    {
        return new EntityDamageSource("mob", par0EntityLiving);
    }

    /**
     * returns an EntityDamageSource of type player
     */
    public static DamageSource causePlayerDamage(EntityPlayer par0EntityPlayer)
    {
        return new EntityDamageSource("player", par0EntityPlayer);
    }

    /**
     * returns EntityDamageSourceIndirect of an arrow
     */
    public static DamageSource causeArrowDamage(EntityArrow par0EntityArrow, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity)).setProjectile();
    }

    /**
     * returns EntityDamageSourceIndirect of a fireball
     */
    public static DamageSource causeFireballDamage(EntityFireball par0EntityFireball, Entity par1Entity)
    {
        if (par1Entity == null)
        {
            return (new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball)).setFireDamage().setProjectile();
        }
        else
        {
            return (new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity)).setFireDamage().setProjectile();
        }
    }

    public static DamageSource causeThrownDamage(Entity par0Entity, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity)).setProjectile();
    }

    public static DamageSource causeIndirectMagicDamage(Entity par0Entity, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity)).setDamageBypassesArmor();
    }

    /**
     * Returns true if the damage is projectile based.
     */
    public boolean isProjectile()
    {
        return projectile;
    }

    /**
     * Define the damage type as projectile based.
     */
    public DamageSource setProjectile()
    {
        projectile = true;
        return this;
    }

    public boolean isUnblockable()
    {
        return isUnblockable;
    }

    /**
     * How much satiate(food) is consumed by this DamageSource
     */
    public float getHungerDamage()
    {
        return hungerDamage;
    }

    public boolean canHarmInCreative()
    {
        return isDamageAllowedInCreativeMode;
    }

    protected DamageSource(String par1Str)
    {
        isUnblockable = false;
        isDamageAllowedInCreativeMode = false;
        hungerDamage = 0.09375F; // [ERKIN]
        thirstDamage = 0.375F; //[ERKIN]
		fatigueDamage = 0.09375F; // [ERKIN]
        damageType = par1Str;
    }

    public Entity getSourceOfDamage()
    {
        return getEntity();
    }

    public Entity getEntity()
    {
        return null;
    }

    protected DamageSource setDamageBypassesArmor()
    {
        isUnblockable = true;
        hungerDamage = 0.0F;
        thirstDamage = 0.0F; //[ERKIN]
		fatigueDamage = 0.0F; // [ERKIN]
        return this;
    }

    protected DamageSource setDamageAllowedInCreativeMode()
    {
        isDamageAllowedInCreativeMode = true;
        return this;
    }

    /**
     * Define the damage type as fire based.
     */
    protected DamageSource setFireDamage()
    {
        fireDamage = true;
        return this;
    }

    public String func_76360_b(EntityPlayer par1EntityPlayer)
    {
        return StatCollector.translateToLocalFormatted((new StringBuilder()).append("death.").append(damageType).toString(), new Object[]
                {
                    par1EntityPlayer.username
                });
    }

    /**
     * Returns true if the damage is fire based.
     */
    public boolean fireDamage()
    {
        return fireDamage;
    }

    /**
     * Return the name of damage type.
     */
    public String getDamageType()
    {
        return damageType;
    }

    public DamageSource func_76351_m()
    {
        field_76381_t = true;
        return this;
    }

    public boolean func_76350_n()
    {
        return field_76381_t;
    }
	
	/**
    * How much satiate(water) is consumed by this DamageSource
    */
    //[ERKIN]
    public float getThirstDamage()
    {
        return thirstDamage;
    }
	// [ERKIN}
	public float getFatigueDamage()
	{
		return fatigueDamage;
	}
	
}
