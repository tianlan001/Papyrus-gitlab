/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Benoit Maggi benoit.maggi@cea.fr - Bug 473366 : get stereotype recursively
 *****************************************************************************/
package org.eclipse.papyrus.infra.architecture.representation.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.provider.SurrogateItemPropertyDescriptor;

/**
 * Represents a descriptor for stereotype properties
 *
 * @author Laurent Wouters
 */
public class StereotypePropertyDescriptor extends SurrogateItemPropertyDescriptor {
	private static final Collection<EObject> empty = new ArrayList<>();

	public StereotypePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	/**
	 * @see org.eclipse.papyrus.infra.architecture.representation.provider.SurrogateItemPropertyDescriptor#getChoiceOfValues(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		EObject current = (EObject) object;
		while (current != null && !(current instanceof ArchitectureDescriptionLanguage)) {
			current = current.eContainer();
		}
		if (current == null) {
			return empty;
		}
		ArchitectureDescriptionLanguage conf = (ArchitectureDescriptionLanguage) current;
		List<EClass> result = new ArrayList<>();
		for (EPackage p : conf.getProfiles()) { 
			result.addAll(getAllStereotypes(p));
		}
		return result;
	}


	/**
	 * Get all stereotype contained (even in sub package)
	 * @param ePackage
	 * @return
	 */
	protected List<EClass> getAllStereotypes(EPackage ePackage) {
		List<EClass> result = new ArrayList<>();
		for (EClassifier clazz : ePackage.getEClassifiers()) {
			if (clazz instanceof EClass) {
				result.add((EClass) clazz);
			}
		}
		for (EPackage subPackage : ePackage.getESubpackages()) {
			result.addAll(getAllStereotypes(subPackage));
		}
		return result;
	}

	@Override
	public IItemLabelProvider getLabelProvider(Object object) {
		return new IItemLabelProvider() {
			public String getText(Object object) {
				if (object instanceof EClass) {
					return eClassToString((EClass) object);
				}
				if (object instanceof EList<?>) {
					EList<?> list = (EList<?>) object;
					if (list.isEmpty()) {
						return "";
					}
					StringBuilder builder = new StringBuilder("{");
					for (int i = 0; i != list.size(); i++) {
						if (i != 0) {
							builder.append(", ");
						}
						builder.append(eClassToString((EClass) list.get(i)));
					}
					builder.append("}");
					return builder.toString();
				}
				return "";
			}

			public Object getImage(Object object) {
				return null;
			}
		};
	}

	private String eClassToString(EClass item) {
		return item.getEPackage().getNsPrefix() + "#" + item.getName();
	}
}
