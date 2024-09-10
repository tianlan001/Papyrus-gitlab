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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperFactory;
import org.eclipse.papyrus.infra.nattable.nattableconfiguration.NattableConfigurationRegistry;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 *
 */
@PluginResource("resources/createMatrixDependencyTests/create_matrix.di")
public class CreateMatrixTableTests extends AbstractTableTest {

	/**
	 * the number of classes in the tested model
	 */
	private static final int NB_CLASSES = 7;

	/**
	 * The number of dependencies in the model
	 */
	private static final int NB_DEPENDENCIES = 4;

	/**
	 * the number of columns in the tested matrix
	 */
	private static final int NB_COLUMNS = NB_CLASSES;

	/**
	 * the number of rows in the tested matrix
	 */
	private static final int NB_ROWS = NB_CLASSES + 2; // +1 for the context package and +1 for the TreeFillingConfiguration

	/**
	 * the name of the created matrix
	 */
	private static final String CREATED_MATRIX_NAME = "NewMatrix"; //$NON-NLS-1$

	/**
	 * the kind of the created matrix
	 */
	private static final String MATRIX_TABLE_TYPE = "UMLGenericMatrixOfRelationships"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return "resources/createMatrixDependencyTests/"; //$NON-NLS-1$
	}

	/**
	 * This test try to create an empty matrix, then it configure its sources for rows and columns and check them
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateANewMatrix() throws Exception {
		final Package root = this.fixture.getModel();
		TableConfiguration configuration = NattableConfigurationRegistry.INSTANCE.getConfiguration(MATRIX_TABLE_TYPE);
		createAndOpenEditorTable(root, configuration, CREATED_MATRIX_NAME);
		final INattableModelManager manager = fixture.getActiveTableManager();
		Assert.assertNotNull("The created matrix has not be open", manager); //$NON-NLS-1$
		final Table table = manager.getTable();
		Assert.assertEquals("The current table is not the expected one", CREATED_MATRIX_NAME, manager.getTable().getName()); //$NON-NLS-1$
		final List<Object> rowElementsList = manager.getRowElementsList();
		final List<Object> columnElementsList = manager.getColumnElementsList();
		Assert.assertEquals("There are rows just after the matrix creation.", 0, rowElementsList.size()); //$NON-NLS-1$
		Assert.assertEquals("There are columns just after the matrix creation.", 0, columnElementsList.size()); //$NON-NLS-1$

		RecordingCommand rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				EObjectWrapper rowSourceWrapper = NattablewrapperFactory.eINSTANCE.createEObjectWrapper();
				rowSourceWrapper.setElement(root);

				EObjectWrapper columnSourceWrapper = NattablewrapperFactory.eINSTANCE.createEObjectWrapper();
				columnSourceWrapper.setElement(root);

				// add rows
				((IMasterAxisProvider) table.getCurrentRowAxisProvider()).getSources().add(rowSourceWrapper);
				EObjectTreeItemAxis axis = NattableaxisFactory.eINSTANCE.createEObjectTreeItemAxis();
				axis.setElement(root);
				final Command tmp = manager.getAddRowElementCommand(Collections.singleton(root));
				tmp.execute();


				// add columns
				((IMasterAxisProvider) table.getCurrentColumnAxisProvider()).getSources().add(columnSourceWrapper);


			}
		};

		fixture.getEditingDomain().getCommandStack().execute(rc);

		((TreeNattableModelManager) manager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		Assert.assertEquals("The number of rows is not the expected one.", NB_ROWS + NB_DEPENDENCIES, rowElementsList.size()); //$NON-NLS-1$
		Assert.assertEquals("The number of columns is not the expected one.", NB_COLUMNS + NB_DEPENDENCIES, columnElementsList.size()); //$NON-NLS-1$
	}

}
