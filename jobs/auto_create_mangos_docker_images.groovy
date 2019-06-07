def repoList = ['mangoszero','mangosone', 'mangostwo', 'mangosthree']

repoList.each{ repo ->

  this.freeStyleJob("auto-create-${repo}-docker-database-image") {
      logRotator(-1, 10)
      wrappers {
        credentialsBinding {
          usernamePassword('USER', 'PASSWORD', 'docker-hub')
          environmentVariables(PROJECT: repo)
        }
      }
      scm {
          github("${repo}/database", 'master')
      }
      triggers {
          scm('H/5 * * * *')
      }
      steps {
          shell(readFileFromWorkspace('scripts/build_mangos_database.sh'))
      }
  }

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

  this.freeStyleJob("auto-create-${repo}-docker-realmd-image") {
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
          shell(readFileFromWorkspace('scripts/build_mangos_realmd.sh'))
      }
  }

}
