/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.uml.domain.services.create.CreationStatus;
import org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreator;
import org.eclipse.papyrus.uml.domain.services.create.ICreator;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services common to all diagrams.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class CommonDiagramServices extends AbstractDiagramServices {

	/**
	 * Prints an element and its context to Standard Stream.
	 * <p>
	 * This method should ONLY be used in Sirius VSM during development phase. <br/>
	 * To prevent misuse, it is indicated as deprecated.
	 * </p>
	 *
	 * @param it
	 *            current object
	 * @param hint
	 *            text to display with details
	 * @return it
	 */
	@Deprecated
	public static EObject debugVsm(EObject it, String hint) {
		String message = "Debug VSM[" + hint + "]: " + it; //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(message);
		Session.of(it).get().getInterpreter().getVariables() // fails if not in Session
				.forEach((key, value) -> {
					String variable = "\t" + key + " = " + value; //$NON-NLS-1$ //$NON-NLS-2$
					System.out.println(variable);
				});
		return it;
	}

	/**
	 * Check if the given view is a compartment.
	 *
	 * @param view
	 *            the view to check,
	 * @return <code>true</code> if the view is a compartment view, <code>false</code> otherwise.
	 */
	public boolean isCompartmentView(DSemanticDecorator view) {
		return this.isCompartment(view);
	}

	/**
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @param targetView
	 *            the view in which the element should be created.
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createElement(Element parent, String type, String referenceName, DSemanticDecorator targetView) {
		ElementCreator elementCreator = new ElementCreator(new ElementConfigurer(), new ElementFeatureModifier(this.getECrossReferenceAdapter(parent), new EditableChecker()));
		return this.createElement(parent, type, referenceName, targetView, elementCreator);
	}

	/**
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @param targetView
	 *            the view in which the element should be created.
	 * @param creator
	 *            the creator to create the new element.
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createElement(Element parent, String type, String referenceName, DSemanticDecorator targetView, ICreator creator) {
		final EObject result;
		if (parent == null) {
			Activator.log.warn("Unable to create an element on nothing"); //$NON-NLS-1$
			result = null;
		} else {
			CreationStatus status = creator.create(parent, type, referenceName);

			result = status.getElement();

			if (status.getState() == State.FAILED) {
				Activator.log.warn("Creation failed : " + status.getMessage()); //$NON-NLS-1$
			} else {
				if (result != null) {
					final Session session = SessionManager.INSTANCE.getSession(parent);
					DSemanticDecorator containerView = targetView;
					String containerViewExpression = "aql:containerView"; //$NON-NLS-1$
					if (this.isCompartment(containerView)) {
						if (this.isBorderNode(result, (DSemanticDecorator) targetView.eContainer())) {
							containerView = (DSemanticDecorator) targetView.eContainer();
							containerViewExpression = "aql:containerView.eContainer()"; //$NON-NLS-1$
						}
					}
					this.createView(result, containerView, session, containerViewExpression);
				}
			}
		}
		return result;
	}

	/**
	 * Checks if the provided {@code graphicalElement} is outside of the graphical element representing its semantic container.
	 *
	 * @param element
	 *            the semantic {@link Element} to check
	 * @param graphicalElement
	 *            the graphical element to check
	 * @return
	 *         {@code true} if the graphicalElement is outside of its semantic container, {@code false} otherwise.
	 */
	public boolean isGraphicalElementOutsideOfItsSemanticContainer(final Element element, final DSemanticDecorator graphicalElement) {
		EObject graphicalContainer = graphicalElement.eContainer();
		if (graphicalContainer instanceof DSemanticDecorator) {
			EObject target = ((DSemanticDecorator) graphicalContainer).getTarget();
			if (target != null) {
				if (target == element.eContainer()) {
					// The element is directly inside the graphical element of its semantic container
					return false;
				}
				if (target instanceof Property && ((Property) target).getType() == element.eContainer()) {
					// The Property/Port is inside an other property type with semantic container of the Property/Port
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the type of the provided {@code element} is strictly equals to {@code type}.
	 * <p>
	 * This method is similar to OCL's {@code isTypeOf} operation. The provided {@code type} must
	 * be the name of a classifier from {@link UMLPackage}. It can be prefixed with {@code 'uml::'} but
	 * it isn't mandatory.
	 * </p>
	 * <p>
	 * This method is used to check the type of elements when multiple metamodels are registered with the
	 * same name. In this context, the AQL interpreter doesn't evaluate {@code oclIsTypeOf} correctly, and
	 * it is necessary to use this service to check the type of an element.
	 * </p>
	 *
	 * @param element
	 *            the element to check the type from
	 * @param type
	 *            the name of the UML classifier to check
	 * @return {@code true} if {@code element}'s type is equal to {@code type}, {@code false} otherwise
	 */
	public boolean isTypeOf(Element element, String type) {
		return Objects.equals(UMLHelper.toEClass(type), element.eClass());
	}
}
