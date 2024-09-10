/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.nattable.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.toolsmiths.nattable.wizard.ExportAsTableConfigurationWizard;
import org.eclipse.swt.widgets.Display;


/**
 *
 * Handler declared on an opened {@link Table} to convert
 * it into a {@link TableConfiguration} using the Wizard
 */
public class ExportAsTableConfigurationHandler extends AbstractTableHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final INattableModelManager manager = getCurrentNattableModelManager();
		final Table table = manager.getTable();
		if (table != null) {
			final ExportAsTableConfigurationWizard wizard = new ExportAsTableConfigurationWizard();
			IStructuredSelection selection = new StructuredSelection(table);
			wizard.init(null, selection);
			WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.open();
		}
		return null;
	}

}
