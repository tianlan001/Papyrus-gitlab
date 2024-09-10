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

import org.eclipse.papyrus.infra.core.sasheditor.internal.SashWindowsContainer;



/**
 * A Visitor used to visit all the {@link IPage} of a {@link SashWindowsContainer}.
 *
 * @author cedric dumoulin
 *
 */
public interface IPageVisitor {

	/**
	 * Visit the provided page of type {@link IComponentPage}.
	 *
	 * @param page
	 */
	void accept(IComponentPage page);

	/**
	 * Visit the provided page of type {@link IEditorPage}.
	 *
	 * @param page
	 */
	void accept(IEditorPage page);

}
