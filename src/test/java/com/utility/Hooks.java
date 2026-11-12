package com.utility;

import com.microsoft.playwright.Page;
import io.cucumber.java.After;
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
     * @Before Hook:
     * Opens a fresh browser and page before each scenario starts.
     */
    @Before
    public void setUp() 
    {
        // Triggers the lazy initialization in PlaywrightManager to open Chrome
        PlaywrightManager.getPage();
    }

    /*
     * @After Hook:
     * Closes the browser and cleans up resources after each scenario ends.
     * Takes a screenshot if the scenario fails.
     */
    @After
    public void tearDown(Scenario scenario) 
    {
        // Attach screenshot to Cucumber report if the scenario failed
        if (scenario.isFailed()) 
        {
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
}