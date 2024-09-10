/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Vincent Lorenzo
 *         This class tests the API defined for tables (creation, deletion and find)
 */
@PluginResource("resources/api/model.di")
public class TableHelperAPITests extends AbstractTableHelperAPITest {

	/**
	 * the type of the tested table
	 */
	private final List<String> tableCreationToTest = new ArrayList<String>();

	/**
	 * the root of the model
	 */
	private Package model;

	/**
	 * the subpackage
	 */
	private Package subPackage;

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		this.model = fixture.getModel();
		this.subPackage = (Package) this.model.getMember("NestedPackage"); //$NON-NLS-1$
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$
		Assert.assertNotNull("subpackage is null", subPackage); //$NON-NLS-1$

		// these can always be created in Papyrus, using a SysML Model an a
		// Package
		tableCreationToTest.add("PapyrusClassTreeTable"); //$NON-NLS-1$
		tableCreationToTest.add("PapyrusUMLGenericTreeTable"); //$NON-NLS-1$
		tableCreationToTest.add("PapyrusGenericTable"); //$NON-NLS-1$
	}

	/**
	 * 
	 * Check the table creation using the same value for context and owner and open the created editor
	 * 
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Test
	public void createTableEditorWithSameContextAndOwnerAndOpenIt() throws NotFoundException, ServiceException {
		for (String tableType : this.tableCreationToTest) {
			createTableEditor(model, model, tableType, true);
		}
	}

	/**
	 * 
	 * Check the table creation using the same value for context and owner and don't open the created editor
	 * 
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Test
	public void createTableEditorWithSameContextAndOwnerWithoutOpenIt() throws NotFoundException, ServiceException {
		for (String tableType : this.tableCreationToTest) {
			createTableEditor(model, model, tableType, false);
		}
	}

	/**
	 * 
	 * Check the table creation using a value for context and a different value for owner and open the created editor
	 * 
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Test
	public void createTableEditorWithDifferentContextAndOwnerAndOpenIt() throws NotFoundException, ServiceException {
		for (String tableType : this.tableCreationToTest) {
			createTableEditor(model, subPackage, tableType, true);
		}
	}

	/**
	 * 
	 * Check the table creation using a value for context and a different value for owner and don't open the created editor
	 * 
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Test
	public void createTableEditorWithDifferentContextAndOwnerWithoutOpenIt() throws NotFoundException, ServiceException {
		for (String tableType : this.tableCreationToTest) {
			createTableEditor(model, subPackage, tableType, false);
		}
	}

}
