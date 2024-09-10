/*****************************************************************************
 * Copyright (c) 2013, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 570097, 570486
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.architecture.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * Represents a descriptor for properties of type EPackage.
 * This class lists the currently loaded ECore metamodels.
 *
 * @author Laurent Wouters
 */
public class EPackagePropertyDescriptor extends SurrogateItemPropertyDescriptor {
	public EPackagePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		Collection<EPackage> result = new UniqueEList.FastCompare<>();

		EPackage.Registry reg = Optional.ofNullable(object)
				.filter(EObject.class::isInstance).map(EObject.class::cast)
				.map(EObject::eResource).map(Resource::getResourceSet)
				.map(ResourceSet::getPackageRegistry)
				.orElse(EPackage.Registry.INSTANCE);

		Set<String> keys = new HashSet<>();
		keys.addAll(reg.keySet());
		if (reg != EPackage.Registry.INSTANCE) {
			// Assume it delegates
			keys.addAll(EPackage.Registry.INSTANCE.keySet());
		}

		for (String key : keys) {
			try {
				EPackage pack = reg.getEPackage(key);
				if (pack != null) {
					result.add(pack);
				}
			} catch (Exception e) {
				continue;
			}
		}

		return result;
	}

	@Override
	public IItemLabelProvider getLabelProvider(Object object) {
		return new IItemLabelProvider() {
			@Override
			public String getText(Object object) {
				return ((EPackage) unwrap(object)).getNsURI();
			}

			@Override
			public Object getImage(Object object) {
				return null;
			}
		};
	}
}
