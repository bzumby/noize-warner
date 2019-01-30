node ('master'){
		deleteDir() // clean ws
		stage ('GIT fetch changes') {
			checkout ([
			$class: 'GitSCM',
				branches: [[name: 'lucy_origin/lucy_sb']],
				extensions: [
					[$class: 'PruneStaleBranch'],
					[$class: 'CleanCheckout'] //,
					// looks like we dont need this for PROD
					// [$class: 'PreBuildMerge',
					// 	options: [
					// 		fastForwardMode: 'FF_ONLY',
					// 		mergeRemote: 'lucy_origin',
					// 		mergeTarget: 'kube_prod'
					// 	]	
					// ]
				],
				userRemoteConfigs: [
				    [name: 'lucy_origin',
					 url: 'https://github.com/bzumby/noize-warner.git',
					]
				]
			])
		}

		stage ('Docker Build') {
			def image = docker.build("bzumby/lucy_app_sb:${env.BUILD_ID}")
				withDockerRegistry([credentialsId: 'docker_hub_bz', url: 'https://index.docker.io/v1/']) {
            	image.push("v1.${env.BUILD_ID}")
            	image.push('latest') }
		}
	}