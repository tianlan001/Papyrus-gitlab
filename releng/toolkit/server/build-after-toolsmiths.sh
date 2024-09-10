#--------------------------------------------------------------------------------
# Copyright (c) 2012-2014 CEA LIST.
#
#    
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License 2.0
# which accompanies this distribution, and is available at
# https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#    Nicolas Bros (Mia-Software)
#	 Camille Letavernier (CEA LIST)
#--------------------------------------------------------------------------------

########## publishing ##########

##
## Known variables:
## BUILD_ID=2014-10-01_05-16-17
##
## For stable builds:
## BUILD_ALIAS=M4
##

p2UpdateSiteDir=${WORKSPACE}/source/releng/toolsmiths/site/target/repository
updateSite=${WORKSPACE}/repository

if [ -n "$BUILD_ALIAS" ]; then
  buildType=S
else
  buildType=N
fi

COMPACT_BUILD_ID="$BUILD_TIMESTAMP"
FULL_BUILD_ID=${buildType}${COMPACT_BUILD_ID}

if [ -n "$BUILD_ALIAS" ]; then
  updateZipName=Papyrus-Toolsmiths-${BUILD_ALIAS}.zip
else
  updateZipName=Papyrus-Toolsmiths-${FULL_BUILD_ID}.zip
fi
zipName="Papyrus-Toolsmiths.zip"

rm -rf tmp
#mkdir -p "tmp/$FULL_BUILD_ID"
mkdir -p "tmp/toolsmiths"

rm -rf $updateSite
mv $p2UpdateSiteDir $updateSite

# create the update site zip
(cd $updateSite && zip -r $updateZipName *)
#mv $updateSite/$updateZipName "tmp/$FULL_BUILD_ID"
mv $updateSite/$updateZipName "tmp/toolsmiths"

(cd tmp && zip -r $zipName *)
mv tmp/$zipName .
