#!/bin/sh

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

DROPS_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/downloads/drops
ARCHIVE_DIR=/home/data/httpd/archive.eclipse.org/modeling/mdt/papyrus/downloads/drops
ARCHIVE_INDEX=/home/data/httpd/archive.eclipse.org/modeling/mdt/papyrus/downloads/index.html
UPDATE_SITES_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/updates
ZIP_PREFIX="Papyrus-Update-"
PROMOTE_FUNCTIONS_SH=/opt/public/modeling/mdt/papyrus/promoteFunctions.sh
ADD_TO_COMPOSITE_SH=/opt/public/modeling/mdt/papyrus/addToComposite.sh

# constants required by promoteFunctions.sh
#export ADD_DOWNLOAD_STATS=/opt/public/modeling/mdt/papyrus/addDownloadStats.sh

# enable the job defined parameters
env

# include promote functions
source "$PROMOTE_FUNCTIONS_SH"

# get the nightly identifier
# org.eclipse.papyrus.sdk.feature_4.2.0.201810030243.jar yields 4.2.0_201810030243N && 4.2.0

nightlyID () {
    sdkJar="$(find ${HOME}/.jenkins/jobs/$1/lastSuccessful/archive/repository/features/ -name "org.eclipse.papyrus.sdk.feature_*.jar" | awk -F "sdk.feature_" '{ print $2}' | awk -F ".jar" '{ print $1}')"
	version="$(echo $sdkJar | awk '{match($1,"[0-9]+.[0-9]+.[0-9]",a)}END{print a[0]}')"
	timestamp="$(echo $sdkJar | awk 'match($0,/[0-9]+$/) {print substr($0,RSTART,RLENGTH)}')"
	echo $version"_"$timestamp"N"
}

versionID () {
    sdkJar="$(find ${HOME}/.jenkins/jobs/$1/lastSuccessful/archive/repository/features/ -name "org.eclipse.papyrus.sdk.feature_*.jar" | awk -F "sdk.feature_" '{ print $2}' | awk -F ".jar" '{ print $1}')"
	version="$(echo $sdkJar | awk '{match($1,"[0-9]+.[0-9]+.[0-9]",a)}END{print a[0]}')"
	echo "$version"
}

getLastSuccessfulBuildNumbers () {
    jobName="Papyrus-$1-Toolsmiths"
	# We need to go from the bottom of the chain to get a full chain
	echo "$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastBuild/api/xml?depth=1 | awk -F "<upstreamBuild>" '{print $2}' | awk -F "</upstreamBuild>" '{print $1}'):$(curl https://ci.eclipse.org/papyrus/job/$jobName/lastBuild/api/xml?depth=1 | awk -F "<id>" '{print $2}' | awk -F "</id>" '{print $1}')"
}

# ============================== USER PARAMETERS ==============================

echo "-------------------- initialize parameters --------------------"

#echo "branchName: $branchName"
#echo "mainBuildNumber: $mainBuildNumber"
#echo "toolsmithsBuildNumber: $toolsmithsBuildNumber"
#echo "testsBuildNumber: $testsBuildNumber"
#echo "version: $version"
#echo "updateSite: $updateSite"
#echo "mainBuildJob: $mainBuildJob"
#echo "toolsmithsBuildJob: $toolsmithsBuildJob"
#echo "testsBuildJob: $testsBuildJob"

localMainBuildNumber=$mainBuildNumber
localToolsmithsBuildNumber=$toolsmithsBuildNumber
if [[ "$localMainBuildNumber" =~ "0" || "$localToolsmithsBuildNumber" =~ "0" ]] ; then
    IFS=":"
    read localMainBuildNumber localToolsmithsBuildNumber <<< "$(getLastSuccessfulBuildNumbers $branchName)"
fi

testsBuildNumber=$testsBuildNumber
version=$version
if [[ "$version" =~ "0" ]] ; then
    read version <<< "$(versionID "Papyrus-$branchName")"
fi
updateSite=$updateSite

echo "-------------------- check parameters --------------------"

if [[ ! "$branchName" =~ ^(Oxygen|Master)$ ]]; then
    echo "branchName (e.g. \"Oxygen\", \"Master\")"
    echo "Canceled."; exit 1
fi

if [[ ! "$localMainBuildNumber" =~ ^[0-9]+$ || "$localMainBuildNumber" < 1 ]]; then
    echo "mainBuildNumber (the number of the \"Papyrus-Master\" Hudson build from which to publish the main Papyrus plug-ins)"
    echo "Canceled."; exit 1
fi

if [[ ! "$localToolsmithsBuildNumber" =~ ^[0-9]+$ || "$localToolsmithsBuildNumber" < 1 ]]; then
    echo "toolsmithsBuildNumber (the number of the \"Papyrus-Master-Toolsmiths\" Hudson build from which to publish the toolsmiths Papyrus plug-ins, or 0 to not publish)"
    echo "Canceled."; exit 1
