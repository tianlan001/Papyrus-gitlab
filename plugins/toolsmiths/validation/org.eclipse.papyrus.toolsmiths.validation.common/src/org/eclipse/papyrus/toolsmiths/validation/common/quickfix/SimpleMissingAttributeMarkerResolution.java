/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import java.util.function.Function;

import org.eclipse.core.resources.IMarker;

import com.google.common.base.Functions;

/**
 * A simple marker resolution that adds an attribute that was missing in an extension
 * element in the <tt>plugin.xml</tt>.
 */
public final class SimpleMissingAttributeMarkerResolution extends AbstractMissingAttributeMarkerResolution {

	private final String label;
	private final String description;
	private final Function<? super IMarker, String> attributeValueFunction;

	/**
	 * Iniitalizes me with everything required to complete the resolution.
	 *
	 * @param problemID
	 *            the problem ID that I fix
	 * @param label
	 *            my label to present to the user
	 * @param description
	 *            my description to present to the user
	 * @param attribute
	 *            the attribute that I add/update
	 * @param attributeValueFunction
	 *            a function that computes the attribute value to set from the problem marker
	 */
	public SimpleMissingAttributeMarkerResolution(int problemID, String label, String description, String attribute,
			Function<? super IMarker, String> attributeValueFunction) {

		super(problemID, attribute);

		this.label = label;
		this.description = description;
		this.attributeValueFunction = attributeValueFunction;
	}

	/**
	 * Iniitalizes me with a static attribute value that does not depend on the details of the problem
	 * marker that I fix.
	 *
	 * @param problemID
	 *            the problem ID that I fix
	 * @param label
	 *            my label to present to the user
	 * @param description
	 *            my description to present to the user
	 * @param attribute
	 *            the attribute that I add/update
	 * @param attributeValue
	 *            the value to set for the attribute in the <tt>plugin.xml</tt>
	 */
	public SimpleMissingAttributeMarkerResolution(int problemID, String label, String description, String attribute,
			String attributeValue) {

		this(problemID, label, description, attribute, Functions.constant(attributeValue));
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	protected String getAttributeValue(IMarker marker) {
		return attributeValueFunction.apply(marker);
	}

}
