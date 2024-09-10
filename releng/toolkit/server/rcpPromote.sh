#!/bin/bash

#Based upon Gef4 publish.sh script

#causes the shell to exit if any subcommand or pipeline returns a non-zero status.
#set -e

# Script may take 5-6 command line parameters:
# $1: Hudson job name: <name>
# $2: Hudson build id: <id>
# $3: Build type: i(ntegration), s(table), r(elease)
# $4: The release label used to label the drop files, e.g. 3.10.0 or 3.10.1
# $5: The eclipse target version, e.g. mars or neon
# $6: Override if exists
# $7: An optional release label suffix to be appended to drop files name, e.g. M1, RC1 

##Additional variables to specify for each project

#The root url that enables to find the job with ${hudsonJobRootUrl}/$jobName/$buildId
jenkinsURL="https://ci.eclipse.org/papyrus/MasterRelease"
sshGenie="genie.papyrus"
sshRemote="projects-storage.eclipse.org"
alias ll="ls -la"

#The specific localization
remoteRoot="/home/data/httpd/download.eclipse.org"
remoteUpdateSiteRoot="modeling/mdt/papyrus"
remoteRCPDir=${remoteRoot}/${remoteUpdateSiteRoot}/"rcp"

#The localization of the local build target
targetRCPRoot="artifact/products"

#The rcpPrompote.sh script may be used to publish the RCP build results. 
#if [ $# -eq 6 -o $# -eq 7  ];
#then
#	jobName=$1
	echo "jobName: $jobName"
#	buildId=$2
	echo "buildId: $buildId"
#	buildType=$3
	echo "buildType: $buildType"
#	releaseLabel=$4	
	echo "releaseLabel: $releaseLabel"
#	eclipseTarget=$5	
	echo "eclipseTarget: $eclipseTarget"
#	override=$6	
	echo "override: $override"

#	if [ -n "$7" ]; then
#		releaseLabelSuffix=$7
		echo "releaseLabelSuffix: $releaseLabelSuffix"
#	fi
#else
#	echo "Missing so parameters: command jobName buildId buildType releaseLabel eclipseTarget override [releaseLabelSuffix]" ; exit 1;
#fi

###########jobName parameter###########
if [ -z "$jobName" ]; then
	echo "The Hudson job you want to promote must be specified" ; exit 1
fi
###########buildId parameter###########
if [ -z "$buildId" ]; then
	echo "The id of the $jobName build you want to promote must be specified" ; exit 1
fi
###########buildType parameter###########
if [ -z "$buildType" ]; then
    echo "The type of build you want to publish to [i(integration), s(table), r(elease)]." ; exit 1
fi
echo "Publishing as $buildType build"

###########releaseLabel parameter###########
if [ -z "$releaseLabel" ]; then
    echo "The release label (e.g. 3.10.0, 3.10.1M2) must be specified." ; exit 1
fi
echo "Release label: $releaseLabel"

###########eclipseTarget parameter###########
if [ -z "$eclipseTarget" ]; then
    echo "The Eclipse target (e.g. mars, neon) must be specified." ; exit 1
fi
echo "Eclipse target : $eclipseTarget"

###########Override parameter###########
if [ "$override" != y -a "$override" != n ]; then
	echo "Parameter override has to 'y'(es) or 'n'(o) but was: $override" ; exit 0
fi
if [ "$override" == "y" ]; then
	echo "Will override the rcp if found"
else
	echo "Will not override the rcp if found"
fi

########### Compute localRCPRoot using buildId ###########
#if [ "$buildId" = "lastStable" -o "$buildId" = "lastSuccessful" ]; then
#	# Reverse lookup the build id (in case lastSuccessful or lastStable was used)
#	for i in $(find ~/.hudson/jobs/$jobName/builds/ -type l) ; do
#		if [ "$(readlink -f $i)" = "$(readlink -f ~/.hudson/jobs/$jobName/$buildId)" ]; then
#			buildId=${i##*/}
#		fi
#	done
#	echo "Reverse lookup (lastStable/lastSuccessful) yielded buildId: $buildId"
#fi

echo "$jenkinsURL/job/$jobName"
jobDir="$jenkinsURL/job/$jobName/$buildId"
#if ! [ -d $jobDir ]; then
#	echo "The specified buildId does not refer to an existing build: $buildId" ; exit 1
#fi

localRCPRoot=${jobDir}/${targetRCPRoot}
echo "localRCPRoot=${localRCPRoot}"

########### Promote RCP ###########
destination=$remoteRCPDir/$eclipseTarget/${releaseLabel}${releaseLabelSuffix}
echo "Destination: $destination"

if [ "$override" == "n" ]; then
	if [ ssh $sshGenie@$sshRemote -d $destination ]; then
		if [ "$(ssh $sshGenie@$sshRemote ls -A ${destination})" ]; then
			echo "The destination is not empty. You may consider overriding or archiving" ; exit 1
		fi
	fi
fi

if [ "$override" == "y" ]; then
	if [ ssh $sshGenie@$sshRemote -d $destination ]; then
		echo "Overriding the RCP by cleaning the $destination folder"
		ssh $sshGenie@$sshRemote rm -rf ${destination}/*
	fi
fi


# Get the RCP files from the API by extracting the relativePaths
tmpdir="tmp"
mkdir $tmpdir
productPath="$(curl $jobDir/api/xml?depth=1 | grep -Po "(?<=relativePath>).*?(?=<)")"
#echo $productPath
arrayRCP=(${productPath// / })

for rcp in "${arrayRCP[@]}" ; do
	wget --no-check-certificate $jobDir/artifact/${rcp}
done

#Clean if already exists
for f in *win*; do
	echo "Prepare $f"
	if [[ $f == *64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	newFileName="papyrus-"${eclipseTarget}"-"${releaseLabel}${releaseLabelSuffix}"-win$bitness.zip"
	cp $f $tmpdir/$newFileName
	md5sum $tmpdir/$newFileName > $tmpdir/$newFileName".md5"
done

for f in *linux*; do
	echo "Prepare $f"
	if [[ $f == *64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	newFileName="papyrus-"${eclipseTarget}"-"${releaseLabel}${releaseLabelSuffix}"-linux$bitness.tar.gz"
	cp $f $tmpdir/$newFileName
	md5sum $tmpdir/$newFileName > $tmpdir/$newFileName".md5"
done

for f in *mac*; do
	echo "Prepare $f"
	if [[ $f == *64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	newFileName="papyrus-"${eclipseTarget}"-"${releaseLabel}${releaseLabelSuffix}"-macosx$bitness.tar.gz"
	cp $f $tmpdir/$newFileName
	md5sum $tmpdir/$newFileName > $tmpdir/$newFileName".md5"
done

ssh $sshGenie@$sshRemote mkdir -p $destination
echo "Promoting the RCPs to $destination"
ssh $sshGenie@$sshRemote mv $tmpdir/* $destination

# Clean up
echo "Cleaning up"
rm -rf $tmpdir