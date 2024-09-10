/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests;

import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * @author Vincent Lorenzo
 *
 */
public class OpenTableTest extends AbstractOpenTableTest {

	@Before
	public void initModel() throws Exception {
		initModel("classTreeTable", "openTest", getBundle()); //$NON-NLS-1$ //$NON-NLS-2$
	};

	/**
	 * This test allows to be sure that we doesn't break existing table model
	 *
	 * @throws Exception
	 */
	@Test
	public void testOpenExistingTable() throws Exception {
		testOpenExistingTable("classTreeTable", "openTest"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected String getSourcePath() {
		return "/resources/"; //$NON-NLS-1$
	}

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}

}
