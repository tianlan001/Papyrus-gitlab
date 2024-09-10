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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.contentoutline;

import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;

/**
 * Extends the original interface to add the init method.
 * 
 * @since 1.2
 */
public interface IPapyrusContentOutlinePage extends org.eclipse.ui.views.contentoutline.IContentOutlinePage {

	/**
	 * Init the content outline.
	 *
	 * @param multiEditor
	 *            the multiEditor is used to access to the context
	 * @throws BackboneException
	 *             during research of the associated context.
	 */
	void init(IMultiDiagramEditor multiEditor) throws BackboneException;

}
