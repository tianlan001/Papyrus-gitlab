/*******************************************************************************
 * Copyright (c) 2009, 2010 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 331203 - table model editor - initial API and implementation
 *    Nicolas Guyomar (Mia-Software) - Bug 332924 - To be able to save the table
 *    Nicolas Guyomar (Mia-Software) - Bug 333029 - To be able to save the size of the lines and the columns
 *******************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.utils;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * Comes from org.eclipse.emf.facet.widget.nattable.workbench.
 *
 * This class should not be used by others plugins
 */
public class TableEditorInput implements IEditorInput {

	/** The input for the Table widget */
	private final Table tableInstance;

	/** A textual description of what is shown in the editor */
	private final String description;

	private final TransactionalEditingDomain editingDomain;

	public TableEditorInput(final Table papyrusTable, final TransactionalEditingDomain editingDomain) {
		this.tableInstance = papyrusTable;
		this.description = papyrusTable.getDescription();
		this.editingDomain = editingDomain;
	}

	public Table getPapyrusTableInstance() {
		return this.tableInstance;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Model Table"; //$NON-NLS-1$
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Model Table"; //$NON-NLS-1$
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		return null;
	}

	protected TransactionalEditingDomain getEditingDomain() {
		return this.editingDomain;
	}
}
