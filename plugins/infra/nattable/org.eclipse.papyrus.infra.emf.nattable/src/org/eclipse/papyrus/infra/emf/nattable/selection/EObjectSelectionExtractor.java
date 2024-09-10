/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.infra.emf.nattable.selection;

import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.selection.ObjectsSelectionExtractor;

/**
 * @author Vincent Lorenzo
 *
 */
public class EObjectSelectionExtractor extends ObjectsSelectionExtractor {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.selection.ObjectsSelectionExtractor#getRealObject(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	protected Object getRealObject(Object object) {
		return EMFHelper.getEObject(object);
	}
}
