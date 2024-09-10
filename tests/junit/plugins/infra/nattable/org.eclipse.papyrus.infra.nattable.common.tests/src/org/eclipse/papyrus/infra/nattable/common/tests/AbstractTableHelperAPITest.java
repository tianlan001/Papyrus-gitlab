/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 535935
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.tests;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.common.api.ITableEditorStatusCode;
import org.eclipse.papyrus.infra.nattable.common.api.TableEditorCreationHelper;
import org.eclipse.papyrus.infra.nattable.common.api.TableEditorDeleteHelper;
import org.eclipse.papyrus.infra.nattable.common.api.TableEditorFinderHelper;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.junit.Assert;
import org.junit.Rule;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractTableHelperAPITest extends AbstractPapyrusTest {

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * 
	 * @param tableContext
	 *            the context to use for the table, cannot be <code>null</code>
	 * @param tableOwner
	 *            the owner to use for the table
	 * @param tableType
	 *            the type of the table to create, cannot be <code>null</code>
	 * @param openEditorAfterCreation
	 *            indicating if the editor must be opened after the creation
	 * @throws NotFoundException
	 * 
	 * @throws ServiceException
	 */
	protected void createTableEditor(final EObject tableContext, final EObject tableOwner, final String tableType, final boolean openEditorAfterCreation) throws NotFoundException, ServiceException {
		final IPageManager pageManager = fixture.getPageManager();
		final TransactionalEditingDomain domain = fixture.getEditingDomain();
		TableEditorCreationHelper helper = new TableEditorCreationHelper(tableContext, "", "", openEditorAfterCreation); //$NON-NLS-1$ //$NON-NLS-2$
		// 0. set the type of the table to create
		helper.setTableType(tableType);

		if (tableOwner != tableContext) {// we do not it when equals to get good test on the API.
			helper.setTableOwner(tableOwner);
		}
		// 1. create the name and the description for the created table
		String tableName = new StringBuilder(tableType).append("_name").toString(); //$NON-NLS-1$
		String tableDescription = new StringBuilder(tableType).append("_description").toString(); //$NON-NLS-1$

		// 2. set these values to the creation helper
		helper.setTableName(tableName);
		helper.setTableDescription(tableDescription);

		// 3. check the creation status
		IStatus res = helper.canCreateTable();
		Assert.assertTrue(createAssertMessage("creation status is not OK", tableType), res.isOK()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("creation status code is not ok", tableType), ITableEditorStatusCode.OK_STATUS_CODE, res.getCode()); //$NON-NLS-1$

		// 4. create the table
		int nbPageBeforeCreation = pageManager.allPages().size();
		Table createdTable = helper.createTableEditor();
		fixture.flushDisplayEvents();

		// 5. check the created table
		Assert.assertNotNull(createAssertMessage("The table has not been created", tableType), createdTable); //$NON-NLS-1$
		checkTableCreation(createdTable, tableContext, tableOwner, tableType, tableName, tableDescription, nbPageBeforeCreation, openEditorAfterCreation);

		// 6. check undo table creation
		domain.getCommandStack().undo();
		fixture.flushDisplayEvents();
		checkTableDeletion(createdTable, tableType, nbPageBeforeCreation + 1);

		// 7. check redo table creation
		domain.getCommandStack().redo();
		fixture.flushDisplayEvents();
		checkTableCreation(createdTable, tableContext, tableOwner, tableType, tableName, tableDescription, nbPageBeforeCreation, openEditorAfterCreation);

		// 8. destroy the table
		int nbPagesBeforeDeletion = fixture.getPageManager().allPages().size();
		TableEditorDeleteHelper deletionHelper = new TableEditorDeleteHelper(createdTable);
		res = deletionHelper.canDeleteTableEditor();
		Assert.assertTrue(createAssertMessage("deletion status is not OK", tableType), res.isOK()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("deletion status code is not OK", tableType), ITableEditorStatusCode.OK_STATUS_CODE, res.getCode()); //$NON-NLS-1$
		deletionHelper.deleteTableEditor();
		fixture.flushDisplayEvents();
		checkTableDeletion(createdTable, tableType, nbPagesBeforeDeletion);

		// 9. check undo deletion
		domain.getCommandStack().undo();
		fixture.flushDisplayEvents();
		checkTableCreation(createdTable, tableContext, tableOwner, tableType, tableName, tableDescription, nbPageBeforeCreation, openEditorAfterCreation);

		// 10. check redo deletion
		domain.getCommandStack().redo();
		fixture.flushDisplayEvents();
		checkTableDeletion(createdTable, tableType, nbPagesBeforeDeletion);
	}


	/**
	 * 
	 * @param createdTable
	 *            the created table, cannot be <code>null</code>
	 * @param context
	 *            the wanted context for the created table
	 * @param owner
	 *            the wanted owner for the created table
	 * @param tableType
	 *            the wanted type for the created table
	 * @param tableName
	 *            the wanted table for the created table
	 * @param tableDescription
	 *            the wanted description for the created table
	 * @param nbPageBeforeCreation
	 *            the number of pages available in the page manager before the creation of the table
	 * @throws NotFoundException
	 * @throws ServiceException
	 * 
	 * @deprecated since 5.1, we must use the same method, with an additional parameter
	 */
	@Deprecated
	protected void checkTableCreation(final Table createdTable, final EObject context, final EObject owner, final String tableType, final String tableName, final String tableDescription, final int nbPageBeforeCreation)
			throws NotFoundException, ServiceException {
		Assert.assertEquals(createAssertMessage("the created table is not in the page manager", tableType), nbPageBeforeCreation + 1, fixture.getPageManager().allPages().size()); //$NON-NLS-1$

		// 1. we check its creation
		checkTable(createdTable, context, owner, tableType, tableName, tableDescription);

		// 2. we try to found it
		findTable(createdTable, context, owner, tableType, tableName);

		// 3. we check if the table is opened
		isOpened(createdTable, true, tableType);
	}

	/**
	 * 
	 * @param createdTable
	 *            the created table, cannot be <code>null</code>
	 * @param context
	 *            the wanted context for the created table
	 * @param owner
	 *            the wanted owner for the created table
	 * @param tableType
	 *            the wanted type for the created table
	 * @param tableName
	 *            the wanted table for the created table
	 * @param tableDescription
	 *            the wanted description for the created table
	 * @param nbPageBeforeCreation
	 *            the number of pages available in the page manager before the creation of the table
	 * @param mustBeOpen
	 *            <code>true</code> if the table must be open after its creation
	 * @throws NotFoundException
	 * @throws ServiceException
	 * @since 5.1
	 */
	protected void checkTableCreation(final Table createdTable, final EObject context, final EObject owner, final String tableType, final String tableName, final String tableDescription, final int nbPageBeforeCreation, boolean mustBeOpen)
			throws NotFoundException, ServiceException {
		Assert.assertEquals(createAssertMessage("the created table is not in the page manager", tableType), nbPageBeforeCreation + 1, fixture.getPageManager().allPages().size()); //$NON-NLS-1$

		// 1. we check its creation
		checkTable(createdTable, context, owner, tableType, tableName, tableDescription);

		// 2. we try to found it
		findTable(createdTable, context, owner, tableType, tableName);

		// 3. we check if the table is opened
		isOpened(createdTable, mustBeOpen, tableType);
	}

	/**
	 * 
	 * @param deletedTable
	 *            the table which must be deleted
	 * @param tableType
	 *            the type of the deleted table
	 * @param nbPagesBeforeDeletion
	 *            the number of editor known by the page manager before the deletion of the table
	 */
	protected void checkTableDeletion(final Table deletedTable, final String tableType, int nbPagesBeforeDeletion) {
		Assert.assertEquals(createAssertMessage("The deleted table is always in the page manager", tableType), nbPagesBeforeDeletion - 1, fixture.getPageManager().allPages().size()); //$NON-NLS-1$

		// the context is not deleted by the undo after the table creation, the main thing is to be sure than the table is not in a resource.
		// Assert.assertNull(createAssertMessage("the context of the table has not been deleted", current), deletedTable.getContext());

		Assert.assertNull(createAssertMessage("the owner of the table has not been deleted", tableType), deletedTable.getOwner()); //$NON-NLS-1$
		Assert.assertNull(createAssertMessage("the resource continues to reference the table after deletion", tableType), deletedTable.eResource()); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param errorMessage
	 *            the error message
	 * @param tableType
	 *            the type of the table
	 * @return
	 * 		the error message to display
	 */
	protected String createAssertMessage(String errorMessage, String tableType) {
		return NLS.bind("Table Type: {0} - {1}", tableType, errorMessage); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param table
	 *            the table
	 * @param shouldBeOpened
	 *            indicates if the table must be opened (<code>true</code>) or closed (false)
	 * @param tableType
	 *            the type of the table
	 */
	protected void isOpened(Table table, boolean shouldBeOpened, final String tableType) {
		Assert.assertEquals(createAssertMessage("The table status (open/closed) is not the wanted one", tableType), shouldBeOpened, this.fixture.getPageManager().isOpen(table)); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param tableToCheck
	 *            the table to check, cannot be <code>null</code>
	 * @param tableContext
	 *            the wanted context of the table
	 * @param tableOwner
	 *            the wanted owner of the table
	 * @param tableType
	 *            the wanted type of the table
	 * @param tableName
	 *            the wanted name of the table
	 * @param tableDescription
	 *            the wanted description of the table
	 */
	protected void checkTable(final Table tableToCheck, final EObject tableContext, final EObject tableOwner, final String tableType, final String tableName, final String tableDescription) {
		Assert.assertEquals(createAssertMessage("The name is not correct", tableType), tableName, tableToCheck.getName()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The description is not correct", tableType), tableDescription, tableToCheck.getDescription()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The context is not correct", tableType), tableContext, tableToCheck.getContext()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The owner is not correct", tableType), tableOwner, tableToCheck.getOwner()); //$NON-NLS-1$

		// we check the type
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		EObject proto = manager.getRepresentationKindById(tableToCheck.getTableKindId());

		Assert.assertTrue(createAssertMessage("The view prototype seems not valid for a table", tableType), proto instanceof PapyrusTable); //$NON-NLS-1$
		String implementation = ((PapyrusRepresentationKind) proto).getImplementationID();
		if (implementation == null || implementation.isEmpty()) {
			implementation = tableToCheck.getTableConfiguration().getType();
		}
		Assert.assertEquals(createAssertMessage("The type is not correct", tableType), tableType, implementation); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param tableToCheck
	 *            the table to find, cannot be <code>null</code>
	 * @param tableContext
	 *            the table context, cannot be <code>null</code>
	 * @param tableOwner
	 *            the table owner, cannot be <code>null</code>
	 * @param tableType
	 *            the table type, cannot be <code>null</code>
	 * @param tableName
	 *            the table name, cannot be <code>null</code>
	 * @throws NotFoundException
	 * @throws ServiceException
	 * 
	 * 
	 * 
	 */
	protected void findTable(final Table tableToCheck, final EObject tableContext, final EObject tableOwner, final String tableType, final String tableName) throws NotFoundException, ServiceException {
		TableEditorFinderHelper finder = new TableEditorFinderHelper(tableContext);
		Assert.assertNotNull(tableToCheck);
		Assert.assertNotNull(tableOwner);
		Assert.assertNotNull(tableContext);
		Assert.assertNotNull(tableType);
		Assert.assertNotNull(tableName);
		List<Table> matchingTable = finder.findMatchingTables(tableContext, tableOwner, tableType, tableName);

		Assert.assertEquals(createAssertMessage("No table found using name, type, owner and context", tableType), 1, matchingTable.size()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The found table is not the good one using name, type, owner and context", tableType), tableToCheck, matchingTable.get(0)); //$NON-NLS-1$


		matchingTable = finder.findTablesByContext(tableContext);
		Assert.assertEquals(createAssertMessage("No table found using the context", tableType), 1, matchingTable.size()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The found table is not the good one using the context", tableType), tableToCheck, matchingTable.get(0)); //$NON-NLS-1$

		matchingTable = finder.findTablesByOwner(tableOwner);
		Assert.assertEquals(createAssertMessage("No table found using the owner", tableType), 1, matchingTable.size()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The found table is not the good one using the owner", tableType), tableToCheck, matchingTable.get(0)); //$NON-NLS-1$

		matchingTable = finder.findTablesByType(tableType);
		Assert.assertEquals(createAssertMessage("No table found using the type", tableType), 1, matchingTable.size()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The found table is not the good one using the type", tableType), tableToCheck, matchingTable.get(0)); //$NON-NLS-1$

		matchingTable = finder.findTablesByName(tableName);
		Assert.assertEquals(createAssertMessage("No table found using the name", tableType), 1, matchingTable.size()); //$NON-NLS-1$
		Assert.assertEquals(createAssertMessage("The found table is not the good one using the name", tableType), tableToCheck, matchingTable.get(0)); //$NON-NLS-1$
	}
}
