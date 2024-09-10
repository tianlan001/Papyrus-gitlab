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


/**
 * Factory that providers an {@link IModelTypesProviderFactory}.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 * @NOT-generated
 */
public interface IModelTypesProviderFactory {

	/**
	 * Provides an {@link IModelTypesProvider} for the given editor.
	 *
	 * @param editorID
	 * @return
	 */
	IModelTypesProvider getProviderForEditor(String editorID);

}
