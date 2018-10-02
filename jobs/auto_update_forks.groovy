def myForks = [
  'ansible': [
    ownerAndProject: 'ansible/ansible',
    branch: 'devel'],
  'postgres-operator': [
    ownerAndProject: 'CrunchyData/postgres-operator',
    branch: 'develop']]

myForks.keySet().each{ repo ->

  this.freeStyleJob("auto-update-${repo}-fork") {
      logRotator(-1, 10)
      wrappers {
        credentialsBinding {
          sshAgent('github-ssh')
          environmentVariables(BRANCH: myForks[repo]['branch'], PROJECT: repo)
        }
      }
      scm {
        git {
          remote {
            github("${myForks[repo]['ownerAndProject']}")
            credentials('github')
          }
          branches("${myForks[repo]['branch']}")
          extensions {
            cleanBeforeCheckout()
            localBranch("${myForks[repo]['branch']}")
          }
        }
      }
      triggers {
          scm('H/5 * * * *')
      }
      steps {
          shell(readFileFromWorkspace('scripts/update_fork.sh'))
      }
  }

}
