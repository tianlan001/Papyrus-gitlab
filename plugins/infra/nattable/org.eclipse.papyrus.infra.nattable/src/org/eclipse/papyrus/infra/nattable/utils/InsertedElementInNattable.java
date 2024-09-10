/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class allows to define the created elements by context on index (for parent and table).
 * Those created elements come from the paste/insert in nattable.
 */
public class InsertedElementInNattable {

	/**
	 * The context of the created elements.
	 */
	protected EObject context;

	/**
	 * The list of created elements.
	 */
	protected List<Object> createdElements;

	/**
	 * The containment feature.
	 */
	protected EStructuralFeature containementFeature;

	/**
	 * The index where add the created elements in the context.
	 */
	protected int indexInParent;

	/**
	 * The index where add the created elements in the table.
	 */
	protected int indexInTable;


	/**
	 * Constructor.
	 *
	 * @param context
	 *            The context of the created elements
	 * @param containementFeature
	 *            The containment feature.
	 * @param indexInParent
	 *            The index where add the created elements in the context.
	 * @param indexInTable
	 *            The index where add the created elements in the table.
	 */
	public InsertedElementInNattable(EObject context, EStructuralFeature containementFeature, int indexInParent, int indexInTable) {
		super();
		this.context = context;
		this.createdElements = new ArrayList<Object>();
		this.containementFeature = containementFeature;
		this.indexInParent = indexInParent;
		this.indexInTable = indexInTable;
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param context
	 *            The context of the created elements.
	 * @param createdElements
	 *            The created elements to add.
	 * @param containementFeature
	 *            The containment feature.
	 * @param indexInParent
	 *            The index where add the created elements in the context.
	 * @param indexInTable
	 *            The index where add the created elements in the table.
	 */
	public InsertedElementInNattable(EObject context, List<Object> createdElements, EStructuralFeature containementFeature, int indexInParent, int indexInTable) {
		super();
		this.context = context;
		this.createdElements = new ArrayList<Object>(createdElements.size());
		this.createdElements.add(createdElements);
		this.containementFeature = containementFeature;
		this.indexInParent = indexInParent;
		this.indexInTable = indexInTable;
	}

	/**
	 * Get the created elements.
	 * 
	 * @return the created elements.
	 */
	public List<Object> getCreatedElements() {
		return createdElements;
	}

	/**
	 * This allows to add a created elements.
	 * 
	 * @param object
	 *            the created element to add.
	 */
	public void addCreatedElement(final Object createdElement) {
		this.createdElements.add(createdElement);
	}

	/**
	 * Get the context.
	 * 
	 * @return the context.
	 */
	public EObject getContext() {
		return context;
	}

	/**
	 * Get the containment feature.
	 * 
	 * @return the containment feature.
	 */
	public EStructuralFeature getContainementFeature() {
		return containementFeature;
	}

	/**
	 * Get the index where add the created elements in the context.
	 * 
	 * @return the index where add the created elements in the context.
	 */
	public int getIndexInParent() {
		return indexInParent;
	}

	/**
	 * Get the index where add the created elements in the table.
	 * 
	 * @return the index where add the created elements in the table.
	 */
	public int getIndexInTable() {
		return indexInTable;
	}

	/**
	 * Need to compare the context for the inserted element.
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (obj instanceof InsertedElementInNattable) {
			result = ((InsertedElementInNattable) obj).getContext().equals(getContext());
		} else if (obj instanceof EObject) {
			result = ((EObject) obj).equals(getContext());
		} else {
			result = super.equals(obj);
		}

		return result;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
