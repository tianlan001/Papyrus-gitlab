/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *   Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.widgets;

import java.util.Comparator;

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;

/**
 * comparator UML NamedElement by taking in account their names
 *
 */
public class NamedElementComparator implements Comparator<Object> {

	public int compare(Object namedElement0, Object namedElement1) {
		if (namedElement0 instanceof NamedElement && namedElement1 instanceof NamedElement) {
			// TODO: This must manage getLabel() instead of getName() ?
			String emp1Name = UMLLabelInternationalization.getInstance().getLabel((NamedElement) namedElement0);
			String emp2Name = UMLLabelInternationalization.getInstance().getLabel((NamedElement) namedElement1);
			// uses compareTo method of String class to compare names of the Eclasses
			return emp1Name.compareTo(emp2Name);
		}
		else {
			return 0;
		}
	}



}
