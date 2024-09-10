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
 *    Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.internal.sheet;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.uml2.uml.Element;

/**
 *
 * This class allow to restraint the the display of the property section to UML Element and GMF Diagram.
 * This filter has been added to contribute to the Advanced Property View only for UML and Diagram elements (see bug 546737)
 *
 * @since 3.3.200
 */
public class UMLPropertySectionFilter implements IFilter {

	/**
	 *
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 *
	 * @param selected
	 * @return
	 */
	@Override
	public boolean select(Object selected) {
		final Object input = UMLPropertySectionInputUtils.transformSelection(selected);
		if (input instanceof Diagram) {
			return true;
		}
		if (input instanceof Element) {
			return true;
		}
		return false;
	}

}
