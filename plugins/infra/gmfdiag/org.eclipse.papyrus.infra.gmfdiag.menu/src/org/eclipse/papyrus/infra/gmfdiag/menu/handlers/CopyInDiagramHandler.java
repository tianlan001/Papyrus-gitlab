/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 430701
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net  - bug 441318, bug 455305
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - bug 573807
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.menu.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.AWTClipboardHelper;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.INonDirtying;
import org.eclipse.papyrus.commands.util.NonDirtyingUtils;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.DefaultDiagramCopyCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.IStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.PasteStrategyManager;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISources;


/**
 * Handler for the Copy Action in Diagram.
 */
public class CopyInDiagramHandler extends AbstractGraphicalCommandHandler {

	/** The Constant COPY_COMMAND_LABEL. */
	private static final String COPY_COMMAND_LABEL = "Copy In Diagram";

	/** The Constant COPY_IMAGE_COMMAND_LABEL. */
	private static final String COPY_IMAGE_COMMAND_LABEL = "Create image to allow paste on system";

	/** The active focus control. */
	private Object activeFocusControl = null;

	/** The active shell. */
	private Object activeShell = null;


	/**
	 * Construct copy command from the selection.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param selectedElements
	 *            the selected elements
	 * @return the command
	 */
	public static Command buildCopyCommand(TransactionalEditingDomain editingDomain, Collection<IGraphicalEditPart> selectedElements) {
		PapyrusClipboard<Object> papyrusClipboard = PapyrusClipboard.getNewInstance();

		Command result;

		DefaultDiagramCopyCommand defaultDiagramCopyCommand = new DefaultDiagramCopyCommand(editingDomain, papyrusClipboard, selectedElements);
		result = EMFtoGEFCommandWrapper.wrap(defaultDiagramCopyCommand);

		IDiagramWorkbenchPart activeDiagramWorkbenchPart = DiagramEditPartsUtil.getActiveDiagramWorkbenchPart();
		Diagram diagram = activeDiagramWorkbenchPart.getDiagram();
		DiagramEditPart diagramEditPart = activeDiagramWorkbenchPart.getDiagramEditPart();
		List<Object> selectedElementModels = new ArrayList<Object>();
		for (IGraphicalEditPart iGraphicalEditPart : selectedElements) {
			selectedElementModels.add(iGraphicalEditPart.getModel());
		}

		PapyrusCopyImageCommand copyImageCommand = new PapyrusCopyImageCommand(COPY_IMAGE_COMMAND_LABEL, diagram, selectedElementModels, diagramEditPart);
		if (copyImageCommand.canExecute()) {
			Command gmFtoGEFCommandWrapper = GMFtoGEFCommandWrapper.wrap(copyImageCommand);
			result = NonDirtyingUtils.chain(result, gmFtoGEFCommandWrapper);
		} else {
			copyImageCommand.dispose();
		}

		List<IStrategy> allStrategies = PasteStrategyManager.getInstance().getAllStrategies();
		for (IStrategy iStrategy : allStrategies) {
			IPasteStrategy iIPasteStrategy = (IPasteStrategy) iStrategy;
			iIPasteStrategy.prepare(papyrusClipboard, null);
		}
		result.setLabel(COPY_COMMAND_LABEL);
		return result;
	}



	/**
	 * Gets the command.
	 *
	 * @return the command
	 * @see org.eclipse.papyrus.views.modelexplorer.handler.AbstractCommandHandler#getCommand()
	 */
	@Override
	protected Command getCommand() {
		return CopyInDiagramHandler.buildCopyCommand(getEditingDomain(), getSelectedElements());
	}




	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.menu.handlers.AbstractGraphicalCommandHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {

		// Local handling
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext iEvaluationContext = (IEvaluationContext) evaluationContext;
			activeFocusControl = iEvaluationContext.getVariable(ISources.ACTIVE_FOCUS_CONTROL_NAME);
			activeShell = iEvaluationContext.getVariable(ISources.ACTIVE_SHELL_NAME);
		}

		// Parent handling
		super.setEnabled(evaluationContext);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.menu.handlers.AbstractGraphicalCommandHandler#computeEnabled()
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnabled() {
		boolean isEnable = false;

		Control focusControl = null;
		if (activeShell instanceof Shell) {
			Shell shell = (Shell) activeShell;
			Display display = !shell.isDisposed() ? shell.getDisplay() : null;
			if (display != null) {
				focusControl = display.getFocusControl();
			}
		}
		if (!(activeFocusControl instanceof StyledText) && !(focusControl instanceof Text)) { // false if the focus is on an internal xtext editor or a text edit
			PapyrusClipboard<Object> instance = PapyrusClipboard.getInstance();
			PapyrusClipboard.setInstance(instance);
			isEnable = true;
		}

		return isEnable;

	}

	/**
	 * Papyrus implementation of {@link CopyImageCommand}.
	 *
	 * <p>
	 * Bug 441318 :<br/>
	 * Fix override {@link CopyImageCommand} to permit Undo. This workaround is used by GMF.
	 * </p>
	 */
	@SuppressWarnings("restriction")
	static class PapyrusCopyImageCommand extends org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand implements INonDirtying {


		/**
		 * Instantiates a new papyrus copy image command.
		 *
		 * @param label
		 *            the label
		 * @param viewContext
		 *            the view context
		 * @param source
		 *            the source
		 * @param diagramEP
		 *            the diagram ep
		 */
		PapyrusCopyImageCommand(String label, View viewContext, @SuppressWarnings("rawtypes") List source, DiagramEditPart diagramEP) {
			super(label, viewContext, source, diagramEP);
		}

		/**
		 * @see org.eclipse.core.commands.operations.AbstractOperation#canExecute()
		 *
		 * @return
		 */
		@Override
		public boolean canExecute() {
			return AWTClipboardHelper.getInstance().isImageCopySupported();
		}

		/**
		 * Can undo.
		 *
		 * @return true, if successful
		 * @see org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand#canUndo()
		 */
		@Override
		public boolean canUndo() {
			return true;
		}

		/**
		 * @see org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand#canRedo()
		 *
		 * @return
		 */
		@Override
		public boolean canRedo() {
			return true;
		}

		/**
		 * @see org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param progressMonitor
		 * @param info
		 * @return
		 * @throws ExecutionException
		 */
		@Override
		protected CommandResult doUndoWithResult(
				IProgressMonitor progressMonitor, IAdaptable info)
				throws ExecutionException {

			return CommandResult.newOKCommandResult();
		}

		/**
		 * @see org.eclipse.gmf.runtime.diagram.ui.render.internal.commands.CopyImageCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param progressMonitor
		 * @param info
		 * @return
		 * @throws ExecutionException
		 */
		@Override
		protected CommandResult doRedoWithResult(
				IProgressMonitor progressMonitor, IAdaptable info)
				throws ExecutionException {

			return CommandResult.newOKCommandResult();
		}

	}
}
