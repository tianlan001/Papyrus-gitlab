/*****************************************************************************
 * Copyright (c) 2010, 2015 LIFL, CEA LIST, Christian W. Damus, and others.
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
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 433371
 *  Christian W. Damus - bug 469188
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ICloseablePart;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;


/**
 * Class providing a utility methods allowing to get the real Model from the {@link IPage#getRawModel()}.
 * The utility takes into account the bug 309943.
 *
 * @author cedric dumoulin
 *
 */
public class IPageUtils {

	/**
	 * Get the real model rather than the PageRef. This method is a trick to temporally solve the bug 309943.
	 *
	 * @param page
	 * @return
	 */
	public static Object getRawModel(IPage page) {

		if (page == null) {
			return null;
		}

		Object pageModel = page.getRawModel();
		// Get the real model because of bug
		if (pageModel instanceof PageRef) {
			return ((PageRef) pageModel).getPageIdentifier();
		}
		// do not use trick
		return pageModel;
	}

	/**
	 * Lookup the IPage model corresponding to the identifier from the {@link ISashWindowsContainer}.
	 * The identifier can be either a {@link PageRef} or a emf Diagram. <br>
	 * This method can be used as a hack to bug 401107
	 *
	 * @param container
	 * @param identifier
	 * @return The corresponding IPage, or null if not found.
	 */
	public static IPage lookupModelPage(ISashWindowsContainer container, Object identifier) {

		LookupIPageVisitor visitor = new LookupIPageVisitor(identifier);
		container.visit(visitor);
		return visitor.getResult();
	}

	/**
	 * Queries whether the user should be permitted to close a {@code page}.
	 * 
	 * @param page
	 *            a page
	 * @return whether it can be closed
	 * @since 2.0
	 */
	public static boolean canClose(IPage page) {
		ICloseablePart closeable = (page instanceof IAdaptable)
				? ((IAdaptable) page).getAdapter(ICloseablePart.class)
				: null;

		return (closeable == null) || closeable.canClose();
	}
}
