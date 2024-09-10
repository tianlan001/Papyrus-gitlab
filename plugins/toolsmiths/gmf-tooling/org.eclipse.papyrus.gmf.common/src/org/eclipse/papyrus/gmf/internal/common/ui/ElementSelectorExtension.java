/******************************************************************************
 * Copyright (c) 2006, 2007 Borland Software Corp.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author dstadnik
 */
public abstract class ElementSelectorExtension extends Observable implements ModelSelectionPageExtension {

	protected List<EObject> modelElements = new ArrayList<EObject>();

	protected EObject selectedModelElement;

	protected abstract String getModelElementName();

	protected abstract String getModelElementLabel(EObject modelElement);

	protected EClass getModelElementClass() {
		return null;
	}

	protected boolean isApplicable(EObject element) {
		return true;
	}

	protected List<EObject> getModelElements(Resource resource) {
		List<EObject> elements = new ArrayList<EObject>();
		for (Iterator<EObject> it = resource.getAllContents(); it.hasNext();) {
			EObject next = it.next();
			boolean applicableType = getModelElementClass() == null || next.eClass().equals(getModelElementClass());
			if (applicableType && isApplicable(next)) {
				elements.add(next);
			}
		}
		sortModelElements(elements);
		return elements;
	}

	protected void fireModelElementChanged() {
		setChanged();
		notifyObservers(selectedModelElement);
	}

	public EObject getModelElement() {
		return selectedModelElement;
	}

	/**
	 * Override if you don't like default alphabetical sort
	 * @param elements modified in-place
	 */
	protected void sortModelElements(List<EObject> elements) {
		Collections.sort(elements, new Comparator<EObject>() {

			public int compare(EObject o1, EObject o2) {
				String n1 = getModelElementLabel(o1);
				String n2 = getModelElementLabel(o2);
				return n1.compareTo(n2);
			}
		});
	}
}
