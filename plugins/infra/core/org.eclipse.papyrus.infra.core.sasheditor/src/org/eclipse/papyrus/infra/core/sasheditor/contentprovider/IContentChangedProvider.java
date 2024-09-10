/*****************************************************************************
 * Copyright (c) 2009, 2010 LIFL, CEA LIST and others.
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
package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;


/**
 * Interface implemented by classes firing events when the ContentProvider content
 * change.
 * This interface can be used by client of ContentProvider.
 * It is not use directly by the SashSystem.
 *
 * @author dumoulin
 */
public interface IContentChangedProvider {

	/**
	 * Add a listener listening on content changed. This listener will be
	 * notified each time the content change.
	 *
	 * @param listener
	 */
	public abstract void addListener(IContentChangedListener listener);

	/**
	 * Add a listener listening on content changed. This listener will be
	 * notified each time the content change.
	 *
	 * @param listener
	 */
	public abstract void removeListener(IContentChangedListener listener);

}
