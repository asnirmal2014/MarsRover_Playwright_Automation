package com.utility;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/*
Cucumber Hooks manage the setup and teardown for every Scenario.
@Before executes before every Scenario.
@After executes after every Scenario.
*/
public class Hooks 
{
    /*
    @Before Hook:
    Opens a fresh browser and page before each scenario starts.
    */
	// "~@api" = run ONLY for scenarios that do NOT have the @api tag
    @Before("~@api")
    public void setUp() 
    {
        // Triggers the lazy initialization in PlaywrightManager to open Chrome
        PlaywrightManager.getPage();
    }

    /*
    @After Hook:
    Closes the browser and cleans up resources after each scenario ends.
    Takes a screenshot if the scenario fails.
    */
    @After("~@api")
    public void tearDown(Scenario scenario) 
    {
        // Attach screenshot to Cucumber report if the scenario failed
        if (scenario.isFailed()) 
        {
            System.out.println("##### Failed Scenario: " + scenario.getName());

            Page currentPage = PlaywrightManager.getPage();
            
            if (currentPage != null) 
            {
                byte[] screenshot = currentPage.screenshot();
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            }
        }
        
        // Close the browser context and browser instance to ensure a clean state for the next test
        PlaywrightManager.closeBrowser();
    }
    
    /*
    @AfterAll Hook:
    Cleanly shuts down the Playwright Node.js driver exactly once at the 
    end of the entire test suite to prevent temp file cleanup warnings.
    */
    @AfterAll
    public static void globalTearDown() 
    {
        // Call whatever method you have in PlaywrightManager that closes the Playwright instance itself
        PlaywrightManager.closePlaywright(); 
        // (If your manager doesn't have this, you can skip this step and just ignore the warning)
    }
}

