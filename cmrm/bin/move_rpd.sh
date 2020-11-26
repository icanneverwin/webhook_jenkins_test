#!/bin/bash

###
echo "Preparing for RPD file movement"
echo "Jenkins Workspace is ${WORKSPACE}"

XML_PATCH=${WORKSPACE}/Application/Patches/rpd_change.xml
DEST_DIR=${WORKSPACE}/Application/RPD
RPD_FILE=${WORKSPACE}/tmp/liverpd_`date +"%Y%m%d_%H%M"`

# check if RPD exists
if [[ ! -e $XML_PATCH ]] ; then
  echo "File does not exist, job failed..."
  exit 1
else
  # prepare tmp dir
  mkdir ${WORKSPACE}/tmp
  RC=$?
  if [[ $RC -ne 0 ]] ; then
    echo "Oops, something bad happened! Cannot create ${WORKSPACE}/tmp folder"
    exit 1
  fi

  echo "XML Patch found, compiling RPD file...."
  touch "${RPD_FILE}"
  echo "file created `date`" >> "${RPD_FILE}"

  # for testing purposes
  cp "${RPD_FILE}" /tmp/jenkins_tmp_data/
  RC=$?
  if [[ $RC -eq 0 ]] ; then
    echo "RPD has been moved successfully"
  else
    echo "Oops, something bad happened! Cannot copy the file ${RPD}"
	exit 1
  fi

  echo "moving file"
  mv "${RPD_FILE}" "${DEST_DIR}"
  RC=$?
  if [[ $RC -eq 0 ]] ; then
    echo "RPD has been moved successfully"
  else
    echo "Oops, something bad happened! Cannot move the file ${RPD}"
	exit 1
  fi
fi

echo "all checks are done, exiting..."

exit 0
