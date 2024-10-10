/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.tests.services;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

/**
 * An abstract class to bundle generic methods and constants used by the classes that test services.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class AbstractPropertiesServicesTest {

	/**
	 * Empty String.
	 */
	protected static final String EMPTY = ""; //$NON-NLS-1$

	/**
	 * Unknown reference name.
	 */
	protected static final String FAKE = "fake"; //$NON-NLS-1$

	/**
	 * Name reference name.
	 */
	protected static final String NAME = "name"; //$NON-NLS-1$

	@SuppressWarnings("unchecked")
	protected <T extends EObject> T create(java.lang.Class<T> type) {
		EClassifier newType = UMLPackage.eINSTANCE.getEClassifier(type.getSimpleName());
		EObject newElement = UMLFactory.eINSTANCE.create((EClass) newType);
		return (T) newElement;
	}

	/**
	 * Creates an element with the given type in the given parent. The containment
	 * reference is automatically computed by finding the feature containment
	 * {@link EReference} that can contains the given object.
	 * 
	 * @param <T>
	 *            the expected type of the given element
	 * @param type
	 *            the expected type of the given element
	 * @param parent
	 *            the container
	 * @return a new element
	 */
	protected <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent) {
		Optional<EReference> defaultContainementRef = parent.eClass().getEAllContainments().stream()
				.filter(ref -> ref.getEType().getInstanceClass().isAssignableFrom(type)).findFirst();

		if (defaultContainementRef.isPresent()) {
			return createIn(type, parent, defaultContainementRef.get().getName());
		} else {
			Assert.fail("Unable to find a containement reference for " + type.getSimpleName() + " in " + parent); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent, String containmentRefName) {
		T newElement = create(type);

		EStructuralFeature ref = parent.eClass().getEStructuralFeature(containmentRefName);
		if (ref == null || !(ref instanceof EReference)) {
			Assert.fail("Invalid reference name"); //$NON-NLS-1$
		}

		if (ref.isDerived()) {
			Assert.fail(ref.getName() + " is a derived feature."); //$NON-NLS-1$
		}
		EReference eRef = (EReference) ref;
		if (!eRef.getEType().isInstance(newElement)) {
			fail("Invalid reference " + eRef.getName() + " for element" + newElement); //$NON-NLS-1$//$NON-NLS-2$
		}
		if (ref.isMany()) {
			((List) parent.eGet(ref)).add(newElement);
		} else {
			parent.eSet(ref, newElement);
		}

		return newElement;
	}

}
