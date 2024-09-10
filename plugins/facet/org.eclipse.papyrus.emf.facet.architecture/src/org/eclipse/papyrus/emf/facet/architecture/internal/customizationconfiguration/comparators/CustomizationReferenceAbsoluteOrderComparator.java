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

import java.util.Comparator;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;

/**
 * This comparator is in charge to organize the {@link CustomizationReference} declared with a {@link IApplicationRule} of type {@link AbsoluteOrder}
 */
public class CustomizationReferenceAbsoluteOrderComparator implements Comparator<CustomizationReference> {

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	@Override
	public int compare(final CustomizationReference custoRef0, final CustomizationReference custoRef1) {
		final AbsoluteOrder absoluteOrder0 = (AbsoluteOrder) custoRef0.getApplicationRule();
		final AbsoluteOrder absoluteOrder1 = (AbsoluteOrder) custoRef1.getApplicationRule();
		return Integer.valueOf(absoluteOrder0.getOrder()).compareTo(Integer.valueOf(absoluteOrder1.getOrder()));
	}

}
