/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.ui.advanced.controls.internal;

import org.eclipse.eef.ext.widgets.reference.eefextwidgetsreference.EefExtWidgetsReferencePackage;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsFactory;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage;
import org.eclipse.sirius.properties.core.api.DefaultDescriptionWithInitialOperationConverter;

/**
 * This class is used to convert the Sirius Extension editable reference
 * description into the EEF one.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class ExtEditableReferenceDescriptionConverter
		extends DefaultDescriptionWithInitialOperationConverter<ExtEditableReferenceDescription> {

	/**
	 * The constructor.
	 */
	public ExtEditableReferenceDescriptionConverter() {
		super(ExtEditableReferenceDescription.class,
				EefAdvancedControlsPackage.Literals.EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION,
				EefExtWidgetsReferencePackage.Literals.EEF_EXT_REFERENCE_DESCRIPTION__ON_CLICK_EXPRESSION);
	}

	@Override
	protected EFactory getEFactory() {
		return EefAdvancedControlsFactory.eINSTANCE;
	}

}
