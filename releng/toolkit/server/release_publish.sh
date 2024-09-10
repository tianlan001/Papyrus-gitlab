#!/bin/bash

###############################################################################
# Copyright (c) 2012-2019 CEA LIST and others.
#
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License 2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
# Nicolas Bros (Mia-Software) - Initial API and implementation
# Quentin Le menez (CEA LIST) - Support for the new Papyrus architecture 
#
###############################################################################
sshGenie="genie.papyrus"
sshRemote="projects-storage.eclipse.org"
alias ll="ls -la"

DROPS_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/downloads/drops
ARCHIVE_DIR=/home/data/httpd/archive.eclipse.org/modeling/mdt/papyrus/downloads/drops
ARCHIVE_INDEX=/home/data/httpd/archive.eclipse.org/modeling/mdt/papyrus/downloads/index.html
UPDATE_SITES_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/updates
ZIP_PREFIX="Papyrus-Update-"


# enable the job defined parameters
#env >/dev/null

########### Get Nighlty Versions ###########
# API, e.g. https://ci.eclipse.org/papyrus/job/Papyrus-Master/api/
# These functions are meant to be used for an automatic nightly publish
# get the nightly identifier
# org.eclipse.papyrus.sdk.feature_4.2.0.201810030243.jar yields 4.2.0_201810030243N && 4.2.0
sdkname="org.eclipse.papyrus.sdk.feature"

nightlyID () {
    sdkJar="$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastSuccessfulBuild/api/xml?depth=1 | awk -F "<fileName>$sdkname\_" '{print $2}' | awk -F ".jar</fileName>" '{print $1}')"
	version="$(echo $sdkJar | awk '{match($1,"[0-9]+.[0-9]+.[0-9]",a)}END{print a[0]}')"
	timestamp="$(echo $sdkJar | awk 'match($0,/[0-9]+$/) {print substr($0,RSTART,RLENGTH)}')"
	echo $version"_"$timestamp"N"
}

versionID () {
    sdkJar="$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastSuccessfulBuild/api/xml?depth=1 | awk -F "<fileName>$sdkname\_" '{print $2}' | awk -F ".jar</fileName>" '{print $1}')"
	version="$(echo $sdkJar | awk '{match($1,"[0-9]+.[0-9]+.[0-9]",a)}END{print a[0]}')"
	echo $version
}

getLastSuccessfulBuildNumbers () {
    jobName="Papyrus-$1-Toolsmiths"
	# We need to go from the bottom of the chain to get a full chain
	echo "$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastSuccessfulBuild/api/xml?depth=1 | awk -F "<upstreamBuild>" '{print $2}' | awk -F "</upstreamBuild>" '{print $1}'):$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastSuccessfulBuild/api/xml?depth=1 | awk -F "<id>" '{print $2}' | awk -F "</id>" '{print $1}')"
}

########### Set Access Rights ###########
# This function sets the acess rights to allow all memebers of the group to edit the files
function setAccessRights() {
  ssh $sshGenie@$sshRemote chmod -R 775 "$1"
  ssh $sshGenie@$sshRemote chgrp -hR modeling.mdt.papyrus "$1"
}

########### Get Zip ###########
# This function fetches and test the zip
function getZip() {
	_zipName=$1
	_jenkinsBaseURL=$2

	[[ "$_zipName" =~ ^.*?\.zip$ ]] || { echo "incorrect parameter: zipName"; exit 1; }

	# Use the Jenkins REST API
	# see https://www.jenkins.io/doc/book/using/remote-access-api/
	wget --no-check-certificate "$_jenkinsBaseURL/${_zipName}"
	if [ ! -f "$_zipName" ]; then echo "ERROR: $_zipName (from Jenkins) not found"; exit -2; fi
	echo "[$(date +%Y%m%d-%H%M)] Testing zip integrity"
	unzip -t "$_zipName"
}


# ============================== USER PARAMETERS ==============================

echo "-------------------- env parameters --------------------"

