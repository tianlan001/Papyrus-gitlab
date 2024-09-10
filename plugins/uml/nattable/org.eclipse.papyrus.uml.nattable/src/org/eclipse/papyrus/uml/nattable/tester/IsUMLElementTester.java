/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.uml.nattable.tester;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.nattable.tester.ITableTester;
import org.eclipse.papyrus.uml.nattable.Activator;
import org.eclipse.uml2.uml.Element;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class IsUMLElementTester implements ITableTester {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.tester.ITableTester#isAllowed(java.lang.Object)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public IStatus isAllowed(Object context) {
		if (context instanceof Element) {
			return new Status(IStatus.OK, Activator.PLUGIN_ID, "The context is a UML Element"); //$NON-NLS-1$
		}
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The context is not an UML Element"); //$NON-NLS-1$
	}

}
