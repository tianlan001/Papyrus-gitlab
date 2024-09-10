/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 455060
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * The content provider for the row containment feature.
 */
public class RowContainmentFeatureContentProvider extends AbstractContainmentFeatureContentProvider {

	/**
	 * Constructor.
	 *
	 * @param table
	 *            the table used to get the available features
	 * @deprecated
	 */
	public RowContainmentFeatureContentProvider(final Table table) {
		this(table, table.getContext().eClass());
	}

	/**
	 * Constructor.
	 *
	 * @param table
	 *            the table used to get the available features
	 * @param eClass
	 *            the {@link EClass} of the parent element
	 */
	public RowContainmentFeatureContentProvider(final Table table, final EClass eClass) {
		this(table, Collections.singletonList(eClass));
	}

	/**
	 * Constructor.
	 *
	 * @param table
	 *            the table used to get the available features
	 * @param eClasses
	 *            the {@link EClass} of the parent elements
	 */
	public RowContainmentFeatureContentProvider(final Table table, final List<EClass> eClasses) {
		super(table, eClasses, false);
	}

}
