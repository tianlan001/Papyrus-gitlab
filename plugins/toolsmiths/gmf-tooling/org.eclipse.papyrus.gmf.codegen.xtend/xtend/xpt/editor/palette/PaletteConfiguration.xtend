/*****************************************************************************
 * Copyright (c) 2017, 2023 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 * Ansgar Radermacher - bug 582492, move to com.google.inject
 *****************************************************************************/
package xpt.editor.palette

import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.papyrus.gmf.codegen.gmfgen.AbstractToolEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroup
import xpt.Common
import xpt.Common_qvto
import com.google.inject.Inject
import com.google.inject.Singleton

/**
 * Template for generation of palette configuration model from gmfgen.
 */
@Singleton class PaletteConfiguration {

	@Inject extension Common
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;

	/**
	 * Generate a palette configuration.
	 */
	def PaletteConfiguration(GenEditorGenerator it) '''
		<?xml version="1.0" encoding="ASCII"?>
		«xmlGeneratedTag»
		<paletteconfiguration:PaletteConfiguration xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:paletteconfiguration="http://www.eclipse.org/papyrus/diagram/paletteconfiguration/0.8"
			xmlns:elementtypesconfigurations="http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.2"
			id="«plugin.ID».paletteconfiguration"
			label="«plugin.name» Palette"
			description="This is the palette of «plugin.name»">
			«FOR tool : diagram.palette.groups»
				«addDrawerConfigurations(tool)»
			«ENDFOR»
		</paletteconfiguration:PaletteConfiguration>
	'''

	/**
	 * Generate drawer configurations.
	 */
	def addDrawerConfigurations(ToolGroup it) '''
		<drawerConfigurations
			id=«IF isQuoted(id,'"')»«id»«ELSE»"«id»"«ENDIF»
			label="«it.title»"
			description="«description»">
			<icon pluginID="«retrieveBundleId(smallIconPath,palette.diagram.editorGen.plugin.ID)»" iconPath="«retrieveLocalPath(smallIconPath)»"/>
			«FOR entry : entries»
				«IF entry instanceof ToolEntry  && isValidTool(entry as AbstractToolEntry)»
					«addToolConfiguration(entry as ToolEntry)»
				«ENDIF»
				«IF entry instanceof ToolGroup»
					«addStackConfiguration(entry as ToolGroup)»
				«ENDIF»
			«ENDFOR»
		</drawerConfigurations>
	'''

	/**
	 * Add tool configuration.
	 */
	def addToolConfiguration(ToolEntry it) '''
		<ownedConfigurations xsi:type="paletteconfiguration:ToolConfiguration"
			id=«IF isQuoted(id,'"')»«id»«ELSE»"«id»"«ENDIF»
			label="«title»"
			«IF null!==qualifiedToolName && !qualifiedToolName.empty»
				toolClassName="«qualifiedToolName»"
			«ENDIF»
			description="«description»"«IF genNodes.empty && !genLinks.empty» kind="ConnectionTool"«ENDIF»>
			<icon pluginID="«retrieveBundleId(smallIconPath,group.palette.diagram.editorGen.plugin.ID)»" iconPath="«retrieveLocalPath(smallIconPath)»"/>
			«IF elements.empty && ( null===qualifiedToolName || qualifiedToolName.empty)»
				«ERROR('No elements for tool generation (Palette):'+title)»
			«ENDIF»
			«FOR e : elements»
				«val href = getElementTypeConfHRef(e.elementType.uniqueIdentifier,eResource as XMLResource)»
				«IF null!==href && !href.empty»
					<elementDescriptors>
						<elementType
							xsi:type="«getConfigurationXsiType(e.elementType.uniqueIdentifier,eResource as XMLResource)»"
							href="«href»"/>
					</elementDescriptors>
				«ELSEIF null===qualifiedToolName || qualifiedToolName.empty»
					«ERROR('No element type configuration or classToolName for tool generation (Palette):' + title)»
				«ENDIF»
			«ENDFOR»
		</ownedConfigurations>
	'''

	/**
	 * Add stack configuration.
	 */
	def addStackConfiguration(ToolGroup it) '''
		<ownedConfigurations xsi:type="paletteconfiguration:StackConfiguration" id=«IF isQuoted(id,'"')»«id»«ELSE»"«id»"«ENDIF» label="«title»">
			«FOR entry : entries»
				«IF entry instanceof ToolEntry && isValidTool(entry as AbstractToolEntry)»
					«addToolConfiguration(entry as ToolEntry)»
				«ENDIF»
			«ENDFOR»
		</ownedConfigurations>
	'''
}
