/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.ExecutionEditPart;
import org.eclipse.sirius.diagram.ui.edit.internal.part.DiagramBorderNodeEditPartOperation;

/**
 * A specific EditPart for Executions. We override the Sirius one to contribute a specific editPolicy.
 * It is intended to prevent execution with messages to be moved.
 * 
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@SuppressWarnings("restriction")
public class PapyrusExecutionEditPart extends ExecutionEditPart {
	/**
	 * Constructor.
	 * 
	 * @param view
	 *            the view.
	 */
	public PapyrusExecutionEditPart(View view) {
		super(view);
	}

	@Override
	public EditPolicy getPrimaryDragEditPolicy() {
		final ResizableEditPolicy result = new PapyrusExecutionSelectionEditPolicy();
		DDiagramElement dDiagramElement = this.resolveDiagramElement();
		if (dDiagramElement instanceof DNode dNode) {
			DiagramBorderNodeEditPartOperation.updateResizeKind(result, dNode);
		}
		return result;
	}

}
