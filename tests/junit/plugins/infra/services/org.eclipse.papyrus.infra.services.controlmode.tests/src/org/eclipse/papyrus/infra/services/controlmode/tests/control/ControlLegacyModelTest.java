/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.controlmode.tests.control;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.PackageableElement;
import org.junit.Test;

/**
 * Test of Control feature with Legacy model.
 * 
 * @author Gabriel Pascual
 *
 */
@PluginResource("model/ControlModeLegacyModelTest/model.di")
public class ControlLegacyModelTest extends AbstractControlModeTest {

	@Test
	public void testControlLegacyModel() throws Exception {

		desactivateDialog();
		ControlModeAssertion controlModeRunnable = new ControlModeAssertion("C") {

			/**
			 * @see org.eclipse.papyrus.infra.services.controlmode.tests.control.AbstractControlModeTest.ControlModeAssertion#assertAfterSave()
			 *
			 */
			@Override
			protected void assertAfterSave() {
				PackageableElement controlElement = (PackageableElement) getElementToControl();
				Resource resource = ((ModelSet) editorFixture.getResourceSet()).getAssociatedResource(controlElement, DiModel.DI_FILE_EXTENSION, true);
				assertFalse(resource.getContents().isEmpty());
				assertNotNull(DiUtils.lookupSashWindowsMngr(resource));

			}
		};

		controlModeRunnable.testControl();

	}




}
