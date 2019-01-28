node {

		stage ('GIT Fetch & PreMerge') {
			checkout ([
			$class: 'GitSCM',
				url: 'CHANGETHIS',
				branches: [[name: 'master']],
				extensions: [
					[$class: 'PruneStaleBranch'],
					[$class: 'CleanCheckout'],
					[$class: 'PreBuildMerge',
						options: [
							fastForwardMode: 'FF_ONLY',
							mergeRemote: 'origin',
							mergeTarget: 'master'
						]	
					]
				]
			])
		}


		stage ('Docker Build') {
			def image = docker.build("bzumby/hello_app_py:${env.BUILD_ID}")
				withDockerRegistry([credentialsId: 'CHANGETHIS', url: 'https://index.docker.io/v1/']) {
            	image.push() }
		}
	}
