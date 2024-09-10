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
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;

/**
 * @author melaasar
 * @deprecated An item provider factory is registered for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedItemPropertyDescriptor extends ItemPropertyDescriptor {

	public MergedItemPropertyDescriptor(AdapterFactory adapterFactory,
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
			Object editorFactory) {
		super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags, null);
	}

	public Object getPropertyValue(Object object) {
		if (object instanceof MergedArchitectureContext) {
			switch (feature.getFeatureID()) {
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
				return ((MergedArchitectureContext)object).getConversionCommandClassName();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
				return ((MergedArchitectureContext)object).getCreationCommandClassName();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS:
				return ((MergedArchitectureContext)object).getDefaultViewpoints();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES:
				return ((MergedArchitectureContext)object).getElementTypes();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
				return ((MergedArchitectureContext)object).getExtensionPrefix();
			}
		};
		if (object instanceof MergedArchitectureDescriptionLanguage) {
			switch (feature.getFeatureID()) {
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL:
				return ((MergedArchitectureDescriptionLanguage)object).getMetamodel();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES:
				return ((MergedArchitectureDescriptionLanguage)object).getProfiles();
			}
		};
		if (object instanceof MergedArchitectureViewpoint) {
			switch (feature.getFeatureID()) {
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONCERNS:
				return ((MergedArchitectureViewpoint)object).getConcerns();
			case ArchitecturePackage.ARCHITECTURE_VIEWPOINT__REPRESENTATION_KINDS:
				return ((MergedArchitectureViewpoint)object).getRepresentationKinds();
			}
		};
		if (object instanceof MergedADElement) {
			switch (feature.getFeatureID()) {
			case ArchitecturePackage.AD_ELEMENT__DESCRIPTION:
				return ((MergedADElement)object).getDescription();
			case ArchitecturePackage.AD_ELEMENT__ICON:
				return ((MergedADElement)object).getIcon();
			case ArchitecturePackage.AD_ELEMENT__ID:
				return ((MergedADElement)object).getId();
			case ArchitecturePackage.AD_ELEMENT__NAME:
				return ((MergedADElement)object).getName();
			case ArchitecturePackage.AD_ELEMENT__QUALIFIED_NAME:
				return ((MergedADElement)object).getQualifiedName();
			}
		};
		
		return null;
	}

}
