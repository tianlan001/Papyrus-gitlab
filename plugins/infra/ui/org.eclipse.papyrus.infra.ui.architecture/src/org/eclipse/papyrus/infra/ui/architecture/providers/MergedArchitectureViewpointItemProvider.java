/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureViewpointItemProvider extends MergedArchitectureItemProvider {

	public MergedArchitectureViewpointItemProvider(AdapterFactory adapterFactory, MergedArchitectureViewpoint owner) {
		super(adapterFactory, owner);
	}

	@Override
	protected ArchitectureViewpoint getADElement() {
		return (ArchitectureViewpoint) getValue();
	}

	@Override
	protected MergedArchitectureViewpoint getMergedElement() {
		return (MergedArchitectureViewpoint) getOwner();
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof MergedArchitectureViewpoint) {
			return ((MergedArchitectureViewpoint) object).getContext();
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof MergedArchitectureViewpoint) {
			MergedArchitectureViewpoint viewpoint = (MergedArchitectureViewpoint) object;
			return !(viewpoint.getConcerns().isEmpty() && viewpoint.getRepresentationKinds().isEmpty());
		}
		return super.hasChildren(object);
	}

	@Override
	public Collection<?> getChildren(Object object) {
		if (object instanceof MergedArchitectureViewpoint) {
			MergedArchitectureViewpoint viewpoint = (MergedArchitectureViewpoint) object;
			List<Object> result = new ArrayList<>();

			// Wrap objects that aren't owned but cross-referenced
			wrapInto(viewpoint, viewpoint.getConcerns(), result);
			wrapInto(viewpoint, viewpoint.getRepresentationKinds(), result);
			return result;
		} else {
			return super.getChildren(object);
		}
	}

	@Override
	protected Predicate<IItemPropertyDescriptor> propertyDescriptorFilter(Object object) {
		// Remove properties for wrapped children
		Set<EStructuralFeature> unwanted = Set.of(ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT__CONCERNS,
				ArchitecturePackage.Literals.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS);
		return pd -> unwanted.contains(pd.getFeature(object));
	}

}
