/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.newchild.tests;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.newchild.CreationMenuRegistry;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class to test deploy an undeploy of creation menu model.
 */
public class DeployUndeployTests extends AbstractPapyrusTest {

	/**
	 * Test of deployment and undeployment of a creation menu model.
	 * 
	 * @throws Exception
	 */
	@Test
	public void DeployUndeployTest() throws Exception {

		URI creationMenuModelURI = URI.createPlatformPluginURI("org.eclipse.papyrus.infra.newchild.tests/models/DeployUndeployTests.creationmenumodel", false);

		// Test load action
		CreationMenuRegistry.getInstance().loadCreationMenuModel(creationMenuModelURI);
		Assert.assertTrue("The creation menu is not deployed", isDeployed());

		// Test unload action
		CreationMenuRegistry.getInstance().unloadCreationMenuModel(creationMenuModelURI);
		Assert.assertFalse("The creation menu is not undeployed", isDeployed());

		// Test deploy handler
		// TODO with rcptt when available with undo/redo

		// Test undeploy handler
		// TODO with rcptt when available with undo/redo

	}

	/**
	 * @return true if the creation menu is deployed
	 */
	private Boolean isDeployed() {
		boolean deployed = false;
		List<Folder> rootFolder = CreationMenuRegistry.getInstance().getRootFolder();

		for (Folder folder : rootFolder) {
			if ("DeployUndeployTest".equals(folder.getLabel())) {
				// The fold is well present
				for (Menu menu : folder.getMenu()) {
					if ("MyClass".equals(menu.getLabel())) {
						// The expected tool is present
						deployed = true;
					}
				}
			}
		}
		return deployed;
	}
}
