package com.utility;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/*
PlaywrightManager manages the Playwright browser lifecycle.
Browser, BrowserContext and Page are created only when required.
BrowserContext acts like a fresh Incognito/Private window.
*/
public class PlaywrightManager 
{
    private static Playwright playwright = null;
    private static Browser browser = null;
    private static BrowserContext context = null;
    private static Page page = null;

    /*
     * Lazy initialization for Playwright Page.
     */
    public static Page getPage() 
    {
        if (page == null) 
        {
            playwright = Playwright.create();
            
            // Read browser channel and slowMo from config.properties
            String browserChannel = ConfigReader.get("browser.name", "chrome");
            int slowMoValue = Integer.parseInt(ConfigReader.get("playwright.slowmo", "1000"));
            boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("playwright.headless", "false"));
            
            // Launch browser using the configured channel and options
            browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setChannel(browserChannel)
                    .setSlowMo(slowMoValue)
                    .setHeadless(isHeadless)
            );
            
            // Create a new Browser Context (acts like a fresh Incognito/Private window)
            context = browser.newContext();
            
            // Creates a new page in the browser context (a tab in the browser)
            page = context.newPage();
        }
        return page;
    }

    public static void closeBrowser() 
    {
        if (context != null) 
        {
            context.close();
        }
        if (browser != null) 
        {
            browser.close();
        }
        if (playwright != null) 
        {
            playwright.close();
        }
        page = null;
        context = null;
    }
}