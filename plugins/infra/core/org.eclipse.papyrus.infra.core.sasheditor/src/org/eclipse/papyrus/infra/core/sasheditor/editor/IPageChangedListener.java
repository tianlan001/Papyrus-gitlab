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



/**
 * @author cedric dumoulin
 *
 */
public interface IPageChangedListener {

	/**
	 * Method called when the page is changed
	 *
	 * @param newPage
	 *            The new page, or null if there is no container.
	 */
	public void pageChanged(IPage newPage);

}
