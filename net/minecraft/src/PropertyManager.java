package net.minecraft.src;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager
{
    public static Logger field_73674_a = Logger.getLogger("Minecraft");
    private Properties field_73672_b;
    private File field_73673_c;

    public PropertyManager(File par1File)
    {
        field_73672_b = new Properties();
        field_73673_c = par1File;

        if (par1File.exists())
        {
            FileInputStream fileinputstream = null;

            try
            {
                fileinputstream = new FileInputStream(par1File);
                field_73672_b.load(fileinputstream);
            }
            catch (Exception exception)
            {
                field_73674_a.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(par1File).toString(), exception);
                func_73666_a();
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException ioexception) { }
                }
            }
        }
        else
        {
            field_73674_a.log(Level.WARNING, (new StringBuilder()).append(par1File).append(" does not exist").toString());
            func_73666_a();
        }
    }

    public void func_73666_a()
    {
        field_73674_a.log(Level.INFO, "Generating new properties file");
        func_73668_b();
    }

    public void func_73668_b()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            fileoutputstream = new FileOutputStream(field_73673_c);
            field_73672_b.store(fileoutputstream, "Minecraft server properties");
        }
        catch (Exception exception)
        {
            field_73674_a.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(field_73673_c).toString(), exception);
            func_73666_a();
        }
        finally
        {
            if (fileoutputstream != null)
            {
                try
                {
                    fileoutputstream.close();
                }
                catch (IOException ioexception) { }
            }
        }
    }

    public File func_73665_c()
    {
        return field_73673_c;
    }

    public String func_73671_a(String par1Str, String par2Str)
    {
        if (!field_73672_b.containsKey(par1Str))
        {
            field_73672_b.setProperty(par1Str, par2Str);
            func_73668_b();
        }

        return field_73672_b.getProperty(par1Str, par2Str);
    }

    public int func_73669_a(String par1Str, int par2)
    {
        try
        {
            return Integer.parseInt(func_73671_a(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            field_73672_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    public boolean func_73670_a(String par1Str, boolean par2)
    {
        try
        {
            return Boolean.parseBoolean(func_73671_a(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            field_73672_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    public void func_73667_a(String par1Str, Object par2Obj)
    {
        field_73672_b.setProperty(par1Str, (new StringBuilder()).append("").append(par2Obj).toString());
    }
}
