/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Cedric Dumoulin cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Saadia Dhouib saadia.dhouib@cea.fr  - Adapted from Composite Structure Diagram
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.communication;

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.ModelEditPart;

/**
 *
 * Specialization for communication diagram
 *
 */
public class CommunicationDiagramEditorFactory extends GmfEditorFactory {

	/**
	 *
	 * Constructor.
	 *
	 */
	public CommunicationDiagramEditorFactory() {
		super(UmlCommunicationDiagramForMultiEditor.class, ModelEditPart.MODEL_ID);

	}
}
