/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.SpecializationType;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.dev.types.utils.IElementTypeComparator;

public class ElementTypesContentProvider implements ITreeContentProvider {

	private Map<IElementType, List<SpecializationType>> elementTypesHierarchy = new HashMap<IElementType, List<SpecializationType>>();

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof Object[]) {
			elementTypesHierarchy.clear();
			for (Object elementType : ((Object[]) newInput)) {
				if (elementType instanceof MetamodelType) {
					if (!elementTypesHierarchy.containsKey(elementType)) {
						elementTypesHierarchy.put((MetamodelType) elementType, new ArrayList<SpecializationType>());
					}
				} else if (elementType instanceof SpecializationType) {
					for (IElementType superType : ((SpecializationType) elementType).getSpecializedTypes()) {
						if (!elementTypesHierarchy.containsKey(superType)) {
							elementTypesHierarchy.put(superType, new ArrayList<SpecializationType>());
						}
						elementTypesHierarchy.get(superType).add((SpecializationType) elementType);
					}
				}
			}
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		inputChanged(null, null, inputElement);
		ArrayList<IElementType> result = new ArrayList<IElementType>();
		for (IElementType iElementType : elementTypesHierarchy.keySet()) {
			if (iElementType instanceof MetamodelType) {
				result.add(iElementType);
			}
		}
		Collections.sort(result, new IElementTypeComparator());

		return result.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return elementTypesHierarchy.containsKey(parentElement) ? elementTypesHierarchy.get(parentElement).toArray() : Collections.emptyList().toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length == 0 ? false : true;
	}
}
