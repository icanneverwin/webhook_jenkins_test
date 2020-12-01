#!/bin/bash

. ${WORKSPACE}/cmrm/bin/supp_func.sh

###
echo "Preparing for RPD file movement"
echo "Jenkins Workspace is ${WORKSPACE}"

DIFFLOG_LOCATION="${WORKSPACE}/difflog.txt"
RPD_PREFIX="Application/Patches"
DEST_DIR="${WORKSPACE}/Application/RPD"
RPD_FILE="${WORKSPACE}/tmp/liverpd_$(date +"%Y%m%d_%H%M")"

GIT_BRANCH="rpd_prod"

DIFFLOG=$(grep -E "^${RPD_PREFIX}" "${DIFFLOG_LOCATION}")

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
  echo "patch ${i} compiled" >> "${RPD_FILE}"
done


# for testing purposes
echo "copying ${RPD_FILE}"
cp "${RPD_FILE}" /tmp/jenkins_tmp_data/
RC=$?
if [[ $RC -eq 0 ]] ; then
  echo "RPD has been copied to /tmp/jenkins_tmp_data/"
else
  echo "Oops, something bad happened! Cannot copy the file ${RPD}"
exit 1
fi

echo "moving ${RPD_FILE}"
mv "${RPD_FILE}" "${DEST_DIR}"
RC=$?
if [[ $RC -eq 0 ]] ; then
  echo "RPD has been moved to ${DEST_DIR}"
else
  echo "Oops, something bad happened! Cannot move the file ${RPD}"
exit 1
fi


git_push "${DEST_DIR}"/"${RPD_FILE}" "${GIT_BRANCH}" "${PR_TITLE}"
RC=$?
if [[ $RC -eq 0 ]] ; then 
  echo "pushed to repo successfully"
else
  echo "error during pushing to repo, exiting..."
  exit 1
fi

echo "all checks are done, exiting..."

exit 0
