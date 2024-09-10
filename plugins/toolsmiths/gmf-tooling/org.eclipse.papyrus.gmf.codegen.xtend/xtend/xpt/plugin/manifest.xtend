/*****************************************************************************
 * Copyright (c) 2007, 2013, 2015, 2021 Borland Software Corporation, Christian W. Damus, CEA LIST, Artal and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Michael Golubev (Montages) - initial API and implementation
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Thibault Landre (Atos Origin) - add Papyrus dependencies to Papyrus GMF diagram
 * Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr : add the dependency org.eclipse.papyrus.infra.services.edit
 * Christian W. Damus - bug 477384
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.2 cleanup dependency declarations in Require-Bundle section
 * Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 574258: [Toolsmiths] Improve GMF generation for manifest.MF and build.properties
 *****************************************************************************/
package xpt.plugin

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin
import org.eclipse.papyrus.gmf.codegen.util.AbstractManifest
import xpt.GenEditorGenerator_qvto

@Singleton class manifest extends AbstractManifest{

	@Inject extension GenEditorGenerator_qvto

	def qualifiedClassName(GenPlugin it) '''META-INF/MANIFEST.MF'''
	def fullPath(GenPlugin it) '''«qualifiedClassName(it)»'''

	def manifest(GenPlugin it) '''
		«init(ID)»
		«manifestVersion(it)»
		«bundleManifestVersion(it)»
		«bundleName(it)»
		«bundleDescription(it)»
		«symbolicName(it)»
		«bundleVersion(it)»
		«bundleClassPath(it)»
		«bundleActivator(it)»
		«bundleVendor(it)»
		«bundleLocalization(it)»
		«exportPackages()»
		«requireBundle()»
		«importPackage()»
		«bundleActivatorPolicy(it)»
		«eclipseLazyStart(it)»
		«executionEnvironment()»
		«automaticModuleName(it)»
	'''

	def automaticModuleName(GenPlugin it) '''Automatic-Module-Name: «ID»'''
	def manifestVersion(GenPlugin it) '''Manifest-Version: 1.0'''
	def bundleManifestVersion(GenPlugin it) '''Bundle-ManifestVersion: 2'''
	def bundleName(GenPlugin it) '''Bundle-Name: %pluginName'''

	def bundleDescription(GenPlugin it) {
		if (hasBundleDescription) {
			'''Bundle-Description: «getBundleDescription»
			'''
		}
	}

	def symbolicName(GenPlugin it) '''Bundle-SymbolicName: «ID»; singleton:=true'''
	def bundleVersion(GenPlugin it) '''Bundle-Version: «version»'''
	def bundleClassPath(GenPlugin it) '''Bundle-ClassPath: .'''
	def bundleActivator(GenPlugin it) '''Bundle-Activator: «getActivatorQualifiedClassName()»'''
	def bundleVendor(GenPlugin it) '''Bundle-Vendor: %providerName'''
	def bundleLocalization(GenPlugin it) '''Bundle-Localization: plugin'''
	def bundleActivatorPolicy(GenPlugin it) '''Bundle-ActivationPolicy: lazy'''

	def eclipseLazyStart(GenPlugin it) {
		if (eclipseLazyStart) {
			'''Eclipse-LazyStart: true
			'''
		}
	}

	def exportPackages(GenPlugin it) '''«buildExportPackage()»'''

	def buildExportPackage(GenPlugin it) {
		val exportedPackage = getExistingExportedPackages();
		exportedPackage.add(editorGen.diagram.editPartsPackageName);
		exportedPackage.add(editorGen.editor.packageName);
		exportedPackage.add(editorGen.diagram.providersPackageName);
		val builder = new StringBuilder("Export-Package: ");
		val iter = exportedPackage.iterator;
		while (iter.hasNext) {
			builder.append(iter.next);
			if (iter.hasNext) {
				builder.append(",");
				builder.append("\n");
				builder.append(" ");
			} else {
				builder.append("\n");
			}

		}
		return builder.toString();
	}

	def requireBundle(GenPlugin it) '''«buildRequiredBundle()»'''
	
	def importPackage() '''«declareImportPackage»'''

	def executionEnvironment(GenPlugin it) '''
		Bundle-RequiredExecutionEnvironment: «IF editorGen.jdkComplianceLevel() > 8»JavaSE-«editorGen.jdkComplianceLevel()»«ELSE»J2SE-1.«editorGen.jdkComplianceLevel()»«ENDIF»
	'''
}
