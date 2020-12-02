#!/bin/bash

. "${WORKSPACE}"/cmrm/bin/supp_func.sh

if [[ $# -ne 1 ]] ; then
  echo "Usage: $(basename $0 PR_TITLE)"
  exit 1
fi

###
echo "Preparing for RPD file movement"
echo "Jenkins Workspace is ${WORKSPACE}"
echo "PR_TITLE is $1"

DIFFLOG_LOCATION="${WORKSPACE}/difflog.txt"
PATCH_PREFIX="Application/Patches"
RPD_PREFIX="Application/RPD"
PR_TITLE="$1"
DEST_DIR="${WORKSPACE}"/"${RPD_PREFIX}"
RPD_FILE="liverpd_$(date +"%Y%m%d_%H%M")"
GIT_BRANCH="rpd_prod"

DIFFLOG=$(grep -E "^${PATCH_PREFIX}" "${DIFFLOG_LOCATION}")

if [[ -z ${DIFFLOG} ]] ; then
  echo "INFO : Difflog is empty or no files fetched from a repository."
  echo "INFO : Nothing to build. Exiting"
  exit 0
fi

# prepare tmp dir
mkdir "${WORKSPACE}"/tmp
RC=$?
if [[ $RC -ne 0 ]] ; then
  echo "Oops, something bad happened! Cannot create ${WORKSPACE}/tmp folder"
  exit 1
fi

for i in ${DIFFLOG} ; do
  echo "XML Patch found, processing ${i} ..."
  echo "patch ${i} compiled" >> "${WORKSPACE}"/tmp/"${RPD_FILE}"
done


# for testing purposes
echo "copying ${WORKSPACE}/tmp/${RPD_FILE}"
cp "${WORKSPACE}"/tmp/"${RPD_FILE}" /tmp/jenkins_tmp_data/
RC=$?
if [[ $RC -eq 0 ]] ; then
  echo "RPD has been copied to /tmp/jenkins_tmp_data/"
else
  echo "Oops, something bad happened! Cannot copy the file ${WORKSPACE}/tmp/${RPD_FILE}"
exit 1
fi

echo "moving ${WORKSPACE}/tmp/${RPD_FILE}"
mv "${WORKSPACE}"/tmp/"${RPD_FILE}" "${DEST_DIR}"
RC=$?
if [[ $RC -eq 0 ]] ; then
  echo "RPD has been moved to ${DEST_DIR}"
else
  echo "Oops, something bad happened! Cannot move the file ${RPD}"
exit 1
fi


git_push "${RPD_PREFIX}"/"${RPD_FILE}" "${GIT_BRANCH}" "${PR_TITLE}"
RC=$?
if [[ $RC -eq 0 ]] ; then 
  echo "pushed to repo successfully"
else
  echo "error during pushing to repo, exiting..."
  exit 1
fi

echo "all checks are done, exiting..."

exit 0
