/*****************************************************************************
 * Copyright (c) 2019 CEA LIST
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
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.api;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * This class is used to create a wrapper. From which you can launch an IeditorPart that can be seen by Papyrus Core.
 * see also org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory to create a factory that provide it
 * see also the extension point to reference the factory org.eclipse.papyrus.infra.ui.papyrusDiagram
 */
public interface IEditorPartWrapper extends IEditorModel, IPapyrusEditorDeclaration {

	/**
	 * Create the Eclipse Editor that should be shown inside Papyrus
	 *
	 *
	 * @return A new instance of the IEditorPart.
	 */
	@Override
	public IEditorPart createIEditorPart() throws PartInitException;

	/**
	 * Get the EditorActionBarContributor that should be associated with the editor .
	 *
	 * @return
	 */
	@Override
	public EditorActionBarContributor getActionBarContributor();

}
