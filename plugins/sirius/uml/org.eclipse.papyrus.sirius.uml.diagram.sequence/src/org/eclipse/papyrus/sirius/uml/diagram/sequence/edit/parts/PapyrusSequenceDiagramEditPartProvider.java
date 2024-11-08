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

import java.util.Objects;

import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;
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

	private static final String SEQUENCE_DIAGRAM_NAME = "SequenceDiagram"; //$NON-NLS-1$

	@Override
	protected Class<?> getNodeEditPartClass(View view) {
		if (Execution.notationPredicate().apply(view)) {
			return PapyrusExecutionEditPart.class;
		}
		return super.getNodeEditPartClass(view);
	}

	@Override
	public synchronized boolean provides(IOperation operation) {
		if (operation instanceof IEditPartOperation editPartOperation && editPartOperation.getView() != null
				&& editPartOperation.getView().getDiagram() != null
				&& editPartOperation.getView().getDiagram().getElement() instanceof DDiagram diagram) {
			if (Objects.equals(diagram.getDescription().getName(), SEQUENCE_DIAGRAM_NAME)) {
				return super.provides(editPartOperation);
			} else {
				return false;
			}
		}
		return super.provides(operation);
	}
}
