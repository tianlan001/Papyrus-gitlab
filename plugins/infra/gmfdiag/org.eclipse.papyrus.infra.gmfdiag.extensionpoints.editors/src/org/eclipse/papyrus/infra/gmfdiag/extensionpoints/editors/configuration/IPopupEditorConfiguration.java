/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.ui.IPopupEditorHelper;

/**
 * This interface can be used to create an IPopupEditorHelper, that can then be used to show the editor
 */
public interface IPopupEditorConfiguration extends IDirectEditorConfiguration {

	/**
	 * Should create an IPopupEditorHelper, that can then be used to show the editor
	 *
	 * It is expected that the type of the param editPart:Object is instanceof IGraphicalEditPart
	 */
	IPopupEditorHelper createPopupEditorHelper(Object editPart);

}
