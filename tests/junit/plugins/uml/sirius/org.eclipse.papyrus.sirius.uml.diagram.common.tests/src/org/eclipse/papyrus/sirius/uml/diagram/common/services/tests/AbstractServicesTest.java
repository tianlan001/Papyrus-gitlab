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
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Abstract test class offering util methods to create UML models.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class AbstractServicesTest {

	@SuppressWarnings("unchecked")
	protected <T extends EObject> T create(java.lang.Class<T> type) {
		EClassifier newType = UMLPackage.eINSTANCE.getEClassifier(type.getSimpleName());
		EObject newElement = UMLFactory.eINSTANCE.create((EClass) newType);
		return (T) newElement;
	}

	protected Element getRootFromUmlModel(String modelPath, boolean isReadOnly) {
		File file = new File(modelPath);
		AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		CommandStack commandStack = new BasicCommandStack();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
		ResourceSet rs = editingDomain.getResourceSet();
		Resource r1 = rs.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		if (isReadOnly) {
			HashMap<Resource, Boolean> resReadOnly = new HashMap<Resource, Boolean>();
			resReadOnly.put(r1, Boolean.valueOf(true));
			editingDomain.setResourceToReadOnlyMap(resReadOnly);
		}

		return (Element) r1.getContents().get(0);
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
			fail("Unable to find a containement reference for " + type.getSimpleName() + " in " + parent); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends EObject> T createIn(java.lang.Class<T> type, EObject parent, String containmentRefName) {
		T newElement = create(type);

		EStructuralFeature ref = parent.eClass().getEStructuralFeature(containmentRefName);
		if (ref == null || !(ref instanceof EReference)) {
			fail("Invalid reference name"); //$NON-NLS-1$
		}

		if (ref.isDerived()) {
			fail(ref.getName() + " is a derived feature."); //$NON-NLS-1$
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
