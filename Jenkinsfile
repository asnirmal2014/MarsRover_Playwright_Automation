pipeline 
{
    agent 
	{ 
		label 'windows' 
	}

    tools 
	{
        jdk 'jdk-21'
        maven 'maven-3.9'
        // nodejs 'node-24'   // remove unless actually used
    }

    options 
	{
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
    }

    environment 
	{
        PLAYWRIGHT_BROWSERS_PATH = "${WORKSPACE}/.playwright-browsers"
    }

    stages 
	{
        stage('Checkout') 
		{
            steps 
			{
                // cleanWs()   // Optional: Only add if you're debugging. Wipes workspace and the browser cache
				echo 'Checking out latest code from Git repository'
                checkout scm
            }
        }

        stage('Install Playwright Dependencies') 
		{
            steps 
			{
                echo 'Installing Playwright bundled Chromium...'
                bat 'mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"'
            }
        }

        stage('Build and Test') 
		{
            steps 
			{
                echo 'Running Maven tests (headless, no slowmo)...'
                bat 'mvn clean test -Dplaywright.headless=true -Dplaywright.slowmo=0'
            }
        }
    }

    post 
	{
        always 
		{
            echo 'Archiving test reports...'
            archiveArtifacts artifacts: 'target/cucumber/report.html, target/cucumber/report.json', allowEmptyArchive: true
            junit allowEmptyResults: true,  testResults: 'target/surefire-reports/*.xml'
        }
        
		success 
		{
            echo 'Pipeline completed successfully!'
        }
	
	    failure 
		{
            echo 'Pipeline failed. Check the console output and archived reports.'
        }
    }
	
/*
Legitimate uses of script { }:
------------------------------
Use the script { } block inside a Declarative Jenkins Pipeline whenever you need to run Scripted Pipeline (Groovy) logic that the standard Declarative syntax does not support.
Declarative Pipelines are meant to be simple and structured, so they intentionally restrict complex programming constructs. Adding a script block inside a steps section acts as an "escape hatch" for advanced logic.

	
Legitimate uses of `script { }' block inside a Declarative Jenkins Pipeline:

1. Variables that need to be computed at runtime

```groovy
steps 
{
    script 
	{
        def branch = env.BRANCH_NAME.toLowerCase()
        def tag = branch.replaceAll('/', '-')
        env.IMAGE_TAG = "${tag}-${env.BUILD_NUMBER}"
    }
    
	bat "docker build -t myapp:${env.IMAGE_TAG} ."
}
```

You can't do `def x = ...` directly inside `steps { }` — you need `script { }`.


2. `if / else` or `try / catch` around steps

Declarative has `when { }` for stage-level conditions, but for in-step branching you need `script`:

```groovy
steps 
{
    script 
	{
        if (env.BRANCH_NAME == 'main') 
		{
            bat 'mvn deploy'
        } 
		else 
		{
            bat 'mvn verify'
        }
    }
}
```

```groovy
steps 
{
    script 
	{
        try 
		{
            bat 'mvn test'
        } 
		catch (Exception e) 
		{
            echo "Tests failed: ${e.message}"
            currentBuild.result = 'UNSTABLE'
        }
    }
}
```

3. Loops over dynamic lists

```groovy
steps 
{
    script 
	{
        def browsers = ['chromium', 'firefox', 'webkit']
        
		for (b in browsers) 
		{
            bat "mvn exec:java -D exec.args=\"install ${b}\""
        }
    }
}
```

Declarative has no `for` loop. You either use `script { }` or a `matrix { }` (which is often cleaner for this exact case).

4. Looping over a file / parsing output

```groovy
steps 
{
    script 
	{
        def failed = bat(returnStdout: true, script: 'type failures.txt').trim()failed.split('\n')
		.each { line ->
            echo "FAILED: ${line}"
        }
    }
}
```

5. `parallel` with dynamically generated branches

```groovy
steps 
{
    script 
	{
        def branches = [:]
        ['smoke', 'regression'].each { suite ->
            branches[suite] = { bat "mvn test -Dsuite=${suite}" }
        }
        parallel branches
    }
}
```

(For static branches, Declarative's `parallel { }` stage is preferred.)

6. Defining helper functions / closures

```groovy
steps 
{
    script 
	{
        def runSuite = { name ->
            bat "mvn test -Dsuite=${name}"
        }
        runSuite('smoke')
        runSuite('regression')
    }
}
```

7. Accessing `currentBuild` properties programmatically

```groovy
steps 
{
    script 
	{
        currentBuild.description = "Branch: ${env.BRANCH_NAME}"
        currentBuild.result = 'SUCCESS'
    }
}
```
*/	
	
}