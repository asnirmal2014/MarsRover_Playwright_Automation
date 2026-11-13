package com.utility;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
        // Initialize global Playwright and Browser only once (Double-checked locking)
        if (playwright == null) 
		{
            synchronized (PlaywrightManager.class) 
			{
                if (playwright == null) 
				{
                    playwright = Playwright.create();
                    
                    String browserChannel = ConfigReader.get("browser.name", "chrome");
                    int slowMoValue = Integer.parseInt(ConfigReader.get("playwright.slowmo", "0")); // 0 for parallel speed
                    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("playwright.headless", "true"));
                    
                    browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                            .setChannel(browserChannel)
                            .setSlowMo(slowMoValue)
                            .setHeadless(isHeadless)
                    );
                }
            }
        }

        // Initialize Thread-Local Context and Page for this specific test thread
        if (page.get() == null) 
		{
            BrowserContext newContext = browser.newContext();
            context.set(newContext);
            page.set(newContext.newPage());
        }
        
        return page.get();
    }

    public static void closeBrowser() 
	{
        // Close ONLY the resources belonging to the current thread
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