def repoList = ['mangosone', 'mangostwo']

repoList.each{ repo ->

  this.freeStyleJob("auto-create-${repo}-docker-image") {
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
          scm('@daily')
      }
      steps {
          shell(readFileFromWorkspace('scripts/build_mangos.sh'))
      }
  }

}
