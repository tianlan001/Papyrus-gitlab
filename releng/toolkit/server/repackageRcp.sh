#!/bin/bash

#--------------------------------------------------------------------------------
# Copyright (c) 2015 CEA LIST.
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
#	 CEA LIST
#--------------------------------------------------------------------------------

rcpProductDir=${WORKSPACE}/source/releng/rcp/org.eclipse.papyrus.rcp.product/target/products
rcpProductFinalDir=${WORKSPACE}/products
TMP=${rcpProductDir}/tmp
papyrusMacApp=Papyrus.app
papyrusWinApp=papyrus.exe

rm -rf $rcpProductFinalDir
mkdir $rcpProductFinalDir

#Proceed with Windows
cd $rcpProductDir
for f in *win32*; do
	echo "Repackaging $f"

	if [[ "$f" == *x86_64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	rm -rf $TMP
	mkdir $TMP
	cd $TMP
	
	unzip -qq $rcpProductDir/$f

	cd Papyrus

	echo "  Signing $papyrusWinApp"
	curl -o signed.exe -F file=@$papyrusWinApp https://cbi.eclipse.org/authenticode/sign
	mv signed.exe $papyrusWinApp

	cd ..

	zip -r -9 -qq --symlinks $rcpProductFinalDir/papyrus-win-$bitness.zip Papyrus

	cd $rcpProductDir
	rm -rf $TMP
done

#Proceed with Linux
cd $rcpProductDir
for f in *linux*; do
	echo "Repackaging $f"

	if [[ "$f" == *x86_64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	mv $rcpProductDir/$f $rcpProductFinalDir/papyrus-linux-$bitness.tar.gz
done

#Proceed with MacOSX
cd $rcpProductDir
for f in *macosx*; do
	echo "Repackaging $f"

	if [[ "$f" == *x86_64* ]]; then
		bitness=64
	else
		bitness=32
	fi

	rm -rf $TMP
	mkdir $TMP
	cd $TMP
	
	tar -xzf $rcpProductDir/$f

	echo "  Signing $papyrusMacApp"
	zip -r -q unsigned.zip $papyrusMacApp
	rm -rf $papyrusMacApp
	curl -o signed.zip -F file=@unsigned.zip https://cbi.eclipse.org/macos/codesign/sign
	unzip -qq signed.zip
	rm -f unsigned.zip signed.zip

	chmod a+x "$papyrusMacApp/Contents/MacOS/papyrus"
	tar -czf $rcpProductFinalDir/papyrus-macosx-$bitness.tar.gz $papyrusMacApp

	cd $rcpProductDir
	rm -rf $TMP
done

cd $rcpProductDir
rm -f *linux* *macosx* *win32*