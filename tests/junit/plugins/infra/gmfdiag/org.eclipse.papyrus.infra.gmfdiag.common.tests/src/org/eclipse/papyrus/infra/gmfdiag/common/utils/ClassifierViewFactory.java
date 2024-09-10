/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.common.utils;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.core.view.factories.ViewFactory;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassFloatingNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassOperationCompartmentEditPart;

/**
 * extraction from class diagram UML view Provider
 *
 */
public class ClassifierViewFactory implements ViewFactory {

	@Override
	public View createView(IAdaptable semanticAdapter, View containerView, String semanticHint, int index,
			boolean persisted, PreferencesHint preferencesHint) {
		return createClassShape((EObject) semanticAdapter.getAdapter(EObject.class), containerView, semanticHint, index, persisted);
	}

	public Node createClassShape(EObject domainElement, View containerView, String semanticHint, int index, boolean persisted) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(semanticHint);
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences

		createLabel(node, ClassNameEditPart.VISUAL_ID);
		Node classFloatingNamelabel = createLabel(node, ClassFloatingNameEditPart.VISUAL_ID);
		classFloatingNamelabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location classFloatingNameLabelLocation = (Location) classFloatingNamelabel.getLayoutConstraint();
		classFloatingNameLabelLocation.setX(0);
		classFloatingNameLabelLocation.setY(5);
		createCompartment(node, ClassAttributeCompartmentEditPart.VISUAL_ID, true, true, true);
		createCompartment(node, ClassOperationCompartmentEditPart.VISUAL_ID, true, true, true);
		createCompartment(node, ClassNestedClassifierCompartmentEditPart.VISUAL_ID, true, true, true);
		return node;
	}

	protected Node createLabel(View owner, String hint) {
		DecorationNode rv = NotationFactory.eINSTANCE.createDecorationNode();
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	protected Node createCompartment(View owner, String hint, boolean hasTitle, boolean canSort, boolean canFilter) {
		Node rv = NotationFactory.eINSTANCE.createBasicCompartment();

		rv.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());

		if (hasTitle) {
			TitleStyle ts = NotationFactory.eINSTANCE.createTitleStyle();
			rv.getStyles().add(ts);
		}
		if (canSort) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createSortingStyle());
		}
		if (canFilter) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createFilteringStyle());
		}
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}
}
