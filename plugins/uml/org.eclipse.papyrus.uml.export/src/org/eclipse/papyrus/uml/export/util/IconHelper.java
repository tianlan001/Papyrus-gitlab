/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.uml2.uml.Element;


/**
 * A helper class to provide a a way to get icons from UML element and diagrams.
 */
public class IconHelper {

	/** The Constant PREFIX_UML_PLATFORM_URL. */
	private static final String PREFIX_UML_PLATFORM_URL = "platform:/plugin/org.eclipse.uml2.uml.edit/icons/full/obj16/"; //$NON-NLS-1$

	/** The Constant UNKNOW_ICON. */
	public static final String UNKNOW_ICON = "unknow.jpg"; // TODO: also provide unknow diagram icon

	// key: diagram type
	/** The diagram kind id to icon name. */
	// icon name
	public static Map<String, String> diagramKindIdToIconName = new HashMap<>();

	// key: icon name
	/** The Icon name to platform URL. */
	// platform path to the icon
	public static Map<String, String> IconNameToPlatformURL = new HashMap<>();	
	
	
	/**
	 * Instantiates a new icon helper.
	 */
	public IconHelper() {
	}
	

	/**
	 * Gets the icon name.
	 *
	 * @param d the d
	 * @return the icon name
	 */
	public static String getIconName(Diagram d) {
		for (Object object : d.getStyles()) {
			if (object instanceof PapyrusDiagramStyle) {
				PapyrusDiagramStyle papyrusDiagramStyle = (PapyrusDiagramStyle) object;
				String diagramKindId = papyrusDiagramStyle.getDiagramKindId();
				String cachedValue = diagramKindIdToIconName.get(diagramKindId);
				if (cachedValue != null) {
					return cachedValue;
				}
				
				RepresentationKind representationKindbyId = getRepresentationKindbyId(diagramKindId);
				if (representationKindbyId != null) {
					String platformURL = representationKindbyId.getIcon();
					String iconName = FileUtil.getNameFromPlatformURL(platformURL);
					diagramKindIdToIconName.put(diagramKindId, iconName);
					IconNameToPlatformURL.put(iconName, platformURL);
					return iconName;
				}
			}
		}
		return UNKNOW_ICON;
	}

	/**
	 * Gets the icon name.
	 *
	 * @param element the element
	 * @return the icon name
	 */
	public static String getIconName(Element element) {
		String name = element.eClass().getName();
		String key = name+".gif";
		IconNameToPlatformURL.put(key, PREFIX_UML_PLATFORM_URL+key);	
		return key;
	}	
	
	
	/**
	 * Gets the representation kindby id.
	 *
	 * @param diagramKindId the diagram kind id
	 * @return the representation kindby id
	 */
	public static RepresentationKind getRepresentationKindbyId(String diagramKindId) {
		Collection<MergedArchitectureContext> visibleArchitectureContexts = ArchitectureDomainManager.getInstance()
				.getVisibleArchitectureContexts();
		for (MergedArchitectureContext mergedArchitectureContext : visibleArchitectureContexts) {
			if (mergedArchitectureContext instanceof MergedArchitectureDescriptionLanguage) {
				Collection<RepresentationKind> representationKinds = ((MergedArchitectureDescriptionLanguage) mergedArchitectureContext)
						.getRepresentationKinds();
				for (RepresentationKind representationKind : representationKinds) {
					if (representationKind.getId().equals(diagramKindId)) {
						return representationKind;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Prints the icon.
	 *
	 * @param targetContainer the target container
	 */
	public static void printIcon(IPath targetContainer) { // return boolean ?
		Set<Entry<String, String>> entrySet = IconNameToPlatformURL.entrySet();
		for (Entry<String, String> entry : entrySet) {
			FileUtil.copyFileFromPlatform(targetContainer, entry.getKey(), entry.getValue());
		}
	}
	

	
	
}
