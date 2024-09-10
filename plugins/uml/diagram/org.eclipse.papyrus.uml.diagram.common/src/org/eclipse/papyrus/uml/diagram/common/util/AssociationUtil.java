/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 419357
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.util;

import java.util.List;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * 
 * An utility class to use association.
 *
 */
public class AssociationUtil {

	public static boolean isAssociationEndProperty(Property property) {
		return property.getAssociation() != null;
	}

	/**
	 * This compares the Property of an Association with expected informations.
	 * 
	 * @param expectedNavigable
	 *            The expected navigable information.
	 * @param expectedAggregation
	 *            The expected kind of aggregation.
	 * @param property
	 *            The property containing the aggregation to compare.
	 * @return The result of the comparison.
	 */
	public static boolean isIdenticalProperties(final boolean expectedNavigable, final AggregationKind expectedAggregation, final Property property) {
		return (isIdenticalAggregations(expectedAggregation, property) && isIdenticalNavigables(expectedNavigable, property));
	}

	/**
	 * This compares the aggregation information of a Property with an expected kind of aggregation.
	 * 
	 * @param expectedAggregation
	 *            The expected kind of aggregation.
	 * @param property
	 *            The property containing the aggregation to compare.
	 * @return The result of the comparison.
	 */
	public static boolean isIdenticalAggregations(final AggregationKind expectedAggregation, final Property property) {
		boolean identicalAggregation = false;

		final AggregationKind aggregation = property.getAggregation();

		if (expectedAggregation.equals(aggregation)) {
			identicalAggregation = true;
		}

		return identicalAggregation;
	}

	/**
	 * This compares the navigable information of a Property with an expected navigable.
	 * 
	 * @param expectedNavigable
	 *            The expected navigable information.
	 * @param property
	 *            The property containing the navigable information to compare.
	 * @return The result of the comparison.
	 */
	public static boolean isIdenticalNavigables(final boolean expectedNavigable, final Property property) {
		boolean identicalNavigable = false;
		final boolean navigable = property.isNavigable();

		if (expectedNavigable == navigable) {
			identicalNavigable = true;
		}

		return identicalNavigable;
	}

	/**
	 * Returns the first memberEnd of the association. This memberEnd represents the initial target.
	 * 
	 * @param association
	 *            The Association.
	 * @return The first memberEnd.
	 * @since 3.0
	 */
	public static Property getInitialTargetFirstEnd(final Association association) {
		Property property = null;
		List<Property> memberEnds = association.getMemberEnds();
		if (!memberEnds.isEmpty()) {
			property = memberEnds.get(0);
		}
		return property;
	}

	/**
	 * Returns the second memberEnd of the association. This memberEnd represents the initial source.
	 * 
	 * @param association
	 *            The Association.
	 * @return The second memberEnd
	 * @since 3.0
	 */
	public static Property getInitialSourceSecondEnd(final Association association) {
		Property property = null;
		List<Property> memberEnds = association.getMemberEnds();
		if (2 <= memberEnds.size()) {
			property = memberEnds.get(1);
		}
		return property;
	}
}
