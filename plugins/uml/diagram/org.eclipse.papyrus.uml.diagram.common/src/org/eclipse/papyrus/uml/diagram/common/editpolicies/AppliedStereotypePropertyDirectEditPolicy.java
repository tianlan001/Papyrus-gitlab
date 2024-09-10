/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.TextCellEditorEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;


public class AppliedStereotypePropertyDirectEditPolicy extends LabelDirectEditPolicy {

	/**
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(DirectEditRequest edit) {
		if (edit.getCellEditor() instanceof TextCellEditorEx) {
			if (!((TextCellEditorEx) edit.getCellEditor()).hasValueChanged()) {
				return null;
			}
		}

		String labelText = (String) edit.getCellEditor().getValue();

		// for CellEditor, null is always returned for invalid values
		if (labelText == null) {
			return null;
		}

		ITextAwareEditPart compartment = (ITextAwareEditPart) getHost();
		EObject model = (EObject) compartment.getModel();
		SemanticAdapter elementAdapter = new SemanticAdapter(((View) model).getElement(), model);
		// check to make sure an edit has occurred before returning a command.
		String prevText = compartment.getParser().getEditString(elementAdapter,
				compartment.getParserOptions().intValue());
		if (!prevText.equals(labelText)) {
			ICommand iCommand = compartment.getParser().getParseCommand(elementAdapter, labelText, 0);
			return new ICommandProxy(iCommand);
		}

		return null;
	}
}
