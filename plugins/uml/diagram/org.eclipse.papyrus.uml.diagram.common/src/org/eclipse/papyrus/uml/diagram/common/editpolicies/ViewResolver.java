/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte, Generalitat
 * de la Comunitat Valenciana . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano Muñoz (Prodevelop) – Initial implementation.
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.ecore.EObject;


/**
 * Lets a D&D policy know if an <EObject> is a <Node> or a <Edge> in the host <EditPart>.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 * @deprecated -->new functionality for drag and drop
 */
@Deprecated
public interface ViewResolver {

	/**
	 * Checks if is e object node.
	 *
	 * @param element
	 *            the element
	 *
	 * @return true, if is e object node
	 */
	boolean isEObjectNode(EObject element);

	/**
	 * Checks if is e object link.
	 *
	 * @param element
	 *            the element
	 *
	 * @return true, if is e object link
	 */
	boolean isEObjectLink(EObject element);

	/**
	 * Gets the e object semantic hint.
	 *
	 * @param element
	 *            the element
	 *
	 * @return the e object semantic hint
	 */
	int getEObjectSemanticHint(EObject element);
}
