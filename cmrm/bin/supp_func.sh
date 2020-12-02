#!/bin/bash

git_push() {
  # push file to the branch

  FILENAME=$1
  BRANCH=$2
  COMMIT_TITLE=$3

  echo "filename is ${FILENAME}"
  echo "BRANCH is ${BRANCH}"
  echo "COMMIT_TITLE is ${COMMIT_TITLE}"

  #DEST_BRANCH="rpd_prod"

  git config user.email "jogn.doe@example.com"
  git config user.name "John Doe"

  GIT_STATUS=$(git status --porcelain)

  if [[ -n "${GIT_STATUS}" ]] ; then
    # checking if file is in the list
    grep -o "${FILENAME}" ${GIT_STATUS}
    RC=$?
    if [[ $RC -eq 0 || $RC -eq 2 ]] ; then
      echo "file not found in git status output, exiting..."
      exit 1
    else
      git add "${FILENAME}"
      git commit -m "${COMMIT_TITLE}"
      git push origin HEAD:${BRANCH}
    fi
  else 
    echo "no changes, exiting"
    exit 1
  fi
}
