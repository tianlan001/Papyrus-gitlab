/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi  benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils.tests.tests;

import java.util.List;

import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

@PluginResource("resources/recursiveModelImport.di")
public class NameResolutionHelperTest extends AbstractPapyrusTest {

	public static final String PACKAGE1_NAME = "Package1"; //$NON-NLS-1$

	public static final String PACKAGE2_NAME = "Package2"; //$NON-NLS-1$

	public static final String CLASS1_NAME = "Class1"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "NameResolutionHelperTest"; //$NON-NLS-1$

	@Rule
	public final ModelSetFixture model = new ModelSetFixture();

	/**
	 * Check NameResolution on a recursive model
	 */
	@Test
	public void recursiveImportPackage() {
		Package rootUMLModel = model.getModel();
		Assert.assertNotNull("RootModel is null", rootUMLModel); //$NON-NLS-1$
		org.eclipse.uml2.uml.Package package1 = (org.eclipse.uml2.uml.Package) rootUMLModel.getPackagedElement(PACKAGE1_NAME);
		Assert.assertNotNull("The package 1 is not present", package1); //$NON-NLS-1$
		NameResolutionHelper nameResolutionHelper = new NameResolutionHelper(package1, null);
		List<NamedElement> namedElements = nameResolutionHelper.getNamedElements(PACKAGE1_NAME + NamedElementUtil.QUALIFIED_NAME_SEPARATOR + PACKAGE2_NAME + NamedElementUtil.QUALIFIED_NAME_SEPARATOR + CLASS1_NAME);
		Assert.assertNotNull("NamedElements is  null", namedElements); //$NON-NLS-1$
		Assert.assertFalse("NamedElements is empty", namedElements.isEmpty()); //$NON-NLS-1$
	}

}
