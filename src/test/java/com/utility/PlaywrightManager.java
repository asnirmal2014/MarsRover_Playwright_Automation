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
        // Initialize global Playwright and Browser only once (Double-checked locking)
        if (playwright == null) 
        {
			// The synchronized keyword in Java is a thread-safety modifier that ensures only one thread can execute a specific method or block of code at a time. 
        	// The synchronized keyword in Java prevents race conditions and memory consistency errors when multiple threads access shared data simultaneously.
        	// The synchronized block
        	synchronized (PlaywrightManager.class)   
            {
                if (playwright == null) 
                {
                    playwright = Playwright.create();
                    
                    // FIX: Check System properties first (for Jenkins -D flags), 
                    // then fallback to ConfigReader, then default to EMPTY STRING (bundled Chromium).
                    // Browser channel is overridable ONLY via -Dbrowser.name (local: -Dbrowser.name=chrome).
                    // In Jenkins we pass nothing -> empty -> Playwright uses the bundled Chromium from
                    // ${WORKSPACE}/.playwright-browsers. The config file can no longer poison CI.
                    String browserChannel = System.getProperty("browser.name", "").trim();	
                    
                    int slowMoValue = Integer.parseInt(System.getProperty("playwright.slowmo", ConfigReader.get("playwright.slowmo", "0"))); 
                    
                    boolean isHeadless = Boolean.parseBoolean(System.getProperty("playwright.headless", ConfigReader.get("playwright.headless", "true")));
                    
                    // Define arguments to maximize the browser window
                    List<String> args = Arrays.asList("--start-maximized");
                    
                    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                            .setSlowMo(slowMoValue)
                            .setHeadless(isHeadless)
                            .setArgs(args);
                    
                    // ONLY set channel if a specific browser is explicitly requested (e.g., local dev)
                    // If empty, Playwright automatically uses the bundled Chromium we installed in Jenkins.
                    if (!browserChannel.isEmpty()) 
                    {
                        launchOptions.setChannel(browserChannel);
                    }
                    
                    browser = playwright.chromium().launch(launchOptions);
                }
            }
        }

        // Initialize Thread-Local Context and Page for this specific test thread
        if (page.get() == null) 
        {
            // Set viewport to null so the context takes the full size of the maximized window
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