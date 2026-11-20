package com.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Reusable API step definitions shared across all API feature files.
 * Uses Rest Assured for lightweight, standalone HTTP testing.
 */
public class Api_StepDefs 
{
    private RequestSpecification request;
    private Response response;

    @Given("the base URI is set to {string}")
    public void the_base_uri_is_set_to(String baseUri) 
    {
        this.request = RestAssured.given().baseUri(baseUri);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String endpoint) 
    {
        this.response = request.when().get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) 
    {
        response.then().assertThat().statusCode(equalTo(expectedStatusCode));
    }

    @And("the response body should contain {string}")
    public void the_response_body_should_contain(String expectedText) 
    {
        response.then().assertThat().body(containsString(expectedText));
        response.prettyPrint(); // Prints formatted JSON for easy debugging
    }
}
