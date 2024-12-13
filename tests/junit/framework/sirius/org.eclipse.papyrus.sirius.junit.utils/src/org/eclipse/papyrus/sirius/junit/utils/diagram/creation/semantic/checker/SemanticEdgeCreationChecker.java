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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.osgi.util.NLS;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

/**
 * Creation checker for Edge creation.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class SemanticEdgeCreationChecker extends AbstractSemanticEdgeCreationChecker {

	private final Class<? extends Element> expectedType;

	/**
	 * Constructor.
	 * <p>
	 * Expected type is containment type.
	 * </p>
	 *
	 * @param expectedOwner
	 *            expected owner
	 * @param containmentFeature
	 *            containment feature
	 */
	@SuppressWarnings("unchecked")
	public SemanticEdgeCreationChecker(EObject expectedOwner, EReference containmentFeature) {
		this(expectedOwner, containmentFeature,
				(Class<? extends Element>) containmentFeature.getEReferenceType().getInstanceClass());
	}


	/**
	 * Constructor.
	 *
	 * @param expectedOwner
	 *            expected owner
	 * @param containmentFeature
	 *            containment feature
	 * @param expectedType
	 *            type of created element
	 */
	public SemanticEdgeCreationChecker(final EObject expectedOwner, EReference containmentFeature, Class<? extends Element> expectedType) {
		super(expectedOwner, containmentFeature);
		this.expectedType = expectedType;
	}

	@Override
	protected void validateSemanticElementInstance(EObject semanticElement) {
		Assert.assertTrue(NLS.bind("The created element must be a UML {0} instead of a {1}.", //$NON-NLS-1$
				expectedType.getName(), semanticElement.eClass().getName()),
				expectedType.isInstance(semanticElement));
	}

}