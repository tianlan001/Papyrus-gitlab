/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.timing.custom;

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;

public class TimingDiagramEditorFactory extends GmfEditorFactory {

	public TimingDiagramEditorFactory() {
		super(UmlTimingDiagramForMultiEditor.class, TimingDiagramEditPart.MODEL_ID);

	}

}
