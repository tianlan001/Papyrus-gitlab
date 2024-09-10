/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 509357
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.modelexplorer.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.modelexplorer.commands.RenameTableLabelCommand;
import org.eclipse.papyrus.infra.nattable.modelexplorer.messages.Messages;
import org.eclipse.papyrus.views.modelexplorer.commands.RenameElementCommand;
import org.eclipse.papyrus.views.modelexplorer.util.ModelExplorerEditionUtil;

/**
 * This handler provides the method to rename a Table.
 */
public class RenameTableHandler extends AbstractTableCommandHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getCommand(IEvaluationContext context) {
		TransactionalEditingDomain editingDomain = getEditingDomain(context);
		List<Table> tables = getSelectedTables();
		if (editingDomain != null && tables.size() == 1) {

			final Table table = tables.get(0);

			final String tableLabel = LabelInternationalization.getInstance().getTableLabelWithoutName(table);
			if (null != tableLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(table)) {
				AbstractTransactionalCommand cmd = new RenameTableLabelCommand(editingDomain, "ChangeTableLabelCommand", table, tableLabel, Messages.RenameTableHandler_Label_DialogTitle); //$NON-NLS-1$
				return new GMFtoEMFCommandWrapper(cmd);
			} else {
				final String currentName = table.getName();
				if (null != currentName) {
					EStructuralFeature nameFeature = table.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
					return new RenameElementCommand(editingDomain, "RenameTableCommand", table, currentName, nameFeature, Messages.RenameTableHandler_RenameAnExistingTable, Messages.RenameTableHandler_NewName); // $NON-NLS-1$
				}
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean computeEnabled(final IEvaluationContext context) {
		boolean computeEnabled = super.computeEnabled(context);
		if (computeEnabled) {
			List<EObject> selectedElements = getSelectedElements();
			EObject selection = selectedElements.get(0);
			computeEnabled = !EMFHelper.isReadOnly(selection);
		}

		return computeEnabled;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overridden to inline edit a table if it is handled by direct editor.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		EObject selectedTable = getSelectedElement();

		// If the selected table could be handled by direct editor
		if (ModelExplorerEditionUtil.isHandledByDirectEditor(selectedTable)) {
			// Call the edit element method from the model explorer to trigger DirectEditor
			ModelExplorerEditionUtil.editElement(selectedTable);
		} else {
			// Otherwise, show the model dialog to get user input
			super.execute(event);
		}

		return null;
	}
}
