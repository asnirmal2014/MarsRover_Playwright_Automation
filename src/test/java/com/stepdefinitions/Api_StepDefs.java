package com.stepdefinitions;

import org.junit.jupiter.api.Assertions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/*
Only method calling happens in the Step Definition file.
Method definitions and Rest Assured operations happen here.
*/
public class Api_StepDefs 
{
    private String baseUri = null;
    private Response response = null;

    @Given("the base URI is set to {string}")
    public void theBaseUriIsSetTo(String uri) 
    {
        this.baseUri = uri;
        RestAssured.baseURI = uri;
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) 
    {
        response = RestAssured.given()
                              .when()
                              .get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) 
    {
        int actualStatusCode = response.getStatusCode();
        Assertions.assertEquals(
            expectedStatusCode, 
            actualStatusCode, 
            "The API status code is incorrect!"
        );
    }

    @Then("the response body should contain {string}")
    public void theResponseBodyShouldContain(String key) 
    {
        boolean hasKey = response.jsonPath().get(key) != null;
        Assertions.assertTrue(
            hasKey, 
            "The response body does not contain the key: " + key
        );
    }
}