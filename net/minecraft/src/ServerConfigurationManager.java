package net.minecraft.src;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public abstract class ServerConfigurationManager
{
    private static final SimpleDateFormat field_72403_e = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    public static final Logger field_72406_a = Logger.getLogger("Minecraft");
    private final MinecraftServer field_72400_f;
    public final List field_72404_b = new ArrayList();
    private final BanList field_72401_g = new BanList(new File("banned-players.txt"));
    private final BanList field_72413_h = new BanList(new File("banned-ips.txt"));
    private Set field_72414_i;
    private Set field_72411_j;
    private IPlayerFileData field_72412_k;
    private boolean field_72409_l;
    protected int field_72405_c;
    protected int field_72402_d;
    private EnumGameType field_72410_m;
    private boolean field_72407_n;
    private int field_72408_o;

    public ServerConfigurationManager(MinecraftServer par1MinecraftServer)
    {
        field_72414_i = new HashSet();
        field_72411_j = new HashSet();
        field_72408_o = 0;
        field_72400_f = par1MinecraftServer;
        field_72401_g.func_73708_a(false);
        field_72413_h.func_73708_a(false);
        field_72405_c = 8;
    }

    public void func_72355_a(NetworkManager par1NetworkManager, EntityPlayerMP par2EntityPlayerMP)
    {
        func_72380_a(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(field_72400_f.func_71218_a(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.field_71134_c.func_73080_a((WorldServer)par2EntityPlayerMP.worldObj);
        String s = "local";

        if (par1NetworkManager.func_74430_c() != null)
        {
            s = par1NetworkManager.func_74430_c().toString();
        }

        field_72406_a.info((new StringBuilder()).append(par2EntityPlayerMP.username).append("[").append(s).append("] logged in with entity id ").append(par2EntityPlayerMP.entityId).append(" at (").append(par2EntityPlayerMP.posX).append(", ").append(par2EntityPlayerMP.posY).append(", ").append(par2EntityPlayerMP.posZ).append(")").toString());
        WorldServer worldserver = field_72400_f.func_71218_a(par2EntityPlayerMP.dimension);
        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        func_72381_a(par2EntityPlayerMP, null, worldserver);
        NetServerHandler netserverhandler = new NetServerHandler(field_72400_f, par1NetworkManager, par2EntityPlayerMP);
        netserverhandler.func_72567_b(new Packet1Login(par2EntityPlayerMP.entityId, worldserver.getWorldInfo().getTerrainType(), par2EntityPlayerMP.field_71134_c.func_73081_b(), worldserver.getWorldInfo().isHardcoreModeEnabled(), worldserver.worldProvider.worldType, worldserver.difficultySetting, worldserver.getHeight(), func_72352_l()));
        netserverhandler.func_72567_b(new Packet6SpawnPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));
        netserverhandler.func_72567_b(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
        func_72354_b(par2EntityPlayerMP, worldserver);
        func_72384_a(new Packet3Chat((new StringBuilder()).append("\247e").append(par2EntityPlayerMP.username).append(" joined the game.").toString()));
        func_72377_c(par2EntityPlayerMP);
        netserverhandler.func_72569_a(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        field_72400_f.func_71212_ac().func_71745_a(netserverhandler);
        netserverhandler.func_72567_b(new Packet4UpdateTime(worldserver.getWorldTime()));

        if (field_72400_f.func_71202_P().length() > 0)
        {
            par2EntityPlayerMP.func_71115_a(field_72400_f.func_71202_P(), field_72400_f.func_71227_R());
        }

        PotionEffect potioneffect;

        for (Iterator iterator = par2EntityPlayerMP.getActivePotionEffects().iterator(); iterator.hasNext(); netserverhandler.func_72567_b(new Packet41EntityEffect(par2EntityPlayerMP.entityId, potioneffect)))
        {
            potioneffect = (PotionEffect)iterator.next();
        }

        par2EntityPlayerMP.func_71116_b();
    }

    public void func_72364_a(WorldServer par1ArrayOfWorldServer[])
    {
        field_72412_k = par1ArrayOfWorldServer[0].getSaveHandler().func_75756_e();
    }

    public void func_72375_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        WorldServer worldserver = par1EntityPlayerMP.func_71121_q();

        if (par2WorldServer != null)
        {
            par2WorldServer.func_73040_p().func_72695_c(par1EntityPlayerMP);
        }

        worldserver.func_73040_p().func_72683_a(par1EntityPlayerMP);
        worldserver.field_73059_b.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
    }

    public int func_72372_a()
    {
        return PlayerManager.func_72686_a(func_72395_o());
    }

    public void func_72380_a(EntityPlayerMP par1EntityPlayerMP)
    {
        NBTTagCompound nbttagcompound = field_72400_f.field_71305_c[0].getWorldInfo().getPlayerNBTTagCompound();

        if (par1EntityPlayerMP.func_70005_c_().equals(field_72400_f.func_71214_G()) && nbttagcompound != null)
        {
            par1EntityPlayerMP.readFromNBT(nbttagcompound);
        }
        else
        {
            field_72412_k.func_75752_b(par1EntityPlayerMP);
        }
    }

    protected void func_72391_b(EntityPlayerMP par1EntityPlayerMP)
    {
        field_72412_k.func_75753_a(par1EntityPlayerMP);
    }

    public void func_72377_c(EntityPlayerMP par1EntityPlayerMP)
    {
        func_72384_a(new Packet201PlayerInfo(par1EntityPlayerMP.username, true, 1000));
        field_72404_b.add(par1EntityPlayerMP);
        WorldServer worldserver;

        for (worldserver = field_72400_f.func_71218_a(par1EntityPlayerMP.dimension); !worldserver.getCollidingBoundingBoxes(par1EntityPlayerMP, par1EntityPlayerMP.boundingBox).isEmpty(); par1EntityPlayerMP.setPosition(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY + 1.0D, par1EntityPlayerMP.posZ)) { }

        worldserver.spawnEntityInWorld(par1EntityPlayerMP);
        func_72375_a(par1EntityPlayerMP, null);
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_72404_b.iterator(); iterator.hasNext(); par1EntityPlayerMP.field_71135_a.func_72567_b(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.field_71138_i)))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_72358_d(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_71121_q().func_73040_p().func_72685_d(par1EntityPlayerMP);
    }

    public void func_72367_e(EntityPlayerMP par1EntityPlayerMP)
    {
        func_72391_b(par1EntityPlayerMP);
        WorldServer worldserver = par1EntityPlayerMP.func_71121_q();
        worldserver.setEntityDead(par1EntityPlayerMP);
        worldserver.func_73040_p().func_72695_c(par1EntityPlayerMP);
        field_72404_b.remove(par1EntityPlayerMP);
        func_72384_a(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
    }

    public String func_72399_a(SocketAddress par1SocketAddress, String par2Str)
    {
        if (field_72401_g.func_73704_a(par2Str))
        {
            BanEntry banentry = (BanEntry)field_72401_g.func_73712_c().get(par2Str);
            String s1 = (new StringBuilder()).append("You are banned from this server!\nReason: ").append(banentry.func_73686_f()).toString();

            if (banentry.func_73680_d() != null)
            {
                s1 = (new StringBuilder()).append(s1).append("\nYour ban will be removed on ").append(field_72403_e.format(banentry.func_73680_d())).toString();
            }

            return s1;
        }

        if (!func_72370_d(par2Str))
        {
            return "You are not white-listed on this server!";
        }

        String s = par1SocketAddress.toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));

        if (field_72413_h.func_73704_a(s))
        {
            BanEntry banentry1 = (BanEntry)field_72413_h.func_73712_c().get(s);
            String s2 = (new StringBuilder()).append("Your IP address is banned from this server!\nReason: ").append(banentry1.func_73686_f()).toString();

            if (banentry1.func_73680_d() != null)
            {
                s2 = (new StringBuilder()).append(s2).append("\nYour ban will be removed on ").append(field_72403_e.format(banentry1.func_73680_d())).toString();
            }

            return s2;
        }

        if (field_72404_b.size() >= field_72405_c)
        {
            return "The server is full!";
        }
        else
        {
            return null;
        }
    }

    public EntityPlayerMP func_72366_a(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Object obj = field_72404_b.iterator();

        do
        {
            if (!((Iterator)(obj)).hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)((Iterator)(obj)).next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        EntityPlayerMP entityplayermp1;

        for (obj = arraylist.iterator(); ((Iterator)(obj)).hasNext(); entityplayermp1.field_71135_a.func_72565_c("You logged in from another location"))
        {
            entityplayermp1 = (EntityPlayerMP)((Iterator)(obj)).next();
        }

        if (field_72400_f.func_71242_L())
        {
            obj = new DemoWorldManager(field_72400_f.func_71218_a(0));
        }
        else
        {
            obj = new ItemInWorldManager(field_72400_f.func_71218_a(0));
        }

        return new EntityPlayerMP(field_72400_f, field_72400_f.func_71218_a(0), par1Str, ((ItemInWorldManager)(obj)));
    }

    public EntityPlayerMP func_72368_a(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3)
    {
        par1EntityPlayerMP.func_71121_q().func_73039_n().func_72787_a(par1EntityPlayerMP);
        par1EntityPlayerMP.func_71121_q().func_73039_n().func_72790_b(par1EntityPlayerMP);
        par1EntityPlayerMP.func_71121_q().func_73040_p().func_72695_c(par1EntityPlayerMP);
        field_72404_b.remove(par1EntityPlayerMP);
        field_72400_f.func_71218_a(par1EntityPlayerMP.dimension).func_72973_f(par1EntityPlayerMP);
        ChunkCoordinates chunkcoordinates = par1EntityPlayerMP.getSpawnChunk();
        par1EntityPlayerMP.dimension = par2;
        Object obj;

        if (field_72400_f.func_71242_L())
        {
            obj = new DemoWorldManager(field_72400_f.func_71218_a(par1EntityPlayerMP.dimension));
        }
        else
        {
            obj = new ItemInWorldManager(field_72400_f.func_71218_a(par1EntityPlayerMP.dimension));
        }

        EntityPlayerMP entityplayermp = new EntityPlayerMP(field_72400_f, field_72400_f.func_71218_a(par1EntityPlayerMP.dimension), par1EntityPlayerMP.username, ((ItemInWorldManager)(obj)));
        entityplayermp.func_71049_a(par1EntityPlayerMP, par3);
        entityplayermp.entityId = par1EntityPlayerMP.entityId;
        entityplayermp.field_71135_a = par1EntityPlayerMP.field_71135_a;
        WorldServer worldserver = field_72400_f.func_71218_a(par1EntityPlayerMP.dimension);
        func_72381_a(entityplayermp, par1EntityPlayerMP, worldserver);

        if (chunkcoordinates != null)
        {
            ChunkCoordinates chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(field_72400_f.func_71218_a(par1EntityPlayerMP.dimension), chunkcoordinates);

            if (chunkcoordinates1 != null)
            {
                entityplayermp.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
                entityplayermp.setSpawnChunk(chunkcoordinates);
            }
            else
            {
                entityplayermp.field_71135_a.func_72567_b(new Packet70GameEvent(0, 0));
            }
        }

        worldserver.field_73059_b.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);

        for (; !worldserver.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).isEmpty(); entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) { }

        entityplayermp.field_71135_a.func_72567_b(new Packet9Respawn(entityplayermp.dimension, (byte)entityplayermp.worldObj.difficultySetting, entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.worldObj.getHeight(), entityplayermp.field_71134_c.func_73081_b()));
        ChunkCoordinates chunkcoordinates2 = worldserver.getSpawnPoint();
        entityplayermp.field_71135_a.func_72569_a(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
        entityplayermp.field_71135_a.func_72567_b(new Packet6SpawnPosition(chunkcoordinates2.posX, chunkcoordinates2.posY, chunkcoordinates2.posZ));
        func_72354_b(entityplayermp, worldserver);
        worldserver.func_73040_p().func_72683_a(entityplayermp);
        worldserver.spawnEntityInWorld(entityplayermp);
        field_72404_b.add(entityplayermp);
        entityplayermp.func_71116_b();
        return entityplayermp;
    }

    public void func_72356_a(EntityPlayerMP par1EntityPlayerMP, int par2)
    {
        int i = par1EntityPlayerMP.dimension;
        WorldServer worldserver = field_72400_f.func_71218_a(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer worldserver1 = field_72400_f.func_71218_a(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.field_71135_a.func_72567_b(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), worldserver1.getHeight(), par1EntityPlayerMP.field_71134_c.func_73081_b()));
        worldserver.func_72973_f(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        double d = par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.posZ;
        double d2 = 8D;

        if (par1EntityPlayerMP.dimension == -1)
        {
            d /= d2;
            d1 /= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else if (par1EntityPlayerMP.dimension == 0)
        {
            d *= d2;
            d1 *= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else
        {
            ChunkCoordinates chunkcoordinates = worldserver1.func_73054_j();
            d = chunkcoordinates.posX;
            par1EntityPlayerMP.posY = chunkcoordinates.posY;
            d1 = chunkcoordinates.posZ;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, 90F, 0.0F);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }

        if (i != 1)
        {
            d = MathHelper.clamp_int((int)d, 0xfe363d00, 0x1c9c300);
            d1 = MathHelper.clamp_int((int)d1, 0xfe363d00, 0x1c9c300);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver1.spawnEntityInWorld(par1EntityPlayerMP);
                par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
                worldserver1.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
                (new Teleporter()).placeInPortal(worldserver1, par1EntityPlayerMP);
            }
        }

        par1EntityPlayerMP.setWorld(worldserver1);
        func_72375_a(par1EntityPlayerMP, worldserver);
        par1EntityPlayerMP.field_71135_a.func_72569_a(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.field_71134_c.func_73080_a(worldserver1);
        func_72354_b(par1EntityPlayerMP, worldserver1);
        func_72385_f(par1EntityPlayerMP);
        PotionEffect potioneffect;

        for (Iterator iterator = par1EntityPlayerMP.getActivePotionEffects().iterator(); iterator.hasNext(); par1EntityPlayerMP.field_71135_a.func_72567_b(new Packet41EntityEffect(par1EntityPlayerMP.entityId, potioneffect)))
        {
            potioneffect = (PotionEffect)iterator.next();
        }
    }

    public void func_72374_b()
    {
        if (++field_72408_o > 600)
        {
            field_72408_o = 0;
        }

        if (field_72408_o < field_72404_b.size())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)field_72404_b.get(field_72408_o);
            func_72384_a(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.field_71138_i));
        }
    }

    public void func_72384_a(Packet par1Packet)
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_72404_b.iterator(); iterator.hasNext(); entityplayermp.field_71135_a.func_72567_b(par1Packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_72396_a(Packet par1Packet, int par2)
    {
        Iterator iterator = field_72404_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.dimension == par2)
            {
                entityplayermp.field_71135_a.func_72567_b(par1Packet);
            }
        }
        while (true);
    }

    public String func_72398_c()
    {
        String s = "";

        for (int i = 0; i < field_72404_b.size(); i++)
        {
            if (i > 0)
            {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }

            s = (new StringBuilder()).append(s).append(((EntityPlayerMP)field_72404_b.get(i)).username).toString();
        }

        return s;
    }

    public String[] func_72369_d()
    {
        String as[] = new String[field_72404_b.size()];

        for (int i = 0; i < field_72404_b.size(); i++)
        {
            as[i] = ((EntityPlayerMP)field_72404_b.get(i)).username;
        }

        return as;
    }

    public BanList func_72390_e()
    {
        return field_72401_g;
    }

    public BanList func_72363_f()
    {
        return field_72413_h;
    }

    public void func_72386_b(String par1Str)
    {
        field_72414_i.add(par1Str.toLowerCase());
    }

    public void func_72360_c(String par1Str)
    {
        field_72414_i.remove(par1Str.toLowerCase());
    }

    public boolean func_72370_d(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !field_72409_l || field_72414_i.contains(par1Str) || field_72411_j.contains(par1Str);
    }

    public boolean func_72353_e(String par1Str)
    {
        return field_72414_i.contains(par1Str.trim().toLowerCase()) || field_72400_f.func_71264_H() && field_72400_f.field_71305_c[0].getWorldInfo().func_76086_u() && field_72400_f.func_71214_G().equalsIgnoreCase(par1Str) || field_72407_n;
    }

    public EntityPlayerMP func_72361_f(String par1Str)
    {
        for (Iterator iterator = field_72404_b.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                return entityplayermp;
            }
        }

        return null;
    }

    public void func_72393_a(double par1, double par3, double par5, double par7, int par9, Packet par10Packet)
    {
        func_72397_a(null, par1, par3, par5, par7, par9, par10Packet);
    }

    public void func_72397_a(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, double par8, int par10, Packet par11Packet)
    {
        Iterator iterator = field_72404_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1EntityPlayer && entityplayermp.dimension == par10)
            {
                double d = par2 - entityplayermp.posX;
                double d1 = par4 - entityplayermp.posY;
                double d2 = par6 - entityplayermp.posZ;

                if (d * d + d1 * d1 + d2 * d2 < par8 * par8)
                {
                    entityplayermp.field_71135_a.func_72567_b(par11Packet);
                }
            }
        }
        while (true);
    }

    public void func_72389_g()
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = field_72404_b.iterator(); iterator.hasNext(); func_72391_b(entityplayermp))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    public void func_72359_h(String par1Str)
    {
        field_72411_j.add(par1Str);
    }

    public void func_72379_i(String par1Str)
    {
        field_72411_j.remove(par1Str);
    }

    public Set func_72388_h()
    {
        return field_72411_j;
    }

    public Set func_72376_i()
    {
        return field_72414_i;
    }

    public void func_72362_j()
    {
    }

    public void func_72354_b(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        par1EntityPlayerMP.field_71135_a.func_72567_b(new Packet4UpdateTime(par2WorldServer.getWorldTime()));

        if (par2WorldServer.isRaining())
        {
            par1EntityPlayerMP.field_71135_a.func_72567_b(new Packet70GameEvent(1, 0));
        }
    }

    public void func_72385_f(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_71120_a(par1EntityPlayerMP.inventorySlots);
        par1EntityPlayerMP.func_71118_n();
    }

    public int func_72394_k()
    {
        return field_72404_b.size();
    }

    public int func_72352_l()
    {
        return field_72405_c;
    }

    public String[] func_72373_m()
    {
        return field_72400_f.field_71305_c[0].getSaveHandler().func_75756_e().func_75754_f();
    }

    public boolean func_72383_n()
    {
        return field_72409_l;
    }

    public void func_72371_a(boolean par1)
    {
        field_72409_l = par1;
    }

    public List func_72382_j(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_72404_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.func_71114_r().equals(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        return arraylist;
    }

    public int func_72395_o()
    {
        return field_72402_d;
    }

    public MinecraftServer func_72365_p()
    {
        return field_72400_f;
    }

    public NBTTagCompound func_72378_q()
    {
        return null;
    }

    public void func_72357_a(EnumGameType par1EnumGameType)
    {
        field_72410_m = par1EnumGameType;
    }

    private void func_72381_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World)
    {
        if (par2EntityPlayerMP != null)
        {
            par1EntityPlayerMP.field_71134_c.func_73076_a(par2EntityPlayerMP.field_71134_c.func_73081_b());
        }
        else if (field_72410_m != null)
        {
            par1EntityPlayerMP.field_71134_c.func_73076_a(field_72410_m);
        }

        par1EntityPlayerMP.field_71134_c.func_73077_b(par3World.getWorldInfo().func_76077_q());
    }

    public void func_72387_b(boolean par1)
    {
        field_72407_n = par1;
    }

    public void func_72392_r()
    {
        for (; !field_72404_b.isEmpty(); ((EntityPlayerMP)field_72404_b.get(0)).field_71135_a.func_72565_c("Server closed")) { }
    }
}
