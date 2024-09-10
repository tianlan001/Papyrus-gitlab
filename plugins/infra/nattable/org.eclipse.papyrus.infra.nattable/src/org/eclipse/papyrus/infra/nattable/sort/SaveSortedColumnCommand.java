/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.sort;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;
import org.eclipse.papyrus.infra.nattable.sort.copy.NatTableComparatorChooser;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;

/**
 * This command is used to save the sort of the colum
 *
 * @since 7.0
 *
 */
public class SaveSortedColumnCommand extends AbstractTransactionalCommand {

	/**
	 * the manager of the edited trable
	 */
	private final INattableModelManager manager;

	/**
	 * the current comparator chooser
	 */
	private final NatTableComparatorChooser<?> chooser;

	/**
	 * the indexes representing the previous sorted columns
	 */
	private final List<Integer> previousSortedColumns;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain
	 * @param manager
	 *            the table manager
	 * @param chooser
	 *            the comparator chooser
	 * @param previousSortedColumns
	 *            the indexes of sorted columns before saving the new sorted state
	 */
	public SaveSortedColumnCommand(final TransactionalEditingDomain domain, final INattableModelManager manager, final NatTableComparatorChooser<?> chooser, final List<Integer> previousSortedColumns) {
		super(domain, "Save Sorted Columns", null); //$NON-NLS-1$
		this.manager = manager;
		this.chooser = chooser;
		this.previousSortedColumns = previousSortedColumns;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecute(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		final List<Integer> currentSortedColumns = this.chooser.getSortingColumns();
		final List<Integer> columnsToRemove = new ArrayList<>(this.previousSortedColumns);
		columnsToRemove.removeAll(currentSortedColumns);

		// remove all unecessary sort style
		for (int columnIndex : columnsToRemove) {
			final Object column = this.manager.getColumnElement(columnIndex);
			if (column instanceof IAxis) {
				final Style style = ((IAxis) column).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
				if (style != null) {
					((IAxis) column).getStyles().remove(style);
				}
			}
		}

		// create or update existing style
		for (int columnIndex : currentSortedColumns) {
			final Object column = this.manager.getColumnElement(columnIndex);
			if (column instanceof IAxis) {
				StringValueStyle style = (StringValueStyle) ((IAxis) column).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
				if (style == null) {
					style = NattablestyleFactory.eINSTANCE.createStringValueStyle();
					style.setName(NamedStyleConstants.SORT);
					((IAxis) column).getStyles().add(style);
				}
				style.setStringValue(ColumnSortUtils.buildSortStringValue(chooser, columnIndex));
			}
		}
		return CommandResult.newOKCommandResult();
	}

}
