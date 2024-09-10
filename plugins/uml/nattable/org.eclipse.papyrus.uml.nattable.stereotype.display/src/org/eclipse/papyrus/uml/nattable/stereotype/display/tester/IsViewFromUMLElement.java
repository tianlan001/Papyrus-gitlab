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

package org.eclipse.papyrus.uml.nattable.stereotype.display.tester;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.nattable.Activator;
import org.eclipse.papyrus.uml.nattable.tester.IsUMLElementTester;

/**
 * @author Vincent Lorenzo
 *
 */
public class IsViewFromUMLElement extends IsUMLElementTester {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.tester.ITableTester#isAllowed(java.lang.Object)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public IStatus isAllowed(Object context) {
		if (context instanceof View) {
			EObject element = ((View) context).getElement();
			IStatus res = super.isAllowed(element);
			if (res.isOK()) {
				return new Status(IStatus.OK, Activator.PLUGIN_ID, "The context is a GMF View representing a UML Element"); //$NON-NLS-1$
			}
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The context is not an GMF View representing a UML Element"); //$NON-NLS-1$
	}
}
