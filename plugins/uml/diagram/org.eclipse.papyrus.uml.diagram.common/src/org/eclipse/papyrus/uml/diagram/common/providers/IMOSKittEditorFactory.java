/*******************************************************************************
 * Copyright (c) 2009 Conselleria de Infraestructuras y Transporte, Generalitat
 * de la Comunitat Valenciana . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano Muñoz (Prodevelop) – Initial implementation
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.ui.IEditorPart;

/**
 * A factory that creates an {@link IEditorPart} for the given object.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 *
 */
public interface IMOSKittEditorFactory {

	/**
	 * True if it can provide an {@link IEditorPart} for the given {@link Object}.
	 *
	 * @param object
	 * @return
	 */
	// @unused
	boolean providesFor(Object object);

	/**
	 * Will give an {@link IEditorPart} that can edit the given {@link Object}.
	 *
	 * @param object
	 * @return
	 */
	IEditorPart createEditorFor(Object object);

}
