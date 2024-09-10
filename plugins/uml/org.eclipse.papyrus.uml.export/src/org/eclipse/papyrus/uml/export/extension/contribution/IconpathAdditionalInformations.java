/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.extension.contribution;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.export.extension.AdditionalInformations;
import org.eclipse.papyrus.uml.export.util.IconHelper;
import org.eclipse.uml2.uml.Element;


/**
 * The Class IconpathAdditionalInformations.
 */
public class IconpathAdditionalInformations implements AdditionalInformations {

	/**
	 * @see org.eclipse.papyrus.uml.export.extension.AdditionalInformations#getData(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public String getData(Object object) {
		if (object instanceof Diagram ) {
			Diagram diagram = (Diagram) object;
			return IconHelper.getIconName(diagram);	
		} else if (object instanceof Element ) {
			Element element = (Element) object;
			return IconHelper.getIconName(element);	
		}
		return null;
	}

}
