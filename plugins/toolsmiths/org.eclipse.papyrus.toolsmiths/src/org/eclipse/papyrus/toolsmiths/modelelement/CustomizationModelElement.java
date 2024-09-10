/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.toolsmiths.creation.CustomizationElementCreationFactory;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;


public class CustomizationModelElement extends EMFModelElement {

	public CustomizationModelElement(EObject source) {
		super(source);
	}

	public CustomizationModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	@Override
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		EStructuralFeature feature = getFeature(propertyPath);
		if (feature.getEType() == CustomizationPluginPackage.eINSTANCE.getCustomizableElement()) {
			return new CustomizationElementCreationFactory((EReference) feature);
		}
		return super.getValueFactory(propertyPath);
	}

}
