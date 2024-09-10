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

import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.views.modelexplorer.widgets.MetaclassLabelProvider;
import org.eclipse.uml2.uml.Stereotype;

/**
 * display the name of Eclass or stereotypes
 *
 */
public class StereotypeMetaclassLabelProvider extends MetaclassLabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Stereotype) {
			return UMLLabelInternationalization.getInstance().getKeyword(((Stereotype) element));
		}
		return super.getText(element);
	}
}
