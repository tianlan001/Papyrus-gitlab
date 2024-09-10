/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA LIST, Christian W. Damus, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus - bug 399859
 *  Christian W. Damus - bug 459613
 *  Camille Letavernier (EclipseSource) - bug 530156
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.decoratormodel.helper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.tools.utils.ProfileUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil.StereotypeApplicationHelper;


/**
 * Specific Stereotype Application Helper for Papyrus tool. If it detects the model is not a model handled by Papyrus, it will delegate to the standard Stereotype application helper.
 */
public class PapyrusStereotypeApplicationHelper extends StereotypeApplicationHelper {

	/**
	 * @since 2.1
	 */
	@Override
	protected EList<EObject> getContainmentList(Element element, EClass definition, Stereotype stereotype) {
		// Locate stereotype applications in the same resource as the profile application, if the
		// profile application is not actually in the same content tree as the base 'element'

		ProfileApplication profileApplication = ProfileUtil.getProfileApplication(element, definition, stereotype);
		if ((profileApplication != null) && (profileApplication.getApplyingPackage() != null)
				&& !EcoreUtil.isAncestor(profileApplication.getApplyingPackage(), element)) {

			Resource containingResource = profileApplication.eResource();
			if (containingResource != null) {
				return containingResource.getContents();
			}
		}

		return super.getContainmentList(element, definition, stereotype);
	}

}
