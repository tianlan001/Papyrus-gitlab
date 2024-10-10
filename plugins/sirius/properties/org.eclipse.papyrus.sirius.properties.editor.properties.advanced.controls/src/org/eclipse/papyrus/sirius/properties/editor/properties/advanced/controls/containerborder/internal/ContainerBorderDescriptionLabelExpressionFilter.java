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
package org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.containerborder.internal;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ContainerBorderDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.sirius.editor.properties.filters.common.ViewpointPropertyFilter;

/**
 * The filter used for the property section for the Label Expression text field.
 * of the Container Border.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class ContainerBorderDescriptionLabelExpressionFilter extends ViewpointPropertyFilter {

	@Override
	protected EStructuralFeature getFeature() {
		return PropertiesAdvancedControlsPackage.eINSTANCE.getContainerBorderDescription_LabelExpression();
	}

	@Override
	protected boolean isRightInputType(Object arg0) {
		return arg0 instanceof ContainerBorderDescription;
	}

}
