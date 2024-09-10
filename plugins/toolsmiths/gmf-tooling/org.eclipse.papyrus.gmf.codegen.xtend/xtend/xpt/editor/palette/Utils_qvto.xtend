/*****************************************************************************
 * Copyright (c) 2006, 2017, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Artem Tikhomirov (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 510587
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor.palette

import com.google.inject.Singleton
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl
import org.eclipse.papyrus.gmf.codegen.gmfgen.AbstractToolEntry
import org.eclipse.papyrus.gmf.codegen.gmfgen.Palette
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolEntry
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry
import org.eclipse.papyrus.infra.types.ExternallyRegisteredType
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration
import org.eclipse.papyrus.infra.types.core.IConfiguredHintedElementType
import java.util.HashMap
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl
import com.google.inject.Inject
import java.util.Collection
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolGroup
import xpt.Common_qvto
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.Localization
import org.eclipse.papyrus.gmf.codegen.gmfgen.EntryBase

@Singleton class Utils_qvto {
	@Inject extension Common_qvto;

	def dispatch String activatorFQN(Palette palette) {
		return palette.diagram.editorGen.plugin.getActivatorQualifiedClassName()
	}

	def dispatch String activatorFQN(AbstractToolEntry entry) {
		return entry.group.palette.activatorFQN()
	}

	def dispatch String activatorFQN(ToolGroup group) {
		return group.palette.activatorFQN()
	}

	@Localization def String i18nKey(EntryBase group) {
		var rv = group.createMethodName;
		return switch(rv){
			case rv.startsWith("get") : rv.substringAfter("get")
			case rv.startsWith("create") : rv.substringAfter("create")
			default : rv
		}
	}

	@Localization def String i18nTitleKey(EntryBase group) {
		return i18nKey(group) + '_title'
	}

	@Localization def String i18nDescKey(EntryBase group) {
		return i18nKey(group) + '_desc'
	}

	def Iterable<ToolGroup> collectGroups(Palette palette) {
		var result = <ToolGroup>newLinkedHashSet()
		if (palette !== null) {
			result.addAll(palette.groups)
			for (group : palette.groups) {
				result.addAll(collectSubGroups(group))
			}
		}
		return result
	}

	def Iterable<ToolGroup> collectSubGroups(ToolGroup group) {
		return collectSubGroups(group, <ToolGroup>newLinkedList())
	}

	def Iterable<ToolGroup> collectSubGroups(ToolGroup group, Collection<ToolGroup> acc) {
		var directSubGroups = group.entries.filter(typeof(ToolGroup))
		if (!directSubGroups.empty) {
			acc.addAll(directSubGroups);
			for (next : directSubGroups) {
				collectSubGroups(next, acc)
			}
		}
		return acc;
	}

	def Iterable<AbstractToolEntry> collectTools(Palette palette) {
		return collectGroups(palette).map[g|g.entries.filter(typeof(AbstractToolEntry))].flatten.filter [ tool |
			isValidTool(tool)
		]
	}

	def boolean needsNodeToolEntryClass(Palette palette) {
		return collectTools(palette).filter(typeof(ToolEntry)).exists[e|e.genNodes.notEmpty]
	}

	def boolean needsLinkToolEntryClass(Palette palette) {
		return collectTools(palette).filter(typeof(ToolEntry)).exists[e|e.genLinks.notEmpty]
	}

	def boolean isValidTool(AbstractToolEntry entry) {

		if (entry instanceof ToolEntry) {
			if (null !== entry.qualifiedToolName && !entry.qualifiedToolName.empty) {
				return true;
			}

			val links = (entry as ToolEntry).genLinks.size;
			val nodes = (entry as ToolEntry).genNodes.size;
			return nodes + links != 0;
		} else {
			return true;
		}
	}

	def String getToolPath(String it) {
		var String result = null

		/** returns the '/' separator and then the id of the parent group path */
		if (isQuoted(it, '"')) {
			result = it.substring(1, it.length - 1)
		} else {
			result = it
		}

		return "/" + result;
	}

	def boolean isQuoted(String source, String quoteStr) {

		if (quoteStr === null) {
			return false;
		}

		return source.length() >= (quoteStr.length() * 2) && source.startsWith(quoteStr) && source.endsWith(quoteStr);

	}

	def getConstantIDName(String it) {
		if (isQuoted(it, '"')) {
			return getUpperAndUnderscoreString(it.substring(1, it.length() - 1))
		} else {
			return getUpperAndUnderscoreString(it)
		}
	}

	def String getUpperAndUnderscoreString(String value) {
		return value.toUpperCase.replace(".", "_")
	}

	/**
	 * The plateform protocol prefix.
	 */
	var static String PLUGIN_PROTOCOL = "platform:/plugin/"

	/**
	 * Retrieve the bundle id of a platform/plug-in path.
	 * 
	 * @param initialValue 
	 * 				The initial value from which the bundle has to be retrieved
	 * @param defaultValue
	 * 				The default bundle id value if bundle id not found
	 * @return the bundle id
	 */
	def String retrieveBundleId(String initialValue, String defaultValue) {
		var String result = null
		if (initialValue.startsWith(PLUGIN_PROTOCOL)) {
			var String tmp = initialValue.substring(PLUGIN_PROTOCOL.length())
			var int bundleIdEndIndex = tmp.indexOf("/")
			result = tmp.substring(0, bundleIdEndIndex)
		} else {
			result = defaultValue
		}
		return result
	}

	/**
	 * Retrieve the local path of a platform/plug-in path.
	 * 
	 * @param initialValue
	 *            the initial value from which the local path has to be retrieved
	 * @return the local path
	 */
	def String retrieveLocalPath(String initialValue) {
		var String result = ""
		if (initialValue.startsWith(PLUGIN_PROTOCOL)) {
			var String tmp = initialValue.substring(PLUGIN_PROTOCOL.length())
			var int bundleIdEndIndex = tmp.indexOf("/")
			result = tmp.substring(bundleIdEndIndex)
		} else {
			result = initialValue
		}
		return result
	}

	/**
	 * @return The relative path of the model's resource
	 * 
	 * @param it
	 *            the EObject to get the relative path

	 */
	def String getRelativePath(EObject it) {
		var String path = null;
		var URI resourceURI = eResource().getURI()
		if (resourceURI.isPlatformResource()) {
			var String platformString = resourceURI.toPlatformString(true)
			var IPath workspacePath = new Path(platformString)
			workspacePath = workspacePath.removeFirstSegments(1)
			workspacePath = workspacePath.removeLastSegments(1)
			path = workspacePath.toString()
		} else {
			path = "model"
		}
		return path;
	}

	/**
	 * Get the href string for the elementTypeConfiguration model of the unique identifier.
	 */
	def String getElementTypeConfHRef(String uniqueIdentifier, XMLResource resource) {

		//Load element type registry
		org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry.getInstance().getClass()

		var String href = null
		val type = ElementTypeRegistry.getInstance().getType(uniqueIdentifier)

		if (type instanceof IConfiguredHintedElementType) {
			val xmlHelper = new XMLHelperImpl(resource)
			val options = new HashMap<Object, Object>()
			//Set option to have platform:/ sheme instead of ../../..
			options.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware())
			xmlHelper.options = options;
			href = xmlHelper.getHREF(type.configuration as EObject)
		}
		return href;

	}

	/**
	 * Get the href string for the elementTypeConfiguration model of the unique identifier.
	 */
	def String getConfigurationXsiType(String uniqueIdentifier, XMLResource resource) {
		var String type = null
		val eltype = ElementTypeRegistry.getInstance().getType(uniqueIdentifier)
		if (eltype instanceof IConfiguredHintedElementType) {
			var configuration = eltype.configuration
			if (configuration instanceof SpecializationTypeConfiguration) {
				type = "elementtypesconfigurations:SpecializationTypeConfiguration"
			} else if (configuration instanceof MetamodelTypeConfiguration) {
				type = "elementtypesconfigurations:MetamodelTypeConfiguration"
			} else if (configuration instanceof ExternallyRegisteredType) {
				type = "elementtypesconfigurations:ExternallyRegisteredType"
			}
		}
		return type;

	}

}
