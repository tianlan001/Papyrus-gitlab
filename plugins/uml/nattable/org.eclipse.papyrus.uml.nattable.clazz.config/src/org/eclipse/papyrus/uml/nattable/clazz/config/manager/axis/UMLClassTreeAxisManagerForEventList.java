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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.clazz.config.manager.axis;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.uml.nattable.manager.axis.UMLElementTreeAxisManagerForEventList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;

/**
 * 
 * @author Vincent Lorenzo
 *
 */
public class UMLClassTreeAxisManagerForEventList extends UMLElementTreeAxisManagerForEventList {


	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.UMLElementTreeAxisManagerForEventList#isAllowedContents(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration, int)
	 *
	 * @param objectToTest
	 * @param semanticParent
	 * @param conf
	 * @param depth
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object objectToTest, Object semanticParent, TreeFillingConfiguration conf, int depth) {
		//TODO : must be changed using filter rule
		if (depth == 0) {
			return objectToTest instanceof Class;
		}
		if (depth == 1) {
			return objectToTest instanceof Property || objectToTest instanceof Class || objectToTest instanceof Operation;
		}
		if (depth == 2) {
			return objectToTest instanceof Parameter;
		}

		return false;
	}
}
