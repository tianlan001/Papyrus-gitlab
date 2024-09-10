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
 *****************************************************************************/

package org.eclipse.papyrus.infra.newchild.tests;

import java.util.List;

import org.eclipse.papyrus.infra.newchild.CreationMenuRegistry;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Assert;
import org.junit.Test;

public class NewChildExtensionPointTests extends AbstractPapyrusTest {
	/**
	 * Test of deployment of a creation menu model throw the new child extension point.
	 * 
	 * @throws Exception
	 */
	@Test
	public void NewChildExtensionPointTest() throws Exception {
		Assert.assertTrue("The creation menu is not load with throw the new child extension point", isDeployed());
	}

	/**
	 * @return true if the creation menu is deployed
	 */
	private Boolean isDeployed() {
		boolean deployed = false;
		List<Folder> rootFolder = CreationMenuRegistry.getInstance().getRootFolder();

		for (Folder folder : rootFolder) {
			if ("NewChildExtensionPointTest".equals(folder.getLabel())) {
				// The fold is well present
				for (Menu menu : folder.getMenu()) {
					if ("MyComponent".equals(menu.getLabel())) {
						// The expected tool is present
						deployed = true;
					}
				}
			}
		}
		return deployed;
	}
}
