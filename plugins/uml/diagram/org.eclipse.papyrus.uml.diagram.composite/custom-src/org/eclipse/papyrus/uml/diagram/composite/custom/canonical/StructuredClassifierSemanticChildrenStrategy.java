/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.canonical;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.StructuredClassifier;

/**
 * Custom semantic children strategy for structured classifiers presented (usually as the frame)
 * in a composite structure diagram.
 */
public class StructuredClassifierSemanticChildrenStrategy extends DefaultUMLSemanticChildrenStrategy {

	public StructuredClassifierSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result;

		StructuredClassifier composite = (semanticFromEditPart instanceof StructuredClassifier) ? (StructuredClassifier) semanticFromEditPart : null;
		if (composite == null) {
			result = super.getCanonicalSemanticChildren(semanticFromEditPart, viewFromEditPart);
		} else if (!(viewFromEditPart instanceof Diagram)) {
			// The children of a structured classifier that we present are only its structural
			// features (parts, ports, connectors), remembering that connectors are handled by
			// the getCanonicalSemanticConnections(...) method for connectable elements.
			// And, of course, only within the structure frame (not the diagram, itself)
			result = new java.util.ArrayList<>(composite.getOwnedAttributes());

			// But, we only visualize ports on the borders of the composite frame and parts
			// (not as parts: they're not that kind of attribute)
			if (viewFromEditPart instanceof DecorationNode) {
				result.removeIf(Port.class::isInstance);
			}
		} else {
			result = Collections.emptyList();
		}

		return result;
	}
}
