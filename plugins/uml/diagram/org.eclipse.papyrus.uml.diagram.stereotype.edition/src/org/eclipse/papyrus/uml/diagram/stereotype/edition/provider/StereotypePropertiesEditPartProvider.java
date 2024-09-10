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
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotype.edition.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.RestrictedAbstractEditPartProvider;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeCommentEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeEmptyEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeMultilinePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypesCommentLinkEditPart;


public class StereotypePropertiesEditPartProvider extends RestrictedAbstractEditPartProvider {

	/** Map containing node view types supported by this provider */
	protected Map<String, Class<?>> nodeMap = new HashMap<String, Class<?>>();

	/** Map containing edge view types supported by this provider */
	protected Map<String, Class<?>> edgeMap = new HashMap<String, Class<?>>();

	/** Default constructor */
	public StereotypePropertiesEditPartProvider() {
		super();

		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE, AppliedStereotypeCompartmentEditPart.class);
		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE, AppliedStereotypeMultilinePropertyEditPart.class);
		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_COMMENT_TYPE, AppliedStereotypeCommentEditPart.class);
		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE, AppliedStereotypeEmptyEditPart.class);
		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE, AppliedStereotypeEmptyEditPart.class);
		nodeMap.put(StereotypeDisplayConstant.STEREOTYPE_PROPERTY_BRACE_TYPE, AppliedStereotypeEmptyEditPart.class);


		edgeMap.put(StereotypeDisplayConstant.STEREOTYPE_COMMENT_LINK_TYPE, AppliedStereotypesCommentLinkEditPart.class);



	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof CreateGraphicEditPartOperation) {
			View newView = ((IEditPartOperation) operation).getView();
			if (newView == null) {
				return false;
			}

			String graphicalType = newView.getType();

			if ((newView instanceof Node) && (nodeMap.containsKey(graphicalType))) {
				return true;
			}

			if ((newView instanceof Edge) && (edgeMap.containsKey(graphicalType))) {
				return true;
			}
			return false;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?> getNodeEditPartClass(View view) {
		return nodeMap.get(view.getType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?> getEdgeEditPartClass(View view) {
		return edgeMap.get(view.getType());
	}
}
