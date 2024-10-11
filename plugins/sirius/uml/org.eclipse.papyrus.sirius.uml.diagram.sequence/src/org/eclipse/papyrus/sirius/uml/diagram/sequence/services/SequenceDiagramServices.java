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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the "Sequence" diagram.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramServices {

	private final static String CREATE_LIFELINE_ERROR_NO_PARENT = "Unable to create a Lifeline with no parent"; //$NON-NLS-1$

	/**
	 * Creates a new semantic {@link Lifeline}, initializes and creates a view.
	 * <p>
	 * The new element is placed after (i.e. on the right) of the provided <code>predecessor</code>. The element is placed first if <code>predecessor</code> is <code>null</code>.
	 * 
	 * @param parent
	 *            the semantic parent
	 * @param parentView
	 *            the view of the semantic parent
	 * @param predecessor
	 *            the element preceding the lifeline to create (i.e. on the left)
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createLifeline(Element parent, DSemanticDecorator parentView, EObject predecessor) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn(CREATE_LIFELINE_ERROR_NO_PARENT);
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getLifeline().getName(), UMLPackage.eINSTANCE.getInteraction_Lifeline().getName(), parentView);
			SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
			reorderServices.reorderLifeline(parent, result, predecessor);
		}
		return result;
	}



}
