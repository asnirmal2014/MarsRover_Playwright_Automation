package com.utility;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.util.Arrays;
import java.util.List;

public class PlaywrightManager 
{
    // Global instances (shared across all threads to save memory and startup time)
    private static Playwright playwright;
    private static Browser browser;
    
    // ThreadLocal instances (each parallel test thread gets its own isolated copy)
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public static Page getPage() 
    {
        if (playwright == null) 
        {
            synchronized (PlaywrightManager.class)   
            {
                if (playwright == null) 
                {
                    playwright = Playwright.create();
                    
                    // Read from System property (set by Maven pom.xml or Jenkins -D flags).
                    // Defaults to empty string, which tells Playwright to use bundled Chromium.
                    String browserChannel = System.getProperty("browser.name", "").trim();
                    
                    int slowMoValue = Integer.parseInt(System.getProperty("playwright.slowmo", "0")); 
                    boolean isHeadless = Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
                    
                    List<String> args = Arrays.asList("--start-maximized");
                    
                    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                            .setSlowMo(slowMoValue)
                            .setHeadless(isHeadless)
                            .setArgs(args);
                    
                    // Only apply channel if explicitly requested (e.g., local dev overrides it)                    
                    if (!browserChannel.isEmpty()) 
                    {
                        launchOptions.setChannel(browserChannel);
                    }

                    browser = playwright.chromium().launch(launchOptions);
                }
            }
        }

        if (page.get() == null) 
        {
            BrowserContext newContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
            context.set(newContext);
            page.set(newContext.newPage());
        }
        
        return page.get();
    }

    public static void closeBrowser() 
    {
        // Close only the resources belonging to the current thread
        if (page.get() != null) 
        {
            page.get().close();
            page.remove(); // Clean up ThreadLocal to prevent memory leaks
        }
        
        if (context.get() != null) 
        {
            context.get().close();
            context.remove();
        }
    }

    public static void closePlaywright() 
    {
        // Shut down global resources at the very end of the suite
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

