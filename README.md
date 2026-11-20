# MarsRover Playwright Automation

This project is an automated testing framework for the Mars Rover web application, built using 
Microsoft Playwright for Java and Cucumber for Behavior-Driven Development (BDD).

## Tech Stack

- Language: Java
- Automation Library: Microsoft Playwright (Java)
- BDD Framework: Cucumber
- Build Tool: Maven
- IDE: Spring Tool Suite (STS) / Eclipse
- CI/CD: Jenkins

## Project Structure

```text
MarsRover_Playwright_Automation/
├── src/
│   └── test/
│       ├── java/
│       │   ├── com.pages/           # Page Object Models (e.g., MarsRover_Page.java)
│       │   ├── com.runner/          # Test Runners (e.g., TestRunner.java)
│       │   ├── com.stepdefinitions/ # Cucumber Step Definitions
│       │   └── com.utility/         # Utilities (Hooks, PlaywrightManager, ConfigReader)
│       └── resources/
│           ├── features/            # Cucumber .feature files
│           └── config.properties    # Environment and browser configurations
├── Jenkinsfile                      # Jenkins CI/CD pipeline definition
├── pom.xml                          # Maven dependencies and plugins
└── README.md
```

## Configuration

The project uses a `config.properties` file located in `src/test/resources` to manage environment variables and browser settings. 

Key properties include:
- `browser.name`: The browser to use (e.g., `chrome`, `firefox`, `webkit`).
- `base.url`: The URL of the Mars Rover web application i.e., index.html
- `api.base.url`: The base URL for the NASA API i.e., https://api.nasa.gov
Note: You can override these properties at runtime using Maven command line arguments (e.g., `-Dbrowser.name=chrome`).

## CI/CD Integration (Jenkins)

This project includes a `Jenkinsfile` for automated testing via Jenkins.

Pipeline Stages:
1. Checkout: Pulls the latest code from the repository.
2. Build: Runs `mvn clean install` to download dependencies and compile code.
3. Test: Executes the Cucumber tests in headless mode.
4. Report: Archives the Cucumber HTML reports and test results.

To run the pipeline, simply create a new "Multibrand Pipeline" in Jenkins and point it to this repository.

## How to Run Tests

1. Headless Mode (Default for CI/CD)
To run tests in headless mode (no visible browser), simply execute:
```bash
mvn clean test
```

2. Visually (Non-Headless) in Chrome
To observe the automation running step-by-step in a visible Chrome browser (useful for debugging and verification), use one of the following methods in Spring Tool Suite (STS):

Method 1: Quick Run (One-time)
1. In the Project Explorer, right-click `TestRunner.java`.
2. Navigate to Run As -> Maven build...
3. In the Goals field, enter:
   ```text
   clean test -Dbrowser.name=chrome -Dplaywright.headless=false -Dplaywright.slowmo=1000
   ```
4. Click Save, then click Run.

Method 2: Saved Run Configuration (Recommended for frequent use)
1. In the Project Explorer, right-click `TestRunner.java`.
2. Navigate to Run As -> Run Configurations...
3. Double-click Maven Build to create a new configuration, and set the following:
   - Name: `MarsRover Playwright - Run Tests - Chrome (Visual + SlowMo)`
   - Base directory: `${project_loc:MarsRover_Playwright_Automation}`
   - Goals: `clean test -Dbrowser.name=chrome -Dplaywright.headless=false -Dplaywright.slowmo=1000`
4. Click Apply, then click Run.

Tip: 
The `-Dplaywright.slowmo=1000` flag adds a 1-second delay between actions, making it much easier to visually verify that locators are interacting with the correct elements.

Summary:
This project is fully equipped with a robust automation framework, CI/CD integration, and comprehensive documentation. 
It is ready for immediate use and further development.