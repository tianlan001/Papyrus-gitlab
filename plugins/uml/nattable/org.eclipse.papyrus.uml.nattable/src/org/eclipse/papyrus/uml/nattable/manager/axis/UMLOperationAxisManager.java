/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.manager.axis;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.papyrus.infra.emf.nattable.manager.axis.EOperationAxisManager;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.uml.nattable.provider.UMLOperationRestrictedContentProvider;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @author Céline JANSSENS
 *
 */
public class UMLOperationAxisManager extends EOperationAxisManager {
	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EStructuralFeatureAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		boolean value = super.isAllowedContents(object);
		if (value) {
			final EOperation operation = (EOperation) object;
			value = operation.eContainer().eContainer() == UMLPackage.eINSTANCE;
		}
		return value;
	}



	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#createPossibleAxisContentProvider(boolean)
	 *
	 * @param isRestricted
	 * @return
	 */
	@Override
	public IRestrictedContentProvider createPossibleAxisContentProvider(boolean isRestricted) {
		return new UMLOperationRestrictedContentProvider(this, isRestricted);
	}
}
