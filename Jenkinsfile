pipeline 
{
    agent any

    // Define the tools Jenkins should use. 
    // NOTE: The names 'jdk-21' and 'maven-3.9' must match what your Jenkins admin 
    // has configured in "Manage Jenkins" -> "Global Tool Configuration".
    tools 
    {
        jdk 'jdk-21'
        maven 'maven-3.9'
    }

    // Optional: If your Jenkins agent has Node.js configured, uncomment the line below.
    // tools 
	// {
    //     nodejs 'node-20' 
    // }

    stages 
    {
        stage('Checkout') 
        {
            steps 
            {
                // Pulls the latest code from your Git repository
                checkout scm
            }
        }

        stage('Install Playwright Dependencies') 
        {
            steps 
            {
                script 
                {
                    echo 'Installing Playwright browsers and OS dependencies...'
                    // This ensures the Jenkins agent has the Chromium binary and Linux deps
                    // If your Jenkins agent is Windows, change 'sh' to 'bat'
                    sh 'npx playwright install --with-deps chromium'
                }
            }
        }

        stage('Build and Test') 
        {
            steps 
            {
                echo 'Running Maven tests with headless mode and parallel execution...'
                // We pass -Dplaywright.headless=true to ensure it runs without a display on the Jenkins agent
                // We pass -Dplaywright.slowmo=0 to ensure maximum speed in CI
                sh 'mvn clean test -Dplaywright.headless=true -Dplaywright.slowmo=0'
            }
        }
    }

    post 
    {
        always 
        {
            echo 'Archiving test reports...'
            
            // Save the Cucumber HTML and JSON reports so you can view them in Jenkins
            archiveArtifacts artifacts: 'target/cucumber/report.html, target/cucumber/report.json', allowEmptyArchive: true
            
            // Optional: Publish the JUnit XML results to the Jenkins dashboard
            junit 'target/surefire-reports/*.xml'
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
