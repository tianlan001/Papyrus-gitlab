/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.emftree;

import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.diagram.common.handler.CreateDiagramHandler;

public class CreateEMFTreeDiagramHandler extends CreateDiagramHandler {

	@Override
	protected void addNewDiagram(ServicesRegistry registry) {
		addNewDiagram("Emf Tree", EmfTreeDiagramEditorFactory.EMF_DIAGRAM_TYPE, null, registry);
	}

	public void createDiagram(ServicesRegistry registry) {
		addNewDiagram(registry);
	}
}
