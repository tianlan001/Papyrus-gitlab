/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.contentprovider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.ui.emf.utils.EcoreModelContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * 
 * This class provides the elements of the model to used them as source for the rows and columns of the matrix
 * 
 * @since 3.0
 * @deprecated since Papyrus 6.0.100
 */
@Deprecated
public class MatrixSourcesContentProvider extends EcoreModelContentProvider implements IStaticContentProvider {

	/**
	 * Constructor.
	 *
	 * @param root
	 */
	public MatrixSourcesContentProvider(EObject root) {
		super(root);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getElements() {
		return getElements(null);
	}


}
