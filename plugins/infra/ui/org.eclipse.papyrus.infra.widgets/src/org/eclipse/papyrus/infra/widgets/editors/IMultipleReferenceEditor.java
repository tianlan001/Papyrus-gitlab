/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 516526
 *  
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;


/**
 * Protocol for {@linkplain AbstractMultipleValueEditor multi-value editors}
 * that edit reference properties.
 * 
 * @since 3.0
 */
public interface IMultipleReferenceEditor {

	/**
	 * Sets the Content and Label providers for this editor.
	 *
	 * @param contentProvider
	 *            The content provider describing the elements that can be selected
	 * @param labelProvider
	 *            The label provider for the elements
	 */
	void setProviders(IStaticContentProvider contentProvider, ILabelProvider labelProvider);

	/**
	 * Sets whether the reference property enforces unique values.
	 * 
	 * @param unique
	 *            whether the reference is unique
	 */
	void setUnique(boolean unique);

}