/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.usecase.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the "Use Case" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UseCaseDiagramServices extends AbstractDiagramServices {

	/**
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createUseCase(Element parent, String type, DSemanticDecorator targetView) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn("Unable to create an element on nothing"); //$NON-NLS-1$
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			if (parent instanceof Package) {
				result = commonDiagramServices.createElement(parent, type, UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), targetView);
			} else if (parent instanceof Classifier) {
				result = commonDiagramServices.createElement(parent, type, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase().getName(), targetView);
			}
		}
		return result;
	}
}
