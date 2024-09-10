/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *    Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - bug 476618
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;
import org.eclipse.papyrus.infra.nattable.wizard.ImportTableWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * This handler allows to import a table using the ImportWizard
 */
public class ImportTableHandler extends AbstractTableHandler {
	
	/**
	 * The variable name to determinate if the final dialog must be opened for the import.
	 */
	public static final String OPEN_DIALOG_BOOLEAN_PARAMETER = "openDialog"; //$NON-NLS-1$
	
	/**
	 * The variable name to determinate the selected file path.
	 */
	public static final String SELECTED_FILE_PATH_STRING_PARAMETER = "selectedFilePath"; //$NON-NLS-1$
	
	/**
	 * The variable name to determinate the preferred user action to use for the row insert action when he is not determinate.
	 */
	public static final String USER_ACTION__PREFERRED_USER_ACTION = "preferredUserAction"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// Calculate if the dialog must be opened during the process
		Object res = event.getParameters().get(OPEN_DIALOG_BOOLEAN_PARAMETER);
		final boolean openDialog = ((res == null) || Boolean.TRUE.equals(res));
		
		ImportTableWizard wizard = null;
		if(openDialog){
			wizard = new ImportTableWizard();
		}else{
			// Calculate if the dialog must be opened during the process
			res = event.getParameters().get(SELECTED_FILE_PATH_STRING_PARAMETER);
			final String importedText = null == res ? "" : res.toString();
			
			final Object userAction = event.getParameters().get(USER_ACTION__PREFERRED_USER_ACTION);
			final int preferredUserAction = null == userAction ? UserActionConstants.UNDEFINED_USER_ACTION : Integer.parseInt(userAction.toString());
			
			wizard = new ImportTableWizard(importedText, false, openDialog, preferredUserAction);
		}
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(getCurrentNattableModelManager()));
		if(openDialog){
			final WizardDialog dialog = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			dialog.open();
		}else{
			wizard.addPages();
			wizard.performFinish();
		}
		return null;
	}
}
