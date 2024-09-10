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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.creation.outside;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
public class AbstractCreateElementDepth1_Test extends AbstractCreateElementTableTest {

	private static final String CLASS1 = "Class1"; //$NON-NLS-1$

	private static final int NB_ROWS_BEFORE_CREATION = 2;
	
	private static final int NB_ROWS_AFTER_CREATION = 4;
	
	/**
	 * this test check the content and the display of the table after a creation of an element outside of the table
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void createOperationElement() {
		startTest();
		Table table = this.manager.getTable();
		EObject context = table.getContext();
		Assert.assertTrue(context instanceof Model);
		Model model = (Model) context;
		Class clazz = (Class) model.getMember(CLASS1);
		Assert.assertNotNull(clazz);
		createElement(clazz, UMLElementTypes.OPERATION, NB_ROWS_BEFORE_CREATION, NB_ROWS_AFTER_CREATION);
	}

}
