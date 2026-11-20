package com.stepdefinitions;

import org.junit.jupiter.api.Assertions;
import com.pages.MarsRover_Page;
import com.utility.ConfigReader;
import com.utility.PlaywrightManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/*
Only method calling happens in the Step Definition file.
Method definitions and Playwright operations happen in the Page files.
*/
public class MarsRover_StepDefs 
{
    private MarsRover_Page marsRoverPage = null;

    /*
     * Lazy initialization:
     * MarsRover_Page is created only when required.
     */
    private MarsRover_Page marsRoverPage() 
    {
        if (marsRoverPage == null) 
        {
            marsRoverPage = new MarsRover_Page(PlaywrightManager.getPage());
        }
        return marsRoverPage;
    }

    @Given("the Mars Rover web application is open")
    public void theMarsRoverWebApplicationIsOpen() 
    {
        // marsRoverPage().openApplication(ConfigReader.get("ui.base.url"));
        marsRoverPage().openApplication(ConfigReader.getResolvedFileUrl("ui.base.url", "index.html"));
    }

    @Given("the plateau size is set to {string}")
    public void thePlateauSizeIsSetTo(String size) 
    {
        marsRoverPage().setPlateauSize(size);
    }

    @When("I set the starting position to {string}")
    public void iSetTheStartingPositionTo(String position) 
    {
        marsRoverPage().setStartPosition(position);
    }

    @When("I send the commands {string}")
    public void iSendTheCommands(String commands) 
    {
        marsRoverPage().sendCommands(commands);
    }

    @When("I click the Execute button")
    public void iClickTheExecuteButton() 
    {
        marsRoverPage().clickExecute();
    }

    @Then("the final position should be {string}")
    public void theFinalPositionShouldBe(String expectedPosition) 
    {
        String actualPosition = marsRoverPage().getFinalPosition();
        Assertions.assertEquals(
            expectedPosition, 
            actualPosition, 
            "The rover final position is incorrect!"
        );
    }
}

