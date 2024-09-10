/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.manager.axis;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.SpecializationType;
import org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectAxisManager;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

/**
 *
 * This axis manager restrict the allowed contents to UML Element
 *
 */
public class UMLElementAxisManager extends EObjectAxisManager {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		return object instanceof Element;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#canCreateAxisElement(java.lang.String)
	 *
	 * @param elementId
	 * @return
	 */
	@Override
	public boolean canCreateAxisElement(String elementId) {
		if (elementId == null) {
			return false;
		}
		
		final IElementType types = ElementTypeRegistry.getInstance().getType(elementId);
		if (types == null || (types instanceof SpecializationType && ((SpecializationType) types).getMetamodelType() == null)) {
			return false;
		}

		final EClass eClass = types.getEClass();
		if (eClass != null) {
			if (eClass.getEPackage() == UMLPackage.eINSTANCE) {
				return true;
			}
		}
		return false;
	}

}
