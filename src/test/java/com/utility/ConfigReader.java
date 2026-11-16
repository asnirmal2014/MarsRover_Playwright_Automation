package com.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader 
{
    private static final Properties props = new Properties();

    // Static block to load the properties file once when the class is loaded
    static 
    {
        InputStream in = null;
    
        try 
        {
            in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
        
            if (in != null) 
            {
                props.load(in);
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Could not load config.properties: " + e.getMessage());
        } 
        finally 
        {
            // Standard practice to close the stream when not using try-with-resources
            if (in != null) 
            {
                try 
                {
                    in.close();
                } 
                catch (IOException e) 
                {
                    System.err.println("Could not close config.properties stream: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Convenience: key only (backward compatible)
     * Gets a configuration value with no default (returns empty string if not found).
     * This is a convenience method for backward compatibility.
     */
    public static String get(String key) 
    {
        return get(key, ""); 
    }

    /**
     * Gets a configuration value.
     * Priority: 1. System Property (e.g., from Maven -D flag) 
     *           2. config.properties file 
     *           3. Hardcoded default value
     */
    public static String get(String key, String defaultValue) 
    {
        // Check command-line / CI override first
        String sys = System.getProperty(key);
        
        if (sys != null && !sys.isEmpty()) 
        {
            return sys;
        }
        
        // Check config.properties file
        String file = props.getProperty(key);
        
        if (file != null && !file.isEmpty()) 
        {
            return file;
        }
        
        // Return hardcoded default
        return defaultValue;
    }
    
    /**
     * Resolves a UI base URL.
     * - If the value is already a full URL (http/https/file), use it as-is
     *   (this lets CI override with -Dui.base.url=https://... if needed).
     * - Otherwise treat it as a RELATIVE path and resolve it against the
     *   project root, producing a correct file:// URL on BOTH Windows and Linux.
     */
    public static String getResolvedFileUrl(String key, String defaultRelative) 
    {
        String value = get(key, defaultRelative);

        if (value.startsWith("http://") || value.startsWith("https://") || value.startsWith("file://")) 
        {
            return value;
        }

        String base = System.getProperty("project.basedir");
        
        if (base == null || base.isEmpty()) 
        {
            base = System.getProperty("user.dir");
        }

        String abs = new File(base, value).getAbsolutePath().replace('\\', '/');
        
        if (!abs.startsWith("/")) 
        {
            abs = "/" + abs;
        }
        
        return "file://" + abs; // -> file:///D:/... on Windows, file:///home/... on Linux
    }
}