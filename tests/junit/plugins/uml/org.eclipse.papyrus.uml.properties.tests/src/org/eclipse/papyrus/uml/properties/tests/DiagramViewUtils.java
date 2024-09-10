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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.tests;

import java.util.Iterator;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.junit.utils.DiagramUtils;

/**
 * This allows to define utilities methods concerning diagram views.
 */
public class DiagramViewUtils {

	/**
	 * Constructor to avoid external initialization.
	 */
	private DiagramViewUtils() {
		// Do nothing
	}
	
	/**
	 * Get view of the semantic element.
	 *
	 * @param diagram
	 *            The diagram where find the view.
	 * @param semanticElement
	 *            The name of the semantic element.
	 * @return A corresponding view of the semantic element.
	 */
	public static View getDiagramView(final Diagram diagram, final String semanticElement) {
		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				final Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		return diagramView;
	}
	
}
