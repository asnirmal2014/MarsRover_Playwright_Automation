package com.utility;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.util.Arrays;

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
    Lazy initialization for Playwright Page.
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
                    .setArgs(Arrays.asList("--start-maximized"))
            );
            
            // Create a new Browser Context (acts like a fresh Incognito/Private window)
            // context = browser.newContext();
            
            // Create a new Browser Context with null viewport to respect the maximized state
            context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
            
            /* Since your code explicitly uses playwright.chromium(), the --start-maximized flag is perfectly safe and will work for Chrome, Edge, and standard Chromium channels. */
            
            // Creates a new page in the browser context (a tab in the browser)
            page = context.newPage();
        }
        return page;
    }

    /*
    Closes the current page and context after each scenario.
    Keeps the browser and playwright instance alive for faster subsequent test starts.
    */
    public static void closeBrowser() 
    {
        if (page != null) 
        {
            page.close();
            page = null;
        }
        
        if (context != null) 
        {
            context.close();
            context = null;
        }
        // Note: We intentionally do NOT close the browser or playwright instance here.
        // This allows the next UI scenario to start much faster by reusing the launched browser.
    }

    /*
    Completely shuts down the Browser and Playwright Node.js driver.
    This should be called exactly once at the end of the entire test suite 
    (e.g., in an @AfterAll hook) to prevent Node.js temp file cleanup warnings.
    */
    public static void closePlaywright() 
    {
        if (browser != null) 
        {
            browser.close();
            browser = null;
        }
        
        if (playwright != null) 
        {
            playwright.close();
            playwright = null;
        }
        
        System.out.println("Playwright shut down cleanly.");
    }
}
