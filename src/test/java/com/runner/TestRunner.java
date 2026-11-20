package com.runner;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/1_RoverMovement.feature")
@SelectClasspathResource("features/2_MultipleRovers.feature")
@SelectClasspathResource("features/3_MarsWeatherAPI.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.stepdefinitions, com.utility")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber/report.html, json:target/cucumber/report.json")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
@ConfigurationParameter(key = Constants.ANSI_COLORS_DISABLED_PROPERTY_NAME, value = "false")
public class TestRunner 
{
    // Empty – JUnit Platform Suite will pick up the annotations
	
	/*
	    To see the output visually (Not Headless) in the 'chrome' browser:
	    In the STS IDE:
		Right Click 'TestRunner.java' -> Run As -> Maven build... -> Goals: 
		mvn clean test -Dbrowser.name=chrome -Dplaywright.headless=false -Dplaywright.slowmo=1000 
	*/
}

/*
The naming conventions for the '.feature' files should be in the lowercase with underscores.
Cucumber '.feature' files run in alphabetic order by default.
Cucumber '.feature' file will be executed by Runner class. JUnit Runner Class is treated as Runner Class.
Cucumber executes the program based on Feature file but not based on Step Definition file.
Classes annotated with @RunWith(Cucumber.class) will run a Cucumber Feature.
While passing the parameter to the @RunWith() annotation, do not use single or double quotation inside.
The JUnit Runner Class should be an empty Java class without any fields or methods.
It should be an empty Java class with opening and closing braces.
'@Before' Hook executes first, then 'Background:' keyword is run before each of your Scenario (First Scenario).
Then at the end of each of your Scenario the '@After' Hook executes.
'@Before' Hook executes first, then 'Background:' keyword is run before each of your Scenario (Second Scenario).
Then at the end of each of your Scenario the '@After' Hook executes.
'@Before' Hook executes first, then 'Background:' keyword is run before each of your Scenario Outline (Considered as Third Scenario).
Then at the end of each of your Scenario Outline the '@After' Hook executes.
Cucumber Hooks @Before executes for every Scenario and Scenario Outline.
Cucumber Hooks @After executes after the execution of each endings of Scenario and Scenario Outline.
'@Before' Hook execution is based on Ascending Order. [@Before(order=0)   @Before(order=1)]
'@After' Hook execution is based on Descending Order. [@After(order=1)    @After(order=0)]
Cucumber will look for a '.feature' file on the classpath, using the same resource path as the annotated class (.class substituted by .feature).
Additional hints can be given to Cucumber by annotating the class with @CucumberOptions().
features = { "src/test/resources/features/googlesearchtext.feature "}
[OR]
features = { "src/test/resources/features/loginOrangeHRM.feature ",  "src/test/resources/features/googlesearchtext.feature "}
[OR]
features = { "src/test/resources/features "} = > It should be written as a Unix or Windows directory structure format. [We use '/' to denote the directories]
glue = { "stepdefinitions "}
[OR]
glue = { "com.stepdefinitions "}             = > It should be written as a Java class package structure format. [Step Definition is a Java class so we use '.' to denote the package]
[OR]
glue = { "com.stepdefinitions ",  "utility "}, = > In utility package we can have Cucumber Hooks.
plugin = { "pretty ",  "html:target/cucumber "}
[OR]
plugin = { "pretty ",  "html:reports/test-report "}
monochrome = true
Cucumber Hooks Flow:
Feature File:
@Before Hook:
Background:
Scenario:
@After Hook:
@Before Hook:
Background:
Scenario:
@After Hook:
@Before Hook:
Background:
Scenario Outline:
@After Hook:
Step Definitions:
TestRunner:
OUTPUT:
*/
