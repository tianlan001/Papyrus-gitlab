/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Vincent Lorenzo vincent.lorenzo@cea.fr  - Adapted from Composite Structure Diagram
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.profile;

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.ProfileDiagramEditPart;

/**
 * The profile's diagram editor factory
 *
 * @author Vincent Lorenzo
 *
 */
public class ProfileDiagramEditorFactory extends GmfEditorFactory {

	/**
	 *
	 * Constructor.
	 *
	 */
	public ProfileDiagramEditorFactory() {
		super(UmlProfileDiagramForMultiEditor.class, ProfileDiagramEditPart.MODEL_ID);
	}
}
