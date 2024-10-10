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
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;

/**
 * Creation checker for UML {@link Activity}.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class SemanticNodeCreationChecker extends AbstractSemanticNodeCreationChecker {

	private final Class<? extends Element> expectedType;

	/**
	 * Constructor.
	 *
	 * @param expectedOwner
	 */
	public SemanticNodeCreationChecker(final EObject expectedOwner, EReference containmentFeature, Class<? extends Element> expectedType) {
		super(expectedOwner, containmentFeature);
		this.expectedType = expectedType;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.AbstractSemanticNodeCreationChecker#validateSemanticElementInstance(org.eclipse.emf.ecore.EObject)
	 *
	 * @param semanticElement
	 */
	@Override
	protected void validateSemanticElementInstance(final EObject semanticElement) {
		Assert.assertTrue(NLS.bind("The created element must be a UML {0} instead of a {1}.", expectedType.getName(), semanticElement.eClass().getName()), this.expectedType.isInstance(semanticElement)); //$NON-NLS-1$
	}


}