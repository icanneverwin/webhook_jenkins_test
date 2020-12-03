#!/bin/bash

git_push() {
  # push file to the branch

  FILENAME=$1
  BRANCH=$2
  COMMIT_TITLE=$3
  GIT_USR=$4
  GIT_PWD=$5

  echo "filename is ${FILENAME}"
  echo "BRANCH is ${BRANCH}"
  echo "COMMIT_TITLE is ${COMMIT_TITLE}"

  git config user.email "jogn.doe@example.com"
  git config user.name "John Doe"

  # check whether ${FILENAME} is added to the git stage
  git status --porcelain | grep -o "${FILENAME}"
  RC=$?

  if [[ $RC -ne 0 ]] ; then
    echo "file not found in git staging area or error occured while greping the file, exiting..."
    exit 1
  else
    # get url to push changes to remote repository
    GIT_URL=$(git remote get-url origin | sed "s/\/\//\/\/${GIT_USR}:${GIT_PWD}@/g")
    if [[ -z $GIT_URL ]] ; then
      echo "Error while fetching git url, GIT_URL variable is empty exiting..."
      exit 1
    fi

    echo "git add ${FILENAME}"
    git add "${FILENAME}"
    echo "git commit -m ${COMMIT_TITLE}"
    git commit -m "${COMMIT_TITLE}"
    echo "git push -f origin HEAD:${BRANCH}"
    git push -f "${GIT_URL}" HEAD:"${BRANCH}"
  fi 
}
