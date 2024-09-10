/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
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
package org.eclipse.papyrus.infra.core.sasheditor.editor;

import org.eclipse.ui.IEditorPart;


/**
 * Allows to access to properties of a Sash IEditor Page.
 * This interface allows to read the data. User should not attent to modifiy or write the data in anyway.
 * This interface is provided as parameter of the {@link IPageChangedListener#pageChanged(IPage)} event and
 * in the SashContainer Visitor.
 *
 * @author cedric dumoulin
 *
 */
public interface IEditorPage extends IPage {

	/**
	 * Returns the active nested editor if there is one.
	 *
	 * @return the active nested editor, or <code>null</code> if none
	 */
	public IEditorPart getIEditorPart();

}
