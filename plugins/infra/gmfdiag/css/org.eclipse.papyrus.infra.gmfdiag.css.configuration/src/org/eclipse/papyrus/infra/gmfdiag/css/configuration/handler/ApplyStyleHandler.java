/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding.AddCssClassStyleCommand;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.papyrus.infra.widgets.editors.InputDialog;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * Apply a style to a view using a right click menu
 */
public class ApplyStyleHandler extends AbstractHandler {


	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection;
		try {
			selection = ServiceUtilsForHandlers.getInstance().getNestedActiveIEditorPart(event).getSite().getSelectionProvider().getSelection();
			if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
				Iterator iterator = ((IStructuredSelection) selection).iterator();
				List<View> selectedViews = new ArrayList<>();
				while (iterator.hasNext()) {
					Object it = iterator.next();
					View view = NotationHelper.findView(it);
					if (view != null) {
						selectedViews.add(view);
					}
				}

				if (!selectedViews.isEmpty()) {
					Shell parentShell = ((Event) event.getTrigger()).widget.getDisplay().getActiveShell();
					InputDialog dialog = new InputDialog(parentShell, Messages.StringEditionFactory_EnterANewValue, Messages.StringEditionFactory_EnterANewValue, "", null); //$NON-NLS-1$
					int result = dialog.open();
					if (result == Window.OK) {
						String newStyleValue = dialog.getText();
						TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(selectedViews.get(0));
						AddCssClassStyleCommand addCssClassStyleCommand = new AddCssClassStyleCommand(editingDomain, selectedViews, newStyleValue);
						editingDomain.getCommandStack().execute(addCssClassStyleCommand);
						return newStyleValue;
					}
				}

			}
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}
		return null;
	}


}
