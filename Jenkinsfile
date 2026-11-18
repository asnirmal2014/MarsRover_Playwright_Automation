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
        timeout(time: 30, unit: 'MINUTES')
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
}