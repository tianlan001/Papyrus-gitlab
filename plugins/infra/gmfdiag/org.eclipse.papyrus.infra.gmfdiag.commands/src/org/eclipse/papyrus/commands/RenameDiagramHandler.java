/*****************************************************************************
 * Copyright (c) 2013, 2016 Cedric Dumoulin, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.commands.messages.Messages;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForIEvaluationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;

/**
 * This handler allows to rename a gmf diagram.
 * The handler is activated when the current selection denote a gmf diagram.
 *
 * <br>
 * There is another RenameHandler in Papyrus (for modelexplorer):
 * /org.eclipse.papyrus.infra.gmfdiag.modelexplorer/src/org/eclipse/papyrus/infra/gmfdiag/modelexplorer/handlers/RenameDiagramHandler.java
 *
 * @author cedric dumoulin
 *
 */
public class RenameDiagramHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 * @param event
	 * @return
	 * @throws ExecutionException
	 *
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		executeTransaction(event);

		return null;
	}

	/**
	 * Execute as transaction
	 *
	 * @param event
	 */
	private void executeTransaction(ExecutionEvent event) {

		// Get requested objects
		final Diagram notationDiagramHelper;
		TransactionalEditingDomain editingDomain;
		try {
			IEvaluationContext context = getIEvaluationContext(event);
			notationDiagramHelper = lookupNotationDiagramChecked(context);
			editingDomain = lookupTransactionalEditingDomain(context);
		} catch (NotFoundException e) {
			// silently fails
			return;
		} catch (ServiceException e) {
			// silently fails
			return;
		}

		// If the diagram label is available, modify this one.
		Command cmd = null;
		final String diagramLabel = LabelInternationalization.getInstance().getDiagramLabelWithoutName(notationDiagramHelper);
		if(null != diagramLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(notationDiagramHelper)){
			InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), "Rename diagram label", "New label:", diagramLabel, null); //$NON-NLS-1$ //$NON-NLS-2$
			if (Window.OK == dialog.open()) {
				final String label = dialog.getValue();
				cmd = LabelInternationalization.getInstance().getSetDiagramLabelCommand(editingDomain, notationDiagramHelper, label, null);
			}
		}else{
			// Open the dialog to ask the new name
			String currentName = notationDiagramHelper.getName();
			String newName = null;
			InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), Messages.RenameDiagramHandler_RenameAnExistingDiagram, Messages.RenameDiagramHandler_NewName, currentName, null);
			if (dialog.open() == Window.OK) {
				newName = dialog.getValue();
				if (newName == null || newName.length() <= 0) {
					return;
				}
			} else {
				// cancelled
				return;
			}
	
			final String name = newName;
			cmd = new RecordingCommand(editingDomain, getCommandName()) {
	
				@Override
				protected void doExecute() {
					// Rename the diagram !
					notationDiagramHelper.setName(name);
				}
	
	
			};
		}
		
		editingDomain.getCommandStack().execute(cmd);

	}

	/**
	 * Get the name used in the {@link RecordingCommand}. This name will be visible in
	 * undo/redo.
	 *
	 * @return The command name to show.
	 */
	public String getCommandName() {
		return Messages.RenameDiagramHandler_RenameDiagram;
	}

	protected IEvaluationContext getIEvaluationContext(ExecutionEvent event) throws NotFoundException {
		try {
			return (IEvaluationContext) event.getApplicationContext();
		} catch (ClassCastException e) {
			throw new NotFoundException("IEvaluationContext can't be found."); //$NON-NLS-1$
		}

	}

	// /**
	// *
	// * @return
	// * @throws NotFoundException
	// */
	// protected LayerStackMngr lookupLayerStackMngrChecked() throws NotFoundException {
	//
	// return lookupLayersViewChecked().getLayerStackMngrChecked();
	//
	// }

	/**
	 * Get the notation diagram helper.
	 * This method can be used from {@link #execute(ExecutionEvent)} or {@link #setEnabled(Object)}.
	 *
	 * @return The
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	protected Diagram lookupNotationDiagramChecked(IEvaluationContext context) throws NotFoundException, ServiceException {


		// Get page from the event !
		IEditorPart editor = ServiceUtilsForIEvaluationContext.getInstance().getService(ISashWindowsContainer.class, context).getActiveEditor();

		if (!(editor instanceof DiagramDocumentEditor)) {
			throw new NotFoundException("Selected editor do not contains Diagram"); //$NON-NLS-1$
		}
		DiagramDocumentEditor diagramEditor = (DiagramDocumentEditor) editor;

		Diagram diagram = diagramEditor.getDiagram();
		if (diagram == null) {
			throw new NotFoundException("Selected editor do not contains Diagram"); //$NON-NLS-1$
		}

		// Return a new instance of the Helper
		return diagram;
	}

	/**
	 * Try to lookup the TransactionalEditingDomain.
	 *
	 * @return
	 * @throws ServiceException
	 *             If the Editing domain can't be found.
	 */
	protected TransactionalEditingDomain lookupTransactionalEditingDomain(IEvaluationContext context) throws ServiceException {

		// Get page from the event !
		// IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();

		return ServiceUtilsForIEvaluationContext.getInstance().getTransactionalEditingDomain(context);
	}

	/**
	 * Called by framework. Need to set the enabled flag.
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {

		if (!(evaluationContext instanceof IEvaluationContext)) {
			setBaseEnabled(false);
			return;
		}

		IEvaluationContext context = (IEvaluationContext) evaluationContext;

		try {
			// Try to get the diagram
			lookupNotationDiagramChecked(context);

			// ok, we got it.
			setBaseEnabled(true);

		} catch (ServiceException e) {
			// Can't find ServiceRegistry: disable
			setBaseEnabled(false);
		} catch (NotFoundException e) {
			// Can't find ServiceRegistry: disable
			setBaseEnabled(false);
		}

	}
}
