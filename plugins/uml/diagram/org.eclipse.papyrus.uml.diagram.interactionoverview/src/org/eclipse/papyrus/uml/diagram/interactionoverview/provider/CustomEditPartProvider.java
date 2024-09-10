/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.interactionoverview.provider;

import org.eclipse.papyrus.uml.diagram.interactionoverview.edit.part.CustomUMLInteractionOverviewEditPartFactory;

public class CustomEditPartProvider extends InheritedActivityDiagramEditPartProvider {

	public CustomEditPartProvider() {
		setFactory(new CustomUMLInteractionOverviewEditPartFactory());
		setAllowCaching(true);
	}
}
