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
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.papyrus.sirius.properties.common.utils.ContainerUtil;
import org.eclipse.papyrus.sirius.properties.uml.Activator;
import org.eclipse.papyrus.uml.domain.services.create.CreationStatus;
import org.eclipse.papyrus.uml.domain.services.create.ElementCreator;
import org.eclipse.papyrus.uml.domain.services.destroy.DestroyerStatus;
import org.eclipse.papyrus.uml.domain.services.destroy.ElementDestroyer;
import org.eclipse.papyrus.uml.domain.services.destroy.IDestroyer;
import org.eclipse.papyrus.uml.domain.services.modify.ElementFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.modify.IFeatureModifier;
import org.eclipse.papyrus.uml.domain.services.status.State;

/**
 * This service class includes all common services used to set or update
 * values, remove or create elements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesCrudServices {

	/**
	 * Used to delete an UML element by invoking papyrus uml deletion services.
	 * 
	 * @param selectedObject
	 *            the object to delete
	 * @param target
	 *            the owner of the reference
	 * @param refName
	 *            the name of the reference to update
	 * @return <code>true</code> if the element is removed, <code>false</code>
	 *         otherwise.
	 */
	public boolean delete(Object selectedObject, EObject target, String refName) {
		boolean isDeleted = false;
		if (selectedObject instanceof EObject) {
			EObject objectToRemove = (EObject) selectedObject;
			ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
					.getCrossReferenceAdapter(objectToRemove);
			EReference eReference = getReference(target, refName);
			EditableChecker checker = new EditableChecker();
			if (eReference == null || eReference.isContainment()) {
				IDestroyer destroyer = ElementDestroyer.buildDefault(crossReferenceAdapter, checker);
				DestroyerStatus destroyerStatus = destroyer.destroy(objectToRemove);
				if (destroyerStatus.getState() == State.FAILED) {
					ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
					AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
					Activator.getDefault().getLog()
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, labelProvider.getText(objectToRemove) + " cannot be deleted.")); //$NON-NLS-1$
				} else {
					isDeleted = true;
				}
			} else {
				IFeatureModifier modifier = new ElementFeatureModifier(crossReferenceAdapter, checker);
				org.eclipse.papyrus.uml.domain.services.status.Status removeValueStatus = null;
				if (!eReference.isMany()) {
					removeValueStatus = modifier.setValue(target, refName, null);
				} else {
					removeValueStatus = modifier.removeValue(target, refName, objectToRemove);
				}
				if (State.DONE == removeValueStatus.getState()) {
					isDeleted = true;
				} else {
					Activator.getDefault().getLog()
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, removeValueStatus.getMessage()));
				}
			}
		}
		return isDeleted;
	}

	/**
	 * Creates an UML Element by invoking papyrus uml creation services.
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param typeName
	 *            the type of the element to create
	 * @param refName
	 *            the name of the reference to update
	 * @return the object created by the service
	 */
	public EObject create(EObject target, String typeName, String refName) {
		EObject createdElement = null;
		if (getReference(target, refName) != null) {
			ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
					.getCrossReferenceAdapter(target);
			EditableChecker checker = new EditableChecker();
			ElementCreator ec = ElementCreator.buildDefault(crossReferenceAdapter, checker);
			CreationStatus status = ec.create(target, typeName, refName);
			createdElement = status.getElement();
		}
		return createdElement;
	}

	/**
	 * Updates the reference by adding the specified objectToSet to the target. If the
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param objectToSet
	 *            the object to add to the reference
	 * @param refName
	 *            the name of the reference to update
	 * @return <code>true</code> if the element has been properly set, <code>false</code>
	 *         otherwise.
	 */
	public boolean updateReference(EObject target, Object objectToSet, String refName) {
		boolean isUpdated = false;
		if (target != null) {
			EReference eReference = getReference(target, refName);
			if (eReference != null && !eReference.isContainment()) {
				if (objectToSet instanceof List<?> && eReference.isMany()) {
					// List case
					isUpdated = set(target, refName, objectToSet);
				} else if (objectToSet instanceof EObject) {
					// EObject case
					EObject eObject = (EObject) objectToSet;
					ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
							.getCrossReferenceAdapter(eObject);
					IFeatureModifier modifier = new ElementFeatureModifier(crossReferenceAdapter, new EditableChecker());
					isUpdated = modifier.addValue(target, refName, eObject).getState() == State.DONE;
				}
			}
		}
		return isUpdated;
	}

	/**
	 * Sets the specified value to the reference of the target.
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param refName
	 *            the name of the reference
	 * @param valueToSet
	 *            the new value to set
	 * @return <code>true</code> if the element has been properly set, <code>false</code>
	 *         otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean set(EObject target, String refName, Object valueToSet) {
		boolean isSetted = false;
		EReference eReference = getReference(target, refName);
		if (target != null) {
			if (eReference != null && valueToSet instanceof List<?> && eReference.isMany()) {
				isSetted = setNewList(target, (List<EObject>) valueToSet, refName, new EditableChecker());
			} else {
				isSetted = setUnary(target, refName, valueToSet);
			}
		}
		return isSetted;
	}

	/**
	 * Sets all elements of the specified list to the reference. If there were existing
	 * elements in the reference, they will be deleted and replaced by the content of the list.
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param listToSet
	 *            the list to set
	 * @param refName
	 *            the name of the reference to update
	 * @param checker
	 *            used by the service to check if an element can be modified
	 * @return <code>true</code> if the element has been properly set, <code>false</code>
	 *         otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean setNewList(EObject target, List<EObject> listToSet, String refName, EditableChecker checker) {
		boolean isSetted = true;
		ECrossReferenceAdapter crossReferenceAdapter = new ECrossReferenceAdapter();
		IFeatureModifier modifier = new ElementFeatureModifier(crossReferenceAdapter, checker);
		EReference eReference = getReference(target, refName);
		Object ref = target.eGet(eReference);
		List<EObject> oldRef = new ArrayList<EObject>();
		oldRef.addAll((List<EObject>) ref);
		// remove element
		for (EObject o : oldRef) {
			if (!listToSet.contains(o)) {
				// use domain-uml-service to keep all edition functionalities
				modifier.removeValue(target, refName, o);
			}
		}
		((List<Object>) ref).clear();
		for (EObject o : listToSet) {
			if (oldRef.contains(o)) {
				// do not use domain-uml-service, all edition functionalities have already been applied
				// because element already exist in this reference
				ContainerUtil.addToContainer(target, eReference, o);
			} else {
				isSetted = isSetted && modifier.addValue(target, refName, o).getState() == State.DONE;
			}
		}
		return isSetted;
	}

	/**
	 * Set a <b>unique</b> value of a feature. A set operation always override the
	 * current value. If the feature is many then the collection is cleared and then
	 * the new value is added (so it is the only value left in the collection at the
	 * end of the operation). If the feature is unary then the feature is simply
	 * set.
	 * 
	 * @param elementToUpdate
	 *            the object to modify
	 * @param featureName
	 *            the name of the <b>unary</b> feature
	 * @param newValue
	 *            the new value to set
	 * @return <code>true</code> if the element has been properly set, <code>false</code>
	 *         otherwise.
	 */
	private boolean setUnary(EObject elementToUpdate, String featureName, Object newValue) {
		ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
				.getCrossReferenceAdapter(elementToUpdate);
		ElementFeatureModifier elementFeatureModifier = new ElementFeatureModifier(crossReferenceAdapter, new EditableChecker());
		return elementFeatureModifier.setValue(elementToUpdate, featureName, newValue).getState() == State.DONE;
	}

	/**
	 * Get the reference from a target {@link EObject} by using its name.
	 * 
	 * @param target
	 *            the owner of the reference
	 * @param refName
	 *            the name of the reference to retrieve
	 * @return the reference from a target {@link EObject} by using its name.
	 */
	private EReference getReference(EObject target, String refName) {
		EReference eReference = null;
		if (target != null && refName != null && !refName.isBlank()) {
			EStructuralFeature eStructuralFeature = target.eClass().getEStructuralFeature(refName);
			if (eStructuralFeature instanceof EReference) {
				eReference = (EReference) eStructuralFeature;
			}
		}
		return eReference;
	}
}
