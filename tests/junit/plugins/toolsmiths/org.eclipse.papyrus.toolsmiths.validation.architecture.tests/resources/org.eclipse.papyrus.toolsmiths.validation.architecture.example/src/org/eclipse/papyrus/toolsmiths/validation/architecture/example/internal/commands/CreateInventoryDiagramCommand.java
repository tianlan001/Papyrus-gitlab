/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.architecture.example.internal.commands;

import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.papyrus.infra.gmfdiag.common.AbstractPapyrusGmfCreateDiagramCommandHandler;

public class CreateInventoryDiagramCommand extends AbstractPapyrusGmfCreateDiagramCommandHandler {

	private static final String ID = "org.eclipse.papyrus.toolsmiths.validation.architecture.example.InventoryDiagram";
	private static final PreferencesHint DIAGRAM_PREFERENCES_HINT = new PreferencesHint(ID);
	
	public CreateInventoryDiagramCommand() {
		super();
	}

	@Override
	protected String getDiagramNotationID() {
		return ID;
	}

	@Override
	protected String getDefaultDiagramName() {
		return "Inventory";
	}

	@Override
	protected PreferencesHint getPreferenceHint() {
		return DIAGRAM_PREFERENCES_HINT;
	}

}
