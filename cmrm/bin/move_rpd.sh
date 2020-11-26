#!/bin/bash

###
echo "Preparing for RPD file movement"
echo "Jenkins Workspace is ${WORKSPACE}"

RPD=${WORKSPACE}/liverpd.rpd
TEMP_DIR=/tmp/jenkins_tmp_data/

# check if RPD exists
if [[ ! -e $RPD ]] ; then
  echo "File does not exist, job failed..."
  exit 1
else 
  echo "RPD file exists, moving..."
  mv $RPD $TEMP_DIR
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
