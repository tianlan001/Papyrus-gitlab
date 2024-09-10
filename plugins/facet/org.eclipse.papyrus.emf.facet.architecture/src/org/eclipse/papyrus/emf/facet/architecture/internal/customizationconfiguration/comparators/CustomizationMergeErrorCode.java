/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;

/**
 * this class lists the error code we can get merging {@link EMFFacetTreeViewerConfiguration}
 */

public final class CustomizationMergeErrorCode {

	private CustomizationMergeErrorCode() {
		// to prevent instantiation
	}

	/**
	 * an {@link EMFFacetTreeViewerConfiguration} extends another one which is not in the same ADL
	 */
	public static final int CHECK_ERROR__REFERENCE_OUTSIDE_OF_ADL = 1;

	/**
	 * an {@link EMFFacetTreeViewerConfiguration#getExtends()} create an infinite loop
	 */
	public static final int CHECK_ERROR__EXTENDS_INFINITE_LOOP = CHECK_ERROR__REFERENCE_OUTSIDE_OF_ADL + 1;

	/**
	 * The defined root don't use only {@link AbsoluteOrder} to define {@link CustomizationReference}
	 */
	public static final int CHECK_ERROR__NOT_ONLY_USE_ABSOLUTE_ORDER_IN_ROOTS = CHECK_ERROR__EXTENDS_INFINITE_LOOP + 1;

	/**
	 * Several identical order
	 */
	public static final int CHECK_ERROR__DUPLICATE_ABSOLUTE_ORDER = CHECK_ERROR__NOT_ONLY_USE_ABSOLUTE_ORDER_IN_ROOTS + 1;

	/**
	 * I'm redefined several time
	 */
	public static final int CHECK_ERROR__TOO_MANY_REDEFINES_FOR_ME = CHECK_ERROR__DUPLICATE_ABSOLUTE_ORDER + 1;

	/**
	 * I'm not alone to redefine this customization
	 */
	public static final int CHECK_ERROR__EXTRA_REDEFINES = CHECK_ERROR__TOO_MANY_REDEFINES_FOR_ME + 1;

	/**
	 * Too many Customization must be inserted before me
	 */
	public static final int CHECK_ERROR__TOO_MANY_BEFORE_FOR_ME = CHECK_ERROR__EXTRA_REDEFINES + 1;

	/**
	 * I'm not alone to want to insert a Customization before this one.
	 */
	public static final int CHECK_ERROR__EXTRA_BEFORE = CHECK_ERROR__TOO_MANY_BEFORE_FOR_ME + 1;

	/**
	 * Too many Customization must be inserted after me
	 */
	public static final int CHECK_ERROR__TOO_MANY_AFTER_FOR_ME = CHECK_ERROR__EXTRA_BEFORE + 1;

	/**
	 * I'm not alone to want to insert a Customization after this one.
	 */
	public static final int CHECK_ERROR__EXTRA_AFTER = CHECK_ERROR__TOO_MANY_AFTER_FOR_ME + 1;

	/**
	 * The redefined customization is not found
	 */
	public static final int MERGE_ERROR__REDEFINE_NOT_FOUND = CHECK_ERROR__EXTRA_AFTER + 1;

	/**
	 * The customization before which one I must be inserted is not found
	 */
	public static final int MERGE_ERROR__BEFORE_NOT_FOUND = MERGE_ERROR__REDEFINE_NOT_FOUND + 1;

	/**
	 * The customization after which one I must be inserted is not found
	 */
	public static final int MERGE_ERROR__AFTER_NOT_FOUND = MERGE_ERROR__BEFORE_NOT_FOUND + 1;

	/**
	 * The code used to create a {@link MultiStatus}
	 */
	public static final int MULTI_STATUS_CODE = MERGE_ERROR__AFTER_NOT_FOUND + 1;

}
