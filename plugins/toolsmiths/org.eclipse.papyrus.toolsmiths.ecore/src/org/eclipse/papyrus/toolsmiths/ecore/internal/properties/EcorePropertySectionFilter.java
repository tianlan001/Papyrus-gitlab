/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.ecore.internal.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * This class allows to activate the contributed property view only for element coming from {@link EcorePackage}
 */
public class EcorePropertySectionFilter implements IFilter {

	/**
	 *
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 *
	 * @param toTest
	 * @return
	 */
	@Override
	public boolean select(Object toTest) {
		final EObject elem = EMFHelper.getEObject(toTest);
		if (elem != null) {
			boolean result = elem.eClass().getEPackage() == EcorePackage.eINSTANCE;
			return result;
		}
		return false;
	}

}
