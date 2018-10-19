#!/usr/bin/env bash

set -ev
if [ ! -d "${HOME}/.ssh" ]; then
  mkdir ~/.ssh
fi
if [ ! -f "${HOME}/.ssh/known_hosts" ]; then
  touch ~/.ssh/known_hosts
fi
if [ ! -n "$(grep "^github.com " ~/.ssh/known_hosts)" ]; then
  ssh-keyscan github.com >> ~/.ssh/known_hosts 2>/dev/null
fi
fork_exists=$(git remote | grep fork | wc -l)
if [ "$fork_exists" -eq 0 ]; then
  git remote add fork git@github.com:StephenSorriaux/${PROJECT}.git
fi
git push -uf fork ${BRANCH}
