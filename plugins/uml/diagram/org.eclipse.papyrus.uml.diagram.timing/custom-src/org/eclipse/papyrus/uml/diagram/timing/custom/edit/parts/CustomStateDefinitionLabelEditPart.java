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
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.timing.custom.parsers.StateDefinitionParser;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateDefinitionLabelEditPart;

public class CustomStateDefinitionLabelEditPart extends StateDefinitionLabelEditPart {

	private IParser parser;

	public CustomStateDefinitionLabelEditPart(final View view) {
		super(view);
	}

	@Override
	public Command getCommand(final Request request) {
		if (request.getType() == REQ_DELETE) {
			// if the StateDefinitionLabel is selected, then delete the parent StateDefinition
			return getParent().getCommand(request);
		}
		return super.getCommand(request);
	}

	/**
	 * Generation bug prevents getting the parser for the label EditPart (seems to look for a parser on the parent
	 * EditPart). So, return the right parser here.
	 */
	@Override
	public IParser getParser() {
		if (this.parser == null) {
			setParser(new StateDefinitionParser());
		}
		return this.parser;
	}

	@Override
	public void setParser(IParser parser) {
		super.setParser(parser);
		this.parser = parser;
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class key) {
		// GMF returns the View by default, but Papyrus expects a semantic element.
		// There is no semantic element, so we return null in order for Papyrus to handle
		// the delete menu action enablement correctly.
		// XXX warning: this might cause unforeseen bugs somewhere else.
		if (key == EObject.class) {
			return null;
		}
		return super.getAdapter(key);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#resolveSemanticElement()
	 */
	@Override
	public EObject resolveSemanticElement() {
		return ((IGraphicalEditPart) getParent()).resolveSemanticElement();
	}
}
