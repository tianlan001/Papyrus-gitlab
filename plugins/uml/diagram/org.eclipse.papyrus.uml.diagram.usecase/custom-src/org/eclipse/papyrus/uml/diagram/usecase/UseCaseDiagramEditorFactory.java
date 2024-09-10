/*****************************************************************************
 * Copyright (c) 2008 Atos Origin.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase;

import org.eclipse.papyrus.infra.gmfdiag.common.GmfEditorFactory;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseDiagramEditPart;

/**
 * The editor factory to launch the usecase diagram.
 *
 * @author Emilien Perico
 */
public class UseCaseDiagramEditorFactory extends GmfEditorFactory {

	/**
	 * Instantiates a new use case diagram editor factory.
	 */
	public UseCaseDiagramEditorFactory() {
		super(UmlUseCaseDiagramForMultiEditor.class, UseCaseDiagramEditPart.MODEL_ID);
	}
}
