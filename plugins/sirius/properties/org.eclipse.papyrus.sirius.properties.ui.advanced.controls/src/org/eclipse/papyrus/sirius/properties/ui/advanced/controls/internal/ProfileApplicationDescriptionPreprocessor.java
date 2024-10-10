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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.ProfileApplicationDescription;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage;
import org.eclipse.sirius.properties.core.api.PreconfiguredPreprocessor;

/**
 * The preprocessor of the Profile Application used by Sirius to resolve
 * inheritance and extension relations between property view descriptions.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class ProfileApplicationDescriptionPreprocessor extends PreconfiguredPreprocessor<ProfileApplicationDescription> {

	/**
	 * Constructor.
	 */
	public ProfileApplicationDescriptionPreprocessor() {
		super(ProfileApplicationDescription.class,
				PropertiesAdvancedControlsPackage.Literals.PROFILE_APPLICATION_DESCRIPTION);
	}

	@Override
	public boolean canHandle(EObject description) {
		return description instanceof ProfileApplicationDescription;
	}
}
