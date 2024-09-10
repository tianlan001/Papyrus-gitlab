/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity;

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityDiagramEditPart;

/**
 * The editor factory to launch the activity diagram.
 */
public class ActivityDiagramEditorFactory extends GmfEditorFactory {

	/**
	 * Instantiates a new activity diagram editor factory.
	 */
	public ActivityDiagramEditorFactory() {
		super(UmlActivityDiagramForMultiEditor.class, ActivityDiagramEditPart.MODEL_ID);
	}
}
