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
package org.eclipse.papyrus.infra.ui.editorsfactory;

import org.eclipse.swt.graphics.Image;

/**
 * Registry used to get Icons associated to an editor.
 *
 * @author cedric dumoulin
 * @since 1.2
 */
public interface IPageIconsRegistry {

	/**
	 * Get the icon associated to the editor used to render the model. Model
	 * represent the top level object of a model editor.
	 *
	 * @param model
	 *            the model representing the Editor. This is usually the EObject
	 *            used to reconstruct the editor.
	 * @return the icon representing the editor
	 */
	public Image getEditorIcon(Object model);

	/**
	 * Dispose this registry
	 */
	public void dispose();
}
