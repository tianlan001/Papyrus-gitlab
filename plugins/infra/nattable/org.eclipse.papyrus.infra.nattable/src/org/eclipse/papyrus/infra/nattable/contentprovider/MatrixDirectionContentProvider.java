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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * This class provides the available relationship direction required to edit relationship in case of matrix table
 * 
 * @since 3.0
 *
 */
public class MatrixDirectionContentProvider implements IStaticContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getElements() {
		final List<Object> values = new ArrayList<Object>();
		for (EEnumLiteral lit : NattablecelleditorPackage.eINSTANCE.getMatrixRelationShipDirection().getELiterals()) {
			values.add(lit.getInstance());
		}
		return values.toArray();
	}

}
