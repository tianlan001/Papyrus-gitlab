/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST.
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
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - bug 573807
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.menu.handlers;

import java.util.List;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.IStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.PasteStrategyManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * The handler for the paste with model in Diagram
 *
 */
public class PasteInDiagramHandler extends AbstractGraphicalCommandHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.views.modelexplorer.handler.AbstractCommandHandler#getCommand()
	 *
	 * @return
	 */
	@Override
	protected Command getCommand() {
		PapyrusClipboard.getInstance().resetTarget();
		List<IGraphicalEditPart> selectedElements = getSelectedElements();
		if (selectedElements.size() == 1 && (selectedElements.get(0) instanceof GraphicalEditPart)) { // Paste is only enabled on a simple selection
			GraphicalEditPart targetOwnerEditPart = (GraphicalEditPart) selectedElements.get(0);
			org.eclipse.gef.commands.CompoundCommand compoundCommand = new org.eclipse.gef.commands.CompoundCommand("Paste all elements"); //$NON-NLS-1$

			List<IStrategy> allStrategies = PasteStrategyManager.getInstance().getAllActiveStrategies();
			for (IStrategy iStrategy : allStrategies) {
				IPasteStrategy iPasteStrategy = (IPasteStrategy) iStrategy;
				Command graphicalCommand = iPasteStrategy.getGraphicalCommand(getEditingDomain(), targetOwnerEditPart, PapyrusClipboard.getInstance());
				if (graphicalCommand != null) {
					compoundCommand.add(graphicalCommand);
				}
			}
			return compoundCommand;
		}
		return UnexecutableCommand.INSTANCE;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.menu.handlers.AbstractGraphicalCommandHandler#setEnabled(java.lang.Object)
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		if (!PapyrusClipboard.getInstance().isEmpty()) {
			if (evaluationContext instanceof IEvaluationContext) {
				IEvaluationContext iEvaluationContext = (IEvaluationContext) evaluationContext;
				Object activeFocusControl = iEvaluationContext.getVariable("activeFocusControl"); //$NON-NLS-1$
				Object activeShell = iEvaluationContext.getVariable("activeShell"); //$NON-NLS-1$
				Control focusControl = null;
				if (activeShell instanceof Shell) {
					Shell shell = (Shell) activeShell;
					Display display = !shell.isDisposed() ? shell.getDisplay() : null;
					if (display != null) {
						focusControl = display.getFocusControl();
					}
				}
				if (activeFocusControl instanceof StyledText || focusControl instanceof Text) { // true if the focus is on an internal xtext editor or a text edit
					setBaseEnabled(false);
				} else {
					super.setEnabled(evaluationContext);
				}
			}
		} else {
			setBaseEnabled(false);
		}
	}
}