echo "branchName: $branchName"
echo "mainBuildJob: $mainBuildJob"
echo "mainBuildNumber: $mainBuildNumber"
echo "toolsmithsBuildJob: $toolsmithsBuildJob"
echo "toolsmithsBuildNumber: $toolsmithsBuildNumber"
echo "testsBuildJob: $testsBuildJob"
echo "testsBuildNumber: $testsBuildNumber"
echo "sbomsBuildJob: $sbomsBuildJob"
echo "sbomsBuildNumber: $sbomsBuildNumber"
echo "version: $version"
echo "updateSite: $updateSite"

branchName=$branchName
mainBuildNumber=$mainBuildNumber
toolsmithsBuildNumber=$toolsmithsBuildNumber
testsBuildNumber=$testsBuildNumber
version=$version
updateSite=$updateSite

## This was meant for an automatic nightly release but mirrors had problems catching up hence this should be deactivated to avoid pushing unsigned/broken content
#if [[ -z ${mainBuildNumber+x} || -z ${toolsmithsBuildNumber+x} && ! -z ${$branchName+x} ]] ; then
#    IFS=":"
#    read mainBuildNumber toolsmithsBuildNumber <<< "$(getLastSuccessfulBuildNumbers $branchName)"
#fi
#
#if [[ -z ${version+x} && ! -z ${$branchName+x} ]] ; then
#    read version <<< "$(versionID "Papyrus-$branchName")"
#fi

echo "-------------------- check parameters --------------------"

if [[ ! "$branchName" =~ ^(Oxygen|Master|Reexport)$ ]]; then
    echo "branchName (e.g. \"Oxygen\", \"Master\")"
    echo "Canceled."; exit 1
fi

if [[ ! "$mainBuildNumber" =~ ^[0-9]+$ || "$mainBuildNumber" < 1 ]]; then
    echo "mainBuildNumber (the number of the \"Papyrus-$branchName\" Hudson build from which to publish the main Papyrus plug-ins)"
    echo "Canceled."; exit 1
fi

if [[ ! "$toolsmithsBuildNumber" =~ ^[0-9]+$ || "$toolsmithsBuildNumber" < 1 ]]; then
    echo "toolsmithsBuildNumber (the number of the \"Papyrus-$branchName-Toolsmiths\" Hudson build from which to publish the toolsmiths Papyrus plug-ins, or 0 to not publish)"
    echo "Canceled."; exit 1
fi

if [[ ! "$testsBuildNumber" =~ ^[0-9]+$ || "$testsBuildNumber" < 0 ]]; then
    echo "testsBuildNumber (the number of the \"Papyrus-$branchName-Tests\" Hudson build from which to publish the test results, or 0 to not publish)"
    echo "Canceled."; exit 1
fi

updateSiteDir=""
if [[ ! "$updateSite" =~ ^(nightly/(oxygen|photon|master)|milestones/[0-9]+\.[0-9]+/(I[1-7]|M[1-7]|RC[1-9]|SR[1-9]_RC[1-9])[a-z]?)$ ]]; then
    echo "nightly (e.g. \"nightly/master\"), milestone (e.g. \"milestones/0.9/M5\" or \"milestones/0.9/M5a\")"
    echo "Canceled."; exit 1
elif [[ "$updateSite" =~ ^(nightly/(oxygen|photon|master))$ ]]; then
    updateSiteDir="$UPDATE_SITES_DIR/$updateSite/$(nightlyID "Papyrus-$branchName")"
else [[ "$updateSite" =~ ^(milestones/[0-9]+\.[0-9]+/(I[1-7]|M[1-7]|RC[1-9]|SR[1-9]_RC[1-9])[a-z]?)$ ]]
    updateSiteDir="$UPDATE_SITES_DIR/$updateSite"
fi


echo "-------------------- initialized parameters --------------------"

echo "nightlyID: $(nightlyID "Papyrus-$branchName")"
echo "updateSiteDir: $updateSiteDir"

