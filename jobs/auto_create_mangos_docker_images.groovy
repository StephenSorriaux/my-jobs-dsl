def repoList = ['mangosone', 'mangostwo', 'mangosthree']

repoList.each{ repo ->

  this.freeStyleJob("auto-create-${repo}-docker-server-image") {
      logRotator(-1, 10)
      wrappers {
        credentialsBinding {
          usernamePassword('USER', 'PASSWORD', 'docker-hub')
          environmentVariables(PROJECT: repo)
        }
      }
      scm {
          github("${repo}/server", 'refs/tags/*')
      }
      triggers {
          scm('H/5 * * * *')
      }
      steps {
          shell(readFileFromWorkspace('scripts/build_mangos.sh'))
      }
  }

}
