How to Run Tests Visually (Non-Headless) in Chrome:
To observe the automation running step-by-step in a visible Chrome browser (useful for debugging and verification), 
use one of the following methods in Spring Tool Suite (STS):

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
How to Run Tests Visually (Non-Headless) in Chrome:
To observe the automation running step-by-step in a visible Chrome browser (useful for debugging and verification), 
use one of the following methods in Spring Tool Suite (STS):

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