fi

if [[ ! "$testsBuildNumber" =~ ^[0-9]+$ || "$testsBuildNumber" < 0 ]]; then
    echo "testsBuildNumber (the number of the \"Papyrus-Master-Tests\" Hudson build from which to publish the test results, or 0 to not publish)"
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

#echo "branchName: $branchName"
#echo "mainBuildNumber: $localMainBuildNumber"
#echo "toolsmithsBuildNumber: $localToolsmithsBuildNumber"
#echo "testsBuildNumber: $testsBuildNumber"
#echo "version: $version"
#echo "updateSite: $updateSite"
#echo "nightlyID: $(nightlyID "Papyrus-$branchName")"
#echo "updateSiteDir: $updateSiteDir"


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
nfsURL="" ## Not supported for HIPP builds. Leave the variable since the promote functions are still shared with the Shared Hudson Instance builds
hudsonURL="https://hudson.eclipse.org/papyrus/job/$mainBuildJob/$localMainBuildNumber/artifact/"
zipName="Papyrus-Main.zip"
updateZipPrefix="Papyrus-Update"
getZip "$zipName" "$nfsURL" "$hudsonURL"

mkdir -p "$updateSiteDir"

buildsDir="$DROPS_DIR/$version"
echo "publishing build (version='$version') to the builds directory '$buildsDir'..."
unzip -o "$zipName" -d "$buildsDir"

foldersInZip=$(unzip -t "$zipName" | egrep "testing: *[^/]*/ +OK" | sed 's%^ *testing: *\([^/]*\)/ *OK$%\1%')
[ $(echo "$foldersInZip" | wc -l) == 1 ] || { echo "one directory expected in zip"; exit 1; }
folderName="$foldersInZip"

updateSiteZipName=$(basename $(ls -1 "$buildsDir/$folderName/${updateZipPrefix}"*.zip))
unzip -o "$buildsDir/$folderName/${updateSiteZipName}" -d "$updateSiteDir/main"

# create the composite update site
cat > "$updateSiteDir/compositeArtifacts.xml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="$(date +%s000)"/>
  </properties>
  <children size="2">
    <child location="main"/>
    <child location="toolsmiths"/>
  </children>
</repository>
EOF

cat > "$updateSiteDir/compositeContent.xml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="$(date +%s000)"/>
  </properties>
  <children size="2">
    <child location="main"/>
    <child location="toolsmiths"/>
  </children>
</repository>
EOF

#$ADD_DOWNLOAD_STATS "$updateSiteDir/main" "main"

# ============================== PUBLISH TOOLSMITHS ==============================
if [[ "$localToolsmithsBuildNumber" != "0" ]]; then
    nfsURL="" ## Not supported for HIPP builds. Leave the variable since the promote functions are still shared with the Shared Hudson Instance builds
    hudsonURL="https://hudson.eclipse.org/papyrus/job/$toolsmithsBuildJob/$localToolsmithsBuildNumber/artifact/"
    zipName="Papyrus-Toolsmiths.zip"
    updateZipPrefix="Papyrus-Toolsmiths"
    getZip "$zipName" "$nfsURL" "$hudsonURL"

    echo "publishing toolsmiths (version='$version') to the builds directory '$buildsDir'..."
    unzip -o "$zipName" -d "$buildsDir/$folderName"

    foldersInZip=$(unzip -t "$zipName" | egrep "testing: *[^/]*/ +OK" | sed 's%^ *testing: *\([^/]*\)/ *OK$%\1%')
    [ $(echo "$foldersInZip" | wc -l) == 1 ] || { echo "one directory expected in zip"; exit 1; }
    folderNameToolsmith="$foldersInZip"

    updateSiteZipName=$(basename $(ls -1 "$buildsDir/$folderName/$folderNameToolsmith/${updateZipPrefix}"*.zip))
    unzip -o "$buildsDir/$folderName/$folderNameToolsmith/$updateSiteZipName" -d "$updateSiteDir/toolsmiths"

    #$ADD_DOWNLOAD_STATS "$updateSiteDir/toolsmiths" "toolsmiths"
fi

# ============================== PUBLISH TESTS ==============================
if [[ "$testsBuildNumber" != "0" ]]; then
    nfsURL="" ## Not supported for HIPP builds. Leave the variable since the promote functions are still shared with the Shared Hudson Instance builds
    hudsonURL="https://hudson.eclipse.org/papyrus/job/$testsBuildJob/$testsBuildNumber/artifact/"
    zipName="Papyrus-TestResults.zip"
    getZip "$zipName" "$nfsURL" "$hudsonURL"
    # unzips under a "testresults" folder under the main build's folder
    unzip -o "$zipName" -d "$buildsDir/$folderName"
fi


setAccessRights "$buildsDir/$folderName"
setAccessRights "$updateSiteDir"

echo "publishing done."
