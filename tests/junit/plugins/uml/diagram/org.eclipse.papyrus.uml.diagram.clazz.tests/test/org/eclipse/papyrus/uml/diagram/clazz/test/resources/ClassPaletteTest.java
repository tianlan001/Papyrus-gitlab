/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.resources;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ChildConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.LeafConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.StackConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Check palette for class diagram
 */
@SuppressWarnings("nls")
public class ClassPaletteTest {

	@Before
	public void loadElementTypeRegistry() {
		ElementTypeSetConfigurationRegistry.getInstance();
	}

	/**
	 * Check that association isn't overridden by regeneration
	 */
	@Test
	public void checkPaletteIds() {
		URI createPlatformPluginURI = URI.createPlatformPluginURI("org.eclipse.papyrus.uml.diagram.clazz/model/PapyrusUMLClassDiagram.paletteconfiguration", true);
		ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		Resource resource = resourceSetImpl.getResource(createPlatformPluginURI, true);

		TreeIterator<EObject> allContents = resource.getAllContents();
		while (allContents.hasNext()) {
			EObject eObject = (EObject) allContents.next();
			if (eObject instanceof PaletteConfiguration) {
				PaletteConfiguration p = (PaletteConfiguration) eObject;
				EList<DrawerConfiguration> drawerConfigurations = p.getDrawerConfigurations();
				for (DrawerConfiguration drawerConfiguration : drawerConfigurations) {
					EList<ChildConfiguration> ownedConfigurations = drawerConfiguration.getOwnedConfigurations();
					for (ChildConfiguration childConfiguration : ownedConfigurations) {

						if (childConfiguration instanceof StackConfiguration) {
							StackConfiguration stackConfiguration = (StackConfiguration) childConfiguration;

							EList<LeafConfiguration> leafs = stackConfiguration.getOwnedConfigurations();
							for (LeafConfiguration leafConfiguration : leafs) {
								if (leafConfiguration instanceof ToolConfiguration) {
									ToolConfiguration toolConfiguration = (ToolConfiguration) leafConfiguration;
									EList<ElementDescriptor> elementDescriptors = toolConfiguration.getElementDescriptors();
									for (ElementDescriptor elementDescriptor : elementDescriptors) {
										ElementTypeConfiguration elementType = elementDescriptor.getElementType();
										Assert.assertNotEquals("Association_Edge shouldn't be refered in Palette check class plugin README.md", "org.eclipse.papyrus.umldi.Association_Edge", elementType.getIdentifier());
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
