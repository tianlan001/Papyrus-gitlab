/*****************************************************************************
 * Copyright (c) 2018, 2021 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 570486
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.viewpoints.policy.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.papyrus.infra.core.architecture.provider.ArchitectureDescriptionLanguageItemProvider;

/**
 * @author melaasar
 * @deprecated An item provider factory is registered for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureDescriptionLanguageItemProvider extends ArchitectureDescriptionLanguageItemProvider {

	/**
	 * Constructor.
	 *
	 * @param adapterFactory
	 */
	public MergedArchitectureDescriptionLanguageItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	protected ItemPropertyDescriptor createItemPropertyDescriptor(
			AdapterFactory adapterFactory,
			ResourceLocator resourceLocator,
			String displayName,
			String description,
			EStructuralFeature feature,
			boolean isSettable,
			boolean multiLine,
			boolean sortChoices,
			Object staticImage,
			String category,
			String[] filterFlags,
			Object propertyEditorFactory) {
		return new MergedItemPropertyDescriptor(adapterFactory,
				resourceLocator,
				displayName,
				description,
				feature,
				false,
				multiLine,
				sortChoices,
				staticImage,
				category,
				filterFlags,
				propertyEditorFactory);
	}
}