#exit 0
# The following variables must be set in order to progress somewhat safely
viparams=("mainBuildJob" "mainBuildNumber" "toolsmithsBuildJob" "toolsmithsBuildNumber" "version" "updateSite")
for param in "${viparams[@]}"; do
    if [[ -z ${!param+x} ]]; then
        echo "Error: $param is not set."
        exit 1
    fi
done


deleteUpdateSite="no"
if [ -e "$updateSiteDir" ]; then
    echo "The update site already exists: $updateSiteDir"
    echo "Canceled."; exit 1
fi

# from now on, display executed commands
set -x

dirBefore=$(pwd)
echo "[$DATE] creating working dir"
workingDir=$(mktemp -d)
cd "$workingDir"

# ============================== PUBLISH MAIN ==============================
jenkinsURL="https://ci.eclipse.org/papyrus/job/$mainBuildJob/$mainBuildNumber/artifact"
zipName="Papyrus-Main.zip"
updateZipPrefix="Papyrus-Update"
## The function might be executed in a subshell preventing the zip to be present in the workspace
getZip "$zipName" "$jenkinsURL"

if [ ! -f "$zipName" ]; then 
  echo "ERROR: $zipName (from Jenkins) not found"; 
  exit -2; 
fi
ls -la
#exit 0;

buildsDir="$DROPS_DIR/$version"
foldersInZip=$(unzip -t "$zipName" | egrep "testing: *[^/]*/ +OK" | sed 's%^ *testing: *\([^/]*\)/ *OK$%\1%')
[ $(echo "$foldersInZip" | wc -l) == 1 ] || { echo "one directory expected in zip"; exit 1; }
folderName="$foldersInZip"

echo "publishing main (version='$version') to the builds directory '$buildsDir'..."
ssh $sshGenie@$sshRemote mkdir -p "$buildsDir"
#ziploc=$(find . -type f -name "$zipName" -print)
#ssh $sshGenie@$sshRemote unzip -o "$ziploc" -d "$buildsDir"
scp $zipName $sshGenie@$sshRemote:/$buildsDir
ssh $sshGenie@$sshRemote unzip -o "$buildsDir/$zipName" -d "$buildsDir"
ssh $sshGenie@$sshRemote rm "$buildsDir/$zipName"

echo "publishing main (version='$version') to the updateSite directory '$updateSiteDir'..."
ssh $sshGenie@$sshRemote mkdir -p "$updateSiteDir"
updateSiteZipName=$(basename $(ssh $sshGenie@$sshRemote ls -1 "$buildsDir/$folderName/${updateZipPrefix}"*.zip))
ssh $sshGenie@$sshRemote unzip -o "$buildsDir/$folderName/${updateSiteZipName}" -d "$updateSiteDir/main"

# ============================== PUBLISH TOOLSMITHS ==============================
if [[ "$toolsmithsBuildNumber" != "0" ]]; then
  jenkinsURL="https://ci.eclipse.org/papyrus/job/$toolsmithsBuildJob/$toolsmithsBuildNumber/artifact"
  zipName="Papyrus-Toolsmiths.zip"
  updateZipPrefix="Papyrus-Toolsmiths"
  ## The function might be executed in a subshell preventing the zip to be present in the workspace
  getZip "$zipName" "$jenkinsURL"

  if [ ! -f "$zipName" ]; then 
    echo "ERROR: $zipName (from Jenkins) not found"; 
    exit -2; 
  fi
  ls -la
  #exit 0;

  foldersInZip=$(unzip -t "$zipName" | egrep "testing: *[^/]*/ +OK" | sed 's%^ *testing: *\([^/]*\)/ *OK$%\1%')
  [ $(echo "$foldersInZip" | wc -l) == 1 ] || { echo "one directory expected in zip"; exit 1; }
  folderNameToolsmith="$foldersInZip"

  echo "publishing toolsmiths (version='$version') to the builds directory '$buildsDir'..."
  #ziploc=$(find . -type f -name "$zipName" -print)
  #ssh $sshGenie@$sshRemote unzip -o "$ziploc" -d "$buildsDir/$folderName"
  scp $zipName $sshGenie@$sshRemote:/$buildsDir/$folderName
  ssh $sshGenie@$sshRemote unzip -o "$buildsDir/$folderName/$zipName" -d "$buildsDir/$folderName"
  ssh $sshGenie@$sshRemote rm "$buildsDir/$folderName/$zipName"

