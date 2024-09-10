/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and Others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	 Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * Association Composite edit helper advice.
 * 
 * @since 3.0
 */
public class AssociationCompositeEditHelperAdvice extends AssociationEditHelperAdvice {

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * The currently created {@link Association} is Composite (manage by super class):
	 * 		it source {@link Property} aggregation is set to Composite kind.
	 * </pre>
	 */
	@Override
	protected void configureSourceProperty(Property sourceProperty) {
		sourceProperty.setAggregation(AggregationKind.COMPOSITE_LITERAL);
	}
}
