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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.ViewpointHelpers;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Execution;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.provider.SequenceDiagramEditPartProvider;

/**
 * Provides a specific factory to contribute Papyrus Sequence specific EditParts
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@SuppressWarnings("restriction")
public class PapyrusSequenceDiagramEditPartProvider extends SequenceDiagramEditPartProvider {

	@Override
	protected Class<?> getNodeEditPartClass(View view) {
		if (Execution.notationPredicate().apply(view)
				&& view.getDiagram() != null
				&& view.getDiagram().getElement() instanceof DDiagram diagram
				&& ViewpointHelpers.isSequenceDiagram(diagram)) {
			return PapyrusExecutionEditPart.class;
		}
		return super.getNodeEditPartClass(view);
	}

}
