#!/bin/bash

git_push() {
  # push file to the branch

  FILENAME=$1
  BRANCH=$2
  COMMIT_TITLE=$3

  echo "filename is ${FILENAME}"
  echo "BRANCH is ${BRANCH}"
  echo "COMMIT_TITLE is ${COMMIT_TITLE}"

  git config user.email "jogn.doe@example.com"
  git config user.name "John Doe"

  echo $(git status --porcelain) | grep -o "${FILENAME}"
  RC=$?

  if [[ $RC -ne 0 ]] ; then
    echo "file not found in git staging area or error occured while greping the file, exiting..."
    exit 1
  else
    echo "git add ${FILENAME}"
    git add "${FILENAME}"
    echo "git commit -m ${COMMIT_TITLE}"
    git commit -m "${COMMIT_TITLE}"
    echo "git push origin HEAD:${BRANCH}"
    git push origin HEAD:"${BRANCH}"
  fi 
}
