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
package org.eclipse.papyrus.uml.export.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.NamedElement;


/**
 * The Class HTMLUtil.
 */
public class HTMLUtil {
	
	// utily that get a coherent File Name from diagram Name
	/**
	 * Diagram relativ path.
	 *
	 * @param diagram the diagram
	 * @return the string
	 */
	// for the first version diagram with same name on same namespace isn't handled
	public static String diagramRelativPath(Diagram diagram) {
		String filename = diagram.getName();
		EObject element = diagram.getElement();
		if (element instanceof NamedElement) {
			NamedElement namedElement = (NamedElement) element;
			String qualifiedName1 = namedElement.getNearestPackage().getQualifiedName().replaceAll("::", "/"); // FIXME system separator
			return qualifiedName1+"/"+filename.trim().replace(" ", "");	
		}
		return filename.trim().replace(" ", "");
	}
	
}
