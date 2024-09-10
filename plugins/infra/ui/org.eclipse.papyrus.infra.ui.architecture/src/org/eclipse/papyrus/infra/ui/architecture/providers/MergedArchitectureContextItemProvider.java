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
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.StyledString.Style;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainPreferences;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public abstract class MergedArchitectureContextItemProvider extends MergedArchitectureItemProvider {

	// Omit the URI authority to use the current viewer font family and the size segment to use the current size
	private final StyledString.Style BOLD = Style.newBuilder().setFont(URI.createURI("font:////bold")).toStyle(); //$NON-NLS-1$

	private ArchitectureDomainPreferences preferences = new ArchitectureDomainPreferences();

	public MergedArchitectureContextItemProvider(AdapterFactory adapterFactory, MergedArchitectureContext owner) {
		super(adapterFactory, owner);
	}

	@Override
	protected ArchitectureContext getADElement() {
		return (ArchitectureContext) getValue();
	}

	@Override
	protected MergedArchitectureContext getMergedElement() {
		return (MergedArchitectureContext) getOwner();
	}

	@Override
	public Object getStyledText(Object object) {
		if (object instanceof MergedArchitectureContext) {
			MergedArchitectureContext context = (MergedArchitectureContext) object;
			if (context.getId() != null && context.getId().equals(preferences.getDefaultContextId())) {
				return new StyledString(context.getName(), BOLD);
			}
		}
		return super.getStyledText(object);
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof MergedArchitectureContext) {
			return ((MergedArchitectureContext) object).getDomain();
		}
		return super.getParent(object);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof MergedArchitectureContext) {
			MergedArchitectureContext context = (MergedArchitectureContext) object;
			return !context.getViewpoints().isEmpty();
		}
		return super.hasChildren(object);
	}

	@Override
	public Collection<?> getChildren(Object object) {
		if (object instanceof MergedArchitectureContext) {
			MergedArchitectureContext context = (MergedArchitectureContext) object;
			List<Object> result = new ArrayList<>();
			result.addAll(context.getViewpoints());
			return result;
		} else {
			return super.getChildren(object);
		}
	}

	@Override
	protected Predicate<IItemPropertyDescriptor> propertyDescriptorFilter(Object object) {
		// Remove properties for properties that are elided by merge
		String dependenciesCategory = getString("_UI_DependenciesPropertyCategory");
		return pd -> dependenciesCategory.equals(pd.getCategory(object));
	}

}
