/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL
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
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import org.eclipse.swt.widgets.Composite;

/**
 * Interface implemented by Part that can be parent of a Panel (Sashes or Folders). The interface allows to restrict the classes that can be parent of
 * a Panel. For now, only {@link RootPart} and {@link SashPanelPart} can be parent of a Panel.
 *
 * @author dumoulin
 */
public interface IPanelParent {

	/**
	 * Get the {@link SashWindowsContainer}.
	 *
	 * @return
	 */
	public SashWindowsContainer getSashWindowContainer();

	/**
	 * Get the parent SWT control.
	 *
	 * @return
	 */
	public Composite getControl();

}
