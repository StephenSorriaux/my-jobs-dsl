pipelineJob('pipeline-for-share-my-list') {
    logRotator(-1, 10)
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        credentialsBinding {
          sshAgent('github-ssh')
        }
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        github('StephenSorriaux/share-my-list')
                    }
                    branches('master')
                    extensions {
                            cleanBeforeCheckout()
                    }
                }
            }
        }
    }
}