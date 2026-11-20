package com.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/*
Only method calling happens in the Step Definition file.
Method definitions and Playwright operations happen in the Page files.
*/
public class MarsRover_Page 
{
    public Page page = null;
    
    // Playwright Locators
    private Locator plateauInput;
    private Locator startPositionInput;
    private Locator commandsInput;
    private Locator executeButton;
    private Locator resultOutput;

    public MarsRover_Page(Page page) 
    {
        this.page = page;
        
        // Initialize locators
        this.plateauInput = page.locator("#plateau");
        this.startPositionInput = page.locator("#startPos");
        this.commandsInput = page.locator("#commands");
        this.executeButton = page.locator("#executeBtn");
        this.resultOutput = page.locator("#resultOutput");
        
        // Locating by placeholder text
        // this.plateauInput = page.locator("input[placeholder='Enter plateau size']");

        // Locating by exact button text
        // this.executeButton = page.locator("button", new Page.LocatorOptions().setHasText("Execute Movement"));
    }

    public void openApplication(String baseUrl) 
    {
        page.navigate(baseUrl);
    }

    public void setPlateauSize(String size) 
    {
        plateauInput.fill(size);
    }

    public void setStartPosition(String position) 
    {
        startPositionInput.fill(position);
    }

    public void sendCommands(String commands) 
    {
        commandsInput.fill(commands);
    }

    public void clickExecute() 
    {
        executeButton.click();
    }

    public String getFinalPosition() 
    {
        return resultOutput.innerText();
    }
}
