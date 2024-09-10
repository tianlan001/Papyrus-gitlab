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
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * This class checks an existing matrix after opening it.
 *
 */
@PluginResource("resources/openDependencyMatrixTests/open_matrix.di")
public class OpenDependencyMatrixTest extends AbstractTableTest {

	/**
	 * the number of classes in the tested model
	 */
	private static final int NB_CLASSES = 7;

	/**
	 * the number of columns in the tested matrix
	 */
	private static final int NB_COLUMNS = NB_CLASSES;

	/**
	 * the number of rows in the tested matrix
	 */
	private static final int NB_ROWS = NB_CLASSES + 2; // +1 for the context package and +1 for the TreeFillingConfiguration

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	protected String getSourcePath() {
		return "resources/openDependencyMatrixTests/"; //$NON-NLS-1$
	}

	/**
	 * This JUnit tests check the opening of an existing matrix
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void testOpeningMatrix() {
		initTest();
		final List<Object> rowElementsList = this.manager.getRowElementsList();
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of rows is not the expected one.", NB_ROWS, rowElementsList.size()); //$NON-NLS-1$
		Assert.assertEquals("The number of columns is not the expected one.", NB_COLUMNS, columnElementsList.size()); //$NON-NLS-1$
	}

	/**
	 * This JUnit tests check the opening of an existing matrix
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void testMatrixContents() throws Exception {
		initTest();
		checkTableContent(manager, "");// no suffix here //$NON-NLS-1$
	}
}
