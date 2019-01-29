node ('master') {
	def BUILD_JOB = 'build_lucy' // upstream job
	properties([
		pipelineTriggers([
			upstream(
				threshold: hudson.model.Result.SUCCESS,
				upstreamProjects: "${BUILD_JOB}"
				)
			])
		])
	stage ('Re-Deploy Lucy') {
	    def buildNum = Jenkins.instance.getItem("${BUILD_JOB}").lastSuccessfulBuild.number
	    ws("$JENKINS_HOME/workspace/${BUILD_JOB}") {
	    sh "kubectl set image -f kubernetes/deployment_k8s_lucy.yaml lucy-app-ctr=bzumby/lucy_app:v1.${buildNum}"
		// sh 'pulseaudio -D' // not sure yet.
	}

}
}