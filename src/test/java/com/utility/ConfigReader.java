package com.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
ConfigReader loads configuration from config.properties file.
Provides static methods to read property values by key.
Properties are loaded only once when the class is first accessed.
*/
public class ConfigReader 
{
    private static Properties properties = null;

    /*
     * Static block ensures properties are loaded only once at class initialization.
     */
    static 
    {
        properties = new Properties();
        
        try 
        {
            InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            
            if (inputStream != null) 
            {
                properties.load(inputStream);
                inputStream.close();
            } 
            else 
            {
                throw new RuntimeException("config.properties file not found in classpath");
            }
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /*
     * Returns the property value for the given key.
     * @param key The property key to look up
     * @return The property value as a String
     * @throws RuntimeException if the key is not found
     */
    public static String get(String key) 
    {
        String value = properties.getProperty(key);
        
        if (value == null) 
        {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    /*
     * Returns the property value for the given key, or a default value if not found.
     * @param key The property key to look up
     * @param defaultValue The value to return if key is not found
     * @return The property value as a String, or defaultValue if not found
     */
    public static String get(String key, String defaultValue) 
    {
        return properties.getProperty(key, defaultValue).trim();
    }
}