echo "publishing toolsmiths (version='$version') to the updateSite directory '$updateSiteDir'..."
  updateSiteZipName=$(basename $(ssh $sshGenie@$sshRemote ls -1 "$buildsDir/$folderName/$folderNameToolsmith/${updateZipPrefix}"*.zip))
  ssh $sshGenie@$sshRemote unzip -o "$buildsDir/$folderName/$folderNameToolsmith/$updateSiteZipName" -d "$updateSiteDir/toolsmiths"

fi

# ============================== CREATE UPDATE SITE COMPOSITES ==============================
function cleanComposites () {
  if [ -f compositeArtifacts.xml ] ; then
    rm compositeArtifacts.xml
  fi
  if [ -f compositeArtifacts.xml ] ; then
    rm compositeArtifacts.xml
  fi
}
newTimeStamp=$(date +%s000)
cleanComposites

cat > compositeArtifacts.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="2">
    <child location="main"/>
    <child location="toolsmiths"/>
  </children>
</repository>
EOF
scp compositeArtifacts.xml $sshGenie@$sshRemote:$updateSiteDir/compositeArtifacts.xml

cat > compositeContent.xml <<EOF
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="2">
    <child location="main"/>
    <child location="toolsmiths"/>
  </children>
</repository>
EOF
scp compositeContent.xml $sshGenie@$sshRemote:$updateSiteDir/compositeContent.xml


# ============================== PUBLISH SBOMS ==============================

if [[ -z ${sbomsBuildJob+x} || -z ${sbomsBuildNumber+x} ]] ; then
  echo "SBOMs Job and Number provided, proceeding to download and publish the reports at the root of the new p2 repository."
  jenkinsURL="https://ci.eclipse.org/papyrus/job/$sbomsBuildJob/$sbomsBuildNumber/artifact"
  curl -O "$jenkinsURL*" --create-dirs --output "SBOMs"
  
  scp -r SBOMs $sshGenie@$sshRemote:$updateSiteDir
fi

# ============================== PUBLISH TESTS ==============================
if [[ "$testsBuildNumber" != "0" ]]; then
    nfsURL="" ## Not supported for HIPP builds. Leave the variable since the promote functions are still shared with the Shared Hudson Instance builds
    jenkinsURL="https://ci.eclipse.org/papyrus/job/$testsBuildJob/$testsBuildNumber/artifact"
    zipName="Papyrus-TestResults.zip"
    
    #TODO
fi

setAccessRights "$buildsDir/$folderName"
setAccessRights "$updateSiteDir"

# create the composite update site for the update site root folder
updateSiteChildren=$(($(ssh $sshGenie@$sshRemote find $updateSiteDir/.. -maxdepth 1 -type d -print | wc -l)-1))
folderArray=($(ssh $sshGenie@$sshRemote ls -d $updateSiteDir/../*/))
#echo "${folderArray[@]}"


# ============================== UPDATE ROOT COMPOSITES ==============================
cleanComposites
cat > compositeArtifacts.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${updateSiteChildren}">$(
    for folder in "${folderArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeArtifacts.xml $sshGenie@$sshRemote:$updateSiteDir/../compositeArtifacts.xml

cat > compositeContent.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${updateSiteChildren}">$(
    for folder in "${folderArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeContent.xml $sshGenie@$sshRemote:$updateSiteDir/../compositeContent.xml

setAccessRights "$updateSiteDir/.."

echo "publishing done."
