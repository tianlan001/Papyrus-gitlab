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
package org.eclipse.papyrus.uml.diagram.common.providers.assistant;

import java.util.List;

/**
 * An Assitant that customizes the types for an EditPart by Java code. Created for the
 * 'es.cv.gvcase.mdt.common.editorAssistantProvider' extension point.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 *
 */
public interface AssistantCustomizeEditPart {

	/**
	 * Customizes the types for an EditPart.
	 *
	 * @param types
	 * @return
	 */
	List customizeTypes(List types);

}
