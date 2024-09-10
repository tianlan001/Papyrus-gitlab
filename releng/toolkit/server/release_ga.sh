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
# Quentin Le menez (CEA LIST)
#
###############################################################################

# This script should allow Jenkins to take care of the release process, server wise, once the binaries are there.
# You will need to add several String variables in order to run it correctly (in this precise order):
# - milestone (e.g. 3.6/RC4, 4.7/RC2, ...), the folder under which the milestones for the simrel where published
# - eclipseRelease (e.g. 2019-09, 2020-03, ...),  the simrel GA releases repository version
# - releaseVersion (e.g. 3.6.2, 4.7.0, ...), the released Papyrus version
# - deleteFormerRelease (yes or no), whether or not you want to overwrite existing released material
# - releaseDropSID (e.g. S201912110426, S2020020745, ...), the zipped update sites corresponding to the published milestone
sshGenie="genie.papyrus"
sshRemote="projects-storage.eclipse.org"
alias ll="ls -la"

DROPS_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/downloads/drops
UPDATE_SITES_DIR=/home/data/httpd/download.eclipse.org/modeling/mdt/papyrus/updates


# enable the job defined parameters
#env >dev/null

# updates/releases
#milestone=$1
milestonePath="$UPDATE_SITES_DIR/milestones/$milestone"
echo "$milestonePath"
#eclipseRelease=$2
releaseRoot="$UPDATE_SITES_DIR/releases/$eclipseRelease"
echo "$releaseRoot"
#releaseVersion=$3
releasePath="$releaseRoot/$releaseVersion"
echo "$releasePath"
#deleteFormerRelease=$4
echo "Delete former release: $deleteFormerRelease"
#releaseDropSID=$5
echo "$releaseDropSID"
releaseDropRID="${releaseDropSID/S/R}"
echo "$releaseDropRID"


if ssh $sshGenie@$sshRemote [ -d $releasePath ]; then
  echo "The release already exists: $releasePath"
  if [ "$deleteFormerRelease" != "yes" ]; then echo "Canceled."; exit 1; fi
  
  echo "Deleting: $releasePath"
  ssh $sshGenie@$sshRemote rm -rf "$releasePath"
  
  if ssh $sshGenie@$sshRemote [ -d $releasePath ]; then echo "failed to remove the previous release"; exit 1; fi
fi

####################################
## Publishing the new Update site ##
####################################

echo "Creating the new release folder in: $releasePath"
ssh $sshGenie@$sshRemote mkdir -p "$releasePath"
if ssh $sshGenie@$sshRemote [ ! -d $releasePath ] ; then echo "The release directory was not created"; exit 1; fi
echo "Copy the RC content from: $milestonePath"
ssh $sshGenie@$sshRemote "cp -r $milestonePath/* $releasePath/"


echo "Update the composite files of the release"
rootChildren=$(($(ssh $sshGenie@$sshRemote find $releaseRoot -maxdepth 1 -type d -print | wc -l)-1))
releaseChildren=$(($(ssh $sshGenie@$sshRemote find $releasePath -maxdepth 1 -type d -print | wc -l)-1))
newTimeStamp=$(date +%s000)
rootArray=($(ssh $sshGenie@$sshRemote ls -d $releaseRoot/*/))
releaseArray=($(ssh $sshGenie@$sshRemote ls -d $releasePath/*/))
#echo "${folderArray[@]}"
function cleanComposites () {
  if [ -f compositeArtifacts.xml ] ; then
    rm compositeArtifacts.xml
  fi
  if [ -f compositeContent.xml ] ; then
    rm compositeContent.xml
  fi
}

# Update the releaseRoot composites
cleanComposites

cat > compositeArtifacts.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${rootChildren}">$(
    for folder in "${rootArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeArtifacts.xml $sshGenie@$sshRemote:$releaseRoot/compositeArtifacts.xml

cat > compositeContent.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${rootChildren}">$(
    for folder in "${rootArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeContent.xml $sshGenie@$sshRemote:$releaseRoot/compositeContent.xml


# Update the new releasePath composites
cleanComposites

cat > compositeArtifacts.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${releaseChildren}">$(
    for folder in "${releaseArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeArtifacts.xml $sshGenie@$sshRemote:$releasePath/compositeArtifacts.xml

cat > compositeContent.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${releaseChildren}">$(
    for folder in "${releaseArray[@]}" ; do
      printf "\n    <child location='$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeContent.xml $sshGenie@$sshRemote:$releasePath/compositeContent.xml

# Update the latestRelease folder $releasePath
releaseLatest="$UPDATE_SITES_DIR/releases/latest"
cleanComposites

cat > compositeArtifacts.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.artifact.repository.CompositeArtifactRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${releaseChildren}">$(
    for folder in "${releaseArray[@]}" ; do
      printf "\n    <child location='../$eclipseRelease/$releaseVersion/$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeArtifacts.xml $sshGenie@$sshRemote:$releaseLatest/compositeArtifacts.xml

cat > compositeContent.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<repository name="Papyrus" type="org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository" version="1.0.0">
  <properties size="1">
    <property name="p2.timestamp" value="${newTimeStamp}"/>
  </properties>
  <children size="${releaseChildren}">$(
    for folder in "${releaseArray[@]}" ; do
      printf "\n    <child location='../$eclipseRelease/$releaseVersion/$(basename ${folder})'/>"
    done
    )
  </children>
</repository>
EOF
scp compositeContent.xml $sshGenie@$sshRemote:$releaseLatest/compositeContent.xml


################################
## Publishing the release zip ##
################################

# downloads/drops
releaseDropRoot="$DROPS_DIR/$releaseVersion"
releaseDropS=$releaseDropRoot/$releaseDropSID
releaseDropR=$releaseDropRoot/$releaseDropRID

echo "Creating the new $releaseDropRID release folder in: $releaseDropRoot"
ssh $sshGenie@$sshRemote mkdir -p $releaseDropR
if ssh $sshGenie@$sshRemote [ ! -d $releaseDropR ] ; then echo "The release drop was not created"; exit 1; fi
echo "Copy the RC content from: $releaseDropS"
ssh $sshGenie@$sshRemote "cp -r $releaseDropS/* $releaseDropR/"

echo "Renaming the zip archives in: $releaseDropR"
for file in $( ssh $sshGenie@$sshRemote find $releaseDropR/ -type f) ; do
  newFileName=${file//$releaseVersion[A-Z]*[0-9]/$releaseVersion}
  ssh $sshGenie@$sshRemote mv $file $newFileName
done


echo "release done."
