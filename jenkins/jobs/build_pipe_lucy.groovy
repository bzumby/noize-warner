node ('master'){
		deleteDir() // clean ws
		stage ('GIT Fetch & PreMerge') {
			checkout ([
			$class: 'GitSCM',
				branches: [[name: 'lucy_origin/kube_test']],
				extensions: [
					[$class: 'PruneStaleBranch'],
					[$class: 'CleanCheckout'],
					[$class: 'PreBuildMerge',
						options: [
							fastForwardMode: 'FF_ONLY',
							mergeRemote: 'lucy_origin',
							mergeTarget: 'master'
						]	
					]
				],
				userRemoteConfigs: [
				    [name: 'lucy_origin',
					 url: 'https://github.com/bzumby/noize-warner.git',
					]
				]
			])
		}

		stage ('Docker Build') {
			def image = docker.build("bzumby/lucy_app:${env.BUILD_ID}")
				withDockerRegistry([credentialsId: 'docker_hub_bz', url: 'https://index.docker.io/v1/']) {
            	image.push("v1.${env.BUILD_ID}")
            	image.push('latest') }
		}
	}

