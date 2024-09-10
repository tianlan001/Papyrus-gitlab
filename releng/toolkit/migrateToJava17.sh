#!/bin/bash

##############################################
# This script should help to do the migration from java 11 to java 17
# on Manifest, classpath and prefs files
#
# arg1 = the path to the root directory of the repo you want to migrate
#
# WARNING : poms.xml files should be check manualy unless for version number
##############################################

if [ -n $1 ]; then
  rootPath=$1
else
  rootPath=.
fi

#######################################
# MANIFEST dans pom.xml files
#######################################
echo "Update Manifest and pom.xml"

find $rootPath -type f -name "MANIFEST.MF"| while read fname; do
	# replace java 11 by java 17
	sed -i 's/JavaSE-11/JavaSE-17/g' $fname
	
	
	# update version for manifest
	majorVersion=0
	minorVersion=0
	newMinorVersion=0
	integrationVersion=0

	while read -r line; do 
		if [[ "$line" =~ Bundle-Version:\ ([0-9].*)\.([0-9].*)\.([0-9].*)\.qualifier ]] 
		then 
			majorVersion=${BASH_REMATCH[1]}
			minorVersion=${BASH_REMATCH[2]}
			integrationVersion=${BASH_REMATCH[3]}
		
			newMinorVersion=$((BASH_REMATCH[2]+1)) # increase minor 
		fi
	done < $fname

	sed -i "s/\(Bundle-Version:\ [0-9].*\.\)\([0-9].*\)\(\.[0-9].*\.qualifier\)/\1$newMinorVersion\3/g" $fname
	
	# update version for associated pom.xml
	if [[ "$fname" =~ (.*)/META-INF/MANIFEST.MF ]]
	then
		pomName=${BASH_REMATCH[1]}/pom.xml
		sed -i "s|<version>$majorVersion\.$minorVersion\.$integrationVersion-SNAPSHOT<\/version>|<version>$majorVersion.$newMinorVersion.$integrationVersion-SNAPSHOT</version>|g" $pomName
	fi

done


#######################################
# Classpath files
#######################################
echo "Update classpath"
find $rootPath -type f -name ".classpath" -exec sed -i 's/JavaSE-11/JavaSE-17/g' {} \;

#######################################
# Prefs files
#######################################
echo "Update .prefs"

find $rootPath -type f -name "org\.eclipse\.jdt\.core\.prefs"| while read fname; do
	sed -i 's/org\.eclipse\.jdt\.core\.compiler\.codegen\.targetPlatform=.*/org.eclipse.jdt.core.compiler.codegen.targetPlatform=17/g' $fname
	sed -i 's/org\.eclipse\.jdt\.core\.compiler\.source=.*/org.eclipse.jdt.core.compiler.source=17/g' $fname
	sed -i 's/org\.eclipse\.jdt\.core\.compiler\.compliance=.*/org.eclipse.jdt.core.compiler.compliance=17/g' $fname
done