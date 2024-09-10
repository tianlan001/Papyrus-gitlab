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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Assert;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractCreateElementTableTest extends AbstractTableTest {

	/**
	 * the number of owned element before the creation
	 */
	private static final int NB_OWNED_ELEMENT_BEFORE_CREATION = 0;

	/**
	 * the number of owned element after the creation
	 */
	private static final int NB_OWNED_ELEMENT_AFTER_CREATION = 1;

	private static final String SOURCE_PATH = "resources/creation/outside"; //$NON-NLS-1$
	
	
	/**
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return SOURCE_PATH;
	}


	/**
	 * 
	 * @return
	 * 		the name of the file which contains the wanted contents of the clipboard after the copy to clipboard
	 */
	protected String getResultFileName() {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}

	protected void createElement(final Namespace context, final IElementType elementType, int nbRowOpeningTable, int nbRowsAfterExpandAll) {
		checkInitialTableState(context, nbRowOpeningTable);

		checkCreation(context, nbRowsAfterExpandAll, elementType );

		// check the result after the creation AND the expand all
		checkUndoCreation(context, nbRowOpeningTable);

		// redo
		checkRedoCreation(context, nbRowsAfterExpandAll);
	}

	/**
	 * 
	 * @param context
	 *            the context of the table
	 * @param nbRowOpeningTable
	 *            the number of rows after opening the table and expand all
	 */
	protected void checkInitialTableState(final Namespace context, int nbRowOpeningTable) {
		Assert.assertEquals(nbRowOpeningTable, this.manager.getRowElementsList().size());

		// check the intial state after opening and expand all
		Assert.assertEquals(NB_OWNED_ELEMENT_BEFORE_CREATION, context.getOwnedMembers().size());

		// if the context is the root of the model, the table will be empty. In this case, the clipboard is not filled
		if (context.getOwner() != null) {
			compareCurrentDisplayToWantedDisplay(getResultFileNameAfterOpeningAndExpandAll());
		}
	}

	protected void checkCreation(Namespace context, int nbRowsAfterCreationAndExpandAll, IElementType elementType) {
		TransactionalEditingDomain domain = getTableContextEditingDomain();
		// create a child to the element
		CreateElementRequest request = new CreateElementRequest(domain, context, elementType);
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(context);
		ICommand cmd = provider.getEditCommand(request);
		Assert.assertTrue(cmd.canExecute());
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();

		// check the display after the creation and before the expand all
		Assert.assertEquals(NB_OWNED_ELEMENT_AFTER_CREATION, context.getOwnedMembers().size());
		Assert.assertEquals(nbRowsAfterCreationAndExpandAll, manager.getRowElementsList().size());
		compareCurrentDisplayToWantedDisplay(getResultFileNameAfterCreationAndExpandAll());
	}

	protected void checkUndoCreation(final Namespace context, int nbRowOpeningTable) {
		// undo
		TransactionalEditingDomain domain = getTableContextEditingDomain();
		domain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check the result after the undo
		Assert.assertEquals(NB_OWNED_ELEMENT_BEFORE_CREATION, context.getOwnedMembers().size());
		// if the context is the root of the model, the table will be empty. In this case, the clipboard is not filled
		if (context.getOwner() != null) {
			compareCurrentDisplayToWantedDisplay(getResultFileNameAfterOpeningAndExpandAll());
		}
	}

	protected void checkRedoCreation(Namespace context, int nbElementAfterCreationAndExpandAll) {
		TransactionalEditingDomain domain = getTableContextEditingDomain();
		domain.getCommandStack().redo();
		this.manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		// check the display after the creation and before the expand all
		Assert.assertEquals(NB_OWNED_ELEMENT_AFTER_CREATION, context.getOwnedMembers().size());
		Assert.assertEquals(nbElementAfterCreationAndExpandAll, manager.getRowElementsList().size());
		compareCurrentDisplayToWantedDisplay(getResultFileNameAfterCreationAndExpandAll());
	}

	protected String getResultFileNameAfterOpeningAndExpandAll() {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append("_AfterOpeningAndExpandAll"); //$NON-NLS-1$
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}

	protected String getResultFileNameAfterCreationAndExpandAll() {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append("_AfterCreationAndExpandAll"); //$NON-NLS-1$
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}
}
