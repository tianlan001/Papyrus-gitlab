/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *  Benoit Maggi (CEA LIST)    - Bug 468026
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 493430
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.util.AssociationUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * Helper for labels displaying {@link Property}
 */
public class AssociationEndSourceLabelHelper extends AssociationEndPropertyLabelHelper {

	private static AssociationEndSourceLabelHelper labelHelper;

	public static AssociationEndSourceLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new AssociationEndSourceLabelHelper();
		}
		return labelHelper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Property getUMLElement(GraphicalEditPart editPart) {
		View model = (View) editPart.getModel();
		if (model != null && model.eContainer() != null) {
			Edge eContainer = (Edge) model.eContainer();
			View sourceContainer = eContainer.getSource();
			if (sourceContainer == null) {
				return null;
			}

			Property propertyToDisplay = null;

			if (model != null && (model.getElement() instanceof Association)) {
				propertyToDisplay = AssociationUtil.getInitialSourceSecondEnd((Association) model.getElement());
			}

			if (null != propertyToDisplay) {
				return propertyToDisplay;
			}

			if (model != null && (model.getElement() instanceof Association)) {
				// look for the property that is typed by the classifier
				Iterator<Property> propertiesIterator = ((Association) model.getElement()).getMemberEnds().iterator();
				// find the first
				EObject source = sourceContainer.getElement();
				while (propertiesIterator.hasNext() && propertyToDisplay == null) {
					Property currentProperty = propertiesIterator.next();
					if (EcoreUtil.equals(currentProperty.getType(), source)) {
						propertyToDisplay = currentProperty;
					}
				}
			}
			if (propertyToDisplay != null) {
				return propertyToDisplay;
			}
			// /in the case of reorient the property must be not found,
			// so we have to find the property that is different from the source.

			if (model != null && (model.getElement() instanceof Association)) {
				// look for the property that is typed by the classifier
				Iterator<Property> propertiesIterator = ((Association) model.getElement()).getMemberEnds().iterator();
				// find the last
				EObject target = sourceContainer.getElement();
				while (propertiesIterator.hasNext()) {
					Property currentProperty = propertiesIterator.next();
					if (!EcoreUtil.equals(currentProperty.getType(), target)) {
						propertyToDisplay = currentProperty;
					}
				}
			}
			return propertyToDisplay;
		}
		return null;
	}
}
