/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
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
 *   Nicolas Fauvergue - nicolas.fauvergue@cea.fr - bug 517731
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.widgets.providers.AbstractRestrictedContentProvider;
import org.eclipse.papyrus.uml.nattable.manager.axis.UMLOperationAxisManager;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @author Céline JANSSENS
 *
 */
public class UMLOperationRestrictedContentProvider extends AbstractRestrictedContentProvider {

	/** the uml feature axis manager */
	private final UMLOperationAxisManager axisManager;


	/**
	 *
	 * Constructor.
	 * boolean fields are initialized to false
	 *
	 * @param axisManager
	 *            the axis manager used by this content provider
	 */
	public UMLOperationRestrictedContentProvider(final UMLOperationAxisManager axisManager) {
		this(axisManager, false);
	}


	/**
	 *
	 * Constructor.
	 * Inits {@link #ignoreInheritedFeatures} to true
	 * others boolean fields are initialized to false
	 *
	 * @param axisManager
	 *            the axis manager used by this content provider
	 * @param isRestricted
	 *            if <code>true</code> we return only elements accessible from the current contents of the table
	 */
	public UMLOperationRestrictedContentProvider(UMLOperationAxisManager axisManager, boolean isRestricted) {
		super(isRestricted);
		this.axisManager = axisManager;
		setIgnoreInheritedElements(false);
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (isRestricted()) {
			final boolean columnManager = axisManager.isUsedAsColumnManager();
			final List<?> elements;
			if (columnManager) {
				elements = this.axisManager.getTableManager().getRowElementsList();
			} else {
				elements = this.axisManager.getTableManager().getColumnElementsList();
			}
			if (elements.isEmpty()) {// we returns nothing in restricted mode when the table is empty
				return new Object[0];
			}
		}
		return new Object[] { UMLPackage.eINSTANCE };

		// if(isRestricted() && elements.isEmpty()) {//we must returns nothing when the table is empty
		// return new Object[0];
		// } else {
		// return this.axisManager.getAllPossibleAxis().toArray();
		// }
	}

	/**
	 *
	 * @return
	 */
	protected AbstractAxisProvider getManagedAxisProvider() {
		return this.axisManager.getRepresentedContentProvider();
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> asList = new ArrayList<>();
		if (parentElement instanceof EClass) {
			EClass eClass = (EClass) parentElement;
			if (isIgnoringInheritedElements()) {
				asList.addAll(eClass.getEOperations());
			} else {
				asList.addAll(eClass.getEAllOperations());
			}
			asList.remove(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
			removeOperations(asList);
			return asList.toArray();
		} else if (parentElement instanceof EPackage) {
			EPackage ePackage = (EPackage) parentElement;
			Collection<EClassifier> eClassifiers = null;
			if (isRestricted()) {
				eClassifiers = new HashSet<>();
				AbstractAxisProvider axisProvider = ((INattableModelManager) this.axisManager.getTableManager()).getHorizontalAxisProvider();
				if (axisProvider == this.axisManager.getRepresentedContentProvider()) {
					axisProvider = ((INattableModelManager) this.axisManager.getTableManager()).getVerticalAxisProvider();
				}
				boolean isColumnManager = this.axisManager.isUsedAsColumnManager();
				final List<Object> elementsList;
				if (isColumnManager) {
					elementsList = this.axisManager.getTableManager().getRowElementsList();
				} else {
					elementsList = this.axisManager.getTableManager().getColumnElementsList();
				}
				for (Object object : elementsList) {
					Object representedElement = AxisUtils.getRepresentedElement(object);
					if (representedElement instanceof Element) {
						EClass eClass = ((EObject) representedElement).eClass();
						eClassifiers.add(eClass);
						eClassifiers.addAll(eClass.getEAllSuperTypes());
					}
				}
			} else {
				eClassifiers = ePackage.getEClassifiers();
			}
			eClassifiers.remove(EcorePackage.eINSTANCE.getEModelElement());

			for (EClassifier eClassifier : eClassifiers) {
				if (eClassifier instanceof EClass && eClassifier != EcorePackage.eINSTANCE.getEModelElement()) {
					asList.add(eClassifier);// we returns EClass with no EStructural feature too
				}
			}
			return asList.toArray();
		}
		return asList.toArray();

	}

	/**
	 * This allows to remove the needed EOperation.
	 *
	 * @param objects
	 *            The list of objects
	 * @since 5.2
	 */
	protected void removeOperations(final Collection<?> objects) {
		removeVoidOperations(objects);
		removeOperationsWithParameters(objects);
	}

	/**
	 * This allows to remove the void EOperation.
	 *
	 * @param objects
	 *            The list of objects
	 */
	protected void removeVoidOperations(final Collection<?> objects) {
		Iterator<?> objectsIterator = objects.iterator();
		while (objectsIterator.hasNext()) {
			Object currentObject = objectsIterator.next();

			if (currentObject instanceof EOperation && null == ((EOperation) currentObject).getEType()) {
				objectsIterator.remove();
			}
		}
	}

	/**
	 * This allows to remove the EOperation with parameters.
	 *
	 * @param objects
	 *            The list of objects
	 * @since 5.2
	 */
	protected void removeOperationsWithParameters(final Collection<?> objects) {
		Iterator<?> objectsIterator = objects.iterator();
		while (objectsIterator.hasNext()) {
			Object currentObject = objectsIterator.next();

			if (currentObject instanceof EOperation && !((EOperation) currentObject).getEParameters().isEmpty()) {
				objectsIterator.remove();
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof EOperation) {
			EOperation operation = (EOperation) element;
			return operation.getEContainingClass();
		} else if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			return eClass.getEPackage();
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			final Collection<EOperation> eOperations = eClass.getEOperations();
			removeVoidOperations(eOperations);
			boolean hasChildren = !eOperations.isEmpty();
			return hasChildren;
		} else if (element instanceof EPackage) {
			EPackage ePackage = (EPackage) element;
			EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
			for (EClassifier eClassifier : eClassifiers) {
				if (eClassifier instanceof EClass) {
					return true;
				}
			}
			return false;
		} else if (element instanceof EOperation) {
			return false;
		}
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getElements() {
		return getElements(null);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 * 		<code>true</code> if the element is a UML Feature
	 */
	@Override
	public boolean isValidValue(Object element) {
		return element instanceof EOperation && UMLPackage.eINSTANCE.eContents().contains(((EObject) element).eContainer());
	}
}
