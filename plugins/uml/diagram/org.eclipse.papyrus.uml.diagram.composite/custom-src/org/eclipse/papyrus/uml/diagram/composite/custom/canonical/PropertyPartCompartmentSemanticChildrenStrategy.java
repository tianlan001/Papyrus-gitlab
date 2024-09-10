/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *  Christian W. Damus - bug 477384
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.canonical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Type;

/**
 * This class is used to precise semantic element that could be displayed in compartment of parts
 */
public class PropertyPartCompartmentSemanticChildrenStrategy extends DefaultUMLSemanticChildrenStrategy {

	public PropertyPartCompartmentSemanticChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result = null;

		Property property = (semanticFromEditPart instanceof Property) ? (Property) semanticFromEditPart : null;
		if ((property != null) && !(property instanceof Port)) {
			Type type = property.getType();
			if (type != null) {
				// Show nested structure of the part as defined by its type
				result = super.getCanonicalSemanticChildren(type, viewFromEditPart);

				// But, we only visualize ports on the borders of the parts
				// (not as nested parts: they're not that kind of attribute)
				if (viewFromEditPart instanceof DecorationNode) {
					result.removeIf(Port.class::isInstance);
				}
			}
		}

		return result;
	}

	@Override
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart) {
		if (semanticFromEditPart instanceof Property) {
			List<Element> result = new ArrayList<>();

			Property property = (Property) semanticFromEditPart;

			// We show the nested structure of parts only, not ports
			if (!(property instanceof Port)) {
				Type type = property.getType();

				if (type != null) {
					result.add(type);
				}
			}

			// Add the composite structure context, too, to detect creation of
			// connectors because that does not directly affect a connected part/port.
			// Note that this navigates up the view hierarchy to account for ports on parts
			for (EObject parent = viewFromEditPart.eContainer(); parent instanceof View; parent = parent.eContainer()) {
				View parentView = (View) parent;
				if (parentView.getElement() instanceof StructuredClassifier) {
					result.add((StructuredClassifier) parentView.getElement());
					break;
				}
			}

			return result;
		}
		return null;
	}
}
