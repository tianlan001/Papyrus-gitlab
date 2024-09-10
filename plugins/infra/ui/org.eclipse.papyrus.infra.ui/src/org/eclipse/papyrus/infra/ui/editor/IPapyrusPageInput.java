/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorInput;

/**
 * An IEditorInput used to reference the page(s) to open
 *
 * @author Camille Letavernier
 * @since 1.2
 *
 */
public interface IPapyrusPageInput extends IEditorInput {

	/**
	 * @return the list of pages to open
	 */
	public URI[] getPages();

	/**
	 *
	 * @return true if the editor should close all other pages
	 */
	public boolean closeOtherPages();
}
