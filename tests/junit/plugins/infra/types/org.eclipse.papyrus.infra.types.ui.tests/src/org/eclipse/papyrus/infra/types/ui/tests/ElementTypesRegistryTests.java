/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 431953 (fix test to prevent Model Repair dialog on unrecognized profile schema)
 *  Christian W. Damus (CEA) - bug 434993
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.ui.tests;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.infra.types.tests.AbstractElementTypeTests;
import org.eclipse.papyrus.infra.types.tests.ITestConstants;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Tests for {@link ElementTypeSetConfigurationRegistry}
 */
public class ElementTypesRegistryTests extends AbstractElementTypeTests implements ITestConstants {

	protected static IFile workspaceTestFile;

	/**
	 * Init test class
	 */
	@BeforeClass
	public static void initCreateElementTest() {
		// import user-defined element types model
		try {
			Bundle bundle = FrameworkUtil.getBundle(ElementTypesRegistryTests.class);
			workspaceTestFile = PapyrusProjectUtils.copyIFile("/model/WorkspaceTest.typesconfigurations", bundle, createProject, "/model/WorkspaceTest.typesconfigurations");
			Assert.assertTrue(workspaceTestFile.isAccessible());
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test registration based on plugin declaration
	 */
	@Test
	// transcoded from extended types framework
	public final void testGetTypeRegisteredInplugin() {
		// check standard class
		IElementType classType = ElementTypeRegistry.getInstance().getType(ORG_ECLIPSE_PAPYRUS_UML_CLASS);
		Assert.assertNotNull("Element type should be registered", classType);
		IElementType abstractClassType = ElementTypeRegistry.getInstance().getType(ABSTRACT_CLASS_TOOL);
		Assert.assertNotNull("Element type should be registered", abstractClassType);
	}

	/**
	 * Test workspace registration. Element type in the workspace, but element type set is not loaded
	 */
	@Test
	// transcoded from extended types framework
	public final void testGetTypeRegisteredInWorkspaceNotLoaded() {
		checkPluginTypes();
		// this should not be defined yet
		IElementType workspaceType = ElementTypeRegistry.getInstance().getType(WORKSPACE_ELEMENT_TYPE_TOOL);
		Assert.assertNull("Element type should not be registered yet", workspaceType);
	}

	/**
	 * Test workspace registration. Element type in the workspace, but element type set is not loaded
	 */
	@Test
	// transcoded from extended types framework
	public final void testLoadUnloadWorkspaceSet() {
		// this should not be defined yet
		IElementType workspaceType = ElementTypeRegistry.getInstance().getType(WORKSPACE_ELEMENT_TYPE_TOOL);
		Assert.assertNull("Element type should not be registered yet", workspaceType);
		checkPluginTypes();
		// register
		try {
			ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfiguration(TypeContext.getDefaultContext().getId(), workspaceTestFile.getFullPath().toString());

			//
			workspaceType = ElementTypeRegistry.getInstance().getType(WORKSPACE_ELEMENT_TYPE_TOOL);
			Assert.assertNotNull("Element type should be registered", workspaceType);
			checkPluginTypes();
			// unregister
			ElementTypeSetConfigurationRegistry.getInstance().unload(TypeContext.getDefaultContext().getId(), WORKSPACE_ELEMENT_TYPE_ID);
			workspaceType = ElementTypeRegistry.getInstance().getType(WORKSPACE_ELEMENT_TYPE_TOOL);
			Assert.assertNull("Element type should not be registered anymore", workspaceType);
			checkPluginTypes();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check always present element types are registered
	 */
	private void checkPluginTypes() {
		IElementType classType = ElementTypeRegistry.getInstance().getType(ORG_ECLIPSE_PAPYRUS_UML_CLASS);
		Assert.assertNotNull("Element type should be registered", classType);
		IElementType abstractClassType = ElementTypeRegistry.getInstance().getType(ABSTRACT_CLASS_TOOL);
		Assert.assertNotNull("Element type should be registered", abstractClassType);
	}


}
