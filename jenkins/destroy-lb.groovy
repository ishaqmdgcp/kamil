pipeline {
    agent any

    environment {
    SVC_ACCOUNT_KEY = credentials('jenkins-auth')
  }
     
    stages {
      	stage('Set creds') {
            steps {
              
               sh 'echo $SVC_ACCOUNT_KEY | base64 -d > ./jenkins/jenkins.json'
		sh 'pwd' 
               
            }
        }

	
	stage('Auth-Project') {
	 steps {
		 dir('jenkins')
		 {
    
        sh 'gcloud auth activate-service-account jenkins@gcpkole.iam.gserviceaccount.com --key-file=jenkins.json'
    }
    }
	}
      
     
      
	stage('Delete Instance Template') {
	 steps {
    
    sh 'gcloud compute instance-templates delete nginx-template --quiet'
        
    }
    } 
     
      
     stage('Delete firewall rule') {
	 steps {
    
    sh 'gcloud compute firewall-rules delete allow-fw-http-01 --quiet'
        
    }
    }
     
   }
}